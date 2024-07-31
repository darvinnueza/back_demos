REGISTRO & DESCUBRIMIENTO DE SERVICIOS
===
Los microservicios son una arquitectura de software que descompone una aplicación en servicios pequeños e independientes que se comunican entre sí. En esta arquitectura, es crucial que los servicios puedan encontrarse y comunicarse de manera eficiente y confiable. Aquí es donde entran en juego el descubrimiento de servicios (Service Discovery) y el registro de servicios (Service Registration).

![](https://drive.google.com/uc?export=view&id=1re85B1G9o0i2mUbMLV6dqFSA13xShR1w)

## SERVICE REGISTRY (REGISTRO DE SERVICIOS)
El registro de servicios es el proceso mediante el cual los servicios se registran en un sistema centralizado o distribuido, llamado registro de servicios (Service Registry). Este registro actúa como un directorio de todos los servicios disponibles y sus ubicaciones actuales. Cada vez que un servicio se inicia, se registra en el registro de servicios proporcionando información como su dirección de red y puertos.

**Características del Registro de Servicios:**
- Centralizado o Distribuido: El registro puede ser centralizado (un solo punto de registro) o distribuido (varios puntos de registro replicados).
- Automatización: Los servicios se registran automáticamente cuando se inician y se desregistran cuando se detienen.
- Salud de Servicios: Algunos registros también pueden realizar chequeos de salud para asegurarse de que los servicios registrados están funcionando correctamente.

## SERVICE DISCOVERY (DESCUBRIMIENTO DE SERVICIOS)
El descubrimiento de servicios es el proceso mediante el cual los servicios encuentran la ubicación de otros servicios necesarios para cumplir con una solicitud. En lugar de tener direcciones de servicios codificadas estáticamente, los servicios utilizan el registro de servicios para obtener la información de ubicación de otros servicios dinámicamente.

**Tipos de Service Discovery:**
1. Cliente a Servidor (Client-Side Discovery):
   - En este enfoque, el cliente es responsable de descubrir la ubicación del servicio. El cliente consulta el registro de servicios para obtener la dirección del servicio deseado y luego se comunica directamente con él.
   - *Ejemplo:* Un cliente de Eureka.
2. Servidor Intermediario (Server-Side Discovery):
   - En este enfoque, un servidor intermediario o balanceador de carga actúa como intermediario. El cliente envía la solicitud al balanceador de carga, que luego se encarga de descubrir la ubicación del servicio y redirigir la solicitud.
   - *Ejemplo:* AWS Elastic Load Balancer, NGINX con Consul.

## BENEFICIOS DEL DESCUBRIMIENTO DE SERVICIOS
- **Despliegue Dinámico:** Los servicios pueden ser escalados dinámicamente sin necesidad de actualizar manualmente las configuraciones de red. 
- **Alta Disponibilidad:** Facilita el balanceo de carga y el failover, mejorando la resiliencia de la aplicación.
- **Desacoplamiento:** Los servicios pueden comunicarse sin necesidad de conocer las ubicaciones exactas de otros servicios, promoviendo una arquitectura más desacoplada.

## DESCUBRIMIENTO DE SERVICIOS Y BALANCEO DE CARGA DEL LADO DEL CLIENTE
En el descubrimiento de servicios del lado del cliente, las aplicaciones son responsables de registrarse en un registro de servicios durante el inicio y desregistrarse al apagarse. Cuando una aplicación necesita comunicarse con un servicio de respaldo, consulta el registro de servicios para obtener la dirección IP asociada. Si hay múltiples instancias del servicio disponibles, el registro devuelve una lista de direcciones IP. La aplicación cliente luego selecciona una de estas direcciones en función de su propia estrategia de balanceo de carga. La figura a continuación ilustra el flujo de trabajo de este proceso.

![](https://drive.google.com/uc?export=view&id=1rkLUxdoJWfRw-EqZrhqlMhUFP8AmkyAU)

El descubrimiento de servicios del lado del cliente es un patrón arquitectónico en el que las aplicaciones cliente son responsables de localizar y conectarse a los servicios de los que dependen. En este enfoque, la aplicación cliente se comunica directamente con un registro de servicios para descubrir las instancias de servicio disponibles y obtener la información necesaria para establecer conexiones.

Aquí están los aspectos clave del descubrimiento de servicios del lado del cliente:
- **Registro de Servicios:** Las aplicaciones cliente se registran en el registro de servicios al iniciarse. Proporcionan información esencial sobre su ubicación, como dirección IP, puerto y metadatos, lo que ayuda a identificar y clasificar el servicio. 
- **Descubrimiento de Servicios:** Cuando una aplicación cliente necesita comunicarse con un servicio específico, consulta el registro de servicios para obtener las instancias disponibles de ese servicio. El registro responde con la información necesaria, como direcciones IP y detalles de conexión. 
- **Balanceo de Carga:** El descubrimiento de servicios del lado del cliente a menudo incluye el balanceo de carga para distribuir la carga de trabajo entre múltiples instancias de servicio. La aplicación cliente puede implementar una estrategia de balanceo de carga para seleccionar la instancia adecuada.

La principal ventaja del descubrimiento de servicios del lado del cliente es que permite implementar balanceo de carga utilizando diversos algoritmos, como *round-robin*, *round-robin ponderado*, *menos conexiones*, o incluso *algoritmos personalizados*. Sin embargo, una desventaja es que este enfoque asigna más responsabilidad a los desarrolladores y añade un servicio adicional que debe ser desplegado y mantenido (el registro de servicios). Las soluciones de descubrimiento del lado del servidor abordan estos problemas.

## SOPORTE DE SPRING CLOUD PARA EL DESCUBRIMIENTO DE SERVICIOS DEL LADO DEL CLIENTE
El proyecto Spring Cloud facilita la configuración del descubrimiento y registro de servicios con los siguientes componentes:

- [Spring Cloud Netflix Eureka](https://spring.io/projects/spring-cloud-netflix): Actúa como agente de descubrimiento de servicios. 
- **Spring Cloud Load Balancer:** Biblioteca para el balanceo de carga del lado del cliente. 
- [Netflix Feign](https://spring.io/projects/spring-cloud-openfeign): Cliente para la búsqueda de servicios entre microservicios.

## PASOS PARA CONSTRUIR UN SERVIDOR EUREKA
A continuación se detallan los pasos para construir una aplicación de servidor Eureka utilizando Eureka de Spring Cloud Netflix:
1. **Configura un nuevo proyecto Spring Boot:** Comienza creando un nuevo proyecto Spring Boot utilizando tu IDE preferido o mediante [Spring Initializr](https://start.spring.io/). Asegúrate de incluir la dependencia de Maven `spring-cloud-starter-netflix-eureka-server` en el fichero `pom.xml`.
   ```
   ...
   <dependencies>
   ...
      <!-- Eureka Server: Servidor para el registro y descubrimiento de servicios en una arquitectura de microservicios en la nube. -->
      <dependency>
          <groupId>org.springframework.cloud</groupId>
          <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
      </dependency>
   ...
   </dependencies>
   ...
   ```
2. **Configura las propiedades:** En el archivo de propiedades o YAML de la aplicación `application.yml`, añade las siguientes configuraciones:
   ```
   server:
     port: 8070
   
   eureka:
     instance:
       hostname: localhost
     client:
       fetchRegistry: false
       registerWithEureka: false
       serviceUrl:
         defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
   ```
3. **Agrega la anotación de Eureka Server:** En la clase principal de tu proyecto, anótala con `@EnableEurekaServer. Esta anotación configura la aplicación para que actúe como un servidor Eureka.
4. **Compila y ejecuta el servidor Eureka:** Compila tu proyecto y ejecútalo como una aplicación Spring Boot. Abre un navegador web y navega a http://localhost:8070. Deberías ver el panel de control del servidor Eureka, que muestra información sobre las instancias de servicios registrados.

  ![](https://drive.google.com/uc?export=view&id=1rhPKt0y6kWsvvnlrqpcPpnqes2p3K1TM)
## PASOS PARA REGISTRAR UN MICROSERVICIO COMO CLIENTE DE EUREKA
A continuación se detallan los pasos para que una aplicación de microservicios se registre y funcione como un cliente de Eureka:
1. **Configura un nuevo proyecto Spring Boot:** Comienza creando un nuevo proyecto Spring Boot utilizando tu IDE preferido o mediante [Spring Initializr](https://start.spring.io/). Asegúrate de incluir la dependencia de Maven `spring-cloud-starter-netflix-eureka-client`.
   ```
   ...
   <dependencies>
   ...
      <!-- Eureka Client: Cliente para el registro y descubrimiento de servicios en el servidor Eureka -->
         <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>>spring-cloud-starter-netflix-eureka-client</artifactId>
         </dependency>
   ...
   </dependencies>
   ...
   ```
2. **Configura las propiedades:** En el archivo de configuración de la aplicación `application.yml`, agrega las siguientes configuraciones:
   ```
   eureka:
     instance:
       preferIpAddress: true
     client:
       fetchRegistry: true
       registerWithEureka: true
       serviceUrl:
         defaultZone: http://localhost:8070/eureka/
   ```
3. **Construye y ejecuta la aplicación:** Compila tu proyecto y ejecútalo como una aplicación Spring Boot. Abre un navegador web y accede a http://localhost:8070. Deberías ver que el microservicio se ha registrado correctamente como una aplicación. Puedes confirmar esto en el panel de control del servidor Eureka.

   []()

## FEIGN CLIENT
Feign Client es una solución que proporciona una manera declarativa de consumir APIs REST. Permite a los desarrolladores definir interfaces Java que representan los servicios a los que desean conectarse. Con Feign, no es necesario manejar manualmente los detalles de la implementación de HTTP, como las solicitudes y respuestas. En su lugar, Feign genera automáticamente la implementación de estas interfaces, haciendo que el código sea más limpio y fácil de mantener.

**Cómo Funciona:**
- **Define una interfaz:** Crea una interfaz Java y usa anotaciones como @FeignClient para especificar el nombre del cliente y la URL del servicio REST. 
- **Implementa el cliente:** OpenFeign genera la implementación del cliente en tiempo de ejecución basado en la interfaz definida. 
- **Inyecta el cliente:** Usa la inyección de dependencias para utilizar el cliente en tu aplicación Spring Boot.

**Ejemplo de Uso:**
```
package com.focus.accounts.service.client;

import com.focus.accounts.dto.CardsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("cards")
public interface CardsFeignClient {

    @GetMapping(value = "/api/fetch", consumes = "application/json")
    public ResponseEntity<CardsDto> fetchCardDetails(@RequestParam String mobileNumber);
}
```

En este ejemplo, `CardsFeignClient` define los métodos para consumir un servicio REST externo. OpenFeign se encarga de implementar estos métodos, permitiendo hacer llamadas HTTP a los endpoints especificados.

## ANEXOS
### AUTOCONSERVACIÓN DE EUREKA PARA EVITAR TRAMPAS EN LA RED
Configuraciones que impactan directa o indirectamente el comportamiento de autoconservación de Eureka
- Indica la frecuencia con la que el cliente envía latidos (heartbeats) al servidor para indicar que sigue activo.
  ```
  eureka:
    instance:
      lease-renewal-interval-in-seconds: 30
  ```
- Indica la duración que el servidor espera desde que recibió el último latido antes de poder expulsar una instancia.
  ```
  eureka:
    instance:
      lease-expiration-duration-in-seconds: 90
  ```
- Un programador (EvictionTask) se ejecuta a esta frecuencia para expulsar instancias del registro si el lease de las instancias ha expirado según lo configurado por lease-expiration-duration-in-seconds. También verificará si el sistema ha alcanzado el modo de autoconservación (comparando latidos reales y esperados) antes de expulsar.
  ```
  eureka:
    server:
      eviction-interval-timer-in-ms: 60000
  ```
- Este valor se usa para calcular el porcentaje esperado de latidos por minuto que Eureka espera recibir.
  ```
  eureka:
    server:
      renewal-percent-threshold: 0.85
  ```
- Un programador se ejecuta a esta frecuencia para calcular los latidos esperados por minuto.
  ```
  eureka:
    server:
      renewal-threshold-update-interval-ms: 900000
  ```
- El modo de autoconservación está habilitado por defecto, pero si necesitas desactivarlo, puedes cambiarlo a `false`.
  ```
  eureka:
    server:
      enable-self-preservation: true
  ```
Esta configuración asegura que todas las propiedades relacionadas con la autoconservación de Eureka estén claramente documentadas y configuradas de manera adecuada en el archivo YAML.

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
docker image push darvinueza/configserver:s5 && docker image push darvinueza/accounts:s5 && docker image push darvinueza/loans:s5 && docker image push darvinueza/cards:s5 && docker image push darvinueza/eurekaserver:s5
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

### DOCUMENTACIÓN
- [Spring Cloud Netflix](https://spring.io/projects/spring-cloud-netflix)
- [Spring Cloud OpenFeign](https://spring.io/projects/spring-cloud-openfeign)
- [Netflix Blog](https://netflixtechblog.com/netflix-oss-and-spring-boot-coming-full-circle-4855947713a0)








