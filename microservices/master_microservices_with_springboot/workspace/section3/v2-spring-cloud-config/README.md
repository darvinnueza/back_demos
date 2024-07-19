SPRING CLOUD CONFIG
===
Un servidor de configuración centralizado con [Spring Cloud Config](https://docs.spring.io/spring-cloud-config/docs/current/reference/html/) puede superar todos los inconvenientes que discutimos en la diapositiva anterior. [Spring Cloud Config](https://docs.spring.io/spring-cloud-config/docs/current/reference/html/) proporciona soporte tanto del lado del servidor como del cliente para la configuración externalizada en un sistema distribuido. Con el Config Server, tienes un lugar central para gestionar propiedades externas para aplicaciones en todos los entornos.

La configuración centralizada gira en torno a dos elementos clave:

- Un almacén de datos diseñado para manejar datos de configuración, asegurando durabilidad, gestión de versiones y, potencialmente, control de acceso.
- Un servidor que supervisa los datos de configuración dentro del almacén de datos, facilitando su gestión y distribución a múltiples aplicaciones.


## ¿QUÉ ES SPRING CLOUD?
Spring Cloud proporciona marcos de trabajo para que los desarrolladores construyan rápidamente algunos de los patrones comunes de los microservicios.

### SPRING CLOUD CONFIG
Asegura que, sin importar cuántas instancias de microservicios pongas en funcionamiento, siempre tendrán la misma configuración.

### SERVICE REGISTRATION & DISCOVERY
Nuevos servicios serán registrados y posteriormente los consumidores podrán invocarlos a través de un nombre lógico en lugar de una ubicación física.

### ROUTING & TRACING
Asegura que todas las llamadas a tus microservicios pasen a través de una única "puerta de entrada" antes de que se invoque el servicio objetivo, y que lo mismo sea rastreado.

### LOAD BALANCING
El balanceo de carga distribuye de manera eficiente el tráfico de red entre múltiples servidores de backend o un grupo de servidores.

### SPRING CLOUD SECURITY
Proporciona características relacionadas con la seguridad basada en tokens en aplicaciones/Microservicios de Spring Boot.

### DISTRIBUTED TRACING & MESSAGING
Incorpora componentes que ayudan a comprender las interacciones complejas entre servicios y la comunicación asíncrona, permitiendo sistemas escalables y resilientes.


```

```

## ANEXOS
- [Spring Cloud Config](https://docs.spring.io/spring-cloud-config/docs/current/reference/html/)