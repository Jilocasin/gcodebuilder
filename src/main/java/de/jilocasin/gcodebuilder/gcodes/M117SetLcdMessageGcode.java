package de.jilocasin.gcodebuilder.gcodes;

import java.util.List;

import de.jilocasin.gcodebuilder.Gcode;
import de.jilocasin.gcodebuilder.GcodeParameter;
import de.jilocasin.gcodebuilder.RequiredGcodeParameter;
import lombok.experimental.SuperBuilder;

/**
 * Set the status line message on the LCD.
 *
 * @see <a href="https://marlinfw.org/docs/gcode/M117.html">Marlin Firmware</a>
 */
@SuperBuilder
public class M117SetLcdMessageGcode extends Gcode {

    private final String message;

    @Override
    public String getCommand() {
        return "M117";
    }

    @Override
    public List<GcodeParameter> getParameters() {
        return List.of(
            new RequiredGcodeParameter("", message));
    }
}

