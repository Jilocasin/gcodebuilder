package de.jilocasin.gcodebuilder.gcodes;

import java.util.List;

import de.jilocasin.gcodebuilder.Gcode;
import de.jilocasin.gcodebuilder.GcodeParameter;
import de.jilocasin.gcodebuilder.OptionalGcodeParameter;
import lombok.experimental.SuperBuilder;

/**
 * Dwell pauses the command queue and waits for a period of time.
 *
 * @see <a href="https://marlinfw.org/docs/gcode/G004.html">Marlin Firmware</a>
 */
@SuperBuilder
public class G4DwellGcode extends Gcode {

    private final Double milliseconds;

    private final Double seconds;

    @Override
    public String getCommand() {
        return "G4";
    }

    @Override
    public List<GcodeParameter> getParameters() {
        return List.of(
            new OptionalGcodeParameter("P", milliseconds),
            new OptionalGcodeParameter("S", seconds));
    }
}

