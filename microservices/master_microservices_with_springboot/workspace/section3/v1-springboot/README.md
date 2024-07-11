## Lectura de configuración usando la anotación @Value
```
build:
  version: "1.0"
```
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

## Lectura de configuración usando la interfaz Environment
```
...
import org.springframework.core.env.Environment;
...

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

## Lectura de configuración usando @ConfigurationProperties
```
accounts:
  message: "Welcome to EazyBank accounts related local APIs"
  contactDetails:
    name: "Dario Vinueza - Developer"
    email: "darvinnueza@gmail.com"
  onCallSupport:
    - (555)555-1234
    - (555)523-1345
```
```
package com.focus.accounts.dto;

import java.util.Map;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "accounts")
public record AccountsContactInfoDto(String message, Map<String, String> contactDetails, List<String> onCallSupport) {

}
```