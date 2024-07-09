IMPLEMENTACIÓN, PORTABILIDAD Y ESCALABILIDAD DE MICROSERVICIOS UTILIZANDO DOCKER
===
Dockerfile, Buildpacks y Google Jib son herramientas que facilitan la creación y despliegue de imágenes de contenedores para aplicaciones.

![](https://drive.google.com/uc?export=view&id=1ooV4-zb8bIhgpVKCKq3RWMu07aVmNhSD)
## Comparación de Enfoques: Dockerfile, Buildpacks y Jib
### Dockerfile 
Es un archivo de texto que define una serie de instrucciones para construir una imagen Docker. Permite especificar el sistema operativo base, copiar archivos, instalar dependencias y configurar el entorno, proporcionando un control detallado sobre el proceso de construcción del contenedor.
#### Ventajas
- Flexibilidad: Permite especificar cada paso del proceso de construcción.
- Control: Ofrece control total sobre la configuración del entorno y los comandos ejecutados.
#### Desventajas:
- Complejidad: Requiere conocimientos detallados de Docker y del entorno de desarrollo.
- Mantenimiento: Los cambios en la configuración pueden requerir actualizaciones manuales en el Dockerfile.

### Buildpacks 
Son herramientas que automatizan la conversión del código fuente en imágenes ejecutables. Detectan y empaquetan automáticamente las dependencias necesarias, eliminando la necesidad de escribir Dockerfiles manualmente. Buildpacks son especialmente útiles para desarrolladores que buscan una solución simplificada para construir y desplegar aplicaciones en contenedores.
#### Ventajas
- Simplicidad: Automatiza la detección del entorno y la configuración necesaria.
- Estandarización: Proporciona imágenes de contenedor consistentes y seguras.
#### Desventajas
- Menos Control: Menos flexibilidad y control sobre el proceso de construcción.
- Dependencia: Dependencia de las implementaciones de Buildpacks para características específicas.

### Google Jib 
Es una herramienta de código abierto diseñada para construir imágenes Docker directamente desde proyectos Java. Integrado con Maven y Gradle, Jib simplifica la creación de contenedores optimizando el proceso de construcción y despliegue, sin necesidad de escribir Dockerfiles ni manejar demonios Docker.
#### Ventajas
- Integración: Se integra fácilmente con Maven y Gradle.
- Eficiencia: Construye imágenes optimizadas sin necesidad de Docker en la máquina de desarrollo.
#### Desventajas:
- Lenguaje Específico: Está diseñado específicamente para aplicaciones Java.
- Flexibilidad Limitada: Menos flexible en comparación con un Dockerfile para configuraciones personalizadas.

### Resumen
- **Dockerfile:** Ideal para aquellos que necesitan control total y flexibilidad en la construcción de sus imágenes Docker.
- **Buildpacks:** Óptimo para simplicidad y estandarización sin la necesidad de escribir Dockerfiles.
- **Jib:** Perfecto para desarrolladores Java que buscan una integración fácil y eficiente con sus herramientas de construcción existentes.

## IMPLEMENTACIÓN
### DOCKERFILE
1. Crea el archivo `Dockerfile` dentro de cualquier microservicio. Para este ejemplo, utilizaremos el archivo [Dockerfile](accounts/Dockerfile) del microservicio `accounts`.
   
   ```
   # Start with a base image containing java runtime
   FROM openjdk:17-jdk-slim
    
   # Information around who maintains the image
   MAINTAINER eazybytes.com
   
   # Add the application's jar to the image
   COPY target/accounts-1.0.jar accounts-1.0.jar
    
   # Execute the Application
   ENTRYPOINT ["java", "-jar", "accounts-1.0.jar"]
   ```
2. Crea una imagen Docker del microservicio `accounts` utilizando el Dockerfile en el directorio actual (.) y la etiqueta con el nombre `darvinueza/accounts:s2`.
   
   ```
   cd accounts
   docker build . -t darvinueza/accounts:s2
   ```
3. Proporciona detalles sobre una imagen de Docker específica.
   
   ```
   docker inspect image [IMAGE_ID]
   ```
4. Inicia un contenedor Docker a partir de la imagen `darvinueza/accounts` con la etiqueta `s2`. El puerto `8080` del contenedor se mapea al puerto `8080` de la máquina host, lo que permite acceder al servicio del contenedor a través del puerto `8080` del host.
   
   ```
   docker run -p 8080:8080 darvinueza/accounts:s2
   ```
5. Inicia un contenedor Docker en segundo plano ("-d").
   
   ```
   docker run -d -p 8080:8080 darvinueza/accounts:s2
   ```

### BUILDPACKS
1. Agrega esta configuración del `pom.xml` para el `spring-boot-maven-plugin` permite la generación de una imagen Docker con el nombre `darvinueza/${project.artifactId}:s2`.
   
   ```
   <build>
       <plugins>
           ...
           <plugin>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-maven-plugin</artifactId>
               <configuration>
                   <image>
                       <name>darvinueza/${project.artifactId}:s2</name>
                   </image>
                   <excludes>
                       <exclude>
                           <groupId>org.projectlombok</groupId>
                           <artifactId>lombok</artifactId>
                       </exclude>
                   </excludes>
               </configuration>
           </plugin>
           ...
       </plugins>
   </build>
   ```
2. Ejecuta el comando de Maven desde la ubicación donde se encuentra el `pom.xml` para generar la imagen de Docker sin necesidad de un Dockerfile.
   
   ```
   mvn spring-boot:build-image
   ```
3. Ejecuta el comando de Docker. Esto iniciará el contenedor de Docker basado en la imagen especificada, para el microservicio `loans`.
   
   ```
   docker run -p 8090:8090 darvinueza/loans:s2
   ```
   
### GOOGLE JIB
1. Agrega esta configuración del `pom.xml` para el `jib-maven-plugin` permite la construcción de una imagen Docker para el proyecto Maven sin necesidad de un Dockerfile. La imagen generada se etiquetará como `darvinueza/${project.artifactId}:s2`. El plugin Jib se encarga de optimizar y automatizar el proceso de construcción de la imagen, facilitando el despliegue de aplicaciones Java en contenedores.
   
   ```
   <build>
       <plugins>
          ...
           <plugin>
               <groupId>com.google.cloud.tools</groupId>
               <artifactId>jib-maven-plugin</artifactId>
               <version>3.3.2</version>
               <configuration>
                   <to>
                       <image>darvinueza/${project.artifactId}:s2</image>
                   </to>
               </configuration>
           </plugin>
           ...
       </plugins>
   </build>
   ```
2. Ejecuta el comando de Maven desde la ubicación donde se encuentra el `pom.xml` para generar la imagen de Docker sin necesidad de un Dockerfile.
   
   ```
   mvn compile jib:dockerBuild
   ```
3. Ejecuta el comando de Docker. Esto iniciará el contenedor de Docker basado en la imagen especificada, para el microservicio `cards`.
   
   ```
   docker run -p 9000:9000 darvinueza/cards:s2
   ```
## COMANDOS DOCKER
### Push de Imágenes Locales Docker hacia Docker Hub
Este comando sube la imagen Docker etiquetada como `darvinueza/accounts:s2` a [Docker Hub](https://hub.docker.com), lo que permite compartir y distribuir esta imagen con otros usuarios o servidores.

```
docker image push darvinueza/accounts:s2
```
Este comando sube la imagen Docker etiquetada como `darvinueza/cards:s2` a [Docker Hub](https://hub.docker.com).

```
docker image push darvinueza/cards:s2
```
Este comando sube la imagen Docker etiquetada como `darvinueza/loans:s2` a [Docker Hub](https://hub.docker.com).

```
docker image push darvinueza/loans:s2
```
## DOCKERCOMPOSE
Docker Compose es una herramienta para definir y ejecutar aplicaciones multi-contenedor en Docker. Permite configurar múltiples servicios en un solo archivo YAML [docker-compose.yml](docker-compose.yml) y gestionar el ciclo de vida de la aplicación con comandos simples.

```
services:
  accunts:
    image: "darvinueza/accounts:s4"
    container_name: acounts-ms
    ports:
      - "8080:8080"
    deploy:
      resources:
        limits:
          memory: 700m
    networks:
      - eazybank

  loans:
    image: "darvinueza/loans:s4"
    container_name: loans-ms
    ports:
      - "8090:8090"
    deploy:
      resources:
        limits:
          memory: 700m
    networks:
      - eazybank

  cards:
    image: "darvinueza/cards:s4"
    container_name: cards-ms
    ports:
      - "9000:9000"
    deploy:
      resources:
        limits:
          memory: 700m
    networks:
      - eazybank

networks:
  eazybank:
    driver: "bridge"
```
### COMANDOS DOCKER COMPOSE
Para la ejecución de todos estos comandos nos ubicamos en la raíz del archivo [docker-compose.yml](docker-compose.yml).
1. Iniciar y ejecutar los contenedores en modo detached, basado en la configuración especificada en [docker-compose.yml](docker-compose.yml).
   
   ```
   docker compose up -d
   ```
2. Para detener los contenedores en ejecución y elimina los contenedores, redes y volúmenes definidos en el archivo [docker-compose.yml](docker-compose.yml).
   
   ```
   docker compose down
   ```

## ANEXOS
- [Buildpacks](https://buildpacks.io/)
- [Paketo](https://paketo.io/)
- [Google Jib](https://github.com/GoogleContainerTools/jib)
- [Docker Hub](https://hub.docker.com)