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

## ACTUALIZAR LAS CONFIGURACIONES EN TIEMPO DE EJECUCIÓN 
### UTILIZANDO LA RUTA /refresh
¿Qué sucede cuando se realizan nuevas actualizaciones en el repositorio de Git que soporta el servicio de configuración? En una aplicación Spring Boot típica, modificar una propiedad requeriría un reinicio. Sin embargo, [Spring Cloud Config](https://docs.spring.io/spring-cloud-config/docs/current/reference/html/) introduce la capacidad de actualizar dinámicamente la configuración en las aplicaciones cliente durante el tiempo de ejecución. Cuando se realiza un cambio en el repositorio de configuración, todas las aplicaciones integradas conectadas al servidor de configuración pueden ser notificadas, lo que les solicita recargar las secciones relevantes afectadas por la modificación de la configuración.

Veamos un enfoque para actualizar la configuración, que implica enviar una solicitud `POST` específica a una instancia en ejecución del microservicio. Esta solicitud iniciará la recarga de los datos de configuración modificados, permitiendo una recarga en caliente de la aplicación. A continuación, se presentan los pasos a seguir:

1. **Agregar la dependencia de actuator en los servicios Config Client:** Agrega la dependencia de Spring Boot Actuator en el archivo pom.xml de los microservicios individuales como cuentas, préstamos, tarjetas para exponer el endpoint `/refresh`.
2. **Habilitar la API /refresh:** La biblioteca Spring Boot Actuator proporciona un endpoint de configuración llamado `/actuator/refresh` que puede activar un evento de actualización. De forma predeterminada, este endpoint no está expuesto, por lo que debes habilitarlo explícitamente en el archivo [application.yml](configserver/src/main/resources/application.yml) utilizando la siguiente configuración:
   ```
   management:
     endpoints:
       web:
         exposure:
           include: refresh
   ```

![](https://drive.google.com/uc?export=view&id=1p_KOOGT96DVwIB4oFzS4ESjrVkITTkgx)

Invocaste el mecanismo de actualización en el Servicio de Cuentas y funcionó correctamente, ya que solo había una aplicación con una instancia. Sin embargo, en un entorno de producción, donde puede haber múltiples servicios, ¿qué sucederá? Si un proyecto tiene muchos microservicios, el equipo podría preferir un método automatizado y eficiente para actualizar la configuración en lugar de activar manualmente cada instancia de aplicación. Vamos a evaluar las otras opciones disponibles.

## UTILIZANDO SPRING CLOUD BUS
[Spring Cloud](https://spring.io/projects/spring-cloud-bus) facilita la comunicación fluida entre todas las instancias de la aplicación conectadas al establecer un canal conveniente de transmisión de eventos. Ofrece una implementación para brokers AMQP, como `RabbitMQ` y `Kafka`, permitiendo una comunicación eficiente en todo el ecosistema de la aplicación.

A continuación se presentan los pasos a seguir:

1. **Agregar la dependencia de Actuator en los servicios del Config Server y Config Client:** Agrega la dependencia de Spring Boot Actuator en el archivo `pom.xml` de cada microservicio, como accounts, loans y cards, para exponer el endpoint `/busrefresh`.
2. **Habilitar la API /busrefresh:** La biblioteca Spring Boot Actuator proporciona un endpoint de configuración llamado `/actuator/busrefresh` que puede activar un evento de actualización. De forma predeterminada, este endpoint no está expuesto, por lo que debes habilitarlo explícitamente en el archivo [application.yml](configserver/src/main/resources/application.yml) utilizando la configuración a continuación:
   ```
   management:
     endpoints:
       web:
         exposure:
           include: busrefresh
   ```







```

```

http://localhost:8071/accounts/dev
http://localhost:8071/accounts/qa
http://localhost:8071/accounts/prod

http://localhost:8071/cards/dev
http://localhost:8071/cards/qa
http://localhost:8071/cards/prod

http://localhost:8071/loans/dev
http://localhost:8071/loans/qa
http://localhost:8071/loans/prod

## ANEXOS
- [Spring Cloud Config](https://docs.spring.io/spring-cloud-config/docs/current/reference/html/)