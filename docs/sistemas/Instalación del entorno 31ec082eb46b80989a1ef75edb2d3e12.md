# Instalación del entorno

La instalación del sistema consta de dos partes:

1. El servidor central: backend + base de datos
2. Los equipos cliente (Windows): Aplicación de escritorio

# Instalación Servidor central

El servidor central será un equipo dedicado dentro de la red empresarial que se encargará de ejecutar:

- Backend (Spring Boot)
- Base de datos
- Integraciones con APIs externas (en caso de haberlas)

Los requisitos previos es que el sistema operativo del servidor seas linux (Ubuntu Server o Debian), que tenga acceso a la red local y permisos de administrador.

### Programas necesarios

| Programa | Version | Motivo |
| --- | --- | --- |
| Ubuntu Server / Debian | Ubuntu Server 22.04 LTS o Debian 12 | Sistema operativo estable para el servidor |
| Open JDK | 17 | Para ejecutar la aplicación Spring Boot |
| MySQL Server | 8.0 | Base de datos relacional del sistema |
| Git | 2.x | Descarga y actualicación del código fuente |
| Maven | 3.8+ | Compilación del proyecto Java |
| Systemd | Incuido en Linux | Arranque automático del servicio |

### Librerias, dependencias y frameworks

El backend esta desarrollado con Java y Spring Boot.

Principales dependendias:

| Dependencia | Uso |
| --- | --- |
| Spring Boot | Framework principal del backend |
| Spring Web | Creacion de API REST |
| Spring Security | Login, autenticación y control de roles |
| Spring Data JPA | Acceso a base de datos |
| MySQL Connector | Conexión entre Java y MySQL |
| Lombok | Reducción de código repetitivo |
| Maven | Gestión de dependencias y compilación |

### Variables de entorno

Las variables del entorno nos permitirán editar la configuración de la aplicación sin necesidad de tocar el código fuente.

El valor de las variables estarán alojadas en el servidor, en `systemd`. Un ejemplo de cómo se vería:

```jsx
[Unit]
Description=Logitron Backend Service
After=network.target mysql.service

[Service]
User=logitron
WorkingDirectory=/opt/logitron
ExecStart=/usr/bin/java -jar /opt/logitron/logitron.jar
Restart=always
RestartSec=10

Environment=LOGITRON_DB_URL=jdbc:mysql://localhost:3306/logitron
Environment=LOGITRON_DB_USER=logitron_admin
Environment=LOGITRON_DB_PASSWORD=adminLogitron
Environment=LOGITRON_SERVER_PORT=8080

[Install]
WantedBy=multi-user.target
```

Estas variables serán llamadas se introducen a la lógica del programa en un archivo en `application.properties`. Ejemplo:

```jsx
server.port=${LOGITRON_SERVER_PORT}

spring.datasource.url=${LOGITRON_DB_URL}
spring.datasource.username=${LOGITRON_DB_USER}
spring.datasource.password=${LOGITRON_DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

En producción, las credenciales y parámetros sensibles no se almacenarán directamente en el código fuente ni en el archivo `application.properties`.

Se definirán como variables de entorno dentro del servicio `systemd`, que será el encargado de iniciar automáticamente la aplicación en el servidor.

De esta forma, la aplicación podrá leer la configuración necesaria en tiempo de ejecución sin exponer datos sensibles en el repositorio del proyecto.

El comando para activarlo es:

```jsx
sudo systemctl daemon-reload
sudo systemctl enable logitron
sudo systemctl start logitron
sudo systemctl status logitron
```

### Instalación Java 17

Abre el comando Linux y escribe:

```jsx
sudo apt install openjdk-17-jdk
```

Para confirmar que se ha instalado:

```jsx
java -version
```

Esto instala OpenJDK 17 para poder ejecutar un backend desarrollado con Spring Boot. Esta en particular tiene un Long Term Support (LTS) y es compatrible con casi todas las librerías modernas.

### Instalación base de datos

En el comando Linux, escribe:

```jsx
sudo apt install mysql-server
sudo mysql_secure_installation

# crear la base de datos y un usuario administrador:
CREATE DATABASE logitron;
CREATE USER 'logitron_admin'@'localhost' IDENTIFIED BY 'admin_user';
GRANT ALL PRIVILEGES ON logitron.* TO 'logitron_admin'@'localhost';
FLUSH PRIVILEGES;
```

Instalamos el servidor MySQL, nuestro acceso y creamos la base de datos. Esto nos permite almacenar en tablas relacionales nuestros datos. Posteriormente se crearán las tablas y roles de distintos usuarios.

### Configuración de red

El servidor central estará ubicado físicamente en las instalaciones de la empresa y conectado a la red local mediante conexión ethernet al router o switch de la infraestructura red. La IP de éste servidor debe ser fijo (ej: 192.168.50.10).

A partir de aqui hay dos casuísticas:

### Con DNS interno

Si la empresa dispone de un servidor DNS interno, podremos acceder a la app con el nombre legible del host.

Ejemplo:

```jsx
https://logitron-server:8080
```

### Sin DNS interno

En caso de no disponer de un servidor DNS interno, accederemos a la app a través de su dirección IP.

Ejemplo:

```jsx
https://192.168.50.10:8080
```

### Despliegue de aplicación

La aplicación se compilará en un archivo ejecutable logitron.jar.

Para iniciar la aplicación, abrimos la terminal del servidor y ejecutaremos el comando:

```jsx
java -jar logitron.jar
```

Una vez ejecutado el comando, la aplicación se iniciará en el servidor y podrá ser accedida por los equpos cliente a través de la red interna de la empresa.

Por defecto, la aplicación Spring Boot quedará disponible en el puerto 8080 del servidor.

Ejemplo:

```jsx
https://logitron-server:8080
```

### Actualizar paquetes

```jsx
sudo apt update -y
```

# Instalación Equipo cliente

El sistema cliente debe contar con un Sistema Operativo Windows y tener una conexión a la red local o VPN.

### Programas necesarios

| Programa | Version | Motivo |
| --- | --- | --- |
| Windows | Windows 10 / Windows 11 | Sistema operativo donde se ejecuta la app |
| Aplicación cliente | logitron-client.exe | Interfaz usada por usuarios |
| VPN | FortiClient | Necesaria para trabajr fuera de la oficina |

### Instalación de aplicación

1. Copiar el instalador
2. Ejecutar el instalador
3. Seguir los pasos del asistente
    
    ```jsx
    	logitron-client.exe
    ```
    

### Configuración inicial

Al iniciar la aplicación por primera vez introduciremos la direción del servidor (ej: http://logitron-server:8080)

### Inicio de sesión

- Presencial, en la oficina 🏢
    1. Abre la app e introduce usuario y contraseña
    2. La app mostrará un interfaz u otro según el rol
- Teletrabajo 🏡
    1. Necesitarás conectarte mediante VPN a la red interna de la empresa (FortiClient, WireGuard…)
    2. Abrir la app windows y acceder con usuario y contraseña
    3. La app mostrará un interfaz u otro según el rol