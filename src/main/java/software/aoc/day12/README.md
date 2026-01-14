# Advent of Code 2025 - Day 12 (Parte A)

Este módulo parsea un input compuesto por varios **bloques de shapes** (con `#` como celdas ocupadas) y un bloque final de **regiones** (ancho x alto + cantidades), y calcula cuántas regiones “caben” usando solo una restricción de área total. [file:142][file:139][file:138]

La idea clave es comparar el área de cada región (`width*height`) con el área requerida por las cantidades de shapes (cantidad × área de cada shape). [file:136][file:140][file:139]

## Bibliotecas usadas
- JUnit (tests). [file:133]

## Formato de entrada
- El fichero se divide por líneas en blanco en bloques. [file:142]
- Bloques 0..N-1: cada bloque es un shape:
    - Primera línea: `id:` (por ejemplo `0:`). [file:142]
    - Resto: filas del shape (strings), donde `#` cuenta como celda ocupada. [file:142][file:140]
- Último bloque: regiones, una por línea:
    - Formato `WxH: q0 q1 q2 ...` (por ejemplo `10x5: 2 0 1`). [file:142][file:136]

## Salida (lógica)
`AreaOnlyRegionChecker.countFittableRegions(input)` devuelve el número de regiones cuya área es suficiente para cubrir el “área requerida” por las cantidades de shapes. [file:139][file:136][file:140]

---

## Arquitectura y paquetes
- `domain`
    - `model`: `PuzzleInput`, `Shape`, `RegionSpec`. [file:138][file:140][file:136]
    - `persistence`
        - `io`: `Reader`. [file:137]
        - `deserialization`: `Parser`. [file:135]
- `application`
    - `persistence.io`: `PlainTextReader`, `Loader`. [file:141][file:143]
    - `persistence.deserialization`: `Day12Parser`. [file:142]
    - `service`: `AreaOnlyRegionChecker`. [file:139]

## Componentes principales
- `Day12Parser`:
    - Parte el input en bloques por líneas en blanco. [file:142]
    - Convierte cada bloque (menos el último) en un `Shape(id, rows)`. [file:142][file:140]
    - Convierte el último bloque en `RegionSpec(width, height, quantities)`. [file:142][file:136]
    - Devuelve un `PuzzleInput(shapes, regions)`. [file:142][file:138]
- `Shape.area()` cuenta cuántos `#` hay en todas las filas. [file:140]
- `RegionSpec.area()` calcula `width * height`. [file:136]
- `AreaOnlyRegionChecker` calcula:
    - `requiredArea = Σ (quantity[i] * shapeArea[i])` (limitado por el mínimo tamaño entre listas). [file:139]
    - Filtra regiones donde `regionArea >= requiredArea`. [file:139]

## Error handling
- `PlainTextReader` lanza `IllegalArgumentException` si el fichero no existe y `UncheckedIOException` si falla la lectura. [file:141]
- Si el formato de línea de región o de header `id:` es inválido, el parseo puede fallar con excepciones de parseo (`NumberFormatException`, etc.). [file:142]

## TO DO
- Tipar genéricos: hay `List` raw-type (`List shapes`, `List regions`, `List quantities`, etc.). [file:138][file:136][file:140][file:142]
- Añadir validaciones de consistencia (p.ej., comprobar que todas las filas de un shape tienen la misma longitud si eso importa para el problema). [file:142][file:140]

## UML Diagram
Pendiente (TO DO). [file:133]
