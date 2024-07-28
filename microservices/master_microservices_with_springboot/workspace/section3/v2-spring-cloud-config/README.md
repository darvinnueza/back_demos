SPRING CLOUD CONFIG
===

![](https://drive.google.com/uc?export=view&id=1pjQ0YMo0E1nJnrO5uDiz6I33VY44M99j)

## SPRING CLOUD CONFIG
[Spring Cloud Config](https://docs.spring.io/spring-cloud-config/docs/current/reference/html) es una herramienta esencial para la gestión de configuraciones centralizadas en entornos de microservicios. Ofrece soporte tanto en el servidor como en el cliente para configuraciones externalizadas, permitiendo un control centralizado y dinámico de las propiedades de configuración de las aplicaciones distribuidas. Esto garantiza una gestión eficiente y coherente de las configuraciones a lo largo de todos los microservicios y entornos.

## ACTUALIZAR LAS CONFIGURACIONES EN TIEMPO DE EJECUCIÓN 
### UTILIZANDO LA RUTA /refresh

![](https://drive.google.com/uc?export=view&id=1p_KOOGT96DVwIB4oFzS4ESjrVkITTkgx)

¿Qué sucede cuando se realizan nuevas actualizaciones en el repositorio de Git que soporta el servicio de configuración? En una aplicación Spring Boot típica, modificar una propiedad requeriría un reinicio. Sin embargo, [Spring Cloud Config](https://docs.spring.io/spring-cloud-config/docs/current/reference/html/) introduce la capacidad de actualizar dinámicamente la configuración en las aplicaciones cliente durante el tiempo de ejecución. Cuando se realiza un cambio en el repositorio de configuración, todas las aplicaciones integradas conectadas al servidor de configuración pueden ser notificadas, lo que les solicita recargar las secciones relevantes afectadas por la modificación de la configuración.

Veamos un enfoque para actualizar la configuración, que implica enviar una solicitud `POST` específica a una instancia en ejecución del microservicio. Esta solicitud iniciará la recarga de los datos de configuración modificados, permitiendo una recarga en caliente de la aplicación. A continuación, se presentan los pasos a seguir:

1. **Agregar la dependencia de actuator en los servicios Config Client:** Agrega la dependencia de Spring Boot Actuator en el archivo `pom.xml` de los microservicios individuales como accounts, loans, cards, para exponer el endpoint `/refresh`.
   ```
   ...
   <dependencies>
       ...
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-actuator</artifactId>
	   </dependency>
       ...
   <dependencies>
   ...
   ```
2. **Habilitar la API /refresh:** La biblioteca Spring Boot Actuator proporciona un endpoint de configuración llamado `/actuator/refresh` que puede activar un evento de actualización. De forma predeterminada, este endpoint no está expuesto, por lo que debes habilitarlo explícitamente en el archivo [application.yml](configserver/src/main/resources/application.yml) del servidor de configuración [configserver](configserver) utilizando la siguiente configuración.
   ```
   management:
     endpoints:
       web:
         exposure:
           include: refresh
   ```
> **NOTA**
> 
> Invocaste el mecanismo de actualización en el Servicio `accounts` y funcionó correctamente, ya que solo había una aplicación con una instancia. Sin embargo, en un entorno de producción, donde puede haber múltiples servicios, ¿qué sucederá? Si un proyecto tiene muchos microservicios, el equipo podría preferir un método automatizado y eficiente para actualizar la configuración en lugar de activar manualmente cada instancia de aplicación. Vamos a evaluar las otras opciones disponibles.

## UTILIZANDO SPRING CLOUD BUS

![](https://drive.google.com/uc?export=view&id=1pb1OKyBdw-wCttiXlx3ZsQcvVZOtR3Ai)

[Spring Cloud Bus](https://spring.io/projects/spring-cloud-bus) facilita la comunicación fluida entre todas las instancias de la aplicación conectadas al establecer un canal conveniente de transmisión de eventos. Ofrece una implementación para brokers AMQP, como `RabbitMQ` y `Kafka`, permitiendo una comunicación eficiente en todo el ecosistema de la aplicación.

A continuación se presentan los pasos a seguir:

1. **Agregar la dependencia de Actuator en los servicios del Config Server y Config Client:** Agrega la dependencia de Spring Boot Actuator en el archivo `pom.xml` de cada microservicio, como [accounts](accounts), [loans](loans), [cards](cards), para exponer el endpoint `/busrefresh`.
   ```
   ...
   <dependencies>
       ...
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-actuator</artifactId>
	   </dependency>
       ...
   <dependencies>
   ...
   ```
2. **Habilitar la API /busrefresh:** La biblioteca Spring Boot Actuator proporciona un endpoint de configuración llamado `/actuator/busrefresh` que puede activar un evento de actualización. De forma predeterminada, este endpoint no está expuesto, por lo que debes habilitarlo explícitamente en el archivo [application.yml](configserver/src/main/resources/application.yml) del servidor de configuración [configserver](configserver) utilizando la configuración a continuación.
   ```
   management:
     endpoints:
       web:
         exposure:
           include: busrefresh
   ```
3. **Agregar la dependencia de Spring Cloud Bus en los servicios del Config Server y Config Client:** Agrega la dependencia de Spring Cloud Bus `spring-cloud-starter-bus-amqp` en el archivo `pom.xml` de cada microservicio, como [accounts](accounts), [loans](loans), [cards](cards) y el servidor de configuración [configserver](configserver).
   ```
   ...
   <dependencies>
       ...
       <dependency>
           <groupId>org.springframework.cloud</groupId>
           <artifactId>spring-cloud-starter-bus-amqp</artifactId>
       </dependency>
       ...
   <dependencies>
   ...
   ```
4. **Configurar RabbitMQ:** Utilizando Docker, configura el servicio RabbitMQ. Si el servicio no se inicia con los valores predeterminados, configura los detalles de la conexión a RabbitMQ en el archivo `application.yml` de cada microservicio como [accounts](accounts), [loans](loans), [cards](cards) y del servidor de configuración [configserver](configserver).
   ```
   spring:
     rabbitmq:
       host: "localhost"
       port: 5672
       username: "guest"
       password: "guest"
   ```

### Instalar RabbitMQ

![](https://drive.google.com/uc?export=view&id=1pd6JZhlzPeKZK8hw4dkzrvBT_3SPMQGR)

La última [versión](https://github.com/rabbitmq/rabbitmq-server/releases) de RabbitMQ es la 3.13.5. Consulta el registro de cambios para las notas de la versión. Revisa la línea de soporte de [RabbitMQ para conocer qué series de versiones](https://www.rabbitmq.com/release-information) están soportadas.

¿Experimentando con RabbitMQ en tu estación de trabajo? Prueba la imagen Docker de la comunidad:

```
# latest RabbitMQ 3.13
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.13-management
```

> **NOTA**
>
> Aunque este enfoque reduce considerablemente el trabajo manual, aún hay un paso manual involucrado, que es invocar el endpoint `/actuator/busrefresh` en alguna de las instancias del microservicio. Veamos cómo podemos evitarlo y automatizar completamente el proceso.

## UTILIZANDO SPRING CLOUD BUS Y SPRING CLOUD CONFIG MONITOR

![](https://drive.google.com/uc?export=view&id=1pbTCKFQk5fPTeslaYILk_x5LlH3Cj95q)

[Spring Cloud Config](https://docs.spring.io/spring-cloud-config/docs/current/reference/html/) ofrece la biblioteca Monitor, que permite activar eventos de cambio de configuración en el Config Service. Al exponer el endpoint `/monitor`, facilita la propagación de estos eventos a todas las aplicaciones que estén escuchando a través del Bus. La biblioteca Monitor permite notificaciones push desde proveedores de repositorios de código populares como GitHub, GitLab y Bitbucket. Puedes configurar webhooks en estos servicios para que envíen automáticamente una solicitud POST al Config Service después de cada nuevo push al repositorio de configuración. 

A continuación, se presentan los pasos a seguir:

1. **Agregar la dependencia de Actuator en los servicios del Config Server y Config Client:** Incluye la dependencia de Spring Boot Actuator en el archivo `pom.xml` de cada microservicio, como [accounts](accounts), [loans](loans), [cards](cards) y el servidor de configuración [configserver](configserver), para exponer el endpoint `/busrefresh`.
   ```
   ...
   <dependencies>
       ...
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-actuator</artifactId>
	   </dependency>
       ...
   <dependencies>
   ...
   ```
2. **Habilitar la API /busrefresh:** La biblioteca Spring Boot Actuator proporciona un endpoint de configuración llamado `/actuator/busrefresh` que puede activar un evento de actualización. De forma predeterminada, este endpoint no está expuesto, por lo que debes habilitarlo explícitamente en el archivo [application.yml](configserver/src/main/resources/application.yml) del servidor de configuración [configserver](configserver) utilizando la configuración a continuación.
   ```
   management:
     endpoints:
       web:
         exposure:
           include: busrefresh
   ```
3. **Agregar la dependencia de Spring Cloud Bus en los servicios del Config Server y Config Client:** Incluye la dependencia de Spring Cloud Bus `spring-cloud-starter-bus-amqp` en el archivo `pom.xml` de cada microservicio, como [accounts](accounts), [loans](loans), [cards](cards) y el servidor de configuración [configserver](configserver).
   ```
   ...
   <dependencies>
       ...
       <dependency>
           <groupId>org.springframework.cloud</groupId>
           <artifactId>spring-cloud-starter-bus-amqp</artifactId>
       </dependency>
       ...
   <dependencies>
   ...
   ```
4. **Agregar la dependencia de Spring Cloud Config Monitor en el Config Server:** Incluye la dependencia de Spring Cloud Config Monitor `spring-cloud-config-monitor` en el archivo `pom.xml` del servidor de configuración (configserver), lo que expone el endpoint `/monitor`.
   ```
   ...
   <dependencies>
       ...
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-monitor</artifactId>
        </dependency>
       ...
   <dependencies>
   ...
   ```
5. **Configurar RabbitMQ:** Utilizando Docker, configura el servicio RabbitMQ. Si el servicio no se inicia con los valores predeterminados, configura los detalles de la conexión a RabbitMQ en el archivo `application.yml` de cada microservicio como [accounts](accounts), [loans](loans), [cards](cards) y del servidor de configuración [configserver](configserver).
   ```
   spring:
     rabbitmq:
       host: "localhost"
       port: 5672
       username: "guest"
       password: "guest"
   ```
6. **Configurar un WebHook en GitHub:** Configura un webhook para enviar automáticamente una solicitud POST al endpoint `/monitor` del Config Service después de cada nuevo push al repositorio de configuración [master-microservices-config](https://github.com/darvinnueza/master-microservices-config).

![](https://drive.google.com/uc?export=view&id=1pedazCkmzGrSemQIH8HTLEPZJgl9Ow3m)

> **NOTA**
>
> En esta solución, no hay ningún paso manual involucrado; todo el proceso está automatizado.

## SERVIDOR DE CONFIGURACIÓN

En el [Config Server](configserver), actualiza el archivo `pom.xml` como se muestra a continuación y asegúrate de reemplazar los `...` con el contenido existente en tu archivo `pom.xml`.

1. **Define la versión de Spring Cloud:** En la sección `<properties>`, agrega la versión de Spring Cloud que deseas utilizar.
   ```
   <properties>
      ...
      <spring-cloud.version>2023.0.1</spring-cloud.version>
      ...
   </properties>
   ```
2. **Configura la gestión de dependencias:** En la sección `<dependencyManagement>`, agrega la configuración para importar las dependencias de Spring Cloud.
   ```
   <dependencyManagement>
      <dependencies>
         <dependencies>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring-cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
         <dependencies>
      <dependencies>
   <dependencyManagement>
   ```
3. **Agrega las dependencias necesarias:** En la sección `<dependencies>`, incluye las dependencias de Spring Cloud que necesitas, como `spring-cloud-config-server`.
   ```
   <dependencies>
       ...
         <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
         </dependency>
       ...
   <dependencies>
   ```

En [Config Server](configserver), actualiza el archivo [application.yml](configserver/src/main/resources/application.yml) como se muestra a continuación.
```
spring:
  application:
    name: "configserver"
  profiles:
    active: git
  cloud:
    config:
      server:
        git:
          uri: https://github.com/darvinnueza/master-microservices-config.git
          default-label: main
          timeout: 5
          clone-on-start: true
          force-pull: true
```

## ACTUALIZACIÓN DE LOS MICROSERVICIOS PARA LEER PROPIEDADES DESDE EL CONFIG SERVER

En todos los microservicios [accounts](accounts), [loans](loans) y [cards](cards), actualiza el archivo `pom.xml` como se muestra a continuación y asegúrate de reemplazar los `...` con el contenido existente en tu archivo `pom.xml`.

1. **Define la versión de Spring Cloud:** En la sección `<properties>`, agrega la versión de Spring Cloud que deseas utilizar.
   ```
   <properties>
      ...
      <spring-cloud.version>2023.0.1</spring-cloud.version>
      ...
   </properties>
   ```
2. **Configura la gestión de dependencias:** En la sección `<dependencyManagement>`, agrega la configuración para importar las dependencias de Spring Cloud.
   ```
   <dependencyManagement>
      <dependencies>
         <dependencies>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring-cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
         <dependencies>
      <dependencies>
   <dependencyManagement>
   ```
3. **Agrega las dependencias necesarias:** En la sección `<dependencies>`, incluye las dependencias de Spring Cloud que necesitas, como `spring-cloud-starter-config`.
   ```
   <dependencies>
       ...
         <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
         </dependency>
       ...
   <dependencies>
   ```
En todos los microservicios [accounts](accounts), [loans](loans) y [cards](cards), actualiza el archivo `application.yml` como se muestra a continuación y asegúrate de reemplazar `"microservice_name"` con el nombre adecuado para cada microservicio.
```
spring:
  application:
    name: "microservice_name"
  config:
    import: "optional:configserver:http://localhost:8071/"
```

## ANEXOS
### LIVENESS & READINESS PROBE
En el contexto de microservicios,` Liveness` y `Readiness` son tipos de sondas utilizadas para determinar el estado de las aplicaciones dentro de un entorno de orquestación, como Kubernetes. Aquí tienes una descripción de cada una:
#### Liveness Probe (Sonda de Vitalidad)
La sonda de vitalidad se utiliza para comprobar si una aplicación está viva y funcionando correctamente. Si esta sonda falla, indica que la aplicación está en un estado en el que no puede recuperarse por sí misma y probablemente necesita ser reiniciada. Kubernetes utilizará esta sonda para determinar cuándo reiniciar un contenedor que no está funcionando correctamente.

**Propósito:**
- Detectar aplicaciones que están en un estado incorrecto y no pueden recuperarse.
- Asegurar que los contenedores sean reiniciados automáticamente cuando se detecte un fallo.

**Ejemplo de uso en el fichero [application.yml](configserver/src/main/resources/application.yml) en el [configserver](configserver):**
```
management:
  health:
    liveness-state:
      enabled: true
  endpoint:
    health:
      probes:
        enabled: true
```
#### Readiness Probe (Sonda de Preparación)
La sonda de preparación se utiliza para comprobar si una aplicación está lista para aceptar tráfico. A diferencia de la sonda de vitalidad, una falla en esta sonda no resultará en un reinicio del contenedor. En su lugar, indica que el contenedor no debería recibir ninguna solicitud hasta que esté listo. Kubernetes utilizará esta sonda para controlar el enrutamiento del tráfico hacia los contenedores.

**Propósito:**
- Asegurar que las aplicaciones solo reciban tráfico cuando están completamente listas.
- Evitar el enrutamiento de solicitudes a aplicaciones que están iniciando o en un estado de mantenimiento.

**Ejemplo de uso en el fichero [application.yml](configserver/src/main/resources/application.yml) en el [configserver](configserver):**
```
management:
  health:
    readiness-state:
      enabled: true
  endpoint:
    health:
      probes:
        enabled: true
```

En resumen, ambas sondas son cruciales para mantener la salud y disponibilidad de las aplicaciones en un entorno de microservicios, asegurando que solo los contenedores sanos y listos reciban tráfico.

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
docker image push darvinueza/configserver:s3 && docker image push darvinueza/accounts:s3 && docker image push darvinueza/loans:s3 && docker image push darvinueza/cards:s3
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

### DOCUMENTACIÓN
- [Spring Cloud Config](https://docs.spring.io/spring-cloud-config/docs/current/reference/html/)
- [Rabbitmq](https://www.rabbitmq.com/docs/download)
- [Hookdeck](https://hookdeck.com/)