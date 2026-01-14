
# TODO:
- TODOS LOS CAMPOS HAN DE SER PRIVADOS SIEMPREEEEEEEEEEEE -> acceder a ellos con getters
- clean architecture -> not ddd because i dont have any use case -> Uncle bob reference
  - estructura de la arquitectura
    - application
      - persistence(implementación)
        - io
        - deserialization
      - validators
      - Main
    - domain
      - persistence (interfaces)
        - io
        - deseriallization
      - model
      - vallidators ((interfaces))
- usar patrón command para solver de cacda día??
- pasar todos los lambdas a reference
- mirar qeu la captura de excepciones en el nivel más alto y que se hace bien
- CORREGIR LOS READERS A LOADERS -> El reader y luego el parser y luego el loader-> no son readers sino loaders orquesta entre la logica del parser y el reader 
- // Decorator -> Decorando el filereader con el buffurreader -> en los try con recources en los readers
- Devolver siempre listas inmutables ya que solo son de recorrer y no se modifican
- verificar los métodos privados de la clase si pueden ser estáticos
- corregir las lambdas tienen que ser todas en minuscula y solo la primera letra. luego pasarlas a method reference
- 

Para el examen:
- Nombre del advent of Code: aoc2025

- Tener a mano:
  - El adventOfCode con mi cuenta de Github para ver que he logrado resolverlo
  - Github:
    - Añadir explicación de arquitectura y estructura del código con justificación
    - Añadir Principios y patrones usados y porque fueron necesarios
    - Añadir uso de streams
    - Añadir trozos de código para la explicación o enlace a la página pertinente
    - añadir modelado uml usado en imagen en el readme
    - Añadir los tests y mocks usados
    - 