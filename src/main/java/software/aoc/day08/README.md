# Advent of Code 2025 - Day 08 (Parte B)

Este módulo carga una lista de `JunctionBox` (puntos 3D `x,y,z`) desde un fichero y ofrece servicios de dominio para analizar/conectar circuitos basados en esas cajas. [file:95][file:96][file:90][file:93]

## Bibliotecas usadas
- JUnit (tests). [file:89]

## Entrada y salida
- **Entrada**: fichero de texto donde cada línea representa una junction box como `x,y,z` (separado por comas). [file:99][file:96]
- **Salida**:
    - Depende del “service” ejecutado: por ejemplo, `CircuitAnalysisService.analyzeCircuits(...)` devuelve un `long` con el resultado del análisis. [file:90]
    - `UnifiedCircuitService.connectUntilUnified(...)` devuelve un `Result` con las dos últimas cajas conectadas, el producto `xCoordinateProduct` y el número total de conexiones. [file:93]

---

## Arquitectura y paquetes
La estructura sigue una separación tipo **Clean Architecture**: el dominio define modelos e interfaces, y la aplicación implementa persistencia (lectura/parseo) y wiring. [file:94][file:90][file:96]

- `domain`
    - `model`: `JunctionBox`. [file:95]
    - `repository`: `JunctionBoxRepository` (contrato para obtener cajas). [file:94]
    - `service`: `CircuitAnalysisService`, `UnifiedCircuitService` (contratos de lógica de dominio). [file:90][file:93]
    - `persistence`
        - `io`: `Reader` (interfaz). [file:92]
        - `deserialization`: `Parser<T>` (interfaz). [file:91]
- `application`
    - `persistence.io`: `PlainTextReader`, `JunctionBoxLoader`. [file:97][file:98]
    - `persistence.deserialization`: `JunctionBoxParser`. [file:99]
    - `persistence.repository`: `FileJunctionBoxRepository` (repo basado en fichero). [file:96]

---

## Principios y patrones usados
- **Repository Pattern**: `JunctionBoxRepository` abstrae la fuente de datos; `FileJunctionBoxRepository` es una implementación concreta. [file:94][file:96]
- **DIP (Dependency Inversion Principle)**: `JunctionBoxLoader` depende del repositorio (abstracción) en lugar de depender directamente de lectura/parseo. [file:98][file:94]
- **SRP (Single Responsibility Principle)**:
    - `PlainTextReader` solo lee líneas. [file:97]
    - `JunctionBoxParser` solo transforma `String` → `JunctionBox`. [file:99][file:95]
    - `FileJunctionBoxRepository` coordina “leer + parsear” para entregar entidades. [file:96]
- **Value Object / Record**: `JunctionBox` es un `record` inmutable con comportamiento del dominio (`distanceSquared`). [file:95]

---

## Detalles de implementación

### Parsing
`JunctionBoxParser` separa cada línea por coma y construye `JunctionBox(x,y,z)`. [file:99][file:95]

### Distancia (optimización)
`JunctionBox.distanceSquared(...)` calcula distancia al cuadrado (evita `sqrt`) para comparar distancias de forma eficiente. [file:95]

### Carga de datos
- `FileJunctionBoxRepository.findAll()` crea `PlainTextReader` + `JunctionBoxParser` y devuelve la lista de boxes. [file:96][file:97][file:99]
- `JunctionBoxLoader.load()` delega en `repository.findAll()`. [file:98][file:94]

---

## Error handling
- `PlainTextReader` lanza `IllegalArgumentException` si el fichero no existe y `UncheckedIOException` si hay error de lectura. [file:97]
- `JunctionBoxParser` lanzará excepciones si el formato de línea no contiene 3 valores parseables (por ejemplo, `NumberFormatException` propagada como `RuntimeException`). [file:99]

---

## TO DO
- Tipar genéricos: varias interfaces/clases usan `List` raw-type (`List<JunctionBox>` sería lo correcto). [file:90][file:94][file:96][file:99]
- Evitar estado acumulado en `JunctionBoxParser`: actualmente mantiene una lista interna (`junctionBoxes`), lo que puede acumular datos si se reutiliza la misma instancia para múltiples parseos; sería más seguro crear una lista local en `parse(...)`. [file:99]
- Aplicar DIP también dentro de `FileJunctionBoxRepository`: en vez de instanciar `PlainTextReader` y `JunctionBoxParser` directamente, inyectarlos (o inyectar una factory) para testabilidad y menor acoplamiento. [file:96][file:97][file:99]

## UML Diagram
Pendiente (TO DO). [file:89]
