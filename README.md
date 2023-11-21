# TIC Software.ATN

## Descripción
proporcionar un servicio de software para un manejo sencillo y eficiente de la realidad de un aeropuerto y las aerolineas.
Es decir el desarrollo y la impmentacion de registro de vuelos, con sus caracteristicas 
(codific internacionales,numero de vuelo,  horas despegue, llegada, origen y destino),
y los usuarios del aeropuerto con sus funcionalidades y caracteristicas. 

## Requisitos Previos
Asegúrate de tener instalados los siguientes elementos antes de comenzar:

- PostgreSQL: [Descargar PostgreSQL](https://www.postgresql.org/download/)
- DBeaver: [Descargar DBeaver](https://dbeaver.io/download/)

## Configuración de la Base de Datos
1. **Crear Base de Datos:**
   - Ejecuta el script `nombre_del_script.sql` en tu herramienta de gestión de bases de datos o en la línea de comandos de PostgreSQL para crear la base de datos.

    ```sql
    CREATE DATABASE nombre_de_tu_base_de_datos;
    ```

2. **Configurar Conexión en DBeaver:**
   - Abre DBeaver y configura una nueva conexión utilizando los detalles de tu base de datos recién creada.

## Inicialización del Admin General
Para comenzar, necesitas crear un usuario administrador general en la tabla `usuario_general`. 
Ejecuta el siguiente script SQL en tu herramienta de gestión de bases de datos:

```sql
INSERT INTO usuario_general(pasaporte, apellido, contrasena, email, nombre, tipo)
VALUES ('1', 'dios', '1234', 'emailGeneral', 'dios', 'DIOS');

## Controles
EL codigo IATA de aeropuerto son 2 letras. EL codigo ICAO de aerolinea son 3 letras y eL codigo IATA aeroinea son 2 letras
