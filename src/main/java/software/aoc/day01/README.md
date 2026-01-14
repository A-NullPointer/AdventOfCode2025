# Advent of Code 2025 - Day 01 (Parte B)

Este proyecto resuelve el problema de contar cuántas veces un “click” provoca que el dial apunte al valor mínimo (0 en el caso del enunciado), recorriendo una lista de rotaciones del tipo `Lx` / `Rx`. [file:20][file:22][file:16]

## Bibliotecas usadas
- JUnit (tests). [file:12]

## Estructura del proyecto (paquetes)
- `software.aoc.day01.b.model`: modelos del dominio (`Dial`, `Direction`, `Rotation`). [file:16][file:15][file:13]
- `software.aoc.day01.b.deserialization`: parseo de instrucciones (`RotationParser`, `PlainTextRotationParser`). [file:19][file:22]
- `software.aoc.day01.b.io`: lectura de entradas (`RotationReader`, `PlainTextRotationReader`). [file:17][file:18]
- `software.aoc.day01.b.application`: caso de uso/operación principal (`Operation`). [file:20]
- `software.aoc.day01.b`: punto de entrada (`Main`). [file:14]

## Cómo ejecutar
1. Coloca el archivo de entrada en `src/main/resources/day01/inputs.txt` (o ajusta la ruta en `Main`). [file:14]
2. Ejecuta `Main` para imprimir el resultado por consola. [file:14]

## Deciciones
### Modelado
Se decidió modelar el dominio con clases/records en lugar de usar solo primitivas para mejorar la semántica y hacer el código más fácil de extender y mantener. [file:16][file:15][file:13]

- **Explicación semántica**: `Rotation(Direction, times)` expresa una intención clara (una instrucción), y `Direction` encapsula el delta de movimiento (`-1` o `+1`) en lugar de usar números “mágicos”. [file:13][file:15]
- **División de responsabilidades**:
    - `PlainTextRotationReader` se encarga de leer líneas del fichero y convertirlas a rotaciones usando un parser. [file:18]
    - `PlainTextRotationParser` se encarga del parseo de cada línea (extraer dirección y número de pasos). [file:22]
    - `Operation` aplica rotaciones y cuenta el número de clicks que dejan el dial en el mínimo. [file:20]
    - `Dial` representa límites y posición inicial del dial (min/max/position). [file:16]
- **Posible cambio del formato de instrucciones**:
    - Open/Close Principle: la idea es poder cambiar el formato de entrada sin tocar `Operation`, manteniendo el cambio confinado al parser/reader. [file:20][file:22][file:19]
    - Ejemplo: podrían venir instrucciones en otro idioma o explícitas, como `"Left20"` o `"derecha20"`, en vez de `"L20"`/`"R20"`; en ese caso se crearía otro parser/estrategia de parseo sin modificar la lógica de conteo. [file:22][file:20][file:19]

### Reducción de complejidad ciclomática
La clase `Operation` ya descompone en métodos pequeños (`applyRotationStepByStep`, `executeStep`, `normalize`, `isAtMinimum`) para evitar lógica monolítica. [file:20]

Aun así, la complejidad puede reducirse más:
- Sustituyendo partes por una estrategia/polimorfismo si en el futuro aparecen reglas distintas (por ejemplo, otra forma de normalizar o de contar hits). [file:20][file:16]
- Reorganizando el “paso” de un click en un único método (ver el snippet de TO DO) para que el bucle de clicks llame a una operación atómica “normalizar + comprobar”. [file:21][file:20]

## Principios aplicados (y dónde)
- **SRP (Single Responsibility Principle)**: separación entre lectura (`PlainTextRotationReader`), parseo (`PlainTextRotationParser`) y cálculo (`Operation`). [file:18][file:22][file:20]
- **KISS**: el parseo de la línea es directo (primer char = dirección, resto = entero) y el algoritmo de conteo simula exactamente “cada click”. [file:22][file:20]
- **DRY**: la normalización se encapsula en un método (`normalize`) en vez de repetirse. [file:20]

## Patrones de diseño (actuales y planificados)
- **Strategy (planificado)**: `RotationParser` actúa como interfaz para poder soportar múltiples formatos de entrada (por ejemplo `PlainTextRotationParser` y futuros parsers). [file:19][file:22]
- **Factory / Simple Factory (planificado)**: si se amplían formatos, se puede añadir una factoría que seleccione el parser adecuado según el tipo de fichero/entrada sin modificar el resto del sistema. [file:19][file:18]
- **Composite (planificado, si se exige)**: se puede representar una “lista de instrucciones” como un objeto compuesto que aplique internamente cada rotación, eliminando condicionales en `Operation` cuando aparezcan tipos de instrucciones diferentes (p. ej. `Rotation`, `Repeat`, etc.). [file:20][file:13]

## Notas de corrección semántica (“cada click”)
El enunciado pide: “count the number of times any click causes the dial to point at 0”, por lo que el conteo correcto debe revisarse click a click, no por rotación completa. [file:12][file:20]


Este enfoque es el que implementa el bucle por clicks (`IntStream.range(0, rotation.times())`). [file:12][file:20]

## Error handling
- Actualmente, si el fichero no existe se imprime un mensaje (“Input file not found”) y el programa continúa con una lista vacía, lo que puede dar resultados incorrectos silenciosamente. [file:18]
- Una alternativa más segura es propagar la excepción o terminar la ejecución, para evitar devolver una ejecución “válida” con datos incompletos. [file:18]

## TO DO:
- Cambiar a Constructores privados. [file:21]
- Ver la responsabilidad de cada clase. Ejemplo el `PlainTextRotationReader` con la responsabilidad también de instanciar las clases que ha de usar como la del parser. [file:18][file:21]
- Reducir la complejidad ciclomática -> Strategy pattern(polimorfismo) o solo descomponer en métodos privados¿?. [file:21][file:19]
- Reducir complejidad ciclomática de los buscles de la clase `Operation` usando módulos. [file:21][file:20]

```python
private void normalizeAndCheckPosition() {
    int min = this.dial.getMin();
    
    // Normalizar usando aritmética modular
    int offset = this.currentPosition - min;
    this.currentPosition = ((offset % this.range + this.range) % this.range) + min;
    
    // Verificar si llegó al mínimo
    if (isAtMinimum()) {
        incrementSequenceCounter();
    }
}
