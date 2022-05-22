package de.jilocasin.gcodebuilder.gcodes;

import de.jilocasin.gcodebuilder.Gcode;
import lombok.experimental.SuperBuilder;

/**
 * Unretract (i.e., recover, prime) the filament according to settings of M208.
 * <p>
 * Multiple consecutive G11 or G11 S1 commands without a corresponding G10 or G10 S1 will be ignored.
 * <p>
 * “Performs two moves: An optional Z lower at the maximum Z feedrate (travel acceleration), and a recovery move at the recover feedrate (retract acceleration).”
 *
 * @see <a href="https://marlinfw.org/docs/gcode/G011.html">Marlin Firmware</a>
 */
@SuperBuilder
public class G11RecoverGcode extends Gcode {

    @Override
    public String getCommand() {
        return "G11";
    }
}

