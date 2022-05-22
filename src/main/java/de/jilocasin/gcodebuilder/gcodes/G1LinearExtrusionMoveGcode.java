package de.jilocasin.gcodebuilder.gcodes;

import java.util.List;

import de.jilocasin.gcodebuilder.GcodeParameter;
import de.jilocasin.gcodebuilder.OptionalGcodeParameter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class G1LinearExtrusionMoveGcode extends G0LinearNonExtrusionMoveGcode {

    private final Double extrusion;

    @Override
    public String getCommand() {
        return "G1";
    }

    @Override
    public List<GcodeParameter> getParameters() {
        return List.of(
            new OptionalGcodeParameter("X", x),
            new OptionalGcodeParameter("Y", y),
            new OptionalGcodeParameter("Z", z),
            new OptionalGcodeParameter("E", extrusion),
            new OptionalGcodeParameter("F", feedrate));
    }
}