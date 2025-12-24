# Bibliotecas usadas:
- Junit

## TO DO:
- Cambiar el readme a inglés
- Cambiar a Constructores privados
- Ver la responsabilidad de cada clase. Ejemplo el PlainTextRotationReader con la responsabilidad también de instanciar las clases que ha de usar como la del parser
- Reducir la complejidad ciclomática -> Strategy pattern(polimorfismo) o solo descomponer en métodos privados¿?
- Reducir complejidad ciclomática de los buscles de la clase Operation usando módulos 

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