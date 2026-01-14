# Advent of Code 2025 - Day 04 (Parte B)

Este módulo carga un “tablero” en texto donde el carácter `@` representa una “roll position”, y resuelve un problema sobre accesibilidad/eliminación en cascada basándose en vecindad de 8 direcciones (Moore neighborhood). [file:55][file:53][file:49]

## Bibliotecas usadas
- (Opcional) JUnit para tests.

## Entrada y salida
- **Entrada**: fichero de texto (`inputs.txt`) donde cada línea es una fila del grid; las posiciones con `@` se convierten a coordenadas `Pos(row, col)`. [file:52][file:55]
- **Salida (Parte B)**: un `int` que corresponde al resultado de ejecutar el solver seleccionado sobre el conjunto de posiciones iniciales. [file:49][file:45]

## Cómo ejecutar
1. Coloca el input en `src/main/resources/day04/inputs.txt` (o pasa la ruta como primer argumento). [file:45]
2. Ejecuta `software.aoc.day04.b.Main`. [file:45]

---

## Arquitectura y paquetes
El diseño separa dominio (interfaces/modelo) de infraestructura (IO/parseo) y de la lógica de aplicación (solvers), de forma que los detalles (leer archivo / parseo) no contaminen la lógica del algoritmo. [file:47][file:48][file:54]

- `domain`
    - `model`: `Pos` (coordenada fila/columna). [file:55]
    - `service`: `RollSolver` (interfaz del algoritmo). [file:46]
    - `persistence.io`: `Reader` (interfaz de lectura). [file:47]
    - `persistence.deserialization`: `Parser<T>` (interfaz de parseo). [file:48]
- `application`
    - `persistence.io`: `PlainTextReader`, `Loader<T>`. [file:52][file:54]
    - `persistence.deserialization`: `RollPositionsParser`. [file:55]
    - `service`: `Neighbors8`, `Part1AccessibleSolver`, `Part2CascadeRemovalSolver`. [file:53][file:50][file:49]
- `Main`: composition root (cablea dependencias y decide el solver a ejecutar). [file:45]

---

## Principios y patrones usados
- **DIP (Dependency Inversion Principle)**: `Loader` depende de las abstracciones `Reader` y `Parser<T>`; el detalle concreto (`PlainTextReader`, `RollPositionsParser`) se inyecta desde `Main`. [file:54][file:47][file:48][file:45]
- **SRP (Single Responsibility Principle)**:
    - `PlainTextReader` solo se encarga de leer líneas de un fichero. [file:52]
    - `RollPositionsParser` solo transforma líneas en `Pos` filtrando por `@`. [file:55]
    - `Neighbors8` solo calcula vecinos y cuenta adyacencias. [file:53]
    - Cada solver (`Part1AccessibleSolver`, `Part2CascadeRemovalSolver`) encapsula una regla de resolución distinta. [file:50][file:49]
- **Strategy**: `RollSolver` permite intercambiar la estrategia (parte 1 vs parte 2) sin cambiar el código de carga/parseo. [file:46][file:50][file:49]

---

## Detalles clave de implementación

### Parseo a posiciones (RollPositionsParser)
El parser recorre filas y columnas y crea un `Pos(r,c)` por cada `@`, devolviendo una lista de posiciones que luego se transforma a `Set` para búsquedas O(1). [file:55][file:45]

### Vecindad de 8 (Neighbors8)
Se usa vecindad de 8 direcciones para obtener los adyacentes y contar cuántos “rolls” están alrededor de una posición dada. [file:53]

---

## Solvers (Parte 1 y Parte 2)

### Parte 1: accesibles
`Part1AccessibleSolver` cuenta cuántas posiciones cumplen `adjacentRolls(p, rolls) < 4`, es decir, tienen menos de 4 vecinos ocupados. [file:50][file:53]

### Parte 2: eliminación en cascada
`Part2CascadeRemovalSolver` elimina iterativamente las posiciones “removibles” (mismo criterio `< 4`) y, tras cada eliminación, recalcula solo alrededor de las celdas afectadas (vecinos de las eliminadas), evitando recalcular todo el tablero. [file:49][file:53]

---

## Error handling
`PlainTextReader` lanza `IllegalArgumentException` si el fichero no existe y `UncheckedIOException` si falla la lectura, evitando continuar con un estado inválido silenciosamente. [file:52]

---

## TO DO
- Corregir genéricos y raw-types: hay `Set`/`List` sin tipo en varios puntos; deberían ser `Set<Pos>` y `List<Pos>` para type-safety. [file:45][file:49][file:50][file:55]
- Usar `SolutionCalculator` y `RollSolverFactory` (aparecen importados en `Main`) o eliminar imports si no se usan para mantener el código limpio. [file:45]
- Mantener todos los campos como `private final` cuando sea posible (ya se cumple en `PlainTextReader` y `Loader`). [file:52][file:54]

## UML Diagram
Pendiente (TO DO).
