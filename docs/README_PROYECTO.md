# Sistema de Gestion de Arriendos de Camiones - Camionessa SPA

## Descripcion General
Proyecto de microservicios con Spring Boot para gestionar clientes, camiones, usuarios y arriendos.
El sistema se compone de 4 servicios independientes que se comunican por HTTP usando OpenFeign y almacenan datos en MongoDB Atlas.

## Arquitectura
- Cliente (puerto `8081`)
- Camion (puerto `8082`)
- Usuario (puerto `8083`)
- Arriendo (puerto `8080`, orquestador principal)

## Microservicios
### 1. Cliente (`8081`)
- Gestiona datos de clientes.
- Endpoints base: `/api/clientes`.

### 2. Camion (`8082`)
- Gestiona catalogo y estado de camiones.
- Endpoints base: `/api/camiones`.

### 3. Usuario (`8083`)
- Gestiona usuarios internos del sistema.
- Endpoints base: `/api/usuarios`.

### 4. Arriendo (`8080`)
- Orquesta el flujo de arriendo.
- Consume Cliente, Camion y Usuario mediante OpenFeign.
- Endpoints base: `/api/arriendos`.

## Endpoints REST (CRUD)
### Cliente
- `GET /api/clientes`
- `GET /api/clientes/{id}`
- `POST /api/clientes`
- `PUT /api/clientes/{id}`
- `DELETE /api/clientes/{id}`

### Camion
- `GET /api/camiones`
- `GET /api/camiones/{id}`
- `POST /api/camiones`
- `PUT /api/camiones/{id}`
- `DELETE /api/camiones/{id}`

### Usuario
- `GET /api/usuarios`
- `GET /api/usuarios/{id}`
- `POST /api/usuarios`
- `PUT /api/usuarios/{id}`
- `DELETE /api/usuarios/{id}`

### Arriendo
- `GET /api/arriendos`
- `GET /api/arriendos/{id}`
- `POST /api/arriendos`
- `PUT /api/arriendos/{id}`
- `DELETE /api/arriendos/{id}`

## Swagger / OpenAPI
### UI
- Cliente: `http://localhost:8081/swagger-ui.html`
- Camion: `http://localhost:8082/swagger-ui.html`
- Usuario: `http://localhost:8083/swagger-ui.html`
- Arriendo: `http://localhost:8080/swagger-ui.html`

### API Docs
- Cliente: `http://localhost:8081/api-docs`
- Camion: `http://localhost:8082/api-docs`
- Usuario: `http://localhost:8083/api-docs`
- Arriendo: `http://localhost:8080/api-docs`

## Tecnologias
- Java 21
- Spring Boot 3.5.x
- Spring Cloud OpenFeign
- Spring Data MongoDB
- Spring Boot Actuator
- Lombok
- SpringDoc OpenAPI
- Maven


### Recomendaciones
- Usar variables de entorno para MongoDB y configuracion sensible.
- Evitar usuarios/password en texto plano en `application.properties`.
- Encriptar contrasenas con BCrypt.
- Implementar autenticacion/autorizacion (Spring Security + JWT) para produccion.

### Ejemplo de configuracion
```properties
spring.application.name=Arriendo
server.port=8080
spring.data.mongodb.uri=${MONGODB_URI_ARRIENDO}

cliente.service.url=${CLIENTE_SERVICE_URL:http://localhost:8081}
camion.service.url=${CAMION_SERVICE_URL:http://localhost:8082}
usuario.service.url=${USUARIO_SERVICE_URL:http://localhost:8083}
```

## Prerrequisitos
- Java 21+
- Maven 3.8+
- Acceso a MongoDB Atlas

## Orden de Arranque
1. Cliente
2. Camion
3. Usuario
4. Arriendo

Comando de inicio por servicio:
```bash
mvn spring-boot:run
```

## Build y Tests
```bash
mvn clean install
mvn test
```

## Estructura General
- `Cliente/Cliente`
- `camion/camion`
- `Usuario/Usuario`
- `Arriendo/Arriendo`

## Versiones de Referencia
- Spring Boot: 3.5.10
- Spring Cloud: 2025.0.1
- Java: 21
- SpringDoc OpenAPI: 2.3.0

## Autor
- Maxi Huerta

**Ultima actualizacion**: Abril 2026
