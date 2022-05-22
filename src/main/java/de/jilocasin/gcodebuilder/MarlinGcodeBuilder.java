package de.jilocasin.gcodebuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.jilocasin.gcodebuilder.gcodes.CommentGcode;
import de.jilocasin.gcodebuilder.gcodes.G0LinearNonExtrusionMoveGcode;
import de.jilocasin.gcodebuilder.gcodes.G10RetractGcode;
import de.jilocasin.gcodebuilder.gcodes.G11RecoverGcode;
import de.jilocasin.gcodebuilder.gcodes.G1LinearExtrusionMoveGcode;
import de.jilocasin.gcodebuilder.gcodes.G21MillimeterUnitsGcode;
import de.jilocasin.gcodebuilder.gcodes.G28AutoHomeGcode;
import de.jilocasin.gcodebuilder.gcodes.G90AbsolutePositioningGcode;
import de.jilocasin.gcodebuilder.gcodes.G92SetPositionGcode;
import de.jilocasin.gcodebuilder.gcodes.M106SetFanSpeedGcode;
import de.jilocasin.gcodebuilder.gcodes.M109WaitForHotendTemperatureGcode;
import de.jilocasin.gcodebuilder.gcodes.M117SetLcdMessageGcode;
import de.jilocasin.gcodebuilder.gcodes.M190WaitForBedTemperatureGcode;
import de.jilocasin.gcodebuilder.gcodes.M82AbsoluteExtrusionGcode;
import lombok.Getter;

public class MarlinGcodeBuilder {

    public static final String NEWLINE = "\n";

    public static final String WHITESPACE = " ";

    public static final String COMMENT_PREFIX = WHITESPACE + ";" + WHITESPACE;

    public static final String COMMENT_PREFIX_STANDALONE = ";" + WHITESPACE;

    @Getter
    private final MarlinGcodeBuilderConfiguration config;

    @Getter
    private final MarlinGcodeBuilderStatus status;

    @Getter
    private final List<Gcode> gcodes = new ArrayList<>();

    public MarlinGcodeBuilder(MarlinGcodeBuilderConfiguration config) {
        this.config = config;
        status = new MarlinGcodeBuilderStatus(config);
    }

    /**
     * Adds the given Gcode to the list.
     */
    public void addGcode(Gcode gcode) {
        gcodes.add(gcode);
    }

    /**
     * Adds all given Gcodes to the list.
     */
    public void addAllGcodes(Collection<Gcode> gcodes) {
        this.gcodes.addAll(gcodes);
    }

    /**
     * Adds an comment line with no Gcode command to the list.
     */
    public void addCommentLine(String comment) {
        addGcode(CommentGcode.builder().comment(comment).build());
    }

    /**
     * Adds an LCD message Gcode to the list.
     */
    public void addLcdMessageLine(String message) {
        addGcode(M117SetLcdMessageGcode.builder().message(message).build());
    }

    /**
     * Adds common init Gcodes to the builder. This includes:
     * <ol>
     * <li>Heat up bed until given temperature is reached
     * <li>Heat up hotend until given temperature is reached
     * <li>Home all axes
     * <li>Set units to millimeters
     * <li>Set extrusion and coordinates to absolute mode
     * <li>Reset extruder position to 0
     * <li>Reset fan speed to 0
     * </ol>
     */
    public void addPrintJobInitialisation(int hotendTemperature, int bedTemperature) {
        addLcdMessageLine("Heating bed to " + bedTemperature + "°C");
        addGcode(M190WaitForBedTemperatureGcode.builder().targetTemperature(bedTemperature).build());

        addLcdMessageLine("Heating hotend to " + hotendTemperature + "°C");
        addGcode(M109WaitForHotendTemperatureGcode.builder().targetTemperature(hotendTemperature).build());

        addLcdMessageLine("Homing");
        addGcode(G28AutoHomeGcode.builder().build());

        addGcode(G21MillimeterUnitsGcode.builder().build());
        addGcode(M82AbsoluteExtrusionGcode.builder().build());
        addGcode(G90AbsolutePositioningGcode.builder().build());
        addGcode(G92SetPositionGcode.builder().extruder(0.0).build());
        addGcode(M106SetFanSpeedGcode.builder().fanSpeed(0).build());
    }

    public void addRetractBegin() {
        addGcode(buildRetractBeginGcode(status));
    }

    public void addRetractRecover() {
        addGcode(buildRetractRecoverGcode(status));
    }

    public static Gcode buildRetractBeginGcode(MarlinGcodeBuilderStatus status) {
        var config = status.getConfig();

        status.setRectracted(true);

        if (config.isUseFirmwareRetract()) {
            return G10RetractGcode.builder().build();
        }

        // Non-firmware retraction via extrusion.

        status.queueFilamentLengthExtrusion(-config.getRetractLength());

        return G1LinearExtrusionMoveGcode.builder()
                .extrusion(status.buildExtrusionValue())
                .feedrate(calculateFeedrate(config.getRetractSpeed()))
                .build();
    }

    public static Gcode buildRetractRecoverGcode(MarlinGcodeBuilderStatus status) {
        var config = status.getConfig();

        status.setRectracted(false);

        if (config.isUseFirmwareRetract()) {
            return G11RecoverGcode.builder().build();
        }

        // Non-firmware retraction via extrusion.

        status.queueFilamentLengthExtrusion(config.getRetractLength());

        return G1LinearExtrusionMoveGcode.builder()
                .extrusion(status.buildExtrusionValue())
                .feedrate(calculateFeedrate(config.getRetractSpeed()))
                .build();
    }

    /**
     * Adds common Gcode to clean the nozzle before starting a print job.
     * <p>
     * A linear line is printed from the home position to the positive X axis.
     * <p>
     * Note that common initialisation (like hotend temperature) must have been finished before this.
     */
    public void addNozzlePrimerLine(int lineLength) {
        addLcdMessageLine("Print nozzle primer");

        addGcode(G0LinearNonExtrusionMoveGcode.builder()
            .x(0.0)
            .y(0.0)
            .z(0.3)
            .build());

        status.setLineWidth(0.8);
        status.setLineHeight(0.3);
        status.queueLineExtrusion(lineLength);

        addGcode(G1LinearExtrusionMoveGcode.builder()
            .x((double) lineLength)
            .extrusion(status.buildExtrusionValue())
            .feedrate(calculateFeedrate(10))
            .build());
    }

    /**
     * Converts a commonly used "unit/second" value to the "unit/minute" feedrate used internally.
     */
    public static int calculateFeedrate(double unitPerSecond) {
        return (int) Math.round(unitPerSecond * 60.0);
    }


    public String buildGcodeLines() {
        return Stream.concat(gcodes.stream(), config.getAfterPrintGcodes().stream())
                .map(this::mapGcode)
                .collect(Collectors.joining(NEWLINE));
    }

    private String mapGcode(Gcode gcode) {
        if (config.isUseVerboseGcodeDescription() && !gcode.omitVerboseDescription()) {
            return gcode.toString() + " ; " + buildVerboseDescription(gcode);
        }

        return gcode.toString();
    }

    private String buildVerboseDescription(Gcode gcode) {
        var className = gcode.getClass().getSimpleName();
        var split = className.split("(\\d+)");

        if (split.length > 1) {
            return split[1].replace("Gcode", "");
        }

        return className.replace("Gcode", "");
    }
}
