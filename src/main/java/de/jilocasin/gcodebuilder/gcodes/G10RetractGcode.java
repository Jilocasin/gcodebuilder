package de.jilocasin.gcodebuilder.gcodes;

import de.jilocasin.gcodebuilder.Gcode;
import lombok.experimental.SuperBuilder;

/**
 * Retract the filament according to settings of M207.
 * <p>
 * Firmware retraction allows you to tune retraction at the machine level and can significantly reduce the size of G-code files.
 * <p>
 * Multiple consecutive G10 or G10 S1 commands without a corresponding G11 or G11 S1 will be ignored.
 * <p>
 * Performs two moves: a retract move at the retract feedrate/acceleration, and an optional Z lift at the maximum Z feedrate (travel acceleration).
 *
 * @see <a href="https://marlinfw.org/docs/gcode/G010.html">Marlin Firmware</a>
 */
@SuperBuilder
public class G10RetractGcode extends Gcode {

    @Override
    public String getCommand() {
        return "G10";
    }
}

