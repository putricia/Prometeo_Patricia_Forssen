# Mantenimiento

El mantenimiento del sistema incluye las siguientes tareas:

## Actualización del sistema

**Revisión mensual** de la actualización de:

- sistema operativo
- servidor web
- base de datos
- dependencias de la aplicación.

Las actualizaciones críticas se deberán hacer inmediatamente.

## Monitorización

**Revisión periódica** de:

- logs del servidor
- uso de CPU y RAM
- espacio en disco
- errores del sistema

## Copias de seguridad

De manera automática se generará:

- Copia diraria de la base de datos por los cambios constantes de los datos de pedidos.
- Copia semanal del sistema para poder restaurar el servidor rápidamente en caso de fallo.

Se recomienta verificar periódicamente que las copias se realizan correctamente y se peuden restaurar en caso de ser necesario.

## Acción en caso de fallo

1. Revisar logs del servidor
2. Reiniciar los servicio del servidor
3. Restaurar última copia de seguridad de los datos, en caso de datos corruptos
4. Contactar con el administrador del sistema si el fallo persiste