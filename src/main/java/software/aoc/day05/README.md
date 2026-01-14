# Advent of Code 2025 - Day 05 (Parte B)

Este módulo lee un fichero con **dos secciones** separadas por una línea en blanco: (1) rangos de IDs válidos y (2) una lista de IDs de ingredientes, y calcula cuántos ingredientes están “fresh” (pertenecen a algún rango) y cuántos IDs frescos posibles existen en total. [file:66][file:57][file:64]

## Bibliotecas usadas
- JUnit (tests). [file:56]

## Entrada y salida
- **Entrada**: fichero de texto con:
    - Sección 1: líneas con rangos `a-b`. [file:65][file:64]
    - Línea en blanco separadora. [file:64]
    - Sección 2: líneas con IDs (un número por línea). [file:63][file:64]
- **Salida**:
    - `Fresh ingredients count`: número de IDs de ingredientes que caen en al menos un rango. [file:66][file:57]
    - `Ids frescos posibles`: número de IDs distintos que podrían ser frescos según los rangos. [file:66]

## Cómo ejecutar
1. Coloca el input en `src/main/resources/day05/inputs.txt` (o ajusta la ruta en `Main`). [file:66]
2. Ejecuta `software.aoc.day05.b.application.Main`. [file:66]

> Nota: en el código actual la ruta apunta a `day06/inputs.txt`; es un detalle a corregir para que coincida con el día 05. [file:66]

---

## Arquitectura y paquetes
El diseño separa el **dominio** (modelos + contratos) de la **aplicación** (orquestación) y la **persistencia/IO** (lectura y parseo). [file:61][file:62][file:57]

- `domain`
    - `model`: `IngredientId`, `IdRange`. [file:57]
    - `persistence`
        - `io`: `Reader` (interfaz). [file:61]
        - `deserialization`: `Parser` (interfaz). [file:63][file:65]
- `application`
    - `persistence.io`: `PlainTextReader`, `Loader`. [file:61][file:62]
    - `persistence.deserialization`: `SpliterByBlankLine`, `IdRangeParser`, `IngredientIdParser`. [file:64][file:65][file:63]
    - `control`: `FreshnessChecker`, `FreshnessPosibleIdsCounter` (cálculo del resultado). [file:66]
    - `Main`: composition root (cableado y ejecución). [file:66]

---

## Principios y patrones usados
- **SRP (Single Responsibility Principle)**:
    - `PlainTextReader` solo lee el fichero y devuelve un `Stream` de líneas. [file:61]
    - `SpliterByBlankLine` solo separa el stream en dos listas (antes y después de la línea en blanco). [file:64]
    - `IdRangeParser` solo convierte líneas `a-b` en objetos `IdRange`. [file:65]
    - `IngredientIdParser` solo convierte líneas numéricas en `IngredientId`. [file:63]
    - `Loader` solo coordina lectura + split + parseo para entregar listas listas para usar. [file:62]
- **DIP (Dependency Inversion Principle)**: `Loader` depende de las abstracciones `Reader` y `Parser`, no de implementaciones concretas, de modo que el formato o la fuente de datos puede cambiar sin reescribir el flujo. [file:62][file:61][file:63][file:65]
- **Facade-ish (Loader)**: `Loader` actúa como “fachada” de infraestructura para ocultar el detalle de “leer + separar + parsear” y entregar directamente `obtainIdRanges()` y `obtainIngredientIds()`. [file:62]
- **Value Objects**: `IdRange` e `IngredientId` encapsulan significado del dominio (no son `long` sueltos), y `IdRange.contains(...)` centraliza la regla de pertenencia. [file:57]

---

## Deciciones
### Modelado (por qué `IngredientId` y `IdRange`)
Se modeló `IngredientId` y `IdRange` para expresar intención (semántica) y mover reglas del dominio a un lugar único, por ejemplo `IdRange.contains(id)`. [file:57][file:66]

### Separación por línea en blanco
Se introdujo `SpliterByBlankLine` para capturar explícitamente la estructura del input (dos secciones separadas) y no mezclar lógica de “formato” con lógica de parseo de rangos o IDs. [file:64][file:65][file:63]

---

## Error handling
`PlainTextReader` lanza `IllegalArgumentException` si el fichero no existe y `UncheckedIOException` ante errores de lectura, evitando continuar con datos incompletos silenciosamente. [file:61]

---

## TO DO
- Todos los campos deben ser `private final` cuando aplique: en `Loader` aún hay campos no `final` y con nombres inconsistentes (`IdRangeRarser`). [file:62]
- Evitar estado acumulado en parsers: `IdRangeParser` y `IngredientIdParser` guardan listas internas y devuelven una vista inmutable, lo cual puede acumular datos si se reutiliza la misma instancia para múltiples parseos; sería más seguro usar una lista local dentro de `parse(...)`. [file:65][file:63]
- Corregir genéricos: hay `List`/`Stream` raw-types; deberían ser `List<IdRange>`, `List<IngredientId>`, `Stream<String>`, etc. [file:61][file:62][file:65][file:63]
- Renombrar `SpliterByBlankLine` a `SplitterByBlankLine` (typo) y homogeneizar nombres. [file:64]
- Corregir la ruta en `Main` para que apunte a `day05` y no a `day06`. [file:66]

## UML Diagram
Pendiente (TO DO). [file:56]
