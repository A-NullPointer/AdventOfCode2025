# Advent of Code 2025 - Day 11 (Parte B)

Este módulo carga un grafo dirigido (“reactor graph”) desde un fichero de texto y calcula cuántos caminos distintos existen desde el nodo `you` hasta el nodo `out`, devolviendo un `BigInteger`. [file:131][file:132][file:122]

La solución usa DFS con memoización para contar caminos y además detecta ciclos para evitar recursión infinita. [file:126]

## Bibliotecas usadas
- JUnit (tests). [file:123]

## Entrada y salida
- **Entrada**: líneas tipo `FROM: TO1 TO2 TO3` (separadas por `:` y espacios), ignorando líneas en blanco. [file:130]
- **Salida**: imprime el número de caminos `you -> out` como `BigInteger`. [file:132][file:131]

## Cómo ejecutar
1. Coloca el input en `src/main/resources/day11/inputs.txt` (o pasa la ruta como primer argumento al `main`). [file:131]
2. Ejecuta `software.aoc.day11.b.application.Main`. [file:131]

---

## Arquitectura y paquetes
El diseño separa IO/parseo, modelo de dominio y servicio de conteo de caminos. [file:128][file:130][file:122]

- `domain`
    - `model`: `ReactorGraph` (lista de adyacencia inmutable). [file:124]
    - `service`: `PathCounter` (contrato). [file:122]
    - `persistence`
        - `io`: `Reader`. [file:128]
        - `deserialization`: `Parser`. [file:130]
- `application`
    - `persistence.io`: `PlainTextReader`, `Loader`. [file:128][file:129]
    - `persistence.deserialization`: `GraphParser`. [file:130]
    - `service`: `MemoizedDfsPathCounter`. [file:126]
    - `controller`: `SolutionCalculator` (caso de uso `you -> out`). [file:132]
    - `Main`: composition root. [file:131]

---

## Algoritmo (conteo de caminos)
- `MemoizedDfsPathCounter` hace DFS desde `start` hasta `end` y memoiza por nodo para no recalcular subárboles repetidos. [file:126]
- Para seguridad, mantiene un mapa de estado (`NEW/VISITING/DONE`) y lanza `IllegalStateException` si detecta un ciclo (“Cycle detected involving node: ...”). [file:126]
- Se usa `BigInteger` porque el número de caminos puede crecer mucho y no caber en `long`. [file:122][file:126]

---

## Error handling
- `PlainTextReader` lanza `IllegalArgumentException` si el fichero no existe y `UncheckedIOException` si falla la lectura. [file:128]
- `GraphParser` garantiza que cualquier nodo referenciado como destino exista en el mapa de adyacencia, aunque no tenga salidas (lo añade con lista vacía). [file:130]
- Si el input contiene ciclos, el contador lo detecta y falla explícitamente (en lugar de colgarse). [file:126]

## TO DO
- Tipar genéricos raw-type (`List`, `Map`) en `Loader`, `GraphParser` y `MemoizedDfsPathCounter` para mejorar seguridad de tipos. [file:129][file:130][file:126]
- Definir claramente la política para ciclos (ahora es “error”); si el enunciado permite ciclos, habría que cambiar el modelo (p. ej., limitar longitud o contar caminos simples). [file:126]
