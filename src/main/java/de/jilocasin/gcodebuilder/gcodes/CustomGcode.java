package de.jilocasin.gcodebuilder.gcodes;

import de.jilocasin.gcodebuilder.Gcode;
import lombok.experimental.SuperBuilder;

/**
 * Use this class to queue a custom Gcode command.
 */
@SuperBuilder
public class CustomGcode extends Gcode {

    private final String command;

    @Override
    public String getCommand() {
        return command;
    }
}

