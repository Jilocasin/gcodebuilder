package de.jilocasin.gcodebuilder.gcodes;

import de.jilocasin.gcodebuilder.Gcode;
import lombok.experimental.SuperBuilder;

/**
 * Use this class to queue a custom comment Gcode line.
 */
@SuperBuilder
public class CommentGcode extends Gcode {

    @Override
    public String getCommand() {
        return "";
    }

    @Override
    public boolean omitVerboseDescription() {
        return true;
    }
}

