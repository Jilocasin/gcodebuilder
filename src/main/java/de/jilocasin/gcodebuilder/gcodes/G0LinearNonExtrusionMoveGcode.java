package de.jilocasin.gcodebuilder.gcodes;

import java.util.List;

import de.jilocasin.gcodebuilder.Gcode;
import de.jilocasin.gcodebuilder.GcodeParameter;
import de.jilocasin.gcodebuilder.OptionalGcodeParameter;
import lombok.experimental.SuperBuilder;

/**
 * The G0 and G1 commands add a linear move to the queue to be performed after all previous moves are completed. These commands yield control back to the command parser as soon as the move is queued,
 * but they may delay the command parser while awaiting a slot in the queue.
 * <p>
 * A linear move traces a straight line from one point to another, ensuring that the specified axes will arrive simultaneously at the given coordinates (by linear interpolation). The speed may change
 * over time following an acceleration curve, according to the acceleration and jerk settings of the given axes.
 * <p>
 * A command like G1 F1000 sets the feedrate for all subsequent moves.
 * <p>
 * By convention, most G-code generators use G0 for non-extrusion movements (those without the E axis) and G1 for moves that include extrusion. This is meant to allow a kinematic system to,
 * optionally, do a more rapid uninterpolated movement requiring much less calculation.
 *
 * @see <a href="https://marlinfw.org/docs/gcode/G000-G001.html">Marlin Firmware</a>
 */
@SuperBuilder
public class G0LinearNonExtrusionMoveGcode extends Gcode {

    protected final Double x;

    protected final Double y;

    protected final Double z;

    protected final Integer feedrate;

    @Override
    public String getCommand() {
        return "G0";
    }

    @Override
    public List<GcodeParameter> getParameters() {
        return List.of(
            new OptionalGcodeParameter("X", x),
            new OptionalGcodeParameter("Y", y),
            new OptionalGcodeParameter("Z", z),
            new OptionalGcodeParameter("F", feedrate));
    }
}
