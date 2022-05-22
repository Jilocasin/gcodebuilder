package de.jilocasin.gcodebuilder.gcodes;

import de.jilocasin.gcodebuilder.Gcode;
import lombok.experimental.SuperBuilder;

/**
 * Set units to millimeters. In this mode, all positions, offsets, rates, accelerations, etc., specified in G-code parameters are interpreted as millimeters.
 *
 * @see <a href="https://marlinfw.org/docs/gcode/G021.html">Marlin Firmware</a>
 */
@SuperBuilder
public class G21MillimeterUnitsGcode extends Gcode {

    @Override
    public String getCommand() {
        return "G21";
    }
}

