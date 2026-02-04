# URL Shortener (Spring Boot)

URL shortener service implemented in Java with Spring Boot. Same functionality as the Go version: shorten URLs, redirect, in-memory storage, top 3 domains metrics.

## Requirements

- Java 17+
- Maven 3.6+

## Build and run

```bash
mvn spring-boot:run
```

Or build and run the JAR:

```bash
mvn clean package -DskipTests
java -jar target/url-shortener-1.0.0.jar
```

Server runs on `http://localhost:8080`.

## API

### Shorten a URL

```bash
curl -X POST http://localhost:8080/shorten \
  -H "Content-Type: application/json" \
  -d '{"url": "https://www.youtube.com/watch?v=example"}'
```

Response (201): `{"short_url":"http://localhost:8080/r/...", "original":"..."}`  
Same URL returns the same short URL (idempotent).

### Redirect

```bash
curl -L "http://localhost:8080/r/<shortCode>"
# or
curl -v "http://localhost:8080/r/<shortCode>"
```

### Top 3 domains (metrics)

```bash
curl http://localhost:8080/metrics/domains
```

Response: `{"domains":[{"domain":"...","count":n}, ...]}` (up to 3).

## Tests

```bash
mvn test
```

## Docker

```bash
docker build -t url-shortener-java .
docker run -p 8080:8080 url-shortener-java
```

## Configuration

- `server.port` — default 8080
- `urlshortener.base-url` — base URL for short links (default `http://localhost:8080`)
