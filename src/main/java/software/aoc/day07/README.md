# Advent of Code 2025 - Day 07 (Parte B)

Este módulo simula un haz (“beam”) que cae por un **manifold** (grid) desde una celda de inicio `S`, atravesando celdas vacías `.` y dividiéndose en `^` en dos ramas (izquierda y derecha), para contar splits o contar “timelines” posibles. [file:83][file:85][file:87][file:88]

## Bibliotecas usadas
- JUnit (tests). [file:78]

## Entrada y salida
- **Entrada**: un fichero con un grid de caracteres donde:
    - `S` es la posición inicial. [file:83][file:85]
    - `.` es celda vacía (el haz continúa). [file:85][file:87]
    - `^` es celda “splitter” (divide). [file:85][file:87][file:88]
- **Salida**:
    - Modo “simulación clásica”: número de splits únicos realizados (`splitCount`). [file:87]
    - Modo “cuántico”: número total de timelines generadas. [file:88][file:80]

---

## Modelo del dominio
- `Manifold`: representa el grid y expone `getCell(Position)` y `getStartPosition()`, sin mezclar lógica de simulación (SRP). [file:83]
- `Cell`: interfaz con `getSymbol()` y un `Factory Method` `fromSymbol(char)` para construir celdas desde caracteres. [file:85]
- `Position`: value object con movimientos (`moveDown/moveLeft/moveRight`) y validación de límites. [file:86]
- `Beam`: value object inmutable; cada paso crea un nuevo `Beam`. [file:84]
- `Timeline`: modelo mínimo para una timeline, guardando solo la posición actual (evita explosión de memoria por historial). [file:80]

---

## Simuladores (Parte B)

### BeamSimulator (BFS/cola)
`BeamSimulator` usa una cola (`Queue`) para procesar beams pendientes, evitando recalcular beams ya vistos (`processedBeams`) y evitando contar dos veces el mismo splitter (`visitedSplitters`). [file:87]

Reglas principales:
- El haz avanza hacia abajo. [file:82][file:87]
- Si llega a `^` y ese splitter no se había visitado, incrementa el contador y genera dos nuevos beams en izquierda/derecha (ambos continúan hacia abajo). [file:87][file:86]
- Si sale del grid, se detiene. [file:87][file:86]

### QuantumBeamSimulator (DP + memoización)
`QuantumBeamSimulator` cuenta cuántas timelines emergen desde una posición usando **memoización** (`memoCache`) para no recalcular subproblemas. [file:88]

Reglas principales:
- Avanza hacia abajo; si sale del manifold, cuenta 1 timeline. [file:88][file:86][file:83]
- Si la celda es `^`, suma las timelines de la rama izquierda y derecha. [file:88][file:86]
- Si la celda es `.` o `S`, continúa sin bifurcar. [file:88][file:85]

---

## Patrones y principios usados
- **Factory Method**: `Cell.fromSymbol(char)` crea la implementación adecuada (`EmptyCell`, `SplitterCell`, `StartCell`) sin `if` externos, centralizando la creación. [file:85]
- **Builder Pattern**: `Manifold.Builder.fromLines(...)` construye el grid y valida que exista `S`. [file:83]
- **SRP**:
    - `Manifold` solo gestiona estructura del grid. [file:83]
    - Simuladores (`BeamSimulator`, `QuantumBeamSimulator`) contienen la lógica del cálculo. [file:87][file:88]
- **KISS + performance**:
    - `Timeline` no guarda historial completo (solo posición actual) para controlar memoria. [file:80]
    - `QuantumBeamSimulator` usa memoización para evitar recomputación exponencial. [file:88]

---

## Error handling
- Si no existe `S`, el builder lanza `IllegalArgumentException`. [file:83]
- Si aparece un símbolo desconocido en el grid, `Cell.fromSymbol` lanza `IllegalArgumentException`. [file:85]

---

## TO DO
- Tipar genéricos: hay `Set`/`Queue`/`Map` sin parámetros (`Set<Beam>`, `Set<Position>`, `Map<Position, Long>`, etc.) para mayor seguridad. [file:87][file:88]
- Unificar el enfoque: mantener solo `BeamSimulator` (simulación) o solo `QuantumBeamSimulator` (conteo DP) según el enunciado final. [file:87][file:88]

## UML Diagram
Pendiente (TO DO). [file:78]
