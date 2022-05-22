package de.jilocasin.gcodebuilder.gcodes;

import de.jilocasin.gcodebuilder.Gcode;
import lombok.experimental.SuperBuilder;

/**
 * Request a temperature report to be sent to the host as soon as possible.
 *
 * @see <a href="https://marlinfw.org/docs/gcode/M105.html">Marlin Firmware</a>
 */
@SuperBuilder
public class M105ReportTemperaturesGcode extends Gcode {

    @Override
    public String getCommand() {
        return "M105";
    }

}

