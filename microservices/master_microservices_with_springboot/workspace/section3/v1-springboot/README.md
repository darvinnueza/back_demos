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