package software.aoc.day07.b.domain.control;

import software.aoc.day07.b.domain.model.Beam;
import software.aoc.day07.b.domain.model.Cell;
import software.aoc.day07.b.domain.model.Direction;
import software.aoc.day07.b.domain.model.Manifold;
import software.aoc.day07.b.domain.model.Position;

import java.util.*;

public class BeamSimulator {
    private final Manifold manifold;
    private final Set<Position> visitedSplitters;
    private int splitCount;
    private final boolean debug;

    public BeamSimulator(Manifold manifold) {
        this(manifold, false);
    }

    public BeamSimulator(Manifold manifold, boolean debug) {
        this.manifold = manifold;
        this.visitedSplitters = new HashSet<>();
        this.splitCount = 0;
        this.debug = debug;
    }

    public int simulate() {
        Queue<Beam> beamQueue = new LinkedList<>();
        Set<Beam> processedBeams = new HashSet<>();

        Position start = manifold.getStartPosition();
        Position firstPos = start.move(Direction.DOWN);
        Beam initialBeam = new Beam(firstPos, Direction.DOWN);
        beamQueue.offer(initialBeam);

        if (debug) {
            System.out.println("Iniciando desde: " + start);
        }

        int iteration = 0;
        while (!beamQueue.isEmpty()) {
            Beam currentBeam = beamQueue.poll();

            if (!processedBeams.add(currentBeam)) {
                continue;
            }

            if (debug) {
                System.out.println("\n[" + (++iteration) + "] " + currentBeam);
            }

            traceBeam(currentBeam, beamQueue, processedBeams);
        }

        if (debug) {
            System.out.println("\n=== Total splits: " + splitCount + " ===");
        }

        return splitCount;
    }

    private void traceBeam(Beam beam, Queue<Beam> beamQueue, Set<Beam> processedBeams) {
        Beam current = beam;

        while (true) {
            if (!current.isValid(manifold.getRows(), manifold.getCols())) {
                if (debug) System.out.println("  -> Salió");
                return;
            }

            var cellOpt = manifold.getCell(current.position());
            if (cellOpt.isEmpty()) {
                return;
            }

            Cell cell = cellOpt.get();
            char symbol = cell.getSymbol();

            if (debug) {
                System.out.println("  " + current.position() + " ('" + symbol + "')");
            }

            if (symbol == '^') {
                // SPLIT: Crear dos haces que continúan HACIA ABAJO desde las posiciones laterales
                boolean isNew = visitedSplitters.add(current.position());
                if (isNew) {
                    splitCount++;
                    if (debug) {
                        System.out.println("  *** SPLIT #" + splitCount + " ***");
                    }
                }

                Position pos = current.position();

                // Crear haz izquierdo (una columna a la izquierda, bajando)

                Position leftPos = pos.moveLeft();
                if (leftPos.isValid(manifold.getRows(), manifold.getCols())) {
                    Beam leftBeam = new Beam(leftPos, Direction.DOWN);
                    beamQueue.offer(leftBeam);
                    if (debug) {
                        System.out.println("  + Haz izquierdo en " + leftPos);
                    }
                }

                // Crear haz derecho (una columna a la derecha, bajando)
                Position rightPos = pos.moveRight();
                if (rightPos.isValid(manifold.getRows(), manifold.getCols())) {
                    Beam rightBeam = new Beam(rightPos, Direction.DOWN);
                    beamQueue.offer(rightBeam);
                    if (debug) {
                        System.out.println("  + Haz derecho en " + rightPos);
                    }
                }

                return; // Haz original se detiene

            } else if (symbol == '.' || symbol == 'S') {
                // Continuar hacia abajo
                Beam next = current.move();

                if (processedBeams.contains(next)) {
                    if (debug) System.out.println("  -> Ya procesado");
                    return;
                }

                processedBeams.add(next);
                current = next;

            } else {
                return;
            }
        }
    }

    public int getSplitCount() {
        return splitCount;
    }
}
