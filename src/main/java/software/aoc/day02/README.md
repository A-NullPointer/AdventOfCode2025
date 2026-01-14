# Advent of Code 2025 - Day 02 (Parte B)

Este módulo calcula la suma de los IDs inválidos definidos por una regla de validación, recorriendo rangos de IDs leídos desde un fichero de entrada (por ejemplo `11-22,95-115,...`). [file:32][file:23][file:30]

## Bibliotecas usadas
- (Opcional) JUnit para tests. [file:25]

## Reglas del dominio (resumen)
- La entrada contiene rangos separados por coma, y cada rango se expresa como `first-last`. [file:30][file:31]
- Un ID es “inválido” si un `IdValidator` lo marca como inválido (por ejemplo, patrón repetido en el número). [file:26][file:24][file:29]
- El resultado final es la suma de todos los IDs inválidos generados al expandir cada rango. [file:33][file:23]

## Cómo ejecutar
1. Coloca el input en `src/main/resources/day02/inputs.txt` (o cambia la ruta en `Main`). [file:32]
2. Ejecuta `software.aoc.day02.b.application.Main`. [file:32]

---

## Arquitectura (Clean Architecture, Uncle Bob)
Se ha organizado el proyecto siguiendo **Clean Architecture** (Uncle Bob), separando dominio (reglas) de infraestructura (IO/parseo) y orquestación. [file:30][file:33][file:26]

No es DDD porque aquí no se está modelando un dominio rico con múltiples casos de uso; el objetivo es mantener dependencias apuntando hacia el dominio y permitir cambiar detalles (entrada/validación) sin afectar al core. [file:33][file:30][file:26]

### Estructura propuesta (capas y paquetes)
- `application`
    - `control`
        - `Solver` (orquesta: lee rangos + aplica validación + suma). [file:33]
    - `persistence` (**implementación**)
        - `io` (`PlainTextIdRangeReader`). [file:30]
        - `deserialization` (`PlainTextIdRangeParser`). [file:31]
    - `validators` (implementaciones concretas de validación, ej. `RepeatedPatternIdValidator`, `MultipleRepeatedPatternIdValidator`). [file:24][file:29]
    - `Main` (composition root: cablea dependencias). [file:32]

- `domain`
    - `persistence` (interfaces)
        - `io` (`IdRangeReader`). [file:27]
        - `deserialization` (`IdRangeParser`). [file:28]
    - `model` (`IdRange`). [file:23]
    - `validators` (interfaces: `IdValidator`). [file:26]

### Por qué esta arquitectura
- Permite cambiar el formato de entrada sin modificar el solver (solo cambiando el `IdRangeReader`/`IdRangeParser`). [file:27][file:28][file:33]
- Permite cambiar la regla de invalidez sin tocar el algoritmo de suma (solo cambiando la implementación de `IdValidator`). [file:26][file:33][file:24]

---

## Principios y patrones aplicados
- **DIP (Dependency Inversion Principle)**: `Solver` depende de abstractions (`IdRangeReader`, `IdValidator`) y no de clases concretas. [file:33][file:27][file:26]
- **Strategy Pattern**: `IdValidator` funciona como Strategy; distintas implementaciones (`RepeatedPatternIdValidator`, `MultipleRepeatedPatternIdValidator`) cambian la regla sin cambiar el flujo del solver. [file:26][file:24][file:29][file:33]
- **SRP (Single Responsibility Principle)**:
    - `PlainTextIdRangeParser` solo parsea `a-b` a `IdRange`. [file:31]
    - `PlainTextIdRangeReader` solo lee el fichero y delega el parseo a `IdRangeParser`. [file:30]
    - `Solver` solo orquesta lectura + cálculo. [file:33]

---

## Encapsulación (IMPORTANTE)
**TODOS LOS CAMPOS HAN DE SER PRIVADOS SIEMPREEEEEEEEEEEE**.

Motivo: evita acoplamiento, reduce efectos colaterales, y fuerza a interactuar con el objeto a través de su API (manteniendo invariantes). [file:30][file:33]

En el código actual esto ya se cumple en varias clases (`Solver`, `PlainTextIdRangeReader`), donde los campos están como `private final`. [file:33][file:30]

---

## Deciciones

### 1) Why not Command pattern en el Solver (y “código paranoico”)
No se implementó **Command** porque el problema no requiere deshacer/rehacer, colas de comandos, logging de acciones, ni composición de acciones complejas; añadirlo aquí sería “código paranoico” (diseñar para una complejidad hipotética que el enunciado no pide). [file:33][file:32]

El `Solver` ya actúa como un “use-case-like orchestrator” mínimo: toma un `reader` y un `validator` y calcula un resultado, por lo que Command no aporta beneficios reales frente al coste de más clases/abstracciones. [file:33][file:26]

### 2) Why not polymorphism sino función que divide el número en half
Para el validador de “patrón repetido”, se eligió una solución matemática que divide el número en dos mitades (`firstHalf` y `secondHalf`) en vez de crear un árbol de polimorfismo o varios “tipos de número”. [file:24]

Justificación:
- Es más directa para esta regla concreta (KISS): calcula dígitos, parte el número y compara mitades. [file:24]
- Evita objetos intermedios y conversiones a `String` en el caso simple (mejor rendimiento y menos ruido). [file:24]

---

## Error handling
- `PlainTextIdRangeReader` lanza `IllegalArgumentException` si no encuentra el fichero y `UncheckedIOException` ante errores de lectura, en lugar de imprimir por consola y continuar con datos vacíos. [file:30]
- Esto evita resultados silenciosamente incorrectos cuando el input no se cargó. [file:30][file:33]

---

## TO DO
- Revisar genéricos: varias firmas usan `List` raw-type (por ejemplo `IdRangeReader.read()` y `Solver`), y deberían ser `List<IdRange>` para seguridad de tipos. [file:33][file:27][file:30]
- Extraer la ruta hardcodeada en `Main` a `args[0]` o a configuración. [file:32]
- Asegurar que no haya estado mutable compartido en parsers/readers (en este día el parser es stateless y el reader delega, lo cual es correcto). [file:31][file:30]

## UML Diagram
Pendiente (TO DO).
