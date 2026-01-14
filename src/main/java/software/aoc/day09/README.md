# Advent of Code 2025 - Day 09 (Parte B)

Este módulo lee una lista de puntos 2D desde un fichero (`x,y` por línea) y calcula el área del “rectángulo/cuadrado” definido por pares de puntos, para resolver el problema del **mayor rectángulo bajo ciertas restricciones** (vía `ConstrainedRectangleSolver`). [file:109][file:110][file:105]

## Bibliotecas usadas
- JUnit (tests). [file:100]

## Entrada y salida
- **Entrada**: fichero de texto con una lista de puntos, 1 por línea, con formato `x,y`. [file:110][file:104]
- **Salida**: un `long` con el resultado que imprime `Main`, actualmente `largestConstrainedRectangle`. [file:109]

## Cómo ejecutar
1. Coloca el input en `src/main/resources/day09/inputs.txt` (o cambia la ruta en `Main`). [file:109]
2. Ejecuta `software.aoc.day09.b.application.Main`. [file:109]

---

## Arquitectura y paquetes
El proyecto separa dominio (modelos + contratos) de infraestructura (IO/parseo) y de la orquestación en `Main`. [file:101][file:104][file:105]

- `domain`
    - `model`: `Point`, `Square`, `AreaCalculator`, `RectangleAreaCalculator`. [file:102][file:107][file:103]
    - `persistence`
        - `io`: `Reader` (interfaz). [file:101]
        - `deserialization`: `Parser` (interfaz). [file:104]
- `application`
    - `persistence.io`: `PlainTextReader`, `PointsLoader`. [file:106][file:105]
    - `persistence.deserialization`: `PointParser`. [file:110]
    - `control`: `ConstrainedRectangleSolver` (y aparece `MovieTheaterSolver` importado). [file:109]

---

## Principios y patrones usados
- **SRP (Single Responsibility Principle)**:
    - `PlainTextReader` solo lee líneas del fichero. [file:106]
    - `PointParser` solo transforma cada línea en un `Point`. [file:110][file:104]
    - `PointsLoader` solo coordina lectura + parseo (capa de infraestructura). [file:105]
    - `RectangleAreaCalculator` solo calcula áreas de rectángulos dados dos puntos. [file:103]
- **DIP (parcial)**: existe una interfaz `Parser` y un `Reader` en dominio, pero `PointsLoader` depende de `PlainTextReader` concreto (mejorable para DIP completo). [file:105][file:101][file:104]
- **Composition/Delegation**: `RectangleAreaCalculator` delega el cálculo a un `Square(p1,p2)` y reutiliza su lógica. [file:103][file:102]

---

## Cálculo de área
- `AreaCalculator` define el contrato `calculateArea(Point p1, Point p2)`. [file:107]
- `Square` calcula el área como:
    - `width = |x2 - x1| + 1`
    - `height = |y2 - y1| + 1`
    - `area = width * height` (devuelve `long`). [file:102]
- `RectangleAreaCalculator` reutiliza `Square` para calcular el área entre dos puntos. [file:103][file:102]

---

## Error handling
- `PlainTextReader` lanza `IllegalArgumentException` si el fichero no existe y `UncheckedIOException` si falla la lectura. [file:106]
- Si una línea no cumple `x,y` o no es parseable, `PointParser` fallará al parsear (`NumberFormatException`/`ArrayIndexOutOfBounds`) y se propagará como error. [file:110]

---

## TO DO
- Corregir import en `PlainTextReader`: está importando `software.aoc.day05.b.domain.persistence.io.Reader` en vez del `Reader` de day09 (bug de paquete). [file:106][file:101]
- Tipar genéricos: hay `List` raw-type en `Parser`, `PointParser`, `PointsLoader` (debería ser `List<Point>`). [file:104][file:110][file:105]
- Evitar estado acumulado en `PointParser`: mantiene una lista interna `points`, lo que acumula datos si se reutiliza la instancia para múltiples parseos; sería más seguro crear una lista local en `parse(...)`. [file:110]
- En `Main` se crea `RectangleAreaCalculator` pero no se usa (posible código muerto o pendiente de integración). [file:109]

## UML Diagram
Pendiente (TO DO). [file:100]
