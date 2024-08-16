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

## INSTALACIÓN ORACLE SOA SUITE (OSB)
1. **Accede a Oracle eDelivery:** Visita [Oracle eDelivery](https://edelivery.oracle.com/) y inicia sesión con tus credenciales de Oracle. Si no tienes una cuenta, deberás registrarte.

2. **Buscar Oracle SOA Suite:** 
   - En el sitio de `eDelivery`, utiliza el campo de búsqueda para encontrar Oracle SOA Suite.
     ![](https://drive.google.com/uc?export=view&id=1sAXe_iSU8B0lkhUPw6FKWmpyH44CZ8pm)
   - Selecciona la versión que deseas descargar. Asegúrate de que sea compatible con tu sistema operativo y requisitos.
     ![]()

3. 




unzip V983385-01_1of2.zip

unzip V983385-01_2of2.zip 