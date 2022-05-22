package de.jilocasin.gcodebuilder.gcodes;

import de.jilocasin.gcodebuilder.Gcode;
import lombok.experimental.SuperBuilder;

/**
 * In absolute mode all coordinates given in G-code are interpreted as positions in the logical coordinate space. This includes the extruder position unless overridden by M83.
 *
 * @see <a href="https://marlinfw.org/docs/gcode/G090.html">Marlin Firmware</a>
 */
@SuperBuilder
public class G90AbsolutePositioningGcode extends Gcode {

    @Override
    public String getCommand() {
        return "G90";
    }
}

