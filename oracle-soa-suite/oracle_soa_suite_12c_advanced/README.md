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

1. **Pantalla de Bienvenida:** La pantalla de bienvenida del RCU te da una breve introducción sobre lo que esta herramienta puede hacer. Haz clic en **.Next**. para continuar.

   ![](https://drive.google.com/uc?export=view&id=1dpkKrlSaOgb8YvbtDvOZvrVuWBiQ-oap)

2. **Seleccionar la Acción de Creación del Repositorio:** Aquí puedes seleccionar las acciones que deseas realizar con RCU. Selecciona *Create Repository*, luego, selecciona *System Load and Product Load (necesitas privilegios de DBA)* y haz clic en **Next** para continuar.

   ![](https://drive.google.com/uc?export=view&id=)

3. **Configuración de Conexión a la Base de Datos:** En esta pantalla, se configuran los detalles de la conexión a la base de datos. Configura los siguientes parámetros:
   
   - Host Name: localhost
   - Port: 1521
   - Service Name: xepdb1
   - Username: sys
   - Password: tu_contraseña
   - Role: SYSDBA

   Haz clic en **Next** para continuar.

   ![](https://drive.google.com/uc?export=view&id=)

4. **Advertencia:** Verás un mensaje advirtiendo que la base de datos seleccionada es Oracle XE, que no está certificada para uso en entornos de producción con Oracle Fusion Middleware. Haz clic en **Ignore** para continuar si estás de acuerdo. El RCU verificará los prerrequisitos globales. 

   ![](https://drive.google.com/uc?export=view&id=)

5. Una vez completada la verificación, haz clic en **OK** para continuar.

   ![](https://drive.google.com/uc?export=view&id=)

6.  **Selección de Componentes:** Aquí seleccionarás los componentes de Oracle Fusion Middleware que deseas instalar. Especifica un prefijo único para los esquemas creados (ejemplo: DEV). Marca los componentes que deseas incluir, como *Oracle AS Repository Components, SOA Suite, etc*. Haz clic en Next para continuar.

   ![](https://drive.google.com/uc?export=view&id=)

7.  **Verificación de Prerrequisitos:** El RCU verificará los prerrequisitos para los componentes seleccionados. Haz clic en **OK** para proceder.

   ![](https://drive.google.com/uc?export=view&id=)

8. **Configuración de Contraseñas de Esquema Descripción:** Configura las contraseñas para los esquemas principales y auxiliares. Selecciona *Use same passwords for all schemas*. Ingresa y confirma la contraseña y haz clic en **Next** para continuar.

   ![](https://drive.google.com/uc?export=view&id=)

9. **Custom Variables:** En este paso, configura las variables personalizadas para los componentes seleccionados. He elegido el perfil de base de datos 'Small', adecuado para entornos de desarrollo o pruebas. Para 'Healthcare Integration', selecciona 'Yes' o 'No' según tus necesidades; en este caso, he seleccionado 'No'. Una vez configuradas las opciones, haz clic en **Next** para continuar.

   ![](https://drive.google.com/uc?export=view&id=)

10. **Map Tablespaces:** En este paso, se te presenta un mapeo de los tablespaces para los componentes seleccionados. Cada componente tiene un esquema propietario y un tablespace por defecto asignado. Aquí puedes ver que los tablespaces como DEV_STB, DEV_IAS_OPSS, DEV_ESS, etc. Cada componente también tiene un temporary tablespace asignado, generalmente DEV_IAS_TEMP. Haz clic en Next para continuar.

   ![](https://drive.google.com/uc?export=view&id=)

11. **Crear Tablespaces:** Al hacer clic en **OK**, la utilidad creará los tablespaces necesarios si aún no existen.

   ![](https://drive.google.com/uc?export=view&id=)

## ASISTENTE DE CONFIGURACIÓN

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


Access Oracle XE: 123456
Access Schemas: welcome01