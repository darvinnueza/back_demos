UTILIZANDO LA BASE DE DATOS MYSQL DENTRO DE LOS MICROSERVICIOS
==
## EJECUTAR LOS CONTENEDORES MySQL 

### BASE DE DATOS ACCOUNTS
```
docker run -p 3306:3306 --name accountsdb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=accountsdb -d mysql
```

### BASE DE DATOS LOANS
```
docker run -p 3307:3306 --name loansdb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=loansdb -d mysql
```

### BASE DE DATOS CARDS
```
docker run -p 3308:3306 --name cardsdb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=cardsdb -d mysql
```

## ACTUALIZA EL CÓDIGO DE MICROSERVICIOS
### REEMPLAZAR H2 CON MYSQL DATA BASE
En los archivos `pom.xml` de todos los microservicios, reemplaza la dependencia de H2 con la nueva dependencia de MySQL.
```
...
<dependencies>
   ...
   <!-- MySQL: Conector para la base de datos relacional -->
   <dependency>
       <groupId>com.mysql</groupId>
       <artifactId>mysql-connector-j</artifactId>
       <scope>runtime</scope>
   </dependency>
   ...
<dependencies>
...
```
En los archivos `application.yml` de todos los microservicios, reemplaza la dependencia de H2 con la nueva dependencia de MySQL:

1. Para el Microservicio **Accounts**
   ```
   ...
   spring:
     ...
     datasource:
       url: jdbc:mysql://localhost:3306/accountsdb
       username: root
       password: root
     jpa:
       show-sql: true
     sql:
       init:
         mode: always
   ...
   ```

2. Para el Microservicio **Cardsdb**
   ```
   ...
   spring:
     ...
     datasource:
       url: jdbc:mysql://localhost:3308/cardsdb
       username: root
       password: root
     jpa:
       show-sql: true
     sql:
       init:
         mode: always
   ...
   ```

3. Para el Microservicio **Loans**
   ```
   ...
   spring:
     ...
     datasource:
       url: jdbc:mysql://localhost:3307/loansdb
       username: root
       password: root
     jpa:
       show-sql: true
     sql:
       init:
         mode: always
   ...
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
Sube varias imágenes Docker al repositorio Docker Hub.
```
docker image push darvinueza/configserver:s4 && docker image push darvinueza/accounts:s4 && docker image push darvinueza/loans:s4 && docker image push darvinueza/cards:s4
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

### RABITMQ
- [RabbitMQ](http://localhost:15672/#/)