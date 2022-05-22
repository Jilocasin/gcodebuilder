package de.jilocasin.gcodebuilder.gcodes;

import java.util.List;

import de.jilocasin.gcodebuilder.Gcode;
import de.jilocasin.gcodebuilder.GcodeParameter;
import de.jilocasin.gcodebuilder.OptionalGcodeParameter;
import lombok.experimental.SuperBuilder;

/**
 * This command optionally sets a new target hot end temperature and waits for the target temperature to be reached before proceeding. If the temperature is set with S then M109 waits only when
 * heating. If the temperature is set with R then M109 will also wait for the temperature to go down.
 *
 * @see <a href="https://marlinfw.org/docs/gcode/M109.html">Marlin Firmware</a>
 */
@SuperBuilder
public class M109WaitForHotendTemperatureGcode extends Gcode {

    private final Integer targetTemperature;

    private final Integer targetTemperatureHeatingOnly;

    @Override
    public String getCommand() {
        return "M109";
    }

    @Override
    public List<GcodeParameter> getParameters() {
        return List.of(
            new OptionalGcodeParameter("R", targetTemperature),
            new OptionalGcodeParameter("S", targetTemperatureHeatingOnly));
    }
}

