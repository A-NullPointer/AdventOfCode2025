Bibliotecas usadas:
- Junit

## TO DO:
- Cambiar el readme a inglés
- Cambiar a Constructores privados
- Ver la responsabilidad de cada clase. Ejemplo el PlainTextRotationReader con la responsabilidad también de instanciar las clases que ha de usar como la del parser

# Deciciones
## Modelado
¿Porqué decidi modelar en lugar de usar variables primitivas?
- **Explicación semántica**
- **Explicacion de division de responsabilidades**
- **Explicación de sobre posible cambio del formato de instrucciones**
  - Open/Close Principle: Las direcciones pueden venirme explicitas o en otro idioma y no siempre será siguiendo el formato del símbol "L" para izquierda y "R" para derecha. Por ejemplo podría encontrar una en la que las rotaciones entrantes esten escritas en otro idioma o explícitamente como por ejemplo "Left20" o "derecha20". 

## Estructuración del proyecto - paquetes

## Error handling

## Uml Diagram