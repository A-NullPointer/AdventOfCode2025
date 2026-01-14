package software.aoc.day08.b.domain.service;

import software.aoc.day08.b.domain.model.JunctionBox;

import java.util.List;

public interface UnifiedCircuitService {

    /**
     * Conecta junction boxes hasta formar un único circuito
     * @param boxes Lista de junction boxes
     * @return Resultado con las últimas dos cajas conectadas
     */
    Result connectUntilUnified(List<JunctionBox> boxes);

    /**
     * Resultado del análisis
     */
    record Result(
            JunctionBox lastBox1,
            JunctionBox lastBox2,
            long xCoordinateProduct,
            int totalConnections
    ) {}
}
