package de.jilocasin.gcodebuilder.gcodes;

import java.util.List;

import de.jilocasin.gcodebuilder.Gcode;
import de.jilocasin.gcodebuilder.GcodeParameter;
import de.jilocasin.gcodebuilder.OptionalGcodeParameter;
import lombok.experimental.SuperBuilder;

/**
 * Turn off one of the fans. If no fan index is given, the print cooling fan.
 *
 * @see <a href="https://marlinfw.org/docs/gcode/M107.html">Marlin Firmware</a>
 */
@SuperBuilder
public class M107FanOffGcode extends Gcode {

    private final Integer fanIndex;

    @Override
    public String getCommand() {
        return "M107";
    }

    @Override
    public List<GcodeParameter> getParameters() {
        return List.of(
            new OptionalGcodeParameter("P", fanIndex));
    }
}

