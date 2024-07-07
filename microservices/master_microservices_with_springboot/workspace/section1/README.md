CREACIÓN DE MICROSERVICIOS USANDO SPRING BOOT
==
Spring Boot es un marco poderoso y ampliamente utilizado para desarrollar aplicaciones Java. Con su enfoque simplificado y su compatibilidad con diversas herramientas y bibliotecas, Spring Boot se ha convertido en una opción popular para construir microservicios escalables y eficientes. Una de las maneras más rápidas y efectivas de iniciar un proyecto con Spring Boot es utilizando Spring Initializr, una herramienta que permite generar rápidamente la estructura básica de un proyecto Spring Boot con las dependencias necesarias.

## MICROSERVICIOS
En esta sección, presentamos una serie de microservicios fundamentales que forman la columna vertebral de muchas aplicaciones financieras y de gestión de usuarios. Cada microservicio está diseñado para manejar una funcionalidad específica, garantizando así una arquitectura modular, escalable y mantenible. A continuación, se describen brevemente los microservicios que hemos creado:
- [Accounts](accounts): Este microservicio se encarga de gestionar las cuentas de usuario. Proporciona funcionalidades como la creación, actualización y consulta de cuentas, permitiendo un manejo eficiente de la información de los usuarios.
- [Cards](cards) El microservicio de tarjetas administra todo lo relacionado con las tarjetas de crédito y débito de los usuarios. Incluye operaciones como la emisión de nuevas tarjetas, la consulta de saldo, y la gestión de transacciones, asegurando una experiencia fluida y segura.
- [Loans](loans) Este microservicio está diseñado para la gestión de préstamos. Facilita la solicitud, aprobación, y seguimiento de préstamos, proporcionando a los usuarios un sistema claro y accesible para manejar sus necesidades crediticias.

Cada uno de estos microservicios se ha desarrollado con Spring Boot, utilizando Spring Initializr para la configuración inicial, asegurando así una integración eficiente y un desarrollo ágil.

### SWAGGER
Swagger proporciona una interfaz de usuario interactiva para explorar y probar las APIs de nuestros microservicios. A través de estas interfaces, puedes acceder a la documentación detallada de cada microservicio y realizar llamadas a las APIs de manera sencilla.
- [Accounts](http://localhost:8080/swagger-ui/index.html): Explora y prueba las operaciones disponibles para gestionar cuentas de usuario, incluyendo la creación, actualización y consulta de cuentas.
- [Cards](http://localhost:9000/swagger-ui/index.html): Accede a la documentación y pruebas para gestionar tarjetas de crédito y débito, como la emisión de nuevas tarjetas y la consulta de saldo.
- [Loans](http://localhost:8090/swagger-ui/index.html): Consulta la documentación y realiza pruebas de las operaciones relacionadas con la gestión de préstamos, incluyendo la solicitud, aprobación y seguimiento de préstamos.

### H2-CONSOLE
H2 Console es una herramienta web que te permite interactuar con las bases de datos en memoria de nuestros microservicios. Puedes usarla para consultar y gestionar datos en las bases de datos de prueba para cada microservicio.

JDBC URL: `jdbc:h2:mem:testdb` <br/>
User Name: `sa` <br/>
Password: *(vacío)*
- [Accounts](http://localhost:8080/h2-console/login.jsp): Accede a la base de datos en memoria para el microservicio de cuentas. Puedes consultar y gestionar los datos relacionados con las cuentas de usuario.
- [Cards](http://localhost:9000/h2-console/login.jsp): Explora la base de datos en memoria para el microservicio de tarjetas. Aquí puedes ver y modificar datos de tarjetas de crédito y débito.
- [Loans](http://localhost:8090/h2-console/login.jsp): Interactúa con la base de datos en memoria para el microservicio de préstamos. Consulta y gestiona la información relacionada con los préstamos.

Este acceso te permitirá verificar y manipular los datos almacenados en las bases de datos de prueba de cada microservicio.

## ANEXOS
- [Spring Initializr](https://start.spring.io/)
- [Modelmapper](https://modelmapper.org/)
- [Mapstruct](https://mapstruct.org/)