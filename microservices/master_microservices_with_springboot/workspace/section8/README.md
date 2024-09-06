MONITOREO Y OBSERVABILIDAD DE MICROSERVICIOS
===
## ¿QUÉ ES LA OBSERVABILIDAD?
La observabilidad es la capacidad de comprender el estado interno de un sistema al observar sus salidas. En el contexto de microservicios, la observabilidad se logra mediante la recolección y análisis de datos provenientes de diversas fuentes, como métricas, logs y trazas.

Los tres pilares de la observabilidad son:
- **Métricas:** Son mediciones cuantitativas sobre el estado de salud del sistema. Permiten monitorear aspectos como el uso de CPU, uso de memoria y tiempos de respuesta.
- **Logs:** Son registros de eventos que ocurren dentro de un sistema. Sirven para rastrear errores, excepciones y otros eventos inesperados. 
- **Trazas:** Son registros del recorrido que realiza una solicitud a través del sistema. Ayudan a rastrear el rendimiento de una solicitud e identificar cuellos de botella.

Al recopilar y analizar los datos de estas tres fuentes, es posible obtener una comprensión profunda del estado interno de la arquitectura de microservicios. Este conocimiento permite identificar y solucionar problemas, mejorar el rendimiento y garantizar la salud general del sistema.

## ¿QUÉ ES EL MONITOREO?
El monitoreo en microservicios implica revisar los datos de telemetría disponibles de la aplicación y definir alertas para estados de fallo conocidos. Este proceso consiste en recopilar y analizar datos del sistema para identificar y solucionar problemas, así como rastrear la salud de cada microservicio y de la red de microservicios en su conjunto.

El monitoreo en microservicios es crucial porque permite:
- **Identificar y solucionar problemas:** Al recopilar y analizar los datos de tus microservicios, puedes detectar problemas antes de que provoquen caídas o interrupciones en el servicio. 
- **Rastrear la salud de tus microservicios:** El monitoreo te ayuda a seguir de cerca el estado de tus microservicios, permitiéndote identificar aquellos que están teniendo un bajo rendimiento o que presentan fallos. 
- **Optimizar tus microservicios:** A través del monitoreo, puedes identificar áreas de mejora para optimizar el rendimiento y la fiabilidad de tus microservicios.

El monitoreo y la observabilidad son dos caras de la misma moneda. Ambos dependen de los mismos tipos de datos de telemetría para proporcionar una visión clara de los sistemas distribuidos. Estos datos — métricas, trazas y logs — son comúnmente conocidos como los tres pilares de la observabilidad.

## OBSERVABILIDAD VS MONITOREO
### MONITOREO
El monitoreo se enfoca en la recolección de datos sobre el estado de un sistema, permitiendo identificar y solucionar problemas conocidos. Se basa principalmente en métricas, trazas y logs.
### OBSERVABILIDAD
La observabilidad va un paso más allá, proporcionando la capacidad de comprender el estado interno del sistema a partir de los datos observados. Utiliza métricas, trazas, logs y otras fuentes de datos para ofrecer una visión más proactiva y detallada del sistema.
### COMPARACIÓN: MONITOREO VS OBSERVABILIDAD
| **Característica** | **Monitoreo**                       | **Observabilidad**                              |
|--------------------|-------------------------------------|-------------------------------------------------|
| **Propósito**      | Identificar y solucionar problemas | Comprender el estado interno de un sistema     |
| **Datos**          | Métricas, trazas y logs             | Métricas, trazas, logs y otras fuentes de datos |
| **Objetivo**       | Detectar problemas                  | Entender cómo funciona el sistema               |
| **Enfoque**        | Reactivo                            | Proactivo                                       |

En otras palabras, el monitoreo se trata de recolectar datos, mientras que la observabilidad consiste en entender esos datos.

Mientras el monitoreo reacciona ante los problemas, la observabilidad permite resolverlos en tiempo real.
