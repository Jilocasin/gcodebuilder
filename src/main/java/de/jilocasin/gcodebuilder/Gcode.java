package de.jilocasin.gcodebuilder;

import static de.jilocasin.gcodebuilder.MarlinGcodeBuilder.COMMENT_PREFIX;
import static de.jilocasin.gcodebuilder.MarlinGcodeBuilder.COMMENT_PREFIX_STANDALONE;
import static de.jilocasin.gcodebuilder.MarlinGcodeBuilder.WHITESPACE;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class Gcode {

    private String comment;

    /**
     * Requests the command string from this Gcode.
     */
    public abstract String getCommand();

    /**
     * Requests the list of parameters from this Gcode.
     */
    public List<GcodeParameter> getParameters() {
        return Collections.emptyList();
    }

    public boolean omitVerboseDescription() {
        return false;
    }

    @Override
    public String toString() {
        return getCommand() + buildParameters() + buildComment();
    }

    private String buildParameters() {
        var parameters = getParameters();
        var invalidParameters = parameters.stream().filter(p -> !p.isValid()).collect(Collectors.toUnmodifiableList());

        if (!invalidParameters.isEmpty()) {
            var invalidParameterNames = invalidParameters.stream().map(GcodeParameter::getKey).collect(Collectors.toUnmodifiableList());
            throw new IllegalStateException("Some parameters for " + getCommand() + " command are invalid: " + invalidParameterNames);
        }

        var parameterStrings = parameters
                .stream()
                .filter(GcodeParameter::isIncluded)
                .map(GcodeParameter::toString)
                .collect(Collectors.toUnmodifiableList());

        if (parameterStrings.isEmpty()) {
            return "";
        }

        return WHITESPACE + String.join(WHITESPACE, parameterStrings);
    }

    private String buildComment() {
        if (comment == null || comment.isEmpty()) {
            return "";
        }

        if (getCommand() != null && !getCommand().isBlank()) {
            return COMMENT_PREFIX + comment;
        }

        return COMMENT_PREFIX_STANDALONE + comment;
    }
}
