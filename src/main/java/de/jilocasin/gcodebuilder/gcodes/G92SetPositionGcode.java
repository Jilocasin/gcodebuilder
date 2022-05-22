package de.jilocasin.gcodebuilder.gcodes;

import java.util.List;

import de.jilocasin.gcodebuilder.Gcode;
import de.jilocasin.gcodebuilder.GcodeParameter;
import de.jilocasin.gcodebuilder.OptionalGcodeParameter;
import lombok.experimental.SuperBuilder;

/**
 * Set the current position to the values specified.
 *
 * @see <a href="https://marlinfw.org/docs/gcode/G092.html">Marlin Firmware</a>
 */
@SuperBuilder
public class G92SetPositionGcode extends Gcode {

    private final Double x;

    private final Double y;

    private final Double z;

    private final Double extruder;

    @Override
    public String getCommand() {
        return "G92";
    }

    @Override
    public List<GcodeParameter> getParameters() {
        return List.of(
            new OptionalGcodeParameter("X", x),
            new OptionalGcodeParameter("Y", y),
            new OptionalGcodeParameter("Z", z),
            new OptionalGcodeParameter("E", extruder));
    }
}

