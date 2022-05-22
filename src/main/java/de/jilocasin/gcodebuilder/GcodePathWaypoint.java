package de.jilocasin.gcodebuilder;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class GcodePathWaypoint {

    private Double endX;

    private Double endY;

    private Double endZ;

    private Double speed;

    private Double lineWidth;

    private Double lineHeight;

    @Builder.Default
    private boolean extrude = false;

    @Builder.Default
    private boolean retracted = false;
}
