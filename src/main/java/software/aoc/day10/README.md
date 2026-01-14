# Advent of Code 2025 - Day 10 (Parte B)

Este módulo modela una máquina con un conjunto de luces y botones (cada botón conmuta ciertas luces) y busca el **mínimo número de pulsaciones** necesario para alcanzar una configuración objetivo. [file:113][file:120]

Incluye varias estrategias de resolución (fuerza bruta por subconjuntos, meet-in-the-middle y A*), todas implementando el contrato `MachineSolver`. [file:120][file:118][file:119]

## Modelo del dominio
- `Machine`: contiene la `targetConfiguration`, la lista de `buttons` y una lista de `shakeRequirements` (se parsea, pero hay un comentario indicando que el solver de part1 la ignora). [file:113]
- `LightConfiguration`: wrapper inmutable sobre `List<Light>` y método `matches(...)` para comparar con el objetivo. [file:116]
- (Implícito en los solvers) cada `Button` referencia índices de luces a modificar (`lightIndices`). [file:120][file:118]

## Solvers disponibles
### MinSubsetPressSolver (combinaciones)
Busca el mínimo `k` tal que exista una combinación de `k` botones cuya aplicación produzca exactamente la configuración objetivo (probando combinaciones crecientes). [file:120]  
Aplica un combo inicializando todas las luces a `OFF` y haciendo `toggle` en las posiciones indicadas por cada botón. [file:120]

### MeetInMiddleMinPressSolver (XOR / BigInteger)
Codifica el objetivo y los botones como `BigInteger` (máscara de bits) y divide los botones en dos mitades para enumerar subconjuntos y “hacer match” por XOR (meet-in-the-middle). [file:118]  
Calcula el coste mínimo para cada XOR en la mitad izquierda y luego recorre subconjuntos de la mitad derecha buscando el complemento `need = target XOR xr`. [file:118]

### JoltageAStarMinPressSolver (A*)
Interpreta el problema como búsqueda en el espacio de estados donde cada pulsación decrementa contadores (derivados de `shakeRequirements`) y usa A* con una heurística basada en suma/máximo y `maxCover`. [file:119]  
Codifica el estado en `BigInteger` usando una base (radix) y mantiene `bestG` para podar estados peores. [file:119]

## Cómo ejecutar (orientativo)
1. Const
