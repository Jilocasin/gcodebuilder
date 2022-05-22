package de.jilocasin.gcodebuilder.gcodes;

import de.jilocasin.gcodebuilder.Gcode;
import lombok.experimental.SuperBuilder;

/**
 * When you first start up your machine it has no idea where the toolhead is positioned, so Marlin needs to use a procedure called “homing” to establish a known position. To do this it moves each axis
 * towards one end of its track until it triggers a switch, commonly called an “endstop.” Marlin knows where the endstops are, so once all the endstops have been triggered the position is known.
 *
 * @see <a href="https://marlinfw.org/docs/gcode/G028.html">Marlin Firmware</a>
 */
@SuperBuilder
public class G28AutoHomeGcode extends Gcode {

    @Override
    public String getCommand() {
        return "G28";
    }
}

