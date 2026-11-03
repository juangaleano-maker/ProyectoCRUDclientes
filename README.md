# API de Clientes — Prueba Técnica

CRUD de clientes desarrollado con Java 17 y Spring Boot 3.

## Tecnologías utilizadas

| Tecnología | Versión | Propósito |
|---|---|---|
| Java | 17 | Lenguaje principal |
| Spring Boot | 3.2.5 | Framework principal |
| Spring Data JPA | 3.2.5 | Acceso a datos |
| Spring Validation | 3.2.5 | Validación de campos |
| H2 Database | (runtime) | Base de datos en memoria |
| Lombok | (latest) | Reducción de boilerplate |
| SpringDoc OpenAPI | 2.3.0 | Documentación Swagger |
| Spring Actuator | 3.2.5 | Endpoint de salud |
| Maven | 3.9+ | Gestión de dependencias |

## Requisitos previos

- **Java 17** o superior instalado (`java -version`)
- **Maven 3.9+** instalado (`mvn -version`) — o usar el wrapper incluido (`./mvnw`)
- *(Opcional)* Docker y Docker Compose para ejecución en contenedor

## Cómo levantar el proyecto

### Opción 1 — Maven directo

```bash
# Clonar o descomprimir el proyecto
cd clientes

# Compilar y ejecutar
./mvnw spring-boot:run
# En Windows: mvnw.cmd spring-boot:run
```

### Opción 2 — JAR ejecutable

```bash
./mvnw package -DskipTests
java -jar target/clientes-0.0.1-SNAPSHOT.jar
```

### Opción 3 — Docker Compose

```bash
docker-compose up --build
```

La aplicación queda disponible en `http://localhost:8080`.

## Base de datos

El proyecto utiliza **H2 embebido con persistencia en disco** (`./data/clientesdb.mv.db`), lo que garantiza que:
1. **Los datos persisten entre reinicios**: cualquier cliente creado, editado o eliminado permanece guardado aunque se detenga o reinicie la aplicación.
2. **Setup con cero configuración**: no requiere instalar motores externos (PostgreSQL/MySQL) ni configurar servicios adicionales para evaluar el proyecto.
3. **Carga inicial idempotente**: al iniciar por primera vez se cargan 3 clientes de prueba mediante `DataInitializer`, pero solo si la base de datos se encuentra vacía (`repo.count() == 0`).

### Consola H2 (modo desarrollo)

Acceder a `http://localhost:8080/h2-console` con:

| Campo | Valor |
|---|---|
| JDBC URL | `jdbc:h2:file:./data/clientesdb` |
| Usuario | `sa` |
| Contraseña | *(vacía)* |

## Endpoints de la API

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/api/clientes` | Crear cliente |
| `GET` | `/api/clientes` | Listar clientes (filtros + paginación) |
| `GET` | `/api/clientes/{id}` | Obtener cliente por ID |
| `PUT` | `/api/clientes/{id}` | Actualizar cliente |
| `DELETE` | `/api/clientes/{id}` | Eliminar cliente |
| `GET` | `/actuator/health` | Estado del servicio |
| `GET` | `/swagger-ui.html` | Documentación Swagger |

## Ejemplos de uso (curl)

### Crear cliente
```bash
curl -X POST http://localhost:8080/api/clientes -H "Content-Type: application/json" -d '{"nombre":"Ana","apellido":"Martinez","documento":"40123456","telefono":"+54 11 9999-8888","direccion":"Sarmiento 800"}'
```

### Listar todos los clientes
```bash
curl http://localhost:8080/api/clientes
```

### Listar con filtros y paginación
```bash
curl "http://localhost:8080/api/clientes?nombre=Juan&page=0&size=5&sort=apellido,asc"
```

### Obtener cliente por ID
```bash
curl http://localhost:8080/api/clientes/1
```

### Actualizar cliente
```bash
curl -X PUT http://localhost:8080/api/clientes/1 -H "Content-Type: application/json" -d '{"nombre":"Juan Carlos","apellido":"Perez","documento":"32145678","telefono":"+54 11 0000-1111","direccion":"Nueva Direccion 999"}'
```

### Eliminar cliente
```bash
curl -X DELETE http://localhost:8080/api/clientes/1
```

## Documentación interactiva

Swagger UI disponible en: `http://localhost:8080/swagger-ui.html`

## Decisiones técnicas

- **H2 embebido con persistencia en archivo (`./data/clientesdb`)**: elegida para garantizar la persistencia real de los datos sin comprometer la facilidad de ejecución ni obligar al evaluador a levantar un motor de base de datos externo. La carpeta `./data/` se genera automáticamente.
- **DDL en modo `update`**: conserva la estructura y registros existentes al reiniciar el servidor.
- **DTOs separados**: se usan `ClienteRequestDTO` y `ClienteResponseDTO` para desacoplar la API de la entidad JPA, evitando exponer detalles internos.
- **Mapper manual**: se optó por un mapper propio (sin MapStruct) para mantener la simplicidad y no agregar procesamiento de anotaciones adicional.
- **Capa Service + Interface**: facilita testeo con mocks y permite cambiar implementaciones sin tocar el controlador.
- **Paginación y filtros**: implementados desde el inicio para demostrar buenas prácticas de API REST.
- **Validaciones**: realizadas en el DTO con Bean Validation; el documento tiene unicidad garantizada tanto a nivel de base de datos (constraint `unique`) como a nivel de servicio.
- **Manejo global de errores**: `GlobalExceptionHandler` centraliza todas las respuestas de error con estructura consistente.

## Posibles mejoras

- Reemplazar H2 por PostgreSQL con un perfil `prod` y variables de entorno.
- Agregar autenticación con Spring Security.
- Agregar campo `email` con validación de formato.
