package de.jilocasin.gcodebuilder.gcodes;

import de.jilocasin.gcodebuilder.Gcode;
import lombok.experimental.SuperBuilder;

/**
 * This command is used to override G91 and put the E axis into absolute mode independent of the other axes.
 *
 * @see <a href="https://marlinfw.org/docs/gcode/M082.html">Marlin Firmware</a>
 */
@SuperBuilder
public class M82AbsoluteExtrusionGcode extends Gcode {

    @Override
    public String getCommand() {
        return "M82";
    }
}

