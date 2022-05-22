package de.jilocasin.gcodebuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import de.jilocasin.gcodebuilder.gcodes.G0LinearNonExtrusionMoveGcode;
import de.jilocasin.gcodebuilder.gcodes.M420BedLevelingStateGcode;
import de.jilocasin.gcodebuilder.gcodes.M900LinearAdvanceFactorGcode;

class Runner {

    private static final double CORNER_DISTANCE = 80;

    private static final double SHAPE_DISTANCE = 10;

    private static final double SHAPE_LENGTH = 50.0;

    private static final double LAYER_HEIGHT = 0.2;

    private static final int LAYER_COUNT = 10;

    private static final double K_STEPS = 0.1;

    private static final int K_COUNT = 10;

    @Test
    void test() {
        // Use default config.

        var config = MarlinGcodeBuilderConfiguration.builder().build();
        var builder = new MarlinGcodeBuilder(config);

        builder.addPrintJobInitialisation(220, 60);
        builder.addGcode(M420BedLevelingStateGcode.builder().enabled(true).build());
        builder.addNozzlePrimerLine(150);

        builder.addCommentLine("Starting k-value corner calibration");
        builder.getStatus().setLineHeight(LAYER_HEIGHT);
        builder.getStatus().setLineWidth(0.4);

        var printer = new GcodePathPrinter(builder);

        for (var layerIndex = 0; layerIndex < LAYER_COUNT; layerIndex++) {
            var z = layerIndex * LAYER_HEIGHT + LAYER_HEIGHT;
            builder.addGcode(G0LinearNonExtrusionMoveGcode.builder().z(z).build());

            // Reverse the k printing order for each odd z layer index to avoid long travel moves.

            var kIndices = createKIndicesList(layerIndex);
            var invertOrientation = layerIndex % 2 == 0;

            for (var kTestIndex : kIndices) {
                builder.addGcode(M900LinearAdvanceFactorGcode.builder().kValue(kTestIndex * K_STEPS).build());
                printForwardCalibrationLShape(kTestIndex, printer, config, invertOrientation);
            }
        }

        System.out.println(builder.buildGcodeLines());
    }

    private List<Integer> createKIndicesList(int layerIndex) {
        if (layerIndex % 2 == 0) {
            return IntStream.range(0, K_COUNT)
                .boxed()
                .collect(Collectors.toList());
        }

        return IntStream.range(0, K_COUNT)
                .map(i -> K_COUNT - i - 1)
                .boxed()
                .collect(Collectors.toList());
    }

    void printForwardCalibrationLShape(int kTestIndex, GcodePathPrinter printer, MarlinGcodeBuilderConfiguration config, boolean invertOrientation) {
        var cornerOffset = CORNER_DISTANCE + kTestIndex * SHAPE_DISTANCE;
        var shapePoints = new LinkedList<Entry<Double, Double>>();

        shapePoints.add(Map.entry(5.0, SHAPE_LENGTH));
        shapePoints.add(Map.entry(0.0, SHAPE_LENGTH));
        shapePoints.add(Map.entry(0.0, 0.0));
        shapePoints.add(Map.entry(SHAPE_LENGTH, 0.0));
        shapePoints.add(Map.entry(SHAPE_LENGTH, 5.0));

        var shiftedPoints = new ArrayList<>(shapePoints
            .stream()
            .map(e -> Map.entry(cornerOffset + e.getKey(), cornerOffset + e.getValue()))
            .collect(Collectors.toList()));

        // If the text index is odd, flip the point list to avoid long travel moves.

        if ( (kTestIndex % 2 == 0) ^ invertOrientation) {
            Collections.reverse(shiftedPoints);
        }

        for (var i = 0; i < shiftedPoints.size(); i++) {
            var point = shiftedPoints.get(i);

            // The first point is a retracted travel move.
            // All points after that are print moves.

            var waypointCreator = printer.getWaypointCreator();

            if (i == 0) {
                printer.addWaypoint(waypointCreator.createRetractedTravelMove(shiftedPoints.get(0).getKey(), shiftedPoints.get(0).getValue()));
            } else {
                printer.addWaypoint(waypointCreator.createPrintMove(point.getKey(), point.getValue()));
            }
        }
    }
}
