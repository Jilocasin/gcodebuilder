package de.jilocasin.gcodebuilder;

import static de.jilocasin.gcodebuilder.MarlinGcodeBuilder.calculateFeedrate;

import de.jilocasin.gcodebuilder.GcodePathWaypoint.GcodePathWaypointBuilder;
import de.jilocasin.gcodebuilder.gcodes.G0LinearNonExtrusionMoveGcode;
import de.jilocasin.gcodebuilder.gcodes.G1LinearExtrusionMoveGcode;

public class GcodePathPrinter {

    private final MarlinGcodeBuilder builder;

    private final MarlinGcodeBuilderStatus status;

    private final GcodePathWaypointCreator waypointCreator;

    public GcodePathPrinter(MarlinGcodeBuilder builder) {
        this.builder = builder;
        status = builder.getStatus();
        waypointCreator = new GcodePathWaypointCreator(builder.getConfig());
    }

    public GcodePathWaypointCreator getWaypointCreator() {
        return waypointCreator;
    }

    public GcodePathPrinter addWaypoint(GcodePathWaypoint waypoint) {
        processWaypoint(waypoint);
        return this;
    }

    public GcodePathPrinter addWaypoint(GcodePathWaypointBuilder<?, ?> waypointBuilder) {
        processWaypoint(waypointBuilder.build());
        return this;
    }

    public void processWaypoint(GcodePathWaypoint waypoint) {
        var gcodeIncludeNewFeedrate = false;

        var deltaX = waypoint.getEndX() != null ? (waypoint.getEndX() - status.getCurrentX()) : 0;
        var deltaY = waypoint.getEndY() != null ? (waypoint.getEndY() - status.getCurrentY()) : 0;
        var deltaZ = waypoint.getEndZ() != null ? (waypoint.getEndZ() - status.getCurrentZ()) : 0;

        var distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2) + Math.pow(deltaZ, 2));

        // Only retract when the nextwaypoint covers any distance.

        if (distance > 0) {
            if (waypoint.isRetracted() && !status.isRectracted()) {
                // Perform retraction. Also apply previous feedrate after the command if using non-firmware retract.

                builder.addGcode(MarlinGcodeBuilder.buildRetractBeginGcode(status));

                if (!status.getConfig().isUseFirmwareRetract()) {
                    gcodeIncludeNewFeedrate = true;
                }
            }

            if (!waypoint.isRetracted() && status.isRectracted()) {
                // Restore previous retraction. Also apply previous feedrate after the command if using non-firmware retract.

                builder.addGcode(MarlinGcodeBuilder.buildRetractRecoverGcode(status));

                if (!status.getConfig().isUseFirmwareRetract()) {
                    gcodeIncludeNewFeedrate = true;
                }
            }
        }

        if (waypoint.isExtrude()) {
            if (waypoint.getSpeed() != null) {
                if (waypoint.getSpeed() != status.getCurrentSpeed()) {
                    gcodeIncludeNewFeedrate = true;
                }
                status.setCurrentSpeed(waypoint.getSpeed());
            }

            if (waypoint.getLineWidth() != null) {
                status.setLineWidth(waypoint.getLineWidth());
            }

            if (waypoint.getLineHeight() != null) {
                status.setLineHeight(waypoint.getLineHeight());
            }

            status.queueLineExtrusion(distance);

            builder.addGcode(G1LinearExtrusionMoveGcode.builder()
                .x(deltaX != 0 ? waypoint.getEndX() : null)
                .y(deltaY != 0 ? waypoint.getEndY() : null)
                .z(deltaZ != 0 ? waypoint.getEndZ() : null)
                .feedrate(gcodeIncludeNewFeedrate ? calculateFeedrate(status.getCurrentSpeed()) : null)
                .extrusion(status.buildExtrusionValue())
                .build());
        } else if (distance > 0) {
            builder.addGcode(G0LinearNonExtrusionMoveGcode.builder()
                .x(deltaX != 0 ? waypoint.getEndX() : null)
                .y(deltaY != 0 ? waypoint.getEndY() : null)
                .z(deltaZ != 0 ? waypoint.getEndZ() : null)
                .feedrate(gcodeIncludeNewFeedrate ? calculateFeedrate(status.getCurrentSpeed()) : null)
                .build());
        }

        if (waypoint.getEndX() != null) {
            status.setCurrentX(waypoint.getEndX());
        }

        if (waypoint.getEndY() != null) {
            status.setCurrentY(waypoint.getEndY());
        }

        if (waypoint.getEndZ() != null) {
            status.setCurrentZ(waypoint.getEndZ());
        }

        if (waypoint.getSpeed() != null) {
            status.setCurrentSpeed(waypoint.getSpeed());
        }
    }
}
