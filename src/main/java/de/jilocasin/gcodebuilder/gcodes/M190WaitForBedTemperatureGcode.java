package de.jilocasin.gcodebuilder.gcodes;

import java.util.List;

import de.jilocasin.gcodebuilder.Gcode;
import de.jilocasin.gcodebuilder.GcodeParameter;
import de.jilocasin.gcodebuilder.OptionalGcodeParameter;
import lombok.experimental.SuperBuilder;

/**
 * This command optionally sets a new target temperature for the heated bed and waits for the target temperature to be reached before proceeding. If the temperature is set with S then it waits only
 * when heating.
 *
 * @see <a href="https://marlinfw.org/docs/gcode/M190.html">Marlin Firmware</a>
 */
@SuperBuilder
public class M190WaitForBedTemperatureGcode extends Gcode {

    private final Integer targetTemperature;

    private final Integer targetTemperatureHeatingOnly;

    @Override
    public String getCommand() {
        return "M190";
    }

    @Override
    public List<GcodeParameter> getParameters() {
        return List.of(
            new OptionalGcodeParameter("R", targetTemperature),
            new OptionalGcodeParameter("S", targetTemperatureHeatingOnly));
    }
}

