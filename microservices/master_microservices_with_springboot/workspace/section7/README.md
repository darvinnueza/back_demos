MICROSERVICIOS RESILIENTES
===
Garantizar la estabilidad y resiliencia de los sistemas es esencial para ofrecer un servicio confiable. En la arquitectura de microservicios, esto significa gestionar bien las integraciones entre servicios.

Historicamente, **Hystrix** fue la biblioteca principal para construir aplicaciones resilientes en Java, pero desde 2018, [Resilience4J](https://resilience4j.readme.io/) ha emergido como una alternativa popular, ofreciendo un conjunto completo de herramientas para asegurar la robustez de tus microservicios.

## RESILIENCIA CON RESILIENCE4J
Resilience4J es una biblioteca ligera para la tolerancia a fallos, diseñada para programación funcional. Proporciona los siguientes patrones para aumentar la tolerancia a fallos debido a problemas de red o fallos en uno o varios servicios:
- **Circuit Breaker:** Detiene las solicitudes cuando un servicio invocado está fallando.
- **Fallback:** Ofrece rutas alternativas para las solicitudes que fallan.
- **Retry:** Permite reintentos cuando un servicio ha fallado temporalmente.
- **Rate Limit:** Limita el número de llamadas que un servicio puede recibir en un tiempo determinado.
- **Bulkhead:** Restringe el número de solicitudes concurrentes salientes a un servicio para evitar sobrecargas.

## ESCENARIO TÍPICO EN MICROSERVICIOS
Cuando un microservicio responde lentamente o deja de funcionar, puede provocar la saturación de los hilos de recursos en el servidor de borde y en los servicios intermedios. Esto, a su vez, afecta negativamente el rendimiento general de la red de microservicios.

![](https://drive.google.com/uc?export=view&id=1s7aFfZKwa1G0CtU9YVttcYLYLF0KqQ6Q)

Para manejar este tipo de situaciones, podemos utilizar el patrón Circuit Breaker.

## PATRÓN CIRCUIT BREAKER
El patrón Circuit Breaker se utiliza para detener las solicitudes a un servicio cuando este está fallando, evitando así la saturación de recursos y mejorando la resiliencia del sistema.

El CircuitBreaker se implementa mediante una máquina de estados finitos con tres estados normales: CERRADO, ABIERTO y SEMI_ABIERTO, y dos estados especiales: DESACTIVADO y FORZADO_ABIERTO.

![](https://drive.google.com/uc?export=view&id=1sCXnzgriaH1sz0wVj8iGdF3m35X3GngI)

1. **CLOSED (CERRADO):** En este estado, el Circuit Breaker permite que las solicitudes pasen al servicio. Se considera que el sistema está funcionando correctamente.
2. **OPEN (ABIERTO):** En este estado, el Circuit Breaker ha detectado que el servicio está fallando repetidamente. Por lo tanto, bloquea todas las solicitudes para evitar que el servicio sobrecargado reciba más tráfico y para proteger el sistema en su conjunto.
3. **HALF_OPEN (SEMI_ABIERTO):** En este estado de transición, el Circuit Breaker permite un número limitado de solicitudes para probar si el servicio ha recuperado su estabilidad. Si las solicitudes en este estado son exitosas, el Circuit Breaker puede volver al estado CLOSED. Si aún hay fallos, regresa al estado OPEN.

Además de estos estados normales, hay dos estados especiales:

- **DISABLED (DESACTIVADO):** En este estado, el Circuit Breaker no realiza ninguna acción y permite que todas las solicitudes pasen al servicio sin intervención. Esto se puede utilizar para desactivar temporalmente el Circuit Breaker por razones de mantenimiento o configuración.
- **FORCED_OPEN (FORZADO_ABIERTO):** Este estado es similar al estado OPEN, pero se activa manualmente, independientemente del estado actual del Circuit Breaker. Se utiliza para forzar la interrupción del servicio cuando se necesita una intervención rápida y no se quiere esperar a que el Circuit Breaker entre en estado OPEN por sí mismo.

## ANEXOS
### DOCUMENTACIÓN
- [Resilience4J](https://resilience4j.readme.io/)