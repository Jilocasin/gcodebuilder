package de.jilocasin.gcodebuilder.gcodes;

import java.util.List;

import de.jilocasin.gcodebuilder.Gcode;
import de.jilocasin.gcodebuilder.GcodeParameter;
import de.jilocasin.gcodebuilder.RequiredGcodeParameter;
import lombok.experimental.SuperBuilder;

/**
 * Get and/or set bed leveling state.
 *
 * @see <a href="https://marlinfw.org/docs/gcode/M420.html">Marlin Firmware</a>
 */
@SuperBuilder
public class M420BedLevelingStateGcode extends Gcode {

    private Boolean enabled;

    @Override
    public String getCommand() {
        return "M420";
    }

    @Override
    public List<GcodeParameter> getParameters() {
        return List.of(
            new RequiredGcodeParameter("S", enabled));
    }
}

