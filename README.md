# spring-activemq-integration

[![Java CI with Maven](https://github.com/hendisantika/spring-activemq-integration/actions/workflows/maven.yml/badge.svg)](https://github.com/hendisantika/spring-activemq-integration/actions/workflows/maven.yml)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.2-6DB33F?logo=springboot)
![ActiveMQ](https://img.shields.io/badge/ActiveMQ-Classic-blue?logo=apache)

A bundle of projects that shows how to exchange XML messages through **Apache ActiveMQ** with Spring JMS:

- **person-app** (`com.hendisantika:person-obj`): a shared schema library. It holds a basic `Person` XSD
  (an `id` attribute and a `name` element), and JAXB (Jakarta) classes are generated from it at build time.
- **publisher**: uses Spring `JmsTemplate` to marshal a `Person` to XML and send it to an ActiveMQ queue on startup.
- **consumer**: uses Spring `@JmsListener` to consume messages from the queue, unmarshal them back to `Person`
  and log them.

```
┌──────────────┐  XML (Person)  ┌───────────────────┐  XML (Person)  ┌──────────────┐
│  publisher   │ ─────────────▶ │     ActiveMQ      │ ─────────────▶ │   consumer   │
│ JmsTemplate  │                │ MESSAGE.PROCESS.T │                │ @JmsListener │
└──────────────┘                └───────────────────┘                └──────────────┘
        ▲                                                                   ▲
        └─────────────── person-obj (XSD + generated JAXB classes) ─────────┘
```

## Tech Stack

| Component            | Version                                  |
|----------------------|------------------------------------------|
| Java                 | 25                                       |
| Spring Boot          | 4.1.2                                    |
| Messaging            | Spring JMS + ActiveMQ Classic            |
| XML binding          | Jakarta XML Binding (JAXB) + Spring OXM  |
| Code generation      | `org.jvnet.jaxb:jaxb-maven-plugin` 4.0.x |
| Build                | Maven 3.9.x (Maven Wrapper included)     |
| Tests                | JUnit 5 + embedded ActiveMQ broker       |

## Project Structure

```
.
├── person-app/   # shared XSD -> JAXB classes (artifact: com.hendisantika:person-obj)
├── publisher/    # Spring Boot app that sends Person messages
├── consumer/     # Spring Boot app that receives Person messages
└── .github/
    ├── workflows/maven.yml   # CI: build & test on JDK 25
    └── dependabot.yml        # Weekly Maven & GitHub Actions updates
```

## Prerequisites

- JDK 25
- Maven 3.9+ (or use the bundled `./mvnw` in `publisher` and `consumer`)
- A running ActiveMQ broker on `tcp://localhost:61616`, for example with Docker:

  ```shell
  docker run -d --name activemq -p 61616:61616 -p 8161:8161 apache/activemq-classic:latest
  ```

  The web console is available at http://localhost:8161 (default login `admin` / `admin`).

## Build

`person-obj` must be installed into your local Maven repository first, because both apps depend on it:

```shell
mvn -B install -f person-app/pom.xml
mvn -B verify  -f publisher/pom.xml
mvn -B verify  -f consumer/pom.xml
```

The tests start an embedded in-memory broker (`vm://embedded`), so you don't need a running ActiveMQ to run them.

## Run

1. Start the consumer (it keeps listening):

   ```shell
   cd consumer && ./mvnw spring-boot:run
   ```

2. In another terminal, run the publisher (it sends one `Person` on startup):

   ```shell
   cd publisher && ./mvnw spring-boot:run
   ```

3. The consumer logs something like:

   ```
   Id: 0be6e3da-87ac-42d8-bf6b-255b06b76789 Name: John Doe
   ```

## Configuration

Both apps read these properties from `src/main/resources/application.properties`:

| Property                                     | Default                                 | Description                         |
|----------------------------------------------|-----------------------------------------|-------------------------------------|
| `spring.activemq.broker-url`                 | `tcp://localhost:61616`                 | ActiveMQ broker URL                 |
| `outbound.endpoint` / `inbound.endpoint`     | `MESSAGE.PROCESS.T`                     | Queue name (publisher / consumer)   |
| `context.path`                               | `com.hendisantika.person.schema`        | JAXB context path                   |
| `schema.location`                            | `classpath:xsd/person-schema.xsd`       | XSD used to validate messages       |

## CI/CD & Dependency Updates

- **GitHub Actions** (`.github/workflows/maven.yml`) builds and tests every module on JDK 25 (Temurin) for each push
  and pull request to `master`, and submits the Maven dependency graph to GitHub.
- **Dependabot** (`.github/dependabot.yml`) checks Maven dependencies of all modules and GitHub Actions every
  Monday. It groups minor and patch updates (Spring and the rest), and opens major updates as separate PRs.

## Author

**Hendi Santika**

- Email: hendisantika@gmail.com
- Telegram: [@hendisantika34](https://t.me/hendisantika34)
- Link: [s.id/hendisantika](https://s.id/hendisantika)
