package de.jilocasin.gcodebuilder.gcodes;

import java.util.List;

import de.jilocasin.gcodebuilder.Gcode;
import de.jilocasin.gcodebuilder.GcodeParameter;
import de.jilocasin.gcodebuilder.OptionalGcodeParameter;
import de.jilocasin.gcodebuilder.RequiredGcodeParameter;
import lombok.experimental.SuperBuilder;

/**
 * Turn on one of the fans and set its speed. If no fan index is given, the print cooling fan is selected. The fan speed applies to the next block added to the planner, so it will not take effect
 * until previous moves in the planner are done. Under manual control with an idle machine, M106 will change the fan speed immediately.
 *
 * @see <a href="https://marlinfw.org/docs/gcode/M106.html">Marlin Firmware</a>
 */
@SuperBuilder
public class M106SetFanSpeedGcode extends Gcode {

    /**
     * Speed, from 0 to 255.
     * <p>
     * 255 provides 100% duty cycle; 128 produces 50%.
     */
    private final Integer fanSpeed;

    private final Integer fanIndex;

    @Override
    public String getCommand() {
        return "M106";
    }

    @Override
    public List<GcodeParameter> getParameters() {
        return List.of(
            new RequiredGcodeParameter("S", fanSpeed),
            new OptionalGcodeParameter("P", fanIndex));
    }
}

