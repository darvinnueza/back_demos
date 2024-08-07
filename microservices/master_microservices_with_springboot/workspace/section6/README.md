GATEWAY, ROUTING & CROSS CUTTING CONCERNS EN MICROSERVICES
===

![](https://drive.google.com/uc?export=view&id=1ruR9bkVHXBzAch_VHgAIeaoi4x4akMIz)

## ALGUNAS TAREAS IMPORTANTES QUE REALIZA EL API GATEWAY
Un API Gateway actúa como un intermediario entre los clientes y los servicios de backend, proporcionando una capa adicional de seguridad, control y eficiencia. Este componente es esencial en arquitecturas de microservicios, ya que facilita la gestión de las peticiones y respuestas entre los diversos servicios, mejorando así el rendimiento y la seguridad de la aplicación.

![](https://drive.google.com/uc?export=view&id=1rstuE2b7tWQfjNDHx5yTfnDpor2v-AqY)
 
### EXPLICACIÓN DE LAS TAREAS DEL API GATEWAY

1. **Clients (Clientes)**: Los clientes pueden ser aplicaciones web, móviles o APIs que realizan solicitudes al backend a través del API Gateway.

2. **Request Validation (Validación de Solicitudes)**: El API Gateway verifica que las solicitudes entrantes cumplan con ciertos criterios antes de procesarlas, asegurando que sean válidas y seguras.

3. **Include & Exclude List (Lista de Inclusión y Exclusión)**: El Gateway filtra las solicitudes permitidas y bloquea las no permitidas basándose en listas de inclusión y exclusión predefinidas.

4. **AuthN & AuthZ (Autenticación y Autorización)**: Se encarga de autenticar a los usuarios y autorizar sus solicitudes para asegurarse de que solo los usuarios legítimos puedan acceder a los recursos.

5. **Rate Limit (Límite de Tasa)**: Controla la cantidad de solicitudes que un cliente puede realizar en un período de tiempo determinado, protegiendo así contra abusos y ataques de denegación de servicio.

6. **Dynamic Routing (Enrutamiento Dinámico)**: Dirige las solicitudes a los servicios adecuados según la lógica de enrutamiento configurada.

7. **Service Discovery (Descubrimiento de Servicios)**: Facilita la localización de servicios backend dinámicamente, permitiendo que el API Gateway enrute las solicitudes correctamente.

8. **Modify Req/Res (Modificación de Solicitudes/Respuestas)**: Permite la modificación de solicitudes entrantes y respuestas salientes para adaptarlas a los requisitos específicos del cliente o del servicio.

9. **Protocol Conversion (Conversión de Protocolo)**: Convierte protocolos de comunicación para asegurar la compatibilidad entre clientes y servicios backend.

10. **Otros:**
    - **Exception Handling (Manejo de Excepciones)**: Gestiona errores y excepciones que pueden ocurrir durante el procesamiento de solicitudes, proporcionando respuestas adecuadas a los clientes.
    - **Circuit Breaker (Interruptor de Circuito)**: Ayuda a prevenir fallos en cascada al cortar el tráfico a servicios problemáticos cuando se detectan fallos repetidos.
    - **Logging & Monitoring (Monitoreo y Registro)**: Utiliza herramientas de observabilidad como Grafana para registrar y monitorear las solicitudes y respuestas, ayudando a mantener la salud y el rendimiento del sistema.
    - **Cache**: Almacena respuestas frecuentes en caché para mejorar el rendimiento y reducir la carga en los servicios backend.

## SPRING CLOUD GATEWAY
Spring Cloud Gateway es una biblioteca basada en Spring que facilita la creación de un API Gateway, actuando como un punto de entrada único para todas las solicitudes que van hacia tus microservicios. Ofrece capacidades de enrutamiento, control de tráfico, y procesamiento de solicitudes, entre otras funciones. Es parte del ecosistema de Spring Cloud, que proporciona herramientas para construir aplicaciones distribuidas y basadas en microservicios.

### ARQUITECTURA INTERNA

![](https://drive.google.com/uc?export=view&id=1rvEv6wPaOOWq9WW7o56m01m0jTeEPaaI)

El diagrama ilustra la arquitectura interna del Spring Cloud Gateway, mostrando cómo las solicitudes de los clientes son manejadas y procesadas a través del sistema antes de llegar a los microservicios de backend. A continuación, se detallan los componentes y su funcionamiento:

1. **Clients**: Representados por dispositivos móviles y aplicaciones web que realizan solicitudes al Spring Cloud Gateway.

2. **Spring Cloud Gateway**: Actúa como intermediario entre los clientes y los microservicios. Este componente está compuesto por varios subcomponentes que gestionan las solicitudes.

    - **Predicates**: Evalúan las solicitudes entrantes para determinar si cumplen con ciertas condiciones predefinidas antes de que se procesen. Estas condiciones pueden incluir validaciones de URL, métodos HTTP, cabeceras, entre otros.

    - **Pre Filters**: Filtros que se aplican a las solicitudes antes de que sean enviadas a los microservicios. Estos filtros pueden realizar diversas funciones como autenticación, autorización, modificación de cabeceras, registro de solicitudes, etc.

3. **Microservicios**: Representan los servicios de backend que manejan la lógica de negocio. Cada microservicio puede estar implementado en diferentes tecnologías, como se muestra en el diagrama con íconos de diferentes servicios.

4. **Post Filters**: Filtros que se aplican a las respuestas provenientes de los microservicios antes de que sean enviadas de vuelta a los clientes. Estos filtros pueden modificar las respuestas, añadir cabeceras, realizar transformaciones de datos, entre otros.

5. **Gateway Handler Mapping using Routing Configs**: Este componente maneja el mapeo de las rutas y la configuración de enrutamiento. Basándose en la solicitud entrante, decide a qué microservicio debe enviarse la solicitud y aplica las configuraciones de enrutamiento necesarias.

### FLUJO DE PROCESO

1. Los clientes envían una solicitud al Spring Cloud Gateway.
2. Las solicitudes pasan por los **Predicates**, que verifican si cumplen con las condiciones predefinidas.
3. Las solicitudes que cumplen las condiciones son procesadas por los **Pre Filters**.
4. Las solicitudes filtradas son enviadas a los microservicios correspondientes.
5. Las respuestas de los microservicios pasan por los **Post Filters**.
6. Finalmente, las respuestas filtradas y modificadas son enviadas de vuelta a los clientes.

## PASOS PARA CREAR SPRING CLOUD GATEWAY
A continuación, se describen los pasos para construir una aplicación que funcione como un API Gateway utilizando Spring Cloud Gateway y registrarla como cliente en Eureka:
1. **Configura un nuevo proyecto Spring Boot:** Comienza creando un nuevo proyecto Spring Boot utilizando tu IDE preferido o mediante [Spring Initializr](https://start.spring.io/). Incluye las dependencias de Maven `spring-cloud-starter-gateway`, `spring-cloud-starter-config` y `spring-cloud-starter-netflix-eureka-client`.

   ```
   ...
   <dependencies>
        ...
        <!-- Gateway: Es una biblioteca de Spring Cloud para crear Gateways de microservicios de manera fácil y eficiente utilizando Spring Cloud Gateway.-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-gateway</artifactId>
        </dependency>
   
        <!-- Starter Config -->
        <dependency>
            <groupId>>org.springframework.cloud</groupId>
            <artifactId>>spring-cloud-starter-config</artifactId>
        </dependency>
   
        <!-- Eureka Client -->
        <dependency>
            <groupId>>org.springframework.cloud</groupId>
            <artifactId>>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        ...
   </dependencies>
   ...
   ```
2. **Configura las propiedades:** En el archivo de propiedades o YAML de la aplicación [application.yml](gatewayserver/src/main/resources/application.yml), añade las siguientes configuraciones:

   ```
   spring:
     cloud:
       gateway:
         discovery:
           locator:
             enabled: false
             lowerCaseServiceId: true
   
   eureka:
     instance:
       preferIpAddress: true
     client:
       fetchRegistry: true
       registerWithEureka: true
       serviceUrl:
         defaultZone: http://localhost:8070/eureka/
   ```
3. **Configurar el enrutamiento:** Realiza las configuraciones de enrutamiento utilizando `RouteLocatorBuilder` como se muestra a continuación:

   ```
   ...
   @Bean
   public RouteLocator routeConfig(RouteLocatorBuilder routeLocatorBuilder) {
     return routeLocatorBuilder.routes()
         .route(p -> p.path("/focus/accounts/**")
             .filters(f -> f.rewritePath("/focus/accounts/(?<segment>.*)", "/${segment}")
                 .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
             .uri("lb://ACCOUNTS"))
         .route(p -> p.path("/focus/loans/**")
             .filters(f -> f.rewritePath("/focus/loans/(?<segment>.*)", "/${segment}")
                 .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
             .uri("lb://LOANS"))
         .route(p -> p.path("/focus/cards/**")
             .filters(f -> f.rewritePath("/focus/cards/(?<segment>.*)", "/${segment}")
                 .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
             .uri("lb://CARDS"))
         .build();
   }
   ...
   ``` 
4. **Compila y ejecuta la aplicación:** Construye tu proyecto y ejecútalo como una aplicación Spring Boot. Invoca las APIs utilizando `http://localhost:8072`, que es la ruta del gateway.

## CROSS-CUTTING CONCERNS
En el contexto de microservicios, Cross-Cutting Concerns (Preocupaciones Transversales) se refiere a aquellos aspectos o funcionalidades que afectan a múltiples microservicios y que deben ser gestionados de manera consistente a lo largo de toda la arquitectura. Dado que cada microservicio es una unidad independiente con su propia lógica y base de datos, estas preocupaciones transversales deben ser tratadas de manera centralizada o uniforme para asegurar la coherencia y eficiencia del sistema en su conjunto.

### EJEMPLOS DE CROSS-CUTTING CONCERNS
#### Tracing (Trazado):
- **Propósito:** Permite seguir el rastro de una solicitud a través de diferentes microservicios para entender cómo fluye a través del sistema y diagnosticar problemas. 
- **Implementación:** Usualmente se implementa mediante la generación y propagación de IDs de correlación a lo largo de las solicitudes y respuestas entre servicios.
#### Logging (Registro):
- **Propósito:** Captura y almacena información sobre el funcionamiento de los microservicios, incluyendo errores, advertencias y eventos importantes.
- **Implementación:** Puede requerir un sistema centralizado de registro para consolidar y analizar los logs de todos los microservicios, utilizando herramientas como ELK Stack (Elasticsearch, Logstash, Kibana) o soluciones en la nube.
#### Security (Seguridad):
- **Propósito:** Asegura que solo los usuarios y servicios autorizados puedan acceder a los recursos y operaciones de los microservicios.
- **Implementación:** Puede incluir autenticación, autorización, y políticas de seguridad que se aplican a nivel de gateway o mediante servicios de seguridad centralizados.

## DIAGRAMA DE SECUENCIA PARA LA IMPLEMENTACIÓN DE CROSS-CUTTING CONCERNS

![](https://drive.google.com/uc?export=view&id=1rvz5_PF7fsqv8ilo7mI3cq2rjWJGjksR)

1. **Clients (Clientes):**
    - El cliente hace una solicitud para obtener detalles del cliente usando el endpoint `/fetchCustomerDetails` proporcionando el `mobileNumber`.

2. **API Gateway:**
    - El gateway recibe la solicitud y genera un `correlationId` que será utilizado para rastrear esta solicitud a través de los diferentes microservicios.
    - Propaga el `correlationId` y el `mobileNumber` a los microservicios correspondientes usando el endpoint `/fetchCustomerDetails`.

3. **Microservicio de Cuentas (Accounts Microservice):**
    - Recibe la solicitud con `correlationId` y `mobileNumber` y realiza alguna operación para obtener detalles de la cuenta.
    - Luego, envía la solicitud al microservicio de Tarjetas para obtener detalles adicionales.

4. **Microservicio de Tarjetas (Cards Microservice):**
    - Recibe la solicitud con `correlationId` y `mobileNumber` para obtener detalles de la tarjeta.
    - Envia la respuesta de vuelta al microservicio de Cuentas con el `correlationId`.

5. **Microservicio de Préstamos (Loans Microservice):**
    - Similarmente, recibe la solicitud con `correlationId` y `mobileNumber` para obtener detalles de préstamos.
    - Envia la respuesta con `correlationId`.

6. **Respuestas:**
    - Las respuestas de los microservicios de Tarjetas y Préstamos son enviadas de vuelta al microservicio de Cuentas, y de ahí al API Gateway.
    - Finalmente, el API Gateway responde al cliente incluyendo el `correlationId` para que pueda ser rastreado.

## ANEXOS
### COMANDOS COMUNES
#### HOOKDECK
Instala Hookdeck usando Homebrew.
```
brew install hookdeck/hookdeck/hookdeck
```
Inicia sesión en tu cuenta de Hookdeck.
```
hookdeck login
```
Escucha eventos en el puerto `8071` para la fuente `focus-banck-source`.
```
hookdeck listen 8071 focus-banck-source
```
#### MAVEN
Compila el proyecto Maven y construye una imagen Docker usando Jib.
```
mvn compile jib:dockerBuild
```
#### DOCKER
Sube varias imágenes Docker al repositorio Docker Hub.
```
docker image push darvinueza/configserver:s6 && docker image push darvinueza/accounts:s6 && docker image push darvinueza/loans:s6 && docker image push darvinueza/cards:s6 && docker image push darvinueza/eurekaserver:s6 && docker image push darvinueza/gatewayserver:s6
```
#### DOCKER COMPOSE
Inicia todos los servicios definidos en el archivo `docker-compose.yml` en segundo plano.
```
docker compose up -d
```
Detiene y elimina los contenedores, redes y volúmenes definidos en el archivo `docker-compose.yml`.
```
docker compose down
```
### SWAGGER
- [Accounts Microservice](http://localhost:8080/swagger-ui/index.html)
- [Loans Microservice](http://localhost:8090/swagger-ui/index.html)
- [Cards Microservice](http://localhost:9000/swagger-ui/index.html)
### CONFIG SERVER
#### ACCOUNTS ENVIRONMENTS
- [Desarrollo](http://localhost:8071/accounts/dev)
- [Calidad](http://localhost:8071/accounts/qa)
- [Producción](http://localhost:8071/accounts/prod)
#### LOANS ENVIRONMENTS
- [Desarrollo](http://localhost:8071/loans/dev)
- [Calidad](http://localhost:8071/loans/qa)
- [Producción](http://localhost:8071/loans/prod)
#### CARDS ENVIRONMENTS
- [Desarrollo](http://localhost:8071/loans/dev)
- [Calidad](http://localhost:8071/loans/qa)
- [Producción](http://localhost:8071/loans/prod)
## EUREKA SERVER
- [Eureka](http://localhost:8070/)
### RABITMQ
- [RabbitMQ](http://localhost:15672/#/)
### UNIT TEST
- [Postman](unit_test)
### DOCUMENTACIÓN
- [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway)