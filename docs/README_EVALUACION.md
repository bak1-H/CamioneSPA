📦 Ingieneria - Base DevOps
📌 Descripción

Este repositorio corresponde a un proyecto de [tipo de sistema, ej: microservicios en Spring Boot], cuyo objetivo es servir como base para la implementación de un flujo DevOps, integrando control de versiones, trabajo colaborativo y automatización.

Este trabajo forma parte del encargo de la asignatura Ingeniería DevOps.

🧩 Tecnologías Utilizadas
    Lenguaje: Java 21
    Framework: Spring Boot 3.5.10
    Base de datos: MongoDB Atlas (Cloud Cluster)
    Control de versiones: Git
    Plataforma: GitHub
    CI/CD: GitHub Actions
    Gestión de Dependencias: Maven 3.8+


⚙️ Ejecución del Proyecto
    - Java 21 o superior
    - Maven 3.8+
    - Git
    - Acceso a Internet (MongoDB Atlas)

    Verificar instalación:
    java -version
    mvn -version
    git --version

⚙️ Ejecución
    # Terminal 1 - Cliente (puerto 8081)
    cd Cliente/Cliente
    mvn spring-boot:run

    # Terminal 2 - Camión (puerto 8082)
    cd camion/camion
    mvn spring-boot:run

    # Terminal 3 - Usuario (puerto 8083)
    cd Usuario/Usuario
    mvn spring-boot:run

    # Terminal 4 - Arriendo (puerto 8080, ÚLTIMO)
    cd Arriendo/Arriendo
    mvn spring-boot:run

⚙️ Build 
    cd Cliente/Cliente && mvn clean install && cd ../../..
    cd camion/camion && mvn clean install && cd ../../..
    cd Usuario/Usuario && mvn clean install && cd ../../..
    cd Arriendo/Arriendo && mvn clean install && cd ../../..


🔐 Configuración
Las credenciales sensibles no se almacenan en el repositorio, sino en llaves secretas en el mismo GitHub.

Ejemplo:
    MONGODB_URI_CLIENTE=mongodb+srv://usuario:password@cluster.mongodb.net/cliente
    MONGODB_URI_CAMION=mongodb+srv://usuario:password@cluster.mongodb.net/camion
    MONGODB_URI_USUARIO=mongodb+srv://usuario:password@cluster.mongodb.net/usuario
    MONGODB_URI_ARRIENDO=mongodb+srv://usuario:password@cluster.mongodb.net/arriendo


🌿 Estrategia de Ramificación
Se ha implementado el modelo:

👉 [ GitFlow ]
Justificación

Justificación:
Se utiliza GitFlow por el contexto del encargo (uso de ramas feature y hotfix), porque mejora la organización del trabajo colaborativo, separa desarrollo de producción y facilita el control de calidad mediante Pull Requests y CI.

Beneficios:

Permite trabajo colaborativo ordenado
Mejora la trazabilidad de cambios
Separa claramente desarrollo y producción
Facilita la integración continua y revisiones

🌱 Estructura de Ramas
main
develop
feature/<nombre>
hotfix/<nombre>

📌 Naming de Ramas
Convención utilizada:
feature/<descripcion-corta>
hotfix/<descripcion-corta>

Ejemplos usados en el proyecto:

feature/implementacion_ci
feature/docs_corrige_readme
hotfix/variables_entorno_mongodb


💬 Convención de Commits
Se utiliza una convención basada en buenas prácticas:

Ejemplos
feat: nueva funcionalidad
fix: corrección de errores
docs: cambios en documentación
ci: cambios en integración continua
chore: tareas menores
refactor: mejora interna

🔀 Flujo de Trabajo (Merge)

Flujo para Features
Crear rama desde develop
Realizar cambios
Hacer commit
Subir cambios (push)
Crear Pull Request hacia develop
Revisar código
Merge

🔀Flujo para Hotfix

Crear rama desde main
Corregir error
Crear Pull Request hacia main
Merge


👥 Estrategia de Revisión

Antes de aprobar un Pull Request se valida:

✔ Que el cambio tenga sentido
✔ Uso correcto de ramas
✔ Mensajes de commit claros
✔ Código funcional
✔ Que el pipeline CI pase correctamente


⚙️ Integración Continua (CI/CD)
Se implementa automatización con GitHub Actions para validar compilación y pruebas en cada cambio relevante.

Eventos configurados:

“Se implementó un pipeline de integración continua con GitHub Actions que se ejecuta automáticamente en cada push a la rama develop y en cada pull request hacia main. Este pipeline compila cada microservicio de forma independiente utilizando Maven, validando que el código sea integrable correctamente antes de su despliegue.”


Ubicación del pipeline:

.github\workflows\ci.yml

1. Checkout del código con actions/checkout@v4
2. Configuración de Java 21 (Temurin) con caché de Maven
3. Build con Maven: mvn clean install -DskipTests

Validaciones que entrega el pipeline:

1. Compilación correcta del proyecto en Java 21
2. Resolución correcta de dependencias Maven
3. Ejecución de pruebas automáticas en integración
