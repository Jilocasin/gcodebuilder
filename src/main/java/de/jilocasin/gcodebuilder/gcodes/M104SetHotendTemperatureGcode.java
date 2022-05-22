package de.jilocasin.gcodebuilder.gcodes;

import java.util.List;

import de.jilocasin.gcodebuilder.Gcode;
import de.jilocasin.gcodebuilder.GcodeParameter;
import de.jilocasin.gcodebuilder.RequiredGcodeParameter;
import lombok.experimental.SuperBuilder;

/**
 * Set a new target hot end temperature and continue without waiting. The firmware will continue to try to reach and hold the temperature in the background.
 * <p>
 * Use M109 to wait for the hot end to reach the target temperature.
 *
 * @see <a href="https://marlinfw.org/docs/gcode/M104.html">Marlin Firmware</a>
 */
@SuperBuilder
public class M104SetHotendTemperatureGcode extends Gcode {

    private final Integer temperature;

    @Override
    public String getCommand() {
        return "M104";
    }

    @Override
    public List<GcodeParameter> getParameters() {
        return List.of(
            new RequiredGcodeParameter("S", temperature));
    }
}

