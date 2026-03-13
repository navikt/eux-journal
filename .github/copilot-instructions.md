# Copilot Instructions — eux-journal

## Build & Test

```bash
# Build (requires running PostgreSQL, see env vars below)
mvn clean install -B --no-transfer-progress

# Build without tests
mvn clean install -DskipTests -B --no-transfer-progress

# Run a single test class
mvn test -pl eux-journal-webapp -Dtest=RinasakerTest -B --no-transfer-progress

# Run a single test method (backtick names need quoting)
mvn test -pl eux-journal-webapp -Dtest='RinasakerTest#POST feilregistrer - ok - 200' -B --no-transfer-progress
```

Tests require a running PostgreSQL database:

```bash
export DATABASE_HOST=localhost DATABASE_PORT=5432 DATABASE_USERNAME=postgres DATABASE_PASSWORD=postgres DATABASE_DATABASE=postgres
```

## Architecture

Multi-module Maven project (Kotlin, Spring Boot 4, JDK 25) integrating EUX (EESSI/RINA) with NAV's journal system (Dokarkiv/SAF).

**Module dependency flow:**

```
openapi → model → persistence
                → integration
         service (depends on persistence + integration)
         webapp  (depends on service, runs the Spring Boot app)
```

- **openapi** — OpenAPI 3 YAML spec + generated Kotlin Spring interfaces (`kotlin-spring` generator, interface-only). Controllers implement these generated interfaces.
- **model** — Domain entities, enums, and DTOs as Kotlin data classes.
- **persistence** — Spring Data JPA repositories (`JpaRepository<Entity, UUID>`).
- **integration** — HTTP clients for external NAV services (Dokarkiv, SAF, EUX Oppgave, EUX Nav Rinasak). Uses Spring RestTemplate with OAuth2 bearer token interceptors.
- **service** — Business logic orchestrating persistence and integration layers.
- **webapp** — REST controllers, `@RestControllerAdvice` exception handlers, Spring Boot main class.

## Key Conventions

### Language & Style

- **Pure Kotlin** — no Java source code. All modules use Kotlin data classes (not Java records).
- **Constructor injection** — services/clients take dependencies as `val` constructor parameters (no `@Autowired` fields).
- **Extension functions** — used extensively for mapping (`toOkResponseEntity()`, `toEmptyResponseEntity()`), MDC context, HTTP helpers, and enum conversion.
- **Infix functions** — used for pairing domain objects (e.g., `dokument med journalpost`).
- **Logging** — `val log = logger {}` from `kotlin-logging-jvm`. MDC enrichment via custom `mdc()` extensions.

### Naming

| Type | Pattern | Example |
|------|---------|---------|
| Controller | `{Domain}ApiImpl` | `RinasakerApiImpl` |
| Service | `{UseCase}Service` | `FeilregistrerJournalpostService` |
| Client | `{ExternalService}Client` | `DokarkivClient`, `SafClient` |
| Repository | `{Entity}Repository` | `FeilregistreringRepository` |
| Exception | `{Service}{Type}Exception` | `DokarkivBadRequestException` |
| Advice | `{Exception}Advice` | `DokarkivBadRequestAdvice` |

### API Layer

- API spec lives in `eux-journal-openapi/src/main/resources/static/eux-journal-v1.yaml` with modular `$ref` YAML files.
- OpenAPI Generator produces interfaces in `no.nav.eux.journal.openapi.api` and models in `no.nav.eux.journal.openapi.model`.
- Controllers implement the generated interface and delegate to services. Endpoints use `@Protected` (NAV token-validation) for JWT security.

### Persistence

- JPA entities are Kotlin data classes with Jakarta annotations.
- Flyway manages migrations in `src/main/resources/db/migration/`.
- Database: PostgreSQL 18+.

### Testing

- Integration tests in `eux-journal-webapp/src/test/` extend an abstract base class with `@SpringBootTest`, `@EnableMockOAuth2Server`, and `@AutoConfigureTestRestTemplate`.
- Tests use `MockWebServer` (OkHttp) to stub external service calls, with mock responses defined per service.
- Test method names use Kotlin backtick syntax for readability (e.g., `` `POST feilregistrer - ok - 200` ``).
- AssertJ for assertions.

### Security

- JWT validation via `no.nav.security:token-validation-spring` with Azure AD as issuer.
- OAuth2 client credentials for outgoing service calls, configured per-service with separate `RestTemplate` beans and bearer token interceptors.
