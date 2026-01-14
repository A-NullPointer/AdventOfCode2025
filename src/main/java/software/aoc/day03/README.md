# Advent of Code 2025 - Day 03 (Parte B)

Este módulo lee una lista de “banks” (cada línea del input es un número muy largo) y calcula la suma del “largest joltage” de cada bank. [file:36][file:38][file:34][file:39]

## Bibliotecas usadas
- JUnit (tests). [file:35][file:40]

## Entrada y salida
- **Entrada**: fichero de texto donde cada línea es un bank (una secuencia de dígitos). [file:34][file:36]
- **Salida**: un `long` que es la suma de `Bank::getLargestJoltage` para todos los banks leídos. [file:38][file:39]

## Cómo ejecutar
1. Coloca el input en `src/main/resources/day03/inputs.txt`. [file:36]
2. Ejecuta `software.aoc.day03.b.application.Main` para imprimir el resultado. [file:36]

---

## Arquitectura y paquetes
El proyecto separa **dominio** (modelo + interfaces) de **aplicación** (orquestación + IO concreto), siguiendo la misma línea de dependencia hacia abstracciones (DIP) usada en días anteriores. [file:38][file:37][file:34]

- `software.aoc.day03.b.domain`
    - `model`: `Bank`. [file:39]
    - `persistence.io`: `BankReader` (interfaz). [file:37]
- `software.aoc.day03.b.application`
    - `control`: `Solver`. [file:38]
    - `persistence.io`: `PlaintTextBankReader` (implementación concreta). [file:34]
    - `Main`: composition root (cableado). [file:36]

---

## Principios y patrones usados
- **DIP (Dependency Inversion Principle)**: `Solver` depende de `BankReader` (abstracción) y no de `PlaintTextBankReader` (detalle). [file:38][file:37][file:34]
- **SRP**:
    - `PlaintTextBankReader` solo lee y construye `Bank` desde cada línea. [file:34][file:39]
    - `Bank` encapsula la lógica para obtener el mayor joltage del propio bank. [file:39]
    - `Solver` solo orquesta lectura y agregación (suma). [file:38]
- **KISS**: no se introduce capa de deserialización adicional porque el formato es 1 línea = 1 bank; se crea `new Bank(line)` directamente. [file:34][file:40]

---

## Deciciones
### Modelado (String vs long/BigInteger)
- No se usa `long` ni `BigInteger` para representar el bank completo, sino `String`, para conservar números extremadamente largos (p. ej. 103 dígitos) sin overflow y sin complejidad innecesaria. [file:40][file:39]
- Esto permite que la lógica trabaje “por dígitos” (caracteres) sin depender del rango numérico de Java. [file:39][file:40]

### Por qué no se necesitó deserializer
- No se creó un parser/deserializer porque cada línea ya es el dato final: el “bank” es literalmente la línea de texto. [file:34][file:40]

### Naming
- Hay un TODO de naming: `getLargestJoltage` describe intención, pero si el enunciado realmente habla de “seleccionar 12 dígitos” o “joltage de dos dígitos”, conviene renombrar a algo más específico (por ejemplo `getLargestJoltageOfTwoDigits` o `max12DigitJoltage`, según el enunciado real). [file:40][file:39]

### ¿El método debe estar dentro del record?
- Se mantuvo `getLargestJoltage()` dentro de `Bank` porque está directamente relacionado con los datos del record, no tiene dependencias externas y mantiene la lógica cohesionada con el modelo. [file:39][file:40]
- Si la lógica creciera (más reglas, varias estrategias de cálculo, etc.), sería mejor extraerla a un servicio del dominio o a una estrategia. [file:40]

---

## Detalles del algoritmo (Bank.getLargestJoltage)
El método `getLargestJoltage()` implementa un enfoque greedy usando un `StringBuilder` y eliminando dígitos “menos convenientes” mientras todavía se puedan eliminar, para quedarse con exactamente `REQUIRED_DIGITS = 12`. [file:39]

Esto evita streams anidados y bucles cuadráticos que aparecen en el enfoque alternativo comentado dentro de la clase (la sección “SIN NESTED STREAMS”). [file:39]

---

## Error handling
- `PlaintTextBankReader` lanza `IllegalArgumentException` si no encuentra el fichero y `UncheckedIOException` si hay un error de lectura, evitando continuar con datos vacíos silenciosamente. [file:34]

---

## TO DO
- **Todos los campos deben ser privados**: en `PlaintTextBankReader` el campo `filePath` debería ser `private final` para garantizar inmutabilidad y encapsulación. [file:34]
- Corregir genéricos: `List read()` debería ser `List<Bank>` en `BankReader`/`PlaintTextBankReader`/`Solver` para type-safety. [file:37][file:34][file:38]
- Añadir UML Diagram (pendiente). [file:35][file:40]
