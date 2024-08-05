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

![](https://drive.google.com/uc?export=view&id=1rtyH3vkh1K61mjGLz5Tl_q_83G7LeofT)

El diagrama ilustra la arquitectura interna del Spring Cloud Gateway, mostrando cómo las solicitudes de los clientes son manejadas y procesadas a través del sistema antes de llegar a los microservicios de backend. A continuación, se detallan los componentes y su funcionamiento:

1. **Clients**: Representados por dispositivos móviles y aplicaciones web que realizan solicitudes al Spring Cloud Gateway.

2. **Spring Cloud Gateway**: Actúa como intermediario entre los clientes y los microservicios. Este componente está compuesto por varios subcomponentes que gestionan las solicitudes.

    - **Predicates**: Evalúan las solicitudes entrantes para determinar si cumplen con ciertas condiciones predefinidas antes de que se procesen. Estas condiciones pueden incluir validaciones de URL, métodos HTTP, cabeceras, entre otros.

    - **Pre Filters**: Filtros que se aplican a las solicitudes antes de que sean enviadas a los microservicios. Estos filtros pueden realizar diversas funciones como autenticación, autorización, modificación de cabeceras, registro de solicitudes, etc.

3. **Microservicios**: Representan los servicios de backend que manejan la lógica de negocio. Cada microservicio puede estar implementado en diferentes tecnologías, como se muestra en el diagrama con íconos de diferentes servicios.

4. **Post Filters**: Filtros que se aplican a las respuestas provenientes de los microservicios antes de que sean enviadas de vuelta a los clientes. Estos filtros pueden modificar las respuestas, añadir cabeceras, realizar transformaciones de datos, entre otros.

5. **Gateway Handler Mapping using Routing Configs**: Este componente maneja el mapeo de las rutas y la configuración de enrutamiento. Basándose en la solicitud entrante, decide a qué microservicio debe enviarse la solicitud y aplica las configuraciones de enrutamiento necesarias.

### Flujo de Proceso

1. Los clientes envían una solicitud al Spring Cloud Gateway.
2. Las solicitudes pasan por los **Predicates**, que verifican si cumplen con las condiciones predefinidas.
3. Las solicitudes que cumplen las condiciones son procesadas por los **Pre Filters**.
4. Las solicitudes filtradas son enviadas a los microservicios correspondientes.
5. Las respuestas de los microservicios pasan por los **Post Filters**.
6. Finalmente, las respuestas filtradas y modificadas son enviadas de vuelta a los clientes.

## ANEXOS
### DOCUMENTACIÓN
- [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway)