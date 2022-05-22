package de.jilocasin.gcodebuilder;

import java.util.List;

import de.jilocasin.gcodebuilder.gcodes.G0LinearNonExtrusionMoveGcode;
import de.jilocasin.gcodebuilder.gcodes.G1LinearExtrusionMoveGcode;
import de.jilocasin.gcodebuilder.gcodes.G90AbsolutePositioningGcode;
import de.jilocasin.gcodebuilder.gcodes.G91RelativePositioningGcode;
import de.jilocasin.gcodebuilder.gcodes.M104SetHotendTemperatureGcode;
import de.jilocasin.gcodebuilder.gcodes.M107FanOffGcode;
import de.jilocasin.gcodebuilder.gcodes.M140SetBedTemperatureGcode;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MarlinGcodeBuilderConfiguration {

    private static final double DEFAULT_FILAMENT_DIAMETER = 1.75;

    private static final int DEFAULT_RETRACT_SPEED = 50;

    private static final int DEFAULT_PRINT_SPEED = 50;

    private static final int DEFAULT_TRAVEL_SPEED = 150;

    private static final double DEFAULT_RETRACT_LENGTH = 5;

    private static final double DEFAULT_INITIAL_LINE_WIDTH = 0.4;

    private static final double DEFAULT_INITIAL_LAYER_HEIGHT = 0.2;

    @Builder.Default
    private boolean useVerboseGcodeDescription = false;

    @Builder.Default
    private boolean useAbsoluteExtrusion = true;

    @Builder.Default
    private boolean useFirmwareRetract = false;

    /**
     * Unit: mm
     */
    @Builder.Default
    private double filamentDiameter = DEFAULT_FILAMENT_DIAMETER;

    /**
     * Unit: mm
     */
    @Builder.Default
    private double initialLineWidth = DEFAULT_INITIAL_LINE_WIDTH;

    /**
     * Unit: mm
     */
    @Builder.Default
    private double initialLayerHeight = DEFAULT_INITIAL_LAYER_HEIGHT;

    /**
     * Unit: mm/s
     */
    @Builder.Default
    private double printSpeed = DEFAULT_PRINT_SPEED;

    /**
     * Unit: mm/s
     */
    @Builder.Default
    private double travelSpeed = DEFAULT_TRAVEL_SPEED;

    /**
     * Unit: mm
     */
    @Builder.Default
    private double retractLength = DEFAULT_RETRACT_LENGTH;

    /**
     * Unit: mm/s
     */
    @Builder.Default
    private double retractSpeed = DEFAULT_RETRACT_SPEED;

    @Builder.Default
    private List<Gcode> afterPrintGcodes = List.of(
        M140SetBedTemperatureGcode.builder().temperature(0).build(),
        M104SetHotendTemperatureGcode.builder().temperature(0).build(),
        M107FanOffGcode.builder().build(),
        G91RelativePositioningGcode.builder().build(),
        G1LinearExtrusionMoveGcode.builder().z(10.0).extrusion(-5.0).feedrate(MarlinGcodeBuilder.calculateFeedrate(DEFAULT_RETRACT_SPEED)).build(),
        G90AbsolutePositioningGcode.builder().build(),
        G0LinearNonExtrusionMoveGcode.builder().x(10.0).y(150.0).feedrate(MarlinGcodeBuilder.calculateFeedrate(DEFAULT_TRAVEL_SPEED)).build());
}
