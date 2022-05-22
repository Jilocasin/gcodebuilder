package de.jilocasin.gcodebuilder.gcodes;

import java.util.List;

import de.jilocasin.gcodebuilder.Gcode;
import de.jilocasin.gcodebuilder.GcodeParameter;
import de.jilocasin.gcodebuilder.RequiredGcodeParameter;
import lombok.experimental.SuperBuilder;

/**
 * This command sets and/or reports the Linear Advance K factors.
 * <p>
 * Setting the K factor to 0 disables Linear Advance.
 *
 * @see <a href="https://marlinfw.org/docs/gcode/M900.html">Marlin Firmware</a>
 */
@SuperBuilder
public class M900LinearAdvanceFactorGcode extends Gcode {

    private Double kValue;

    @Override
    public String getCommand() {
        return "M900";
    }

    @Override
    public List<GcodeParameter> getParameters() {
        return List.of(
            new RequiredGcodeParameter("K", kValue));
    }
}

