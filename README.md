# Sistema de Gestión de Arriendos de Camiones - Camionessa SPA

## 📋 Descripción General

Sistema de microservicios desarrollado en Spring Boot para la gestión integral de arriendos de camiones. La arquitectura está compuesta por 4 microservicios independientes que se comunican entre sí mediante **OpenFeign** y utilizan **MongoDB Atlas** como base de datos distribuida.

## 🏗️ Arquitectura del Sistema

```
┌─────────────────────────────────────────────────────────────┐
│                     Sistema Camionessa                       │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐      │
│  │   Cliente    │  │   Camión     │  │   Usuario    │      │
│  │  :8081       │  │   :8082      │  │   :8083      │      │
│  └──────┬───────┘  └──────┬───────┘  └──────┬───────┘      │
│         │                  │                  │              │
│         └──────────────────┼──────────────────┘              │
│                            │                                 │
│                    ┌───────▼────────┐                        │
│                    │   Arriendo     │                        │
│                    │    :8080       │                        │
│                    └────────────────┘                        │
│                                                               │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
                   ┌────────────────┐
                   │  MongoDB Atlas │
                   │   (Cluster)    │
                   └────────────────┘
```

## 🚀 Microservicios

### 1️⃣ Microservicio Cliente (Puerto 8081)

**Descripción**: Gestiona la información de los clientes que arriendan los camiones.

**Tecnologías**:
- Spring Boot 3.5.10
- Java 21
- MongoDB
- OpenFeign
- Lombok
- SpringDoc OpenAPI (Swagger)

**Modelo de Datos**:
```java
Cliente {
    String id
    TipoCliente tipo              // PERSONA | EMPRESA
    String nombreRazonSocial
    String identificacion         // RUT o DNI (único)
    LocalDate fechaRegistro
    DatosContacto contacto {
        String email
        String telefono
        String direccion
        String ciudad
    }
}
```

**Endpoints REST**:
- `GET /api/clientes` - Listar todos los clientes
- `GET /api/clientes/{id}` - Obtener cliente por ID
- `POST /api/clientes` - Crear nuevo cliente
- `PUT /api/clientes/{id}` - Actualizar cliente
- `DELETE /api/clientes/{id}` - Eliminar cliente

**Base de Datos**: `cliente` en MongoDB Atlas

---

### 2️⃣ Microservicio Camión (Puerto 8082)

**Descripción**: Administra el catálogo de camiones disponibles para arriendo.

**Tecnologías**:
- Spring Boot 3.5.10
- Java 21
- MongoDB
- OpenFeign
- Lombok
- SpringDoc OpenAPI (Swagger)

**Modelo de Datos**:
```java
Camion {
    String id
    String patente               // Único
    String marca
    String modelo
    Integer anio
    EstadoCamion estado         // DISPONIBLE | ARRENDADO | MANTENIMIENTO | BAJA
    Double precioDiaBase
}
```

**Endpoints REST**:
- `GET /api/camiones` - Listar todos los camiones
- `GET /api/camiones/{id}` - Obtener camión por ID
- `POST /api/camiones` - Crear nuevo camión
- `PUT /api/camiones/{id}` - Actualizar camión
- `DELETE /api/camiones/{id}` - Eliminar camión

**Base de Datos**: `camion` en MongoDB Atlas

---

### 3️⃣ Microservicio Usuario (Puerto 8083)

**Descripción**: Gestiona los usuarios del sistema (empleados) que administran los arriendos.

**Tecnologías**:
- Spring Boot 3.5.10
- Java 21
- MongoDB
- OpenFeign
- Lombok
- SpringDoc OpenAPI (Swagger)

**Modelo de Datos**:
```java
Usuario {
    String id
    String nombre
    String email                // Único
    String password             // Debe estar encriptado (BCrypt)
    RolUsuario rol             // ADMIN | VENDEDOR | OPERARIO
    boolean activo
    LocalDateTime fechaCreacion
}
```

**Endpoints REST**:
- `GET /api/usuarios` - Listar todos los usuarios
- `GET /api/usuarios/{id}` - Obtener usuario por ID
- `POST /api/usuarios` - Crear nuevo usuario
- `PUT /api/usuarios/{id}` - Actualizar usuario
- `DELETE /api/usuarios/{id}` - Eliminar usuario

**Base de Datos**: `usuario` en MongoDB Atlas

---

### 4️⃣ Microservicio Arriendo (Puerto 8080) 🎯

**Descripción**: Microservicio principal que orquesta las operaciones de arriendo, consumiendo los servicios de Cliente, Camión y Usuario mediante **OpenFeign**.

**Tecnologías**:
- Spring Boot 3.5.10
- Java 21
- MongoDB
- Spring Cloud OpenFeign
- Spring Boot Actuator
- Lombok
- SpringDoc OpenAPI (Swagger)

**Modelo de Datos**:
```java
Arriendo {
    String id
    // Referencias a otras entidades
    String clienteId
    String camionId
    String usuarioResponsableId
    
    LocalDateTime fechaInicio
    Date fechaDevolucionReal
    
    // Snapshots (copia de datos en el momento del arriendo)
    SnapshotCliente clienteSnapshot {
        String id
        String nombreRazonSocial
        String identificacion
        String tipo
    }
    
    SnapshotCamion camionSnapshot {
        String id
        String patente
        String estado
        Double precioDiaBase
    }
    
    SnapshotUsuario usuarioSnapshot {
        String id
        String nombre
        String email
        String rol
    }
    
    EstadoArriendo estado   // ARRENDADO | ACTIVO | FINALIZADO | CANCELADO | ATRASADO | RESERVADO
}
```

**Clientes Feign**:
- `ClienteClient` → Consume `http://localhost:8081`
- `CamionClient` → Consume `http://localhost:8082`
- `UsuarioClient` → Consume `http://localhost:8083`

**Endpoints REST**:
- `GET /api/arriendos` - Listar todos los arriendos
- `GET /api/arriendos/{id}` - Obtener arriendo por ID
- `POST /api/arriendos` - Crear nuevo arriendo (consume los 3 microservicios)
- `PUT /api/arriendos/{id}` - Actualizar arriendo
- `DELETE /api/arriendos/{id}` - Eliminar arriendo

**Base de Datos**: `arriendo` en MongoDB Atlas

**Lógica de Negocio Especial**:
- Al crear un arriendo, se consultan los microservicios de Cliente, Camión y Usuario
- Se almacenan **snapshots** de la información para mantener un registro histórico
- Esto permite que el arriendo conserve la información original aunque los datos en otros servicios cambien

---

## 🗄️ Base de Datos

**Tipo**: MongoDB Atlas (Cloud)

**Conexión**: 
```
mongodb+srv://maxihuerta_db_user:6nOo7ULu9UD7Uine@camionessa.00fvf3k.mongodb.net/
```

**Bases de Datos**:
- `cliente` - Colección: `clientes`
- `camion` - Colección: `camiones`
- `usuario` - Colección: `usuarios`
- `arriendo` - Colección: `arriendos`

**Características**:
- Auto-indexación habilitada
- Índices únicos en: `patente` (Camión), `identificacion` (Cliente), `email` (Usuario)
- Logging de MongoDB Template habilitado en modo DEBUG

---

## 🔧 Configuración y Ejecución

### Prerrequisitos

- **Java 21** o superior
- **Maven 3.8+**
- Conexión a Internet (para MongoDB Atlas)

### Variables de Entorno

Cada microservicio tiene su configuración en `application.properties`:

**Cliente** (`application.properties`):
```properties
spring.application.name=Cliente
server.port=8081
spring.data.mongodb.uri=mongodb+srv://...@camionessa.00fvf3k.mongodb.net/cliente
```

**Camión** (`application.properties`):
```properties
spring.application.name=camion
server.port=8082
spring.data.mongodb.uri=mongodb+srv://...@camionessa.00fvf3k.mongodb.net/camion
```

**Usuario** (`application.properties`):
```properties
spring.application.name=Usuario
server.port=8083
spring.data.mongodb.uri=mongodb+srv://...@camionessa.00fvf3k.mongodb.net/usuario
```

**Arriendo** (`application.properties`):
```properties
spring.application.name=Arriendo
server.port=8080
spring.data.mongodb.uri=mongodb+srv://...@camionessa.00fvf3k.mongodb.net/arriendo

# URLs de servicios remotos (Feign)
cliente.service.url=http://localhost:8081
camion.service.url=http://localhost:8082
usuario.service.url=http://localhost:8083
```

### Orden de Arranque

**⚠️ IMPORTANTE**: Los servicios deben iniciarse en este orden:

1. **Cliente** (puerto 8081)
```bash
cd Cliente/Cliente
mvn spring-boot:run
```

2. **Camión** (puerto 8082)
```bash
cd camion/camion
mvn spring-boot:run
```

3. **Usuario** (puerto 8083)
```bash
cd Usuario/Usuario
mvn spring-boot:run
```

4. **Arriendo** (puerto 8080) - **Último**
```bash
cd Arriendo/Arriendo
mvn spring-boot:run
```

### Compilar los Proyectos

Para compilar todos los microservicios:

```bash
# Cliente
cd Cliente/Cliente && mvn clean install

# Camión
cd ../../camion/camion && mvn clean install

# Usuario
cd ../../Usuario/Usuario && mvn clean install

# Arriendo
cd ../../Arriendo/Arriendo && mvn clean install
```

---

## 📚 Documentación API (Swagger)

Cada microservicio expone su documentación OpenAPI/Swagger:

- **Cliente**: http://localhost:8081/swagger-ui.html
- **Camión**: http://localhost:8082/swagger-ui.html
- **Usuario**: http://localhost:8083/swagger-ui.html
- **Arriendo**: http://localhost:8080/swagger-ui.html

**Endpoints de API Docs**:
- Cliente: http://localhost:8081/api-docs
- Camión: http://localhost:8082/api-docs
- Usuario: http://localhost:8083/api-docs

---

## 🔄 Comunicación entre Microservicios

### Patrón de Comunicación: OpenFeign

El microservicio **Arriendo** utiliza **Spring Cloud OpenFeign** para comunicación síncrona con los otros microservicios:

```java
@FeignClient(name = "cliente-service", url = "${cliente.service.url}")
public interface ClienteClient {
    @GetMapping("/api/clientes/{id}")
    ClienteDto obtenerClientePorId(@PathVariable("id") String id);
}
```

### Flujo de Creación de Arriendo

```
1. Usuario hace POST /api/arriendos
   ├─ clienteId: "123"
   ├─ camionId: "456"
   └─ usuarioResponsableId: "789"
   
2. ArriendoService consulta:
   ├─► GET http://localhost:8081/api/clientes/123 (ClienteClient)
   ├─► GET http://localhost:8082/api/camiones/456 (CamionClient)
   └─► GET http://localhost:8083/api/usuarios/789 (UsuarioClient)
   
3. Crea snapshots con la información obtenida

4. Guarda el arriendo en MongoDB con toda la información
```

---

## 🛠️ Dependencias Principales

### Comunes a todos los Microservicios

```xml
<!-- Spring Boot 3.5.10 -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.5.10</version>
</parent>

<!-- MongoDB -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>

<!-- Web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- OpenFeign -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>

<!-- Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>

<!-- Swagger/OpenAPI -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.3.0</version>
</dependency>
```

### Específico del Microservicio Arriendo

```xml
<!-- Actuator para monitoreo -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

---

## 📊 Estructura de Paquetes (Patrón MVC)

Todos los microservicios siguen la misma estructura:

```
com.Camionesspa.{Microservicio}
├── {Microservicio}Application.java   # Clase principal
├── config/
│   └── SwaggerConfig.java            # Configuración de Swagger
├── controller/
│   └── {Microservicio}Controller.java # Endpoints REST
├── model/
│   └── {Microservicio}.java          # Entidad de dominio
├── repository/
│   └── {Microservicio}Repository.java # Acceso a datos (MongoDB)
└── service/
    └── {Microservicio}Service.java   # Lógica de negocio
```

**Adicional en Arriendo**:
```
com.Camionesspa.Arriendo
└── client/
    ├── ClienteClient.java            # Feign Client
    ├── CamionClient.java             # Feign Client
    ├── UsuarioClient.java            # Feign Client
    └── dto/
        ├── ClienteDto.java
        ├── CamionDto.java
        └── UsuarioDto.java
```

---

## 🔐 Seguridad

⚠️ **Consideraciones de Seguridad Actuales**:

1. **Passwords sin encriptar**: El modelo Usuario incluye un campo `password` que debe almacenarse encriptado usando BCrypt
2. **Sin autenticación**: No hay implementación de Spring Security
3. **URLs hardcodeadas**: Las URLs de los microservicios están en configuración local
4. **Credenciales expuestas**: La URI de MongoDB está en texto plano

**Recomendaciones para Producción**:
- Implementar Spring Security con JWT
- Usar variables de entorno para credenciales
- Implementar Service Discovery (Eureka)
- Añadir API Gateway (Spring Cloud Gateway)
- Encriptar contraseñas con BCrypt

---

## 🧪 Testing

Cada microservicio incluye tests básicos:

```bash
# Ejecutar tests de un microservicio
cd {microservicio}/{microservicio}
mvn test
```

**Tests incluidos**:
- `{Microservicio}ApplicationTests.java` - Prueba de contexto de Spring

---

## 📈 Escalabilidad y Mejoras Futuras

### Implementaciones Recomendadas

1. **Service Discovery** (Eureka Server)
   - Registro automático de microservicios
   - Balanceo de carga del lado del cliente

2. **API Gateway** (Spring Cloud Gateway)
   - Punto de entrada único
   - Enrutamiento inteligente
   - Rate limiting

3. **Circuit Breaker** (Resilience4j)
   - Tolerancia a fallos
   - Fallback automático

4. **Config Server**
   - Configuración centralizada
   - Actualización dinámica

5. **Distributed Tracing** (Zipkin/Sleuth)
   - Trazabilidad de requests
   - Debugging distribuido

6. **Message Queue** (RabbitMQ/Kafka)
   - Comunicación asíncrona
   - Desacoplamiento de servicios

---

## 🐛 Troubleshooting

### Problema: Error de conexión entre microservicios

**Solución**: Verificar que todos los servicios estén corriendo en los puertos correctos:
```bash
netstat -ano | findstr "8080 8081 8082 8083"
```

### Problema: Error de MongoDB

**Solución**: Verificar la conexión a MongoDB Atlas:
- Comprobar que la IP esté en la whitelist
- Verificar credenciales
- Comprobar conexión a internet

### Problema: Puerto ya en uso

**Solución**: 
```bash
# Windows
netstat -ano | findstr :{PUERTO}
taskkill /PID {PID} /F

# Cambiar puerto en application.properties
server.port=XXXX
```

---

## 👥 Equipo de Desarrollo

- **Desarrollador**: Maxi Huerta
- **Proyecto**: EVA3 - Microservicios
- **Organización**: Camionessa SPA

---

## 📝 Licencias

Este proyecto utiliza:
- Spring Boot (Apache License 2.0)
- MongoDB (Server Side Public License)
- Lombok (MIT License)

---

## 🎯 Casos de Uso Principales

### 1. Crear un Arriendo

```json
POST http://localhost:8080/api/arriendos

{
  "clienteId": "67890abcdef123456789",
  "camionId": "12345abcdef123456789",
  "usuarioResponsableId": "abcdef123456789012345",
  "fechaInicio": "2026-01-23T10:00:00"
}
```

El sistema automáticamente:
- Consulta la información del cliente en el servicio Cliente
- Consulta la información del camión en el servicio Camión
- Consulta la información del usuario en el servicio Usuario
- Crea snapshots de toda la información
- Establece el estado inicial como ARRENDADO
- Guarda el arriendo en la base de datos

### 2. Consultar Arriendo con Datos Históricos

```json
GET http://localhost:8080/api/arriendos/{id}

Response:
{
  "id": "arr123",
  "clienteId": "67890...",
  "camionId": "12345...",
  "usuarioResponsableId": "abcdef...",
  "fechaInicio": "2026-01-23T10:00:00",
  "estado": "ACTIVO",
  "clienteSnapshot": {
    "id": "67890...",
    "nombreRazonSocial": "Juan Pérez",
    "identificacion": "12.345.678-9",
    "tipo": "PERSONA"
  },
  "camionSnapshot": {
    "id": "12345...",
    "patente": "AB-CD-12",
    "estado": "ARRENDADO",
    "precioDiaBase": 150000.0
  },
  "usuarioSnapshot": {
    "id": "abcdef...",
    "nombre": "María González",
    "email": "maria@camionessa.cl",
    "rol": "VENDEDOR"
  }
}
```

---

## 📊 Versiones

| Componente | Versión |
|-----------|---------|
| Spring Boot | 3.5.10 |
| Spring Cloud | 2025.0.1 |
| Java | 21 |
| SpringDoc OpenAPI | 2.3.0 |
| MongoDB Driver | Incluido en Spring Boot |
| Maven | 3.8+ |

---

**Última actualización**: Enero 2026
