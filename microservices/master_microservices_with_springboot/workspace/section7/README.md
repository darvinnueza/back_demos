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

## IMPLEMENTACIÓN DEL PATRÓN CIRCUIT BREAKER
### SPRING CLOUD GATEWAY

A continuación, se detallan los pasos para aplicar el patron ` Circuit Breaker` dentro del servidor [gatewayserver](gatewayserver):

1. **Agregar dependencia de Maven:** Incluye la dependencia `spring-cloud-starter-circuitbreaker-reactor-resilience4j` en el archivo [pom.xml](gatewayserver/pom.xml) del [gatewayserver](gatewayserver).
   ```
   ...
   <dependencies>
        ...
        <!-- Dependencia para Circuit Breaker con Resilience4j usando Reactor -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-circuitbreaker-reactor-resilience4j</artifactId>
        </dependency>
        ...
   </dependencies>
   ...
   ```
2. **Agregar filtro de Circuit Breaker:** Dentro del método donde estamos creando un bean de `RouteLocator`, añade un filtro de Circuit Breaker como se muestra a continuación y crea una API REST para manejar el URI de fallback `/contactSupport`.
   ```
   ...
   @Bean
   pulic RouteLocator routeConfig(RouteLocatorBuilder routeLocatorBuilder) {
      return routeLocatorBuilder.routes()
                .route(p -> p.path("/focus/accounts/**")
                        .filters(f -> f.rewritePath("/focus/accounts/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("accountsCircuitBreaker")
                                        .setFallbackUri("forward:/contactSupport")))
                        .uri("lb://ACCOUNTS"))
                .build();
   }
   ...
   ```
3. **Agregar propiedades:** Incluye las siguientes propiedades en el archivo [application.yml](gatewayserver/src/main/resources/application.yml).
   ```
   ...
   resilience4j.circuitbreaker:
     configs:
       default:
         slidingWindowSize: 10
         permittedNumberOfCallsInHalfOpenState: 2
         failureRateThreshold: 50
         waitDurationInOpenState: 10000
   ...
   ```
   
### SPRING CLOUD SERVICE

A continuación, se detallan los pasos para aplicar el patron ` Circuit Breaker` dentro del microservicio [accounts](accounts):

1. **Agrega la dependencia de Maven:** Incluye la dependencia `spring-cloud-starter-circuitbreaker-resilience4j` en el archivo `pom.xml` del microservicio [accounts](accounts).
   ```
   ...
   <dependencies>
        ...
        <!-- Dependencia para Circuit Breaker con Resilience4j usando Reactor -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-circuitbreaker-reactor-resilience4j</artifactId>
        </dependency>
        ...
   </dependencies>
   ...
   ```
2. **Realiza los cambios relacionados con el circuito de protección en las interfaces de Feign Client:** Como se muestra a continuación.

   **INTERFAZ DEL CLIENTE FEIGN**
   
   ```
   @FeignClient(name = "loans", fallback = LoansFallback.class)
   public interface ILoansFeignClient {
      @GetMapping(value = "/api/fetch", consumes = "application/json")
      public ResponseEntity<LoansDto> fetchLoanDetails(
            @RequestHeader("correlation-id") String correlationId, 
            @RequestParam String mobileNumber
      );
   }
   ```
   
   **IMPLEMENTACIÓN DEL CLIENTE FEIGN**

   ```
   @Component
   public class LoansFallback implements ILoansFeignClient {
      @Override
      public ResponseEntity<LoansDto> fetchLoanDetails(String correlationId, String mobileNumber) {
         return null;
      }
   }
   ```
3. **Agregar propiedades:** Incluye las siguientes propiedades en los archivos `application.yml` del microservicio [accounts](accounts).
   ```
   ...
   spring:
     cloud:
       openfeign:
         circuitbreaker:
           enabled: true
   ...
   resilience4j.circuitbreaker:
     configs:
       default:
         slidingWindowSize: 10
         permittedNumberOfCallsInHalfOpenState: 2
         failureRateThreshold: 50
         waitDurationInOpenState: 10000
   ...
   ```
   
## CONFIGURACIÓN DE TIMEOUTS HTTP
Los timeouts HTTP (tanto de respuesta como de conexión) pueden configurarse de manera global para todas las rutas y, si es necesario, personalizarse para rutas específicas.
### TIMEOUTS GLOBALES
Para configurar timeouts HTTP globales:
- `connect-timeout` debe especificarse en milisegundos.
- `response-timeout` debe definirse como un java.time.Duration.

Agregar la siguiente configuración al archivo [application.yml](gatewayserver/src/main/resources/application.yml) del proyecto [gatewayserver](gatewayserver):
```
spring:
  cloud:
    gateway:
      ...
      httpclient:
        connect-timeout: 1000
        response-timeout: 2s
```

## PATRÓN DE REINTENTO (RETRY PATTERN)
El patrón de reintento realiza múltiples intentos de reintento configurados cuando un servicio falla temporalmente. Este patrón es muy útil en escenarios como las interrupciones de red, donde la solicitud del cliente puede tener éxito después de un intento de reintento.

Aquí están algunos componentes clave y consideraciones para implementar el patrón de reintento en microservicios:

1. **Lógica de Reintento:** Determina cuándo y cuántas veces intentar nuevamente una operación. Esto puede basarse en factores como códigos de error, excepciones o el estado de la respuesta.

2. **Estrategia de Retroceso (Backoff):** Define una estrategia para retrasar los reintentos con el fin de evitar sobrecargar el sistema o agravar el problema subyacente. Esta estrategia puede implicar aumentar gradualmente el tiempo de espera entre cada reintento, conocido como retroceso exponencial.

3. **Integración con el Circuit Breaker:** Considera combinar el patrón de reintento con el patrón de cortacircuitos (circuit breaker). Si un cierto número de reintentos falla de manera consecutiva, el circuito puede abrirse para evitar intentos adicionales y preservar los recursos del sistema.

4. **Operaciones Idempotentes:** Asegúrate de que la operación reintentada sea idempotente, es decir, que produzca el mismo resultado sin importar cuántas veces se invoque. Esto evita efectos secundarios no deseados o operaciones duplicadas.

---

A continuación, se detallan los pasos para aplicar el patron `Retry` dentro del servidor [gatewayserver](gatewayserver):

1. **Agregar el filtro de reintento:** Dentro del método donde se crea el bean de `RouteLocator`, añade un filtro de reintento como se muestra a continuación.
   ```
   ...
   @Bean
   public RouteLocator routeConfig(RouteLocatorBuilder routeLocatorBuilder) {
      return routeLocatorBuilder.routes()
         ...
         .route(p -> p.path("/focus/loans/**")
            .filters(f -> f.rewritePath("/focus/loans/(?<segment>.*)", "/${segment}")
               .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
               .retry(retryConfig -> retryConfig.setRetries(3).setMethods(HttpMethod.GET).setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true)))
            .uri("lb://LOANS"))
         ...
         .build();
	}
   ...
   ```

---

A continuación, se detallan los pasos para aplicar el patron `Retry` dentro del microservicio [accounts](accounts):

1. **Agregar anotaciones para el patrón de reintento:** Selecciona un método e incluye las anotaciones correspondientes al patrón de reintento junto con las configuraciones que se muestran a continuación. Luego, crea un método de respaldo (fallback) con la misma firma que el método original.

   Este ejemplo muestra cómo aplicar un patrón de reintento utilizando las anotaciones `@Retry` y `@GetMapping` en un servicio de Spring Boot. El método `getBuildInfo` intenta recuperar la información de la versión de la aplicación.

   ```
   @Retry(name = "getBuildInfo", fallbackMethod = "getBuildInfoFallback")
   @GetMapping("/build-info")
   public ResponseEntity<String> getBuildInfo() {
      return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
   }
   ```

   Si el método falla, se invoca automáticamente el método de respaldo `getBuildInfoFallback`, que devuelve una versión predeterminada (0.9). Además, se utiliza un logger para registrar cuando el método de respaldo es invocado.

   ```
   public ResponseEntity<String> getBuildInfoFallback(Throwable throwable) {
      logger.debug("getBuildInfoFallback() method Invoked");
      return ResponseEntity.status(HttpStatus.OK).body("0.9");
   }
   ```
2. **Añadir propiedades:** Incorpora las propiedades que se detallan a continuación en el archivo [application.yml](accounts).
   ```
   ...
   resilience4j.retry:
     configs:
       default:
         maxRetryAttempts: 3
         waitDuration: 500
         enableExponentialBackoff: true
         exponentialBackofffMultiplier: 2
           ignoreExceptions:
             - java.lang.NullPointerException
           retryExceptions:
             - java.util.concurrent.TimeoutException
   ...
   ```

## PATRÓN DE LIMITACIÓN DE TASA (RATE LIMITTER PATTERN)

El patrón Rate Limitter en microservicios es una estrategia para controlar y limitar la cantidad de solicitudes que un servicio o API puede recibir en un período determinado. Su propósito es prevenir abusos, proteger los recursos del sistema y garantizar un uso equitativo del servicio.

En una arquitectura de microservicios, este patrón ayuda a evitar problemas como la degradación del rendimiento y posibles ataques de denegación de servicio (DoS) al restringir el número de solicitudes permitidas por usuario, IP u otro criterio. Si un usuario excede este límite, las solicitudes adicionales son rechazadas con un error HTTP 429 - Too Many Requests.

Este enfoque también permite aplicar diferentes límites según los niveles de suscripción, como básico, premium o empresarial, asegurando la estabilidad y disponibilidad del sistema para todos los usuarios.

---

A continuación, se detallan los pasos para aplicar el patron `RateLimiter` dentro del servidor [gatewayserver](gatewayserver):

1. **Añadir dependencia de Maven:** Incluye la dependencia `spring-boot-starter-data-redis-reactive` en el archivo [pom.xml](gatewayserver/pom.xml) del [gatewayserver](gatewayserver). 
   ```
   ...
   <dependencies>
        ...
        <!-- Dependencia para Redis Reactive -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis-reactive</artifactId>
        </dependency>
        ...
   </dependencies>
   ...
   ```
2. **Añadir filtro de limitación:** En el método donde se está creando un bean de [RouteLocator](gatewayserver/src/main/java/com/focus/gatewayserver/GatewayserverApplication.java), añade un filtro de limitación de tasa como se muestra a continuación. Además, crea los beans de soporte `RedisRateLimiter` y `KeyResolver`.
   ```
   ...
   @Bean
   public RouteLocator eazyBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
      return routeLocatorBuilder.routes()
         .route(p -> p.path("/eazybank/cards/**")
            .filters(f -> f.rewritePath("/eazybank/cards/(?<segment>.*)", "/${segment}")
               .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
               .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter()).setKeyResolver(userKeyResolver())))
            .uri("lb://CARDS"))
         .build();
   }
   
   ...
   
   @Bean
   public RedisRateLimiter redisRateLimiter() {
      return new RedisRateLimiter(1, 1, 1);
   }
   
   @Bean
   KeyResolver userKeyResolver() {
      return exchange -> Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("user")).defaultIfEmpty("anonymous");
   }
   ...
   ```
3. **Contenedor de Redis:** Asegúrate de que un contenedor de Redis esté en funcionamiento. Especifica los detalles de conexión a Redis en el archivo [application.yml](gatewayserver/src/main/resources/application.yml).
   ```
   spring:
     ...
     data:
       redis:
         connect-timeout: 2s
         host: localhost
         port: 6379
         timeout: 1s
   ```



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
Ejecuta un contenedor Redis, exponiendo el puerto 6379 del contenedor en el puerto 6379 de la máquina anfitriona, con el nombre focusredis, en modo desacoplado (-d).
```
docker run -p 6379:6379 --name focusredis -d redis
```
Sube varias imágenes Docker al repositorio [Docker Hub](https://hub.docker.com/).
```
docker image push darvinueza/configserver:s7 && docker image push darvinueza/accounts:s7 && docker image push darvinueza/loans:s7 && docker image push darvinueza/cards:s7 && docker image push darvinueza/eurekaserver:s7 && docker image push darvinueza/gatewayserver:s7
```

### DOCUMENTACIÓN
- [Resilience4J](https://resilience4j.readme.io/)
- [Spring Cloud Gateway](https://docs.spring.io/spring-cloud-gateway/docs/4.0.10-SNAPSHOT/reference/html/#gateway-starter)
- [Spring Cloud Open Feign](https://spring.io/projects/spring-cloud-openfeign)
- [Feign Spring Cloud CircuitBreaker Support](https://docs.spring.io/spring-cloud-openfeign/docs/4.0.7-SNAPSHOT/reference/html/#spring-cloud-feign-circuitbreaker)


https://stripe.com/blog/rate-limiters