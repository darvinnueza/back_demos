## CÓMO LEER PROPIEDADES EN APLICACIONES SPRINGBOOT
En Spring Boot, existen múltiples enfoques para leer propiedades. A continuación se presentan los enfoques más comúnmente utilizados:
- Usando la anotación @Value
- Usando Environment
- Usando Environment @ConfigurationProperties (Enfoque recomendado ya que evita la codificación rígida de las claves de propiedades)

### Usando la anotación `@Value`
Puedes usar la anotación `@Value` para inyectar directamente valores de propiedades en tus beans. Este enfoque es adecuado para inyectar propiedades individuales en campos específicos.

Por ejemplo:

1. Editar el archivo [application.yml](accounts/src/main/resources/application.yml)
   
   ```
   ...
   
   build:
     version: "1.0"
   
   ...
   ```
2. Controlador de Spring Boot que expone un endpoint para obtener el valor de la propiedad `build.version` del archivo de configuración en un campo privado llamado `buildVersion`.
   
    ```
   public class AccountsController {
   
       ...
   
       @Value("${build.version}")
       private String buildVersion;
       
       ...
       
       @GetMapping("/build-info")
       public ResponseEntity<String> getBuildInfo() {
           return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
       }
       
       ...
       
   }
   ```

## Usando Environment
La interfaz `Environment` proporciona métodos para acceder a las propiedades del entorno de la aplicación. Puedes inyectar el bean `Environment` y usar sus métodos para recuperar los valores de las propiedades. Este enfoque es más flexible y permite acceder a las propiedades de manera programática. 

Por ejemplo:

1. Controlador de Spring Boot que expone un endpoint para obtener el valor de la variable de entorno `JAVA_HOME`. Utiliza la interfaz `Environment` para acceder a las propiedades del entorno.
   
   ```
   public class AccountsController {
   
       ...
       
       @Autowired
       private Environment environment;
       
       ...
       
       @GetMapping("/java-version")
       public ResponseEntity<String> getJavaVersion() {
           String javaHome = environment.getProperty("JAVA_HOME");
           return ResponseEntity.status(HttpStatus.OK).body(javaHome != null ? javaHome : "JAVA_HOME is not set.");
       }
       
       ...
       
   }
   ```

## Usando Environment @ConfigurationProperties
La anotación `@ConfigurationProperties` permite enlazar grupos completos de propiedades a un bean. Puedes definir una clase de configuración con campos anotados que coincidan con las propiedades, y Spring Boot mapea automáticamente las propiedades a los campos correspondientes.

Por ejemplo: 

1. Editar el archivo [application.yml](accounts/src/main/resources/application.yml)
   
   ```
   ...
   
   accounts:
     message: "Welcome to EazyBank accounts related local APIs"
     contactDetails:
       name: "Dario Vinueza - Developer"
       email: "darvinnueza@gmail.com"
     onCallSupport:
       - (555)555-1234
       - (555)523-1345
   ...
   ```
2. Crea una clase de datos inmutable utilizada en Spring Boot para enlazar propiedades de configuración desde archivos de propiedades o archivos YAML. Utiliza la anotación `@ConfigurationProperties` con el prefijo accounts para mapear automáticamente los valores de configuración a los campos de la clase.
   
   ```
   @ConfigurationProperties(prefix = "accounts")
   public record AccountsContactInfoDto(String message, Map<String, String> contactDetails, List<String> onCallSupport) { }
   ```
3. Controlador de Spring Boot que expone un endpoint para acceder a los datos de contacto. Inyecta `AccountsContactInfoDto` para obtener la configuración y usa el método `getContactInfo()` para proporcionar estos datos a los clientes.
   
   ```
   public class AccountsController {
   
       ...
       
       @Autowired
       private AccountsContactInfoDto accountsContactInfoDto;
       
       ...
       
       @GetMapping("/contact-info")
       public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
           return ResponseEntity.status(HttpStatus.OK).body(accountsContactInfoDto);
       }
       
       ...
       
   }
   ```
4. Clase principal de la aplicación Spring Boot. Habilita la carga de propiedades para `AccountsContactInfoDto` y arranca la aplicación con `SpringApplication.run()`, permitiendo que las propiedades configuradas en los archivos de configuración se inyecten en el bean `AccountsContactInfoDto`.
   
   ```
   ...
   @EnableConfigurationProperties(value = {AccountsContactInfoDto.class})
   ...
   public class AccountsApplication {
   
       public static void main(String[] args) {
           SpringApplication.run(AccountsApplication.class, args);
       }
   }
   ```

## PERFILES
Spring ofrece una herramienta poderosa para agrupar propiedades de configuración en lo que se conoce como perfiles (dev, qa, prod), permitiéndonos activar un conjunto de configuraciones en función del perfil activo.

Los perfiles son ideales para preparar nuestra aplicación para diferentes entornos, pero también se utilizan en otros casos, como la creación de beans en función de un perfil específico, entre otros.

El perfil predeterminado está siempre activo. Spring Boot carga todas las propiedades del archivo `application.properties` o [application.yml](accounts/src/main/resources/application.yml) en el perfil predeterminado. Podemos crear otros perfiles creando archivos de propiedades como los siguientes:
- [application_prod.yml](accounts/src/main/resources/application_prod.yml) → para el perfil prod (producción)
- [application_qa.yml](accounts/src/main/resources/application_qa.yml) → para el perfil QA (calidad)
Para activar un perfil específico, usamos la propiedad `spring.profiles.active` de la siguiente manera:
```
spring:
   profiles:
      active: prod
```
Un punto importante a tener en cuenta es que, una vez que la aplicación está construida y empaquetada, no debe ser modificada. Cualquier cambio de configuración necesario, como actualizar credenciales o manejadores de base de datos, debe hacerse externamente.

## ¿CÓMO EXTERNALIZAR CONFIGURACIONES USANDO ARGUMENTOS DE LÍNEA DE COMANDOS?
Spring Boot convierte automáticamente los argumentos de línea de comandos en pares clave/valor y los agrega al objeto Environment. En una aplicación de producción, esta fuente de propiedades tiene la mayor precedencia. Puedes personalizar la configuración de la aplicación especificando argumentos de línea de comandos al ejecutar el JAR que construiste anteriormente.
```
java -jar accounts-1.0.jar --build.version="1.1"
```
El argumento de línea de comandos sigue la misma convención de nombres que la propiedad correspondiente de Spring, utilizando el prefijo `--` para los argumentos de CLI (Command-Line Interface).

## ¿CÓMO EXTERNALIZAR CONFIGURACIONES UTILIZANDO PROPIEDADES DEL SISTEMA JVM?
Las propiedades del sistema JVM, al igual que los argumentos de línea de comandos, pueden sobrescribir las propiedades de Spring con una prioridad más baja. Este enfoque permite externalizar la configuración sin necesidad de reconstruir el artefacto JAR. La propiedad del sistema JVM sigue la misma convención de nombres que la propiedad correspondiente de Spring, con el prefijo `-D para los argumentos de JVM. En la aplicación, el mensaje definido como una propiedad del sistema JVM será utilizado, teniendo prioridad sobre los archivos de propiedades.
```
java -Dbuild.version="1.2" -jar accounts-1.0.jar
```
En el escenario en el que se especifican tanto una propiedad del sistema JVM como un argumento de línea de comandos, las reglas de precedencia dictan que Spring dará prioridad al valor proporcionado como argumento de línea de comandos. Esto significa que el valor especificado a través del CLI (Command-Line Interface) será utilizado por la aplicación, teniendo prioridad sobre las propiedades de la JVM.

## ¿CÓMO EXTERNALIZAR CONFIGURACIONES UTILIZANDO VARIABLES DE ENTORNO?
Las variables de entorno se utilizan ampliamente para la configuración externalizada, ya que ofrecen portabilidad en diferentes sistemas operativos, siendo universalmente compatibles. La mayoría de los lenguajes de programación, incluyendo Java, proporcionan mecanismos para acceder a las variables de entorno, como el método `System.getenv()`.

Para mapear una clave de propiedad de Spring a una variable de entorno, necesitas convertir todas las letras a mayúsculas y reemplazar cualquier punto o guion con guiones bajos. Spring Boot manejará este mapeo correctamente de forma interna. Por ejemplo, una variable de entorno llamada `BUILD_VERSION` será reconocida como la propiedad `build.version`. Esta característica se conoce como binding relajado.

### WINDOWS
```
env:BUILD _VERSION="1.3"; java -jar accounts-1.0.jar
```
### LINUX BASED OS
```
BUILD _VERSION="1.3"; java -jar accounts-1.0.jar
```

## DESCRIPCIÓN GENERAL DEL PROYECTO
Este proyecto se configura y ejecuta utilizando varias opciones que determinan su comportamiento en el entorno de producción. Las opciones se establecen a través de argumentos del programa, opciones de la máquina virtual Java (JVM) y variables de entorno. A continuación se detallan estas configuraciones:

![](https://drive.google.com/uc?export=view&id=1QGNQLfIwFM8S0n2MYTq9lWnHlYE4o5sp)
### Argumentos del Programa (Program Arguments)
```
--spring.profiles.active=prod --build.version=1.1
```
![](https://drive.google.com/uc?export=view&id=1p3uHHF9zAvJxM5r7eeUlwvfH55Y6xjaZ)
- `--spring.profiles.active=prod`: Activa el perfil de Spring correspondiente al entorno de producción, asegurando que la aplicación use configuraciones específicas para producción. 
- `--build.version=1.1`: Especifica la versión de construcción del proyecto como 1.1. Esta versión puede ser utilizada por la aplicación para mostrar información de versión o para otros propósitos internos.
### Opciones de la Máquina Virtual (VM Options)
![](https://drive.google.com/uc?export=view&id=1oyxQctZypfkRdR8n5SEdfw-R1ZxKxyAx)
- `-Dspring.profiles.active=prod`: Similar al argumento del programa, esta opción activa el perfil de Spring para el entorno de producción, pero se establece a nivel de la JVM.
- `-Dbuild.version=1.3`: Define la versión de construcción del proyecto como 1.3 a nivel de la JVM, lo que puede sobreescribir la versión establecida en los argumentos del programa.
```
-Dspring.profiles.active=prod -Dbuild.version=1.3
```
### Variables de Entorno (Environment Variables)
![](https://drive.google.com/uc?export=view&id=1p2TbI3lTwrw7Qoui8Y5sV7H6Zy2PIeQr)
- `SPRING_PROFILES_ACTIVE=prod`: Establece el perfil activo de Spring como prod a través de una variable de entorno, reforzando el uso de configuraciones de producción.
- `BUILD_VERSION=1.5`: Define la versión de construcción del proyecto como 1.5 a través de una variable de entorno. Esta configuración suele tener la mayor prioridad y probablemente será la versión efectiva utilizada por la aplicación.
```
SPRING_PROFILES_ACTIVE=prod;BUILD_VERSION=1.5;
```

## DESVENTAJAS DE LAS CONFIGURACIONES EXTERNALIZADAS UTILIZANDO SOLO SPRING BOOT
1. Los argumentos de CLI (Command-Line Interface), las propiedades de JVM y las variables de entorno son formas efectivas de externalizar la configuración y mantener la inmutabilidad del build de la aplicación. Sin embargo, utilizar estos enfoques a menudo implica ejecutar comandos por separado y configurar manualmente la aplicación, lo cual puede introducir errores potenciales durante el despliegue. 
2. Dado que los datos de configuración evolucionan y requieren cambios, al igual que el código de la aplicación, ¿qué estrategias se deben emplear para almacenar, rastrear revisiones y auditar la configuración utilizada en una versión? 
3. En escenarios donde las variables de entorno carecen de características de control de acceso granular, ¿cómo se puede controlar efectivamente el acceso a los datos de configuración? 
4. Cuando el número de instancias de la aplicación crece, manejar la configuración de manera distribuida para cada instancia se vuelve un desafío. ¿Cómo se pueden superar estos desafíos? 
5. Teniendo en cuenta que ni las propiedades de Spring Boot ni las variables de entorno soportan la encriptación de la configuración, ¿cómo se deben gestionar de forma segura los secretos? 
6. Después de modificar los datos de configuración, ¿cómo se puede asegurar que la aplicación pueda leerlos en tiempo de ejecución sin necesitar un reinicio completo?

## SPRING CLOUD CONFIG


