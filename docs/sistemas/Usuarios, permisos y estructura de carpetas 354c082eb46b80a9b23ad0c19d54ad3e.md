# Usuarios, permisos y estructura de carpetas

Distinguimos dos grandes grupos de usuarios: Usuarios del servidor Linux y usuarios de la apliación.

## Usuarios de servidor

Existen en el sistema operativo del servidor, no dentro de la aplicación.

| Usuario | Función | Permisos |
| --- | --- | --- |
| root | Administrador total del servidor | Todos los permisos |
| adminsys | Administrador técnico del servidor | Puede usar `sudo` |
| developer | Despliegue y mantenimiento técnico | Permisos limitados sobre el entorno de desarrollo segúin área de trabajo |
| backupuser | Usuario de copias de seguridad | Lectura de datos y escritura en backups |
| logitron | Usuarioq ue ejecuta la app | Sin permisos de administrador de servidor |

## Usuarios de aplicación

Al ejecutar la aplicación, introducen sus credenciales y según su rol se despliega un interfaz u otro.

| Perfil | Función | Lectura | Escritura |
| --- | --- | --- | --- |
| Administrador de empleados | Gestiona empleados y visualiza resultados de productividad | Si | Si, empleados existentes |
| Comercial | Genera y consulta pedidos de venta en su nombre | Si | Si, pedidos comerciales |
| Logística | Gestiona envíos, preparación y estado de pedidos. También hacen seguimiento del inventario | Si | Si, envíos y preparación |
| Finanzas | Consulta facturación y pagos | Si | Sí, datos financieros |
| Administrador de app | Puede hacer ediciones en cualquier área de trabajo | Si | Si |
| Solo lectura | Ve el proceso de trabajo de todos | Si | No |

## Carpetas utilizadas

Estructura de carpetas en el servidor:

```jsx
/opt/logitron/  # Carpeta principal de instalacion de aplicacion
├── logitron.jar  # Ejecutable backend (servidor)
├── config/  # Archivos de configuracion de aplicacion (no sensibles)
├── uploads/  # Archivos subidos y actualizados por la app
└── temp/  # Archivos temporales durante la ejecucion

/var/log/logitron/  # Carpeta de logs del sistema
├── app.log  # Registro general de la app
├── error.log  # Registro de errores y excepciones
└── access.log  # Registro de accesos al sistema

/var/backups/logitron/  # Carpeta de copias de seguridad
├── database/  # Backups manuales de la base de datos
└── daily/  # Backupos automaticos

/etc/systemd/system/  # Carpeta de servicios del sistema Linux
└── logitron.service  # Archivo de configuracion del servicio
```

## Permisos de usuarios a las carpetas

| Carpeta | Uso | logitron | backupuser | adminsys | developer |
| --- | --- | --- | --- | --- | --- |
| /opt/logitron/ | instalacion de aplicacion | rwx | —- | rwx | r-x |
| /var/log/logitron | Logs de la app | rwx | r— | rwx | r— |
| /var/backups/logitron | Copias de seguridad | —- | rwx | rwx | —- |
| /etc/systemd/system/logitron.service | Servicio del sistema | r— | —- | rwx | r— |