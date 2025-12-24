# Bibliotecas usadas:
- Junit

## TO DO:
- Cambiar el readme a inglés
- Cambiar a Constructores privados
- Ver la responsabilidad de cada clase. Ejemplo el PlainTextRotationReader con la responsabilidad también de instanciar las clases que ha de usar como la del parser
- Reducir la complejidad ciclomática -> Strategy pattern(polimorfismo) o solo descomponer en métodos privados¿?
- Reducir complejidad ciclomática de los buscles de la clase Operation usando módulos 
- EXPLICAR:
```
Step 1: 50+1=51 (no es 0)
Step 2: 51+1=52 (no es 0)
...
Step 50: 99+1=0 (¡sequence++! = 1)
Step 51: 0+1=1 (no es 0)
...
Step 150: 99+1=0 (¡sequence++! = 2)
...
(10 veces pasa por 0 en 1000 pasos)
Resultado: sequence = 10 (cada vez que toca 0)
Ambos dan 10, pero por razones diferentes: uno cuenta rebotes, el otro cuenta clicks en 0. Solo el segundo es correcto según el enunciado que dice "count the number of times any click causes the dial to point at 0".
​

Ventajas del Enfoque con Streams
No usa for explícito: IntStream.range() reemplaza el bucle tradicional
​

Funcional y declarativo: Más legible para quien conoce streams
​

Correcto semánticamente: Modela "cada click" como pide el enunciado

Complejidad controlada: Siempre O(n) donde n = total de times de todas las rotaciones
```

# Deciciones
## Modelado
¿Porqué decidi modelar en lugar de usar variables primitivas?
- **Explicación semántica**
- **Explicacion de division de responsabilidades**
- **Explicación de sobre posible cambio del formato de instrucciones**
  - Open/Close Principle: Las direcciones pueden venirme explicitas o en otro idioma y no siempre será siguiendo el formato del símbol "L" para izquierda y "R" para derecha. Por ejemplo podría encontrar una en la que las rotaciones entrantes esten escritas en otro idioma o explícitamente como por ejemplo "Left20" o "derecha20". 

- Explicación de la reducción la complejidad ciclomática -> Strategy pattern o polimorfismo¿? -> Explicación del porqué fue necesario.

## Estructuración del proyecto - paquetes

## Error handling

## Uml Diagram