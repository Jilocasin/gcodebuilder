package de.jilocasin.gcodebuilder;

import static lombok.AccessLevel.PRIVATE;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarlinGcodeBuilderStatus {

    private final MarlinGcodeBuilderConfiguration config;

    private double currentX = 0;

    private double currentY = 0;

    private double currentZ = 0;

    private double currentSpeed = 0;

    private double lineWidth = 0;

    private double lineHeight = 0;

    private boolean isRectracted = false;

    @Getter(PRIVATE)
    @Setter(PRIVATE)
    private BigDecimal extrusion = new BigDecimal(0);

    public MarlinGcodeBuilderStatus(MarlinGcodeBuilderConfiguration config) {
        this.config = config;
        currentSpeed = config.getPrintSpeed();
        lineWidth = config.getInitialLineWidth();
        lineHeight = config.getInitialLayerHeight();
    }

    /**
     * Builds the next extrusion value (in mm) based on previously performed extrusions.
     * <p>
     * When in relative extrusion mode, the internal value is reset after calling this method.
     *
     * @return
     */
    public double buildExtrusionValue() {
        var result = extrusion.doubleValue();

        if (!config.isUseAbsoluteExtrusion()) {
            extrusion = new BigDecimal(0);
        }

        return result;
    }

    public void queueFilamentLengthExtrusion(double relativeExtrusion) {
        extrusion = extrusion.add(new BigDecimal(relativeExtrusion));
    }

    public void queueLineExtrusion(double lineLength) {
        var requiredSqMm = lineLength * lineHeight * lineWidth;
        var filamentSqMmPerMm = Math.PI * Math.pow(config.getFilamentDiameter() / 2, 2);

        queueFilamentLengthExtrusion(requiredSqMm / filamentSqMmPerMm);
    }
}
