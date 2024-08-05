GATEWAY, ROUTING & CROSS CUTTING CONCERNS EN MICROSERVICES
===
## SPRING CLOUD GATEWAY
Spring Cloud Gateway es una biblioteca basada en Spring que facilita la creación de un API Gateway, actuando como un punto de entrada único para todas las solicitudes que van hacia tus microservicios. Ofrece capacidades de enrutamiento, control de tráfico, y procesamiento de solicitudes, entre otras funciones. Es parte del ecosistema de Spring Cloud, que proporciona herramientas para construir aplicaciones distribuidas y basadas en microservicios.

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

**Exception Handling (Manejo de Excepciones)**: Gestiona errores y excepciones que pueden ocurrir durante el procesamiento de solicitudes, proporcionando respuestas adecuadas a los clientes.
**Circuit Breaker (Interruptor de Circuito)**: Ayuda a prevenir fallos en cascada al cortar el tráfico a servicios problemáticos cuando se detectan fallos repetidos.
**Logging & Monitoring (Monitoreo y Registro)**: Utiliza herramientas de observabilidad como Grafana para registrar y monitorear las solicitudes y respuestas, ayudando a mantener la salud y el rendimiento del sistema.
**Cache**: Almacena respuestas frecuentes en caché para mejorar el rendimiento y reducir la carga en los servicios backend.
