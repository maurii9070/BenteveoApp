# BenteveoApp — Agent Guide

## Project Structure

Two independent projects in one Git repo (NOT a monorepo — no shared workspace/build):

- `backend-benteveo/` — Java 21, Spring Boot 4.1.0, Maven, PostgreSQL
- `frontend-benteveo/` — Angular 22, TypeScript 6, Tailwind CSS 4, Vitest

Each project is built and run independently from its own directory.

## Quick Start

### Backend
```bash
cd backend-benteveo
docker compose up -d          # Start PostgreSQL 18 (port 5432)
cp .env.example .env          # Configure env vars (defaults work locally)
./mvnw spring-boot:run         # Run on port 8080
```

### Frontend
```bash
cd frontend-benteveo
npm install
npm start                      # Dev server on localhost:4200
```

## Commands

### Backend (from `backend-benteveo/`)
| Command | What it does |
|---------|-------------|
| `./mvnw spring-boot:run` | Run the app |
| `./mvnw compile` | Compile (serves as typecheck) |
| `./mvnw test` | Run JUnit 5 tests |
| `./mvnw package` | Build JAR |

No linting tool configured. Java compilation is the type check.

### Frontend (from `frontend-benteveo/`)
| Command | What it does |
|---------|-------------|
| `npm start` / `ng serve` | Dev server (port 4200) |
| `npm run build` | Production build |
| `npm run test` | Unit tests (Vitest) |
| `npx prettier --write .` | Format code |

No ESLint configured. TypeScript checking happens during `ng build`/`ng serve`.

## Key Conventions

### Backend
- **Package structure:** Feature-based under `ar.com.benteveo.backend.features.{feature}.{subfeature}` (e.g. `features/auth/login/`)
- **Shared code:** `shared/` for cross-cutting concerns (exceptions, config, response wrappers)
- **DTOs:** Java records (not classes) for request/response bodies
- **No `@Autowired`:** Constructor injection via Lombok `@RequiredArgsConstructor` or implicit single constructors
- **Entities:** Use Lombok `@Data`/`@Builder`/`@NoArgsConstructor`/`@AllArgsConstructor`. UUIDs via `@UuidGenerator`
- **API prefix:** All endpoints under `/api/v1/`
- **Response format:** Wrapped in `ApiResponse<T>` record with `success`, `message`, `data`, `timestamp`
- **DB schema:** Auto-managed by Hibernate (`ddl-auto=update`). No migration tool.
- **Security:** Spring Security 4.1.0 with lambda DSL. CSRF disabled. Stateless JWT sessions. `/api/v1/auth/**` is public, everything else requires auth.
- **WARNING:** No JWT authentication filter is wired into the `SecurityFilterChain` yet. The `JwtService` exists but `JwtAuthenticationFilter` is not configured. Non-auth routes will reject requests until this is implemented.

### Frontend
- **Angular 22 standalone components** — no NgModules. Use `bootstrapApplication()`.
- **New file convention:** Component files use `.ts` extension (e.g. `app.ts` not `app.component.ts`). Class names are `App` not `AppComponent`.
- **Template in separate `.html` file**, styles in separate `.css` file
- **New control flow syntax:** `@for ... track ...`, `@if`, etc.
- **Angular signals** for state management
- **Tailwind CSS 4** imported via `@import 'tailwindcss'` in `styles.css`
- **Formatting:** Prettier with `printWidth: 100`, `singleQuote: true`, Angular HTML parser
- **Indentation:** 2 spaces
- **No HttpClient configured yet** — `provideHttpClient()` is not in app config providers
- **Routes empty** — no routes defined yet in `app.routes.ts`
- **Tests:** Vitest (not Karma/Jasmine)

## Gotchas

- Backend runs on port 8080, frontend on 4200. CORS must be configured for local dev.
- PostgreSQL container name is `benteveo`, database `benteveo_db`, user `dev_user`, password `dev_password`.
- Spring Boot 4.1.0 is very recent — some online examples target 3.x and may use different APIs (e.g. lambda DSL vs. method chaining for Security config).
- Lombok annotation processor is configured in `maven-compiler-plugin` — ensure IDE has Lombok plugin installed.
- The README mentions Cloudinary for image storage and Supabase, but neither is implemented in code yet.
