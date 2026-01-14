# Advent of Code 2025 - Day 06 (Parte B)

Este módulo lee una “worksheet” (varias filas de números + una última fila con operadores `+` y `*`), construye una lista de problemas verticales y calcula el total aplicando el operador correspondiente a cada secuencia. [file:73][file:69][file:74]

## Bibliotecas usadas
- JUnit (tests). [file:67]

## Entrada y salida
- **Entrada**: fichero de texto donde:
    - Las primeras N líneas contienen números separados por espacios (múltiples espacios permitidos). [file:73]
    - La última línea contiene operadores `+` y `*` (ignorando espacios u otros caracteres). [file:73][file:74]
- **Salida**: un `long` total que se obtiene aplicando, por cada “columna/problema”, el operador a la secuencia de números y sumando los resultados (ver `Worksheet.calculateTotal()` en tu capa de control). [file:76][file:69]

## Cómo ejecutar
1. Coloca el input en `src/main/resources/day06/inputs.txt`.
2. Lee y parsea usando `PlainTextReader` + `WorksheetParser` (o `VerticalWorksheetParser` si el formato exige lectura dígito a dígito). [file:72][file:73][file:77]
3. Usa `Main_Debug` para verificar rápidamente lectura y parseo si el worksheet sale vacío. [file:76]

---

## Arquitectura y paquetes
Se separan claramente las capas de IO/parseo (application.persistence) del dominio (model + estrategias), manteniendo dependencias hacia interfaces donde aplica. [file:72][file:70][file:74]

- `application`
    - `persistence.io`: `PlainTextReader`, `WorksheetLoader`. [file:72][file:71]
    - `persistence.deserialization`: `WorksheetParser`, `VerticalWorksheetParser`. [file:73][file:77]
    - `Main_Debug`: herramienta de diagnóstico para validar archivo, lectura, parseo y primeros problemas. [file:76]
- `domain`
    - `model`: `NumberSequence`, `Operator`. [file:69][file:74]
    - `control`: `OperationStrategy` (estrategia de operación usada por `Operator`). [file:75]
    - `persistence.io`: `Reader`. [file:72]
    - `persistence.deserialization`: `Parser`. [file:70]

---

## Principios y patrones usados
- **Strategy / Polimorfismo**: `OperationStrategy` encapsula la operación binaria y `Operator` selecciona la estrategia por símbolo, eliminando `if/switch` al aplicar `+` o `*`. [file:75][file:74]
- **SRP (Single Responsibility Principle)**:
    - `PlainTextReader` solo lee líneas del fichero. [file:72]
    - `WorksheetParser` solo transforma líneas en un `Worksheet` (secuencias + operadores). [file:73]
    - `NumberSequence` solo conoce cómo aplicar un `Operator` sobre su lista de números. [file:69]
- **DIP (Dependency Inversion Principle)**: `WorksheetLoader.load(reader, parser)` recibe las abstracciones `Reader` y `Parser`, lo que permite cambiar fuente/parseo sin tocar el flujo principal. [file:71][file:70]

---

## Deciciones de parsing (WorksheetParser vs VerticalWorksheetParser)

### WorksheetParser (transposición “por tokens”)
`WorksheetParser`:
- Separa la última línea como `operatorsLine`. [file:73]
- Parsea operadores filtrando solo `+` y `*`. [file:73][file:74]
- Parsea cada línea de números con `split("\\s+")` y transpone para construir “columnas”. [file:73]
- Valida que todas las filas tengan el mismo número de columnas; si no, lanza `IllegalArgumentException`. [file:73]

### VerticalWorksheetParser (lectura “dígito a dígito”)
`VerticalWorksheetParser` existe para un formato más complejo:
- Lee caracteres de derecha a izquierda y agrupa dígitos para construir números “verticales”. [file:77]
- Normaliza el ancho rellenando con espacios (`padRight`) para poder indexar columnas. [file:77]
- Cierra un “problema” cuando detecta una columna vacía y guarda la secuencia + su operador. [file:77]

---

## Cálculo del resultado (NumberSequence + Operator)
- `Operator` es un enum con símbolo (`+`/`*`) y una `OperationStrategy` asociada, y ofrece `apply(a,b)` para operar sin condicionales. [file:74][file:75]
- `NumberSequence.applyOperator(operator)` reduce la secuencia aplicando el operador acumulativamente y devuelve `long` (no `int`). [file:69]

---

## Error handling
`PlainTextReader` usa `try-with-resources` y consume el stream dentro del `try` para no devolver un stream con el reader ya cerrado; ante errores lanza excepciones unchecked con mensaje claro. [file:72]

---

## TO DO
- Corregir genéricos raw-type (`List` sin tipo) en `NumberSequence` y parsers para mejorar type-safety (`List<Long>`, etc.). [file:69][file:73]
- Unificar un solo parser final (elegir `WorksheetParser` o `VerticalWorksheetParser`) según el formato definitivo del enunciado. [file:73][file:77]
- Mantener todos los campos `private final` en implementaciones (consistencia con encapsulación). [file:72][file:74]

## UML Diagram
Pendiente (TO DO). [file:67]
