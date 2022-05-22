package de.jilocasin.gcodebuilder.gcodes;

import java.util.List;

import de.jilocasin.gcodebuilder.Gcode;
import de.jilocasin.gcodebuilder.GcodeParameter;
import de.jilocasin.gcodebuilder.RequiredGcodeParameter;
import lombok.experimental.SuperBuilder;

/**
 * Set a new target temperature for the heated bed and continue without waiting. The firmware manages heating in the background.
 *
 * @see <a href="https://marlinfw.org/docs/gcode/M140.html">Marlin Firmware</a>
 */
@SuperBuilder
public class M140SetBedTemperatureGcode extends Gcode {

    private final Integer temperature;

    @Override
    public String getCommand() {
        return "M140";
    }

    @Override
    public List<GcodeParameter> getParameters() {
        return List.of(
            new RequiredGcodeParameter("S", temperature));
    }
}

