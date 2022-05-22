package de.jilocasin.gcodebuilder.gcodes;

import de.jilocasin.gcodebuilder.Gcode;
import lombok.experimental.SuperBuilder;

/**
 * Set relative position mode. In this mode all coordinates are interpreted as relative to the last position. This includes the extruder position unless overridden by M82.
 *
 * @see <a href="https://marlinfw.org/docs/gcode/G091.html">Marlin Firmware</a>
 */
@SuperBuilder
public class G91RelativePositioningGcode extends Gcode {

    @Override
    public String getCommand() {
        return "G91";
    }
}

