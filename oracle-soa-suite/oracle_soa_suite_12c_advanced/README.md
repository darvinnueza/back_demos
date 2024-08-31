INSTALACIÓN ORACLE SOA SUITE 12c EN CENTOS 9
===

## INSTALACIÓN DEL JDK 8
Para instalar el JDK desde un archivo tar.gz en CentOS 9, sigue estos pasos:
1. Descargar el Archivo JDK, asegúrate de tener el archivo `.tar.gz` adecuado para tu sistema operativo. En mi caso, necesito el archivo `jdk-8u202-linux-arm64-vfp-hflt.tar.gz`. Si aún no lo has descargado, puedes obtenerlo desde la [página oficial de Oracle](https://www.oracle.com/java/technologies/javase/javase8u211-later-archive-downloads.html).

2. Navegar al Directorio del Archivo, para ello abre una terminal y navega al directorio donde está ubicado el archivo tar.gz. Por ejemplo:
   ```
   cd /path/to/your/file
   ```
3. Extraer el Archivo, use el comando `tar` para extraer el archivo.
   ```
   tar -xzvf jdk-8u251-linux-x64.tar.gz
   ```
   - **x** descomprime el archivo.
   - **z** indica que el archivo está comprimido con gzip.
   - **v** muestra el progreso en la terminal.
   - **f** indica que estás especificando el nombre del archivo.

4. Mover el JDK a un Directorio de Instalación, es una buena práctica mover el JDK extraído a un directorio estándar, como `/usr/local/java`. Primero, crea el directorio si no existe.
   ```
   sudo mkdir -p /usr/local/java
   ```
5. Luego, mueve el directorio del JDK extraído.
   ```
   sudo mv jdk1.8.0_251/ /usr/local/java/
   ```
6. Configurar las Variables de Entorno:

    - Para que el sistema reconozca el JDK, debes configurar las variables de entorno. Abre el archivo de configuración de tu shell (`.bashrc`, `.bash_profile`, o `.zshrc`, dependiendo del shell que uses).

      ```
      vim ~/.bashrc
      ```
    - Agrega las siguientes líneas al final del archivo.
      ```
      export JAVA_HOME=/usr/local/java/jdk1.8.0_251
      export PATH=$JAVA_HOME/bin:$PATH
      ```
    - Guarda y cierra el archivo. Luego, recarga la configuración del archivo.
      ```
      source ~/.bashrc
      ```

## DESCARGAR ORACLE SOA SUITE 12C
Para descargar Oracle SOA Suite 12c desde el sitio de Oracle, sigue estos pasos:
1. **Accede a Oracle eDelivery:** Visita [Oracle eDelivery](https://edelivery.oracle.com/) y inicia sesión con tus credenciales de Oracle. Si no tienes una cuenta, deberás registrarte.

2. **Buscar Oracle SOA Suite:** 
   - En el sitio de `eDelivery`, utiliza el campo de búsqueda para encontrar Oracle SOA Suite.

     ![](https://drive.google.com/uc?export=view&id=1sAXe_iSU8B0lkhUPw6FKWmpyH44CZ8pm)

   - Selecciona la versión que deseas descargar. Asegúrate de que sea compatible con tu sistema operativo y requisitos.

     ![](https://drive.google.com/uc?export=view&id=1Z-E0VwkVtzRt__T2jvmymusFHBy6JvRj)

3. **Seleccionar la Plataforma:** Selecciona la plataforma (sistema operativo) para la que necesitas la descarga. Oracle SOA Suite está disponible para varias plataformas, como Windows, Linux, etc.

   ![](https://drive.google.com/uc?export=view&id=18aQX4hwFUXA2GMRKvNXBMJTHBozFglYC)

4. **Aceptar el Contrato de Licencia:** Antes de descargar, Oracle te pedirá que revises y aceptes el acuerdo de licencia. Asegúrate de leer los términos y, si estás de acuerdo, marca la casilla correspondiente.

   ![](https://drive.google.com/uc?export=view&id=1d83rE0DcPvi500OGqgeoeshSOvVZw603)

5. **Descarga del Software:** Haz clic en los enlaces para descargar los archivos requeridos. Según la versión y la plataforma, es posible que necesites descargar varios archivos. Para este manual, deberás descargar los siguientes archivos.
   - Oracle Fusion Middleware 12c (12.2.1.4.0) SOA Quick Start, 1.8 GB
   - Oracle Fusion Middleware 12c (12.2.1.4.0) SOA Quick Start, 1.2 GB

   ![](https://drive.google.com/uc?export=view&id=1IjuYR7lzJY_Q7pruuOSRwa_P5Ke--YFb)

## INSTALACIÓN ORACLE SOA SUITE (OSB)
Una vez descargados los archivos, sigue las instrucciones del instalador para configurar y desplegar Oracle SOA Suite 12c en tu sistema.
### INSTALACIÓN SOA QUICK START
1. Como usuario `root`, crea los siguientes directorios.
   ```
   mkdir -p /opt/u01/middleware/oracle_home
   ```

2. Como usuario `root`, asigna permisos al usuario y grupo, en mi caso es  `darvin` para el directorio `/opt/oracle/` y todos sus subdirectorios.
   ```
   chown -R darvin:darvin /opt/u01/
   ```

3. Descomprime ambos archivos `V983385-01_1of2.zip` y `V983385-01_2of2.zip` con el comando `unzip`.
   ```
   unzip V983385-01_1of2.zip && unzip V983385-01_2of2.zip 
   ```
4. En la misma terminal o símbolo del sistema, ejecuta el siguiente comando.
   ```
   java -jar fmw_12.2.1.4.0_soa_quickstart.jar 
   ```

5. Verifica que el directorio de inventario y el grupo del sistema operativo sean correctos y haz clic en OK para proceder con la creación del inventario central y continuar con la instalación.

   ![](https://drive.google.com/uc?export=view&id=1ckF97orYBGptl1Eh4Ed5In2Qtl-tFpRV)

6. Al iniciar el instalador, verás una pantalla de bienvenida. No se requieren acciones aquí más allá de hacer clic en el botón **Next** para continuar.

   ![](https://drive.google.com/uc?export=view&id=1cmRPuIy5VQPZYfNSZNqpD5soeemtKugO)

7. Si no deseas buscar actualizaciones, selecciona *Skip Auto Updates* y haz clic en **Next**.

   ![](https://drive.google.com/uc?export=view&id=1cjvhITC7pPRHiaCI0ArEYUAvmaAGDTrd)

8. Debes especificar el `Oracle Home`, que es el directorio donde se instalará Oracle Fusion Middleware.
Puedes usar la ubicación predeterminada sugerida o elegir una diferente, como por ejemplo `/opt/u01/middleware/oracle_home`. Haz clic en **Next** para continuar.

   ![](https://drive.google.com/uc?export=view&id=1d1psjXKRNMM7-1EX1LFkTEIMJhF63xuV)

9. El instalador comprobará que el sistema operativo y la versión de Java sean compatibles, si todos los requisitos se cumplen, verás una marca verde junto a cada verificación. Haz clic en **Next** para continuar.

   ![](https://drive.google.com/uc?export=view&id=1d5jV_j3PQzhs7wYHFgrx-0V-8QxxwO_j)

10. Se mostrará un resumen de las opciones seleccionadas, incluyendo la ubicación de instalación, el espacio en disco requerido y disponible, y los componentes que se instalarán. Revisa los detalles y, si todo es correcto, haz clic en **Install** para comenzar la instalación.

    ![](https://drive.google.com/uc?export=view&id=1duLuQoCDQl4iEXbDZwVHnAZhrP2OGugb)

11. Verás una barra de progreso que indica el estado de la instalación. Una vez que la instalación se complete, haz clic en **Next**.

    ![](https://drive.google.com/uc?export=view&id=1dmraNEauSmaf03_6IjIv97DHgeCzv1oS)

12. Se te presenta una opción para iniciar JDeveloper inmediatamente después de la instalación. Esta casilla está marcada por defecto. Si deseas iniciar JDeveloper, deja marcada la casilla `Start JDeveloper`.
Si prefieres no iniciar JDeveloper en este momento, desmarca la casilla. Haz clic en **Finish** para completar el proceso de instalación y cerrar el asistente de instalación.

    ![](https://drive.google.com/uc?export=view&id=1e4nqxPzkVtyhm4iIVk9lQVcv4gkutWuA)

## REPOSITORY CREATION UTILITY (RCU)
Este manual te guiará a través de los pasos necesarios para crear y configurar un repositorio de base de datos para Oracle Fusion Middleware utilizando la herramienta Repository Creation Utility (RCU). Este proceso incluye la creación de esquemas de base de datos necesarios y la configuración de tablaspaces en una base de datos Oracle.

### EJECUCIÓN DEL ARCHIVO RCU
Para crear los esquemas de base de datos requeridos por ciertas aplicaciones de Oracle, es necesario ejecutar la **Utilidad de Creación de Repositorio (RCU)**. A continuación, se detallan los pasos para ejecutar el archivo `rcu` ubicado en el sistema:

1. **Fichero Rcu:** Para ejecutar la utilidad, abre una terminal y navega hasta el directorio donde se encuentra el archivo rcu. 

   ```
   cd /opt/u01/middleware/oracle_home/oracle_common/bin
   ```
2. **Iniciar la Ejecución:** Luego, utiliza el siguiente comando para iniciar el proceso.
   
   ```
   ./rcu
   ```

### UTILIDAD DE CREACIÓN DE REPOSITORIO

1. **Pantalla de Bienvenida:** La pantalla de bienvenida del RCU te da una breve introducción sobre lo que esta herramienta puede hacer. Haz clic en **Next**. para continuar.

   ![](https://drive.google.com/uc?export=view&id=1dpkKrlSaOgb8YvbtDvOZvrVuWBiQ-oap)

2. **Seleccionar la Acción de Creación del Repositorio:** Aquí puedes seleccionar las acciones que deseas realizar con RCU. Selecciona *Create Repository*, luego, selecciona *System Load and Product Load (necesitas privilegios de DBA)* y haz clic en **Next** para continuar.

   ![](https://drive.google.com/uc?export=view&id=1doGm8Ijh47Nx60FdV_mOegxiHugS4_3G)

3. **Configuración de Conexión a la Base de Datos:** En esta pantalla, se configuran los detalles de la conexión a la base de datos. Configura los siguientes parámetros:
   
   - Host Name: *tu_dominio_db*
   - Port: 1521
   - Service Name: xepdb1
   - Username: sys
   - Password: *tu_contraseña*
   - Role: SYSDBA

   Haz clic en **Next** para continuar.

   ![](https://drive.google.com/uc?export=view&id=1dWrxFpXQtATR-ZvO8Z3Pg6FPJPbN7L0B)

4. **Advertencia:** Verás un mensaje advirtiendo que la base de datos seleccionada es Oracle XE, que no está certificada para uso en entornos de producción con Oracle Fusion Middleware. Haz clic en **Ignore** para continuar si estás de acuerdo. El RCU verificará los prerrequisitos globales. 

   ![](https://drive.google.com/uc?export=view&id=1dR-AffSzEO-qnfQQ7auJDWj-PPgPJPbV)

5. Una vez completada la verificación, haz clic en **OK** para continuar.

   ![](https://drive.google.com/uc?export=view&id=1dWFFZncvV4tj-jxacs4v7Ag7p3n8wyMf)

6. **Selección de Componentes:** Aquí seleccionarás los componentes de Oracle Fusion Middleware que deseas instalar. Especifica un prefijo único para los esquemas creados (ejemplo: DEV). Marca los componentes que deseas incluir, como *Oracle AS Repository Components, SOA Suite, etc*. Haz clic en Next para continuar.

   ![](https://drive.google.com/uc?export=view&id=1dm1q8Z-GvTnBb-Q_rZboq9jnwhhzsBuK)

7. **Verificación de Prerrequisitos:** El RCU verificará los prerrequisitos para los componentes seleccionados. Haz clic en **OK** para proceder.

   ![](https://drive.google.com/uc?export=view&id=1dk9NNLPcK1rbep8-8jkzdN-f9jTWDbSY)

8. **Configuración de Contraseñas de Esquema Descripción:** Configura las contraseñas para los esquemas principales y auxiliares. Selecciona *Use same passwords for all schemas*. Ingresa y confirma la contraseña y haz clic en **Next** para continuar.

   ![](https://drive.google.com/uc?export=view&id=1dgkKyilsqtt9uFxmgHjYkdW1GgjBgqAq)

9. **Custom Variables:** En este paso, configura las variables personalizadas para los componentes seleccionados. He elegido el perfil de base de datos 'Small', adecuado para entornos de desarrollo o pruebas. Para 'Healthcare Integration', selecciona 'Yes' o 'No' según tus necesidades; en este caso, he seleccionado 'No'. Una vez configuradas las opciones, haz clic en **Next** para continuar.

   ![](https://drive.google.com/uc?export=view&id=1ctZ8mVtPOwj9SeRAj0Rt8iJpXWYLAvp5)

10. **Map Tablespaces:** En este paso, se te presenta un mapeo de los tablespaces para los componentes seleccionados. Cada componente tiene un esquema propietario y un tablespace por defecto asignado. Aquí puedes ver que los tablespaces como *DEV_STB*, *DEV_IAS_OPSS*, *DEV_ESS*, etc. Cada componente también tiene un temporary tablespace asignado, generalmente *DEV_IAS_TEMP*. Haz clic en **Next** para continuar.

    ![](https://drive.google.com/uc?export=view&id=1dPjVhZFH4882OMFwlqP67c_mxLF5etSQ)

11. **Crear Tablespaces:** Al hacer clic en **OK**, la utilidad creará los tablespaces necesarios si aún no existen.

    ![](https://drive.google.com/uc?export=view&id=1dPbqE9fIM_P3HE0Wzn1zFBhlvZWTP5SY)

12. **Validación y Creación de Tablespaces:** El RCU validará los requisitos de los tablespaces para los componentes seleccionados. Se creará cada tablespace en la base de datos del repositorio. Una vez completado, recibirás un mensaje de confirmación indicando que la operación se ha completado con éxito. Haz clic en **OK** para continuar al siguiente paso.

    ![](https://drive.google.com/uc?export=view&id=1bewHg0cfiuLPKc0EULTFyC3I_JF3wYgt)

13. **Resumen de Configuración del Repositorio:** En la pantalla *Summary*, se te mostrará un resumen completo de la configuración del repositorio.
Los detalles incluyen el nombre de host, puerto, nombre de servicio, usuario conectado, operación a realizar (System and Data Load concurrently), y el prefijo de los esquemas.Revisa cada uno de los componentes que se van a instalar, junto con el esquema propietario y los tablespaces asignados.
Si todos los detalles son correctos, haz clic en **Create** para iniciar el proceso de carga del sistema.

    ![](https://drive.google.com/uc?export=view&id=1btOKNkbwZPEeBw_JszAfc98aVEIa2wZx)

14. **Carga del Sistema en el Repositorio:** El RCU comenzará a cargar el sistema en el repositorio, ejecutando operaciones previas a la creación, creación de servicios, y operaciones posteriores. Este proceso incluye la creación de componentes como servicios de infraestructura común, servicios de seguridad de la plataforma Oracle, y servicios de mensajería de usuario. Una vez completadas todas las tareas, asegúrate de que todos los componentes estén marcados como *Success*.

    ![](https://drive.google.com/uc?export=view&id=1bnd2MmSHF1fK20chCwHNFPNBHrUejV5A)

15. **Resumen de Finalización:** Una vez que todas las operaciones se han completado, el RCU mostrará una pantalla de *Completion Summary*. Aquí, se te indicará el tiempo total de ejecución, la ubicación de los archivos de log, y el estado final de cada componente instalado. Puedes optar por guardar un archivo de respuesta (response file) para replicar esta configuración en otra instalación. Haz clic en **Close** para finalizar el proceso.

    ![](https://drive.google.com/uc?export=view&id=1brmSgsEhcMczonMxS3kr7H2vZPR8i53U)

## ASISTENTE DE CONFIGURACIÓN DE UN DOMINIO PARA ORACLE SERVICE BUS
1. **Navegar al Directorio de Configuración:** Utiliza el siguiente comando para navegar al directorio donde se encuentra el script de configuración.
   
   ```
   cd /opt/u01/middleware/oracle_home/oracle_common/common/bin
   ```
2. **Ejecutar el Script de Configuración:** Una vez en el directorio correcto, ejecuta el script config.sh utilizando el siguiente comando.

   ```
   ./config.sh
   ```
3. **Iniciar el Oracle Fusion Middleware Configuration Wizard:** Al ejecutar `config.sh`, se iniciará el Oracle Fusion Middleware Configuration Wizard.
Este asistente te guiará a través de varios pasos para crear un nuevo dominio o actualizar un dominio existente.

4. **Creación de un Nuevo Dominio:** En la pantalla de Configuration Type, selecciona la opción *Create a new domain*. Especifica la ubicación del dominio en el campo Domain Location. Por defecto, esta ubicación podría ser algo como `/u01/middleware/oracle_home/user_projects/domains/base_domain`. Haz clic en **Next** para proceder. 

    ![](https://drive.google.com/uc?export=view&id=1cKq7NIF2zqTgcA3pJSDS2WenojghPknp)

5. **Selección de Plantillas de Producto:** En la pantalla de Templates, selecciona las plantillas necesarias para la configuración de OSB.
Asegúrate de marcar las opciones: 
   - Oracle SOA Suite Reference Connfiguration [soa]
   - Oracle Enterprise Manager [em]
   - Oracle Service Bus Reference Configuration [osb]
   - Oracle Enterprise Manager Plugin for ESS [em]

   Haz clic en **Next** para continuar.

    ![](https://drive.google.com/uc?export=view&id=1ciOL_FThYAZQ1U8l3LBio4NPXL92X5N_)

6. **Configuración de Alta Disponibilidad (HA):** En la pantalla de High Availability Options, si deseas habilitar la migración automática de servicios, marca *Enable Automatic Service Migration* y selecciona la opción de migración de base de datos o consenso. Configura la persistencia de logs de transacciones JTA y la persistencia del servidor JMS según tus necesidades. Puedes optar por usar Default Persistent Store o JMS File Store. Haz clic en **Next** para proceder.

    ![](https://drive.google.com/uc?export=view&id=1cMhRYHbIUmXzLBQrLLSptflRXTYv3VbH)

7. **Configuración de Ubicación de Aplicaciones:** En la pantalla de Application Location, especifica la ubicación de las aplicaciones del dominio Esto generalmente está relacionado con la ubicación del dominio que configuraste previamente. Haz clic en **Next** para continuar.

    ![](https://drive.google.com/uc?export=view&id=1bVGmFu6np36F7e4nt6UMsL6H57ks5ICd)
   
8. **Configuración de la Cuenta de Administrador:** En la pantalla de Administrator Account, ingresa un nombre de usuario (por ejemplo, weblogic) y una contraseña segura para la cuenta de administrador. Confirma la contraseña y haz clic en **Next**.

    ![](https://drive.google.com/uc?export=view&id=1behWNmchjVejyZopmKB9qIIBR8LUF_lk)

9. **Configuración del Modo de Dominio y JDK:** En la pantalla de Domain Mode and JDK, selecciona el modo de dominio, que puede ser Development o Production. Especifica la ubicación del JDK. Por defecto, el sistema debería seleccionar automáticamente el JDK instalado (por ejemplo, Oracle HotSpot 1.8.0_251). Haz clic en **Next** para proceder.

    ![](https://drive.google.com/uc?export=view&id=1cA7Zg9K37lvQ57OGU9S7GvX_vkzOJdXH)

10. **Configuración de la Base de Datos:** En la pantalla de Database Configuration Type, selecciona *RCU Data* para usar los datos configurados en el Repository Creation Utility (RCU). Ingresa los detalles de conexión a la base de datos, como el *nombre del host*, *puerto*, *nombre del servicio DBMS*, y el *propietario del esquema*. Haz clic en **Get RCU Configuration** para verificar la conexión y obtener los detalles del esquema.

    ![](https://drive.google.com/uc?export=view&id=1bxa86_ChQA3vM5Dz8nTf5P_QWFn7ztsS)

11. Haz clic en **Next** una vez que la conexión se haya verificado.exitosamente.

    ![](https://drive.google.com/uc?export=view&id=1bdX4uAOpPKr0cEuIaOD78IAvGTJCPL0e)

12. **Configuración de Esquemas JDBC:** En la pantalla de Component Datasources, revisa los detalles de conexión para cada esquema de componente Asegúrate de que todos los esquemas relevantes estén seleccionados y configurados correctamente. Haz clic en **Next** para proceder.

    ![](https://drive.google.com/uc?export=view&id=1c4BS0KveDTXIDcZo4Q0esq6OOqIOzWaS)

13. **Prueba de Conexiones JDBC:** En la pantalla de JDBC Component Schema Test, selecciona las conexiones y haz clic en **Test Selected Connections** para asegurarte de que todas las conexiones JDBC sean válidas. Si todas las pruebas son exitosas, haz clic en Next para continuar.

    ![](https://drive.google.com/uc?export=view&id=1byjjrHPuZmEXAkGJzBkMxpyj44dBQu1N)

14. **Configuración de Keystore:** En la pantalla de Keystore, puedes configurar los certificados y claves necesarias para la seguridad de tu dominio. Si no necesitas hacer cambios en esta sección, puedes dejarla en blanco y proceder. Haz clic en **Next** para continuar.

    ![](https://drive.google.com/uc?export=view&id=1byhNr9i0zI8iQzZOWBwTZRTCBExg0bGK)

15. **Configuración Avanzada:** En la pantalla de Advanced Configuration, puedes seleccionar las opciones que desees configurar de manera avanzada.
Las opciones incluyen:
       - **Administration Server:** Modificar configuraciones del servidor de administración.
       - **Node Manager:** Configurar Node Manager.
       - **Topology:** Añadir, eliminar o modificar servidores administrados, clústeres y otras configuraciones de topología.
       - **Domain Frontend Host Capture:** Configurar el host frontal del dominio.
       - **Deployments and Services:** Especificar servidores o clústeres de destino.
       - **File Store:** Modificar configuraciones del File Store.

      Selecciona las configuraciones que desees ajustar y haz clic en **Next** para continuar.

      ![](https://drive.google.com/uc?export=view&id=1cZD_gWtRBi0aOyF4uyZdlG4Fr220cNuh)

1.  **Resumen de la Configuración:** En la pantalla de Configuration Summary, se muestra un resumen completo de todas las configuraciones realizadas hasta el momento. Puedes revisar y verificar que todas las configuraciones son correctas. Si todo es correcto, haz clic en **Create** para comenzar a aplicar la configuración.

    ![](https://drive.google.com/uc?export=view&id=1cYQwVbh6-l_GOgo6JFvm7ITBQSAk9nW_)

2.  **Progreso de la Configuración:** En la pantalla de Configuration Progress, puedes ver el progreso de la configuración, donde se irán aplicando todas las configuraciones seleccionadas. Este proceso puede tardar unos minutos. Asegúrate de que todo se complete con éxito. Una vez que el progreso llegue al 100%, haz clic en **Next**.

    ![](https://drive.google.com/uc?export=view&id=1cQemA9PBwlWfvGo53rv0XQ3b69I4hRCG)

3.  **Finalización de la Configuración:** En la pantalla de End Of Configuration, se muestra un mensaje confirmando que la configuración del dominio ha sido exitosa. Verás la ubicación del nuevo dominio, que generalmente será algo como `/opt/u01/middleware/oracle_home/user_projects/domains/base_domain`. También se proporciona la URL del servidor de administración (por ejemplo, http://osb:7001/console). Esta URL te permitirá acceder a la consola de administración de Oracle WebLogic Server. Es importante guardar esta URL, ya que la necesitarás para administrar tu dominio y sus componentes.

    ![](https://drive.google.com/uc?export=view&id=1cgM8xtHfitc0E4NY87mFZPXQ3E18BdBh)

## GUÍA DE INICIO DE ORACLE SOA SUITE
### INICIAR WEBLOGIC SERVER Y NODE MANAGER
Navegar al directorio donde están los scripts de inicio de WebLogic y Node Manager.
   
```
cd /opt/u01/middleware/oracle_home/user_projects/domains/base_domain/bin
```
Para iniciar WebLogic Server, ejecuta el siguiente comando
   
```
./startWebLogic.sh &
```

Para iniciar Node Manager, ejecuta el siguiente comando.
```
./startNodeManager.sh &
```

## ANEXOS
### IMPLEMENTACIÓN DE ORACLE XE 18C EN UN CONTENEDOR DOCKER
El comando docker login container-registry.oracle.com autentica al usuario en el registro de contenedores de Oracle, permitiendo el acceso a imágenes oficiales de Oracle tras ingresar las credenciales.
```
docker login container-registry.oracle.com
```
Este comando lanza un contenedor Docker con Oracle Database Express Edition 18.4.0, exponiendo los puertos 1521 y 5500, configurando la contraseña de la base de datos, estableciendo el conjunto de caracteres a AL32UTF8, y montando el volumen local en /opt/u01/oracle-data para el almacenamiento persistente de datos.

```
docker run -d --name oracle-db -p 1521:1521 -p 5500:5500 -e ORACLE_PWD=OsB2023 -e ORACLE_CHARACTERSET=AL32UTF8 -v /opt/u01/oracle-data:/opt/oracle/oradata container-registry.oracle.com/database/express:18.4.0-xe
```

### ABRIR ORACLE JDEVELOPER DESDE LA TERMINAL
Para abrir Oracle JDeveloper desde la instalación ubicada en `/opt/u01/middleware/oracle_home/jdeveloper/jdev/bin`, sigue estos pasos:
1. Abre la terminal en tu sistema operativo y utiliza el comando `cd` para cambiar al directorio donde está instalado JDeveloper. Ingresa el siguiente comando en la terminal.

   ```
   cd /opt/u01/middleware/oracle_home/jdeveloper/jdev/bin
   ```

2. Una vez que estés en el directorio correcto, ejecuta el siguiente comando para iniciar JDeveloper.
   ```
   ./jdev
   ```

### CREAR UNA MACHINE EN WEBLOGIC SERVER
1. Abre tu navegador web y accede a la consola de administración de [WebLogic Server](http://osb:7001/console/). Ingresa tus credenciales de administrador para iniciar sesión.

   ![](https://drive.google.com/uc?export=view&id=13AoixZEwSBs4Wcb0NuWq1khuFQGthr8C)
2. Una vez que hayas iniciado sesión, en el menú del lado izquierdo, expande la estructura de *Environment*. Haz clic en la opción *Machines* para acceder a la lista de máquinas configuradas en tu dominio. Haz clic en el botón **New** para iniciar el proceso de creación de una nueva Machine.
   ![](https://drive.google.com/uc?export=view&id=13BAWdvTSl7HX65Y5T-UbEPZt9QUsluGI)
3. Se abrirá un formulario donde deberás ingresar los siguientes detalles:
   - **Name:** Escribe un nombre para tu machine (por ejemplo, "MachineMix").
   - **Machine OS:** Selecciona el sistema operativo correspondiente de la máquina. Si no estás seguro, selecciona "Other".
   - Type: Selecciona el tipo de Node Manager, por ejemplo, SSL.
Listen Address: Ingresa la dirección en la que el Node Manager escuchará, como localhost.
Listen Port: Ingresa el puerto en el que escuchará el Node Manager, como 5556.
Haz clic en Finish para completar la creación de la machine.
   ![](https://drive.google.com/uc?export=view&id=13BBkaAjbWhG7n7MUO2N7ZvN1NW93mUoL)

1. En la siguiente pantalla, configurarás los detalles del Node Manager:
   - **Type:** Selecciona el tipo de Node Manager, por ejemplo, SSL.
   - **Listen Address:** Ingresa la dirección en la que el Node Manager escuchará, como localhost.
   - **Listen Port:** Ingresa el puerto en el que escuchará el Node Manager, como 5556.

   Haz clic en Finish para completar la creación de la machine.
   ![](https://drive.google.com/uc?export=view&id=13AedpWJwuprXKinjI19OHi8ilcHeJN5C)
2. Una vez que la machine se haya creado correctamente, verás un mensaje de confirmación en la pantalla. La nueva machine aparecerá listada en la tabla bajo Summary of Machines.
   ![](https://drive.google.com/uc?export=view&id=13BwecbLQTTOeBpygiiKZ5Mx7tYRohn_g)