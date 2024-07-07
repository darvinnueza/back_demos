IMPLEMENTAR EL PATRÓN DE DISEÑO CQRS CON SPRINGBOOT & AXON FRAMEWORK
===
## CQRS
CQRS (Command Query Responsibility Segregation) es un patrón de diseño que separa las operaciones de lectura (queries) y escritura (commands) de una aplicación, utilizando diferentes modelos para cada una, lo que permite optimizar y escalar cada operación de manera independiente.

### ESTRUCTURA ESTÁNDAR

La imagen muestra una estructura estándar de una aplicación web basada en una API que sigue el patrón de diseño MVC (Modelo-Vista-Controlador).

![](https://drive.google.com/uc?export=view&id=11RRm2UfO1OCUvPCMPgEDCJA1fcebmSlk)

El flujo es el siguiente:

- **API:** Recibe las peticiones HTTP (GET, POST, PUT/PATCH, DELETE). 
- **Controller:** Maneja las solicitudes y las respuestas HTTP. 
- **Service:** Contiene la lógica de negocio. 
- **Repository:** Gestiona las operaciones de base de datos. 
- **DB:** Almacena los datos persistentes.

Este diseño organiza el código en capas, mejorando la mantenibilidad y la escalabilidad de la aplicación.

### ARQUITECTURA CQRS

La imagen ilustra la arquitectura basada en el patrón CQRS (Command Query Responsibility Segregation)

![](https://drive.google.com/uc?export=view&id=1bBDfN1zy6Rc_Mn75mbQ9iIJOZo8dJTsU)

- **API:** Recibe solicitudes HTTP (POST, PUT/PATCH, DELETE para comandos y GET para consultas).
- **Commands:** Gestionados por la Command API, que contiene comandos, manejadores de comandos y manejadores de eventos. Los comandos se almacenan en la base de datos de escritura y en el Event Store.
- **Events:** Los eventos desencadenados se manejan y actualizan ambas partes de la arquitectura.
- **Querys:** Gestionadas por la Query API, que contiene consultas, manejadores de consultas y manejadores de eventos. Las consultas se realizan sobre la base de datos de lectura.

Este diseño separa claramente las operaciones de escritura y lectura para mejorar la escalabilidad y el rendimiento.

### FLUJO DE API DE COMANDOS CQRS

![](https://drive.google.com/uc?export=view&id=1LA7aaJEpG3DGhye1lGzINZm_nRks9Ufr)

La imagen muestra el flujo de creación de un producto en una API basada en CQRS:

- **API:** Recibe una solicitud POST en /products.
- **Controller:** Maneja la solicitud y envía un comando a través del Command Gateway. 
- **Command Gateway:** Envía el CreateProductCommand al Aggregate, donde los manejadores procesan el comando. 
- **Aggregate:** Procesa el comando y genera un ProductCreatedEvent. 
- **Event Handler:** Escucha el evento, lo maneja y actualiza el repositorio. 
- **Repository:** Guarda los cambios en la base de datos. 
- **Event Store:** Publica el evento para asegurar la consistencia eventual.

## AXON FRAMEWORK
[Axon Framework](https://www.axoniq.io/) es un marco de trabajo Java para desarrollar aplicaciones basadas en CQRS y [Event Sourcing](https://docs.axoniq.io/reference-guide/architecture-overview/event-sourcing), facilitando la gestión de comandos, eventos y consultas de manera escalable y robusta.

## ANEXOS
## CONSOLAS
- [H2 DB](http://localhost:8081/h2-console/)
### DOCUMENTATION
- [Event Sourcing](https://docs.axoniq.io/reference-guide/architecture-overview/event-sourcing)
- [Axon Download](https://www.axoniq.io/download)
- [Spring Initializr](https://start.spring.io/)