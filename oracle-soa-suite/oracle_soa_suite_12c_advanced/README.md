ORACLE SOA 12C ADVANCED
===

## INSTALACIÓN DEL JDK 8 EN CENTOS 9
Para instalar el JDK desde un archivo tar.gz en CentOS 9, sigue estos pasos:
1. Descargar el Archivo JDK, asegúrate de tener el archivo `.tar.gz` adecuado para tu sistema operativo. En mi caso, necesito el archivo `jdk-8u202-linux-arm64-vfp-hflt.tar.gz`. Si aún no lo has descargado, puedes obtenerlo desde la [página oficial de Oracle](https://www.oracle.com/java/technologies/javase/javase8u211-later-archive-downloads.html).

2. Navegar al Directorio del Archivo, para ello abre una terminal y navega al directorio donde está ubicado el archivo tar.gz. Por ejemplo:
   ```
   cd /path/to/your/file
   ```
3. Extraer el Archivo, use el comando `tar` para extraer el archivo:
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
   sudo mv jdk-11.0.23 /usr/local/java/
   ```
6. Configurar las Variables de Entorno:

    - Para que el sistema reconozca el JDK, debes configurar las variables de entorno. Abre el archivo de configuración de tu shell (`.bashrc`, `.bash_profile`, o `.zshrc`, dependiendo del shell que uses):

      ```
      vim ~/.bashrc
      ```
    - Agrega las siguientes líneas al final del archivo:
      ```
      export JAVA_HOME=/usr/local/java/jdk1.8.0_251
      export PATH=$JAVA_HOME/bin:$PATH
      ```
    - Guarda y cierra el archivo. Luego, recarga la configuración del archivo:
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
   mkdir -p /opt/oracle/osb/middleware/oracle_home
   ```

2. Como usuario `root`, asigna permisos al usuario y grupo, en mi caso es  `darvin` para el directorio `/opt/oracle/` y todos sus subdirectorios.

   ```
   chown -R darvin:darvin /opt/oracle/
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

   ![](https://drive.google.com/uc?export=view&id=1IviEpMZwWQ7kKIX2IskiFb4_9WPOedFv)

6. Al iniciar el instalador, verás una pantalla de bienvenida. No se requieren acciones aquí más allá de hacer clic en el botón **Next** para continuar.

   ![](https://drive.google.com/uc?export=view&id=1J1EVFDcT9hla1XVzArclWH-K4SJg5Umk)

7. Si no deseas buscar actualizaciones, selecciona *Skip Auto Updates* y haz clic en **Next**.

   ![](https://drive.google.com/uc?export=view&id=1J2lZu2k8K24PQPTkpJSJ_5q_HFWLHGvQ)

8. Debes especificar el `Oracle Home`, que es el directorio donde se instalará Oracle Fusion Middleware.
Puedes usar la ubicación predeterminada sugerida o elegir una diferente, como por ejemplo `/opt/oracle/osb/middleware/oracle_home`. Haz clic en **Next** para continuar.

   ![](https://drive.google.com/uc?export=view&id=1J4iHLhxqG7JLx-ZYZwqgpSdTraXfc7t8)

9.  El instalador comprobará que el sistema operativo y la versión de Java sean compatibles, si todos los requisitos se cumplen, verás una marca verde junto a cada verificación. Haz clic en **Next** para continuar.

   ![](https://drive.google.com/uc?export=view&id=1JPTWAeBsK0JraSHa4oKhGaGS838PLKUX)

10.  Se mostrará un resumen de las opciones seleccionadas, incluyendo la ubicación de instalación, el espacio en disco requerido y disponible, y los componentes que se instalarán. Revisa los detalles y, si todo es correcto, haz clic en **Install** para comenzar la instalación.

   ![](https://drive.google.com/uc?export=view&id=1JYj__rNyzXJ2MsyBt_UZ9bLe8BXnIsqr)

11.  Verás una barra de progreso que indica el estado de la instalación. Una vez que la instalación se complete, haz clic en **Next**.

   ![](https://drive.google.com/uc?export=view&id=1J_Mk__7Kp8ex0tboAyLffmshAcck1P4Y)