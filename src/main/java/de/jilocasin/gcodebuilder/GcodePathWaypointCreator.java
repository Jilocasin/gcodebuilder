package de.jilocasin.gcodebuilder;

import de.jilocasin.gcodebuilder.GcodePathWaypoint.GcodePathWaypointBuilder;

public class GcodePathWaypointCreator {

    private MarlinGcodeBuilderConfiguration config;

    public GcodePathWaypointCreator(MarlinGcodeBuilderConfiguration config) {
        this.config = config;
    }

    public GcodePathWaypointBuilder<?, ?> createTravelMove(double x, double y) {
        return GcodePathWaypoint.builder().endX(x).endY(y).speed(config.getTravelSpeed());
    }

    public GcodePathWaypointBuilder<?, ?> createRetractedTravelMove(double x, double y) {
        return GcodePathWaypoint.builder().endX(x).endY(y).speed(config.getTravelSpeed()).retracted(true);
    }

    public GcodePathWaypointBuilder<?, ?> createPrintMove(double x, double y) {
        return GcodePathWaypoint.builder().endX(x).endY(y).speed(config.getPrintSpeed()).extrude(true);
    }
}
