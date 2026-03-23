# SofInventory — Módulo Java Web

Sistema de inventarios y ventas para ferreterías PYME.  
Este módulo fue desarrollado en Java con Servlets, JSP y PostgreSQL  
como parte de la actividad **GA7-220501096-AA2** del SENA.

---

## Tecnologías utilizadas

- Java 24
- Jakarta Servlets (Jakarta EE 10)
- JSP (Java Server Pages)
- Apache Tomcat 10.1
- PostgreSQL
- NetBeans IDE
- HTML, CSS

---

## Módulos desarrollados

| Módulo | Descripción | Desarrollado por |
|---|---|---|
| Login | Autenticación de usuarios | Ambas |
| Usuarios | CRUD completo de usuarios del sistema | Alejo |
| Proveedores | CRUD completo de proveedores | Compañera |

---

## Relación entre módulos

El módulo de **Proveedores** depende del módulo de **Usuarios**  
porque todo proveedor debe tener un usuario responsable asignado  
como campo `creado_por`. Esto demuestra la sincronización  
entre los dos formularios.

---

## Requisitos previos

- Java 17 o superior
- Apache Tomcat 10.1
- PostgreSQL con puerto 5433
- NetBeans IDE 25 o superior

### Librerías necesarias (agregar en WEB-INF/lib/)
- `postgresql-42.7.10.jar` → https://jdbc.postgresql.org/download/
- `jbcrypt-0.4.jar` → https://mvnrepository.com/artifact/org.mindrot/jbcrypt/0.4

---

## Configuración de la base de datos

Crear la base de datos en PostgreSQL:
```sql
CREATE DATABASE db_sofinventory_java;
```

Luego ejecutar el script de tablas e insertar datos iniciales.  
Ver archivo `/scripts/db_init.sql`

---

## Configurar la conexión

Crear el archivo `src/java/com/sofinventory/utils/Conexion.java`  
con tus propios datos — este archivo NO está en el repositorio  
por seguridad:
```java
package com.sofinventory.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL      = "jdbc:postgresql://localhost:PUERTO/db_sofinventory_java";
    private static final String USUARIO  = "postgres";
    private static final String PASSWORD = "tu_contraseña";

    public static Connection getConexion() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver no encontrado: " + e.getMessage());
        }
    }
}
```

---

## Cómo ejecutar

1. Clonar el repositorio
2. Abrir en NetBeans
3. Crear `Conexion.java` con tus datos
4. Agregar los `.jar` en Libraries y en `WEB-INF/lib/`
5. Configurar Tomcat en NetBeans
6. Ejecutar con **Run** o **F6**
7. Abrir `localhost:8080/SofInventory`

---

## Estándares de codificación aplicados

- Paquetes en minúscula: `com.sofinventory.dao`
- Clases en PascalCase: `UsuarioServlet`, `ProveedorDAO`
- Métodos en camelCase: `listar()`, `buscarPorId()`, `insertar()`
- Variables en camelCase: `nombreCompleto`, `tipoDocumento`
- Constantes en UPPER_CASE: `URL`, `USUARIO`, `PASSWORD`
