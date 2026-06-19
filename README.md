# java-openhab-rest-client-backend

Spring Boot backend for the [java-openhab-rest-client-test-app](https://github.com/Michdo93/java-openhab-rest-client-test-app).

Wraps the [java-openhab-rest-client](https://github.com/Michdo93/java-openhab-rest-client) library as a REST API so the GitHub Pages frontend can reach openHAB through myopenhab.org.

## Architecture

```
Browser (GitHub Pages)
  └── POST /api/items, /api/things, … + SSE GET /api/events/stream
        └── Spring Boot (Render.com)
              └── java-openhab-rest-client (JitPack)
                    └── myopenhab.org → local openHAB
```

## Endpoints

Every endpoint accepts a JSON body with connection credentials:

```json
{
  "url":      "https://myopenhab.org",
  "username": "user@example.com",
  "password": "mypassword",
  "token":    null,
  "body":     "ON",
  "params":   {}
}
```

| Path | Description |
|---|---|
| `POST /api/connect` | Verify credentials |
| `POST /api/uuid` | GET /rest/uuid |
| `POST /api/systeminfo` | GET /rest/systeminfo |
| `POST /api/items` | GET /rest/items |
| `POST /api/items/{name}/command` | Send command |
| `POST /api/things` | GET /rest/things |
| `POST /api/rules` | GET /rest/rules |
| `POST /api/addons` | GET /rest/addons |
| `POST /api/audio/sinks` | GET /rest/audio/sinks |
| `POST /api/logging` | GET /rest/logging |
| `POST /api/sitemaps` | GET /rest/sitemaps |
| `POST /api/voice/voices` | GET /rest/voice/voices |
| `GET  /api/events/stream` | SSE proxy to openHAB |
| … and many more | See MiscControllers.java |

## Local development

```bash
mvn spring-boot:run
# Backend läuft auf http://localhost:8080
```

## Deploy to Render.com

1. Repository auf GitHub pushen
2. Auf render.com → New → Web Service → dieses Repo auswählen
3. Build Command: `mvn clean package -DskipTests`
4. Start Command: `java -jar target/java-openhab-rest-client-backend-1.0.0.jar`
5. Environment: Java, PORT=8080
6. Deploy → URL merken für das Frontend

## Frontend (GitHub Pages)

Das Frontend liegt im Ordner `frontend/` und muss in ein separates Repository
[java-openhab-rest-client-test-app](https://github.com/Michdo93/java-openhab-rest-client-test-app)
gepusht werden, mit GitHub Pages aktiviert.

Die Backend-URL wird im Browser-UI eingetragen.
