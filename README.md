# Selenium Learning Project (Java + Maven)

A hands-on practice project for learning browser automation with **Selenium 4**, **Java**, **JUnit 5**, **TestNG**, and **Maven**. Tests run against the public [Sauce Demo](https://www.saucedemo.com/) site (Swag Labs).

The layout follows common enterprise patterns: page objects, feature-based tests, external config and test data, parallel execution, tags for suites, and Allure reporting.

## Tech stack

| Tool | Role |
|------|------|
| Java 25 | Language |
| Selenium 4.21 | Browser automation |
| JUnit 5 | Primary test framework |
| TestNG | Legacy-style sample (`LoginTestNG` + `testng.xml`) |
| AssertJ | Fluent assertions |
| Gson | JSON test data |
| Maven Surefire | Test execution (parallel methods) |
| Allure | HTML reports and failure attachments |

## Project structure

```
src/test/java/com/learning/
  base/              BaseTest - per-test driver setup (@BeforeEach)
  extensions/        JUnit extensions (screenshot + driver quit on failure)
  pages/             Page Object Model (Login, Inventory, Cart, Checkout)
  tests/             Feature-based JUnit test classes
  tests/support/     Shared flows (TestFlows.loginAsStandardUser)
  utils/             ConfigReader, DriverFactory, WaitUtils, ScreenshotUtils,
                     TestDataReader, CheckoutData

src/test/resources/
  config.properties         browser, headless
  config-dev.properties     dev base.url, username, password
  config-qa.properties      qa environment
  testdata/checkout.json    checkout form data (externalized)
  allure.properties         Allure results directory

testng.xml                  TestNG smoke suite (LoginTestNG)
```

## Test suite (by feature)

Tests live in one class per product area. Use `@Tag("smoke")` or `@Tag("regression")` on methods to filter suites.

| Class | What it covers |
|-------|----------------|
| `LoginTest` | Successful login, empty credentials, parameterized invalid logins, explicit waits |
| `CartTest` | Add item, multiple items, remove item |
| `CheckoutTest` | End-to-end checkout (JSON data), step-one validation |
| `FailureDemoTest` | Intentional failures for screenshot / Allure demos (`@Tag("diagnostic")`) |
| `LoginTestNG` | TestNG sample (not run by default JUnit Surefire) |

`FailureDemoTest` is **excluded** from normal `mvn test` runs. Use `-Pdiagnostic` when you want to verify failure reporting (build will fail on purpose).

## Prerequisites

- JDK 25 (or change `maven.compiler.source` / `target` in `pom.xml`)
- Maven 3.8+
- Chrome (default), or Firefox / Edge via `config.properties`

Selenium 4 downloads matching drivers automatically (Selenium Manager).

## Configuration

| File | Purpose |
|------|---------|
| `config.properties` | `browser`, `headless` |
| `config-dev.properties` | Dev `base.url`, username, password |
| `config-qa.properties` | QA settings |
| `testdata/checkout.json` | Checkout first name, last name, postal code |

Credentials are the public Sauce Demo users only.

Runtime overrides:

```bash
mvn test -Denv=qa
mvn test -Dheadless=true
mvn test -Dbrowser=firefox
```

## Running tests

All feature tests (diagnostic excluded):

```bash
mvn clean test
```

Single class:

```bash
mvn test -Dtest=LoginTest
```

### Maven profiles

| Profile | What it does |
|---------|----------------|
| `dev` | Load `config-dev.properties` (default) |
| `qa` | Load `config-qa.properties` |
| `smoke` | Only `@Tag("smoke")` tests |
| `regression` | Only `@Tag("regression")` tests |
| `headless` | Headless browser |
| `diagnostic` | Only `@Tag("diagnostic")` failure demos |

Examples:

```bash
mvn test -Psmoke -Pheadless
mvn test -Pregression -Pheadless
mvn test -Pqa -Pheadless

# Intentional failures -- expect BUILD FAILURE; check target/screenshots/
mvn test -Pdiagnostic -Pheadless
```

### TestNG suite (optional)

Runs `LoginTestNG` via `testng.xml` only (JUnit tests are not run in this mode):

```bash
mvn test "-DsuiteXmlFile=testng.xml" -Pheadless
```

On PowerShell, quote the `-D` argument so `.xml` is not parsed as a lifecycle phase.

### Parallel execution

Surefire runs test **methods** in parallel (`threadCount=3`) with one `WebDriver` per test via `BaseTest` + `TestResultExtension`. Do not share a static driver across tests when parallel is enabled.

## Test reports

### Allure (recommended)

1. Run tests:

```bash
mvn clean test -Pheadless
```

2. Open the report:

```bash
mvn allure:serve
```

`allure:serve` builds HTML and opens it in your browser (local temporary server).

Static HTML (no server):

```bash
mvn allure:report
```

Open `target/site/allure-maven-plugin/index.html`.

Raw results between runs: `target/allure-results/`. Failed tests attach a **Failure screenshot** in the report.

After diagnostic failures:

```bash
mvn test -Pdiagnostic -Pheadless
mvn allure:serve
```

### Surefire (built-in)

```bash
mvn surefire-report:report
```

Open `target/site/surefire-report.html`. JUnit XML is under `target/surefire-reports/`.

## What this project covers

- Page Object Model with shared `BasePage` and explicit waits
- Feature-based test classes with JUnit `@Tag` suites (smoke / regression)
- Parameterized tests (`@ParameterizedTest` + `@CsvSource`)
- External JSON test data (`TestDataReader` + `testdata/checkout.json`)
- Parallel Surefire execution with isolated drivers per test
- TestNG sample class and `testng.xml` for legacy pack patterns
- Screenshots on failure (disk under `target/screenshots/` + Allure attachments)
- Allure HTML reporting
- Environment profiles (`dev` / `qa`) and headless mode

## Git and ignored files

Commit source, `pom.xml`, config, and test data only. Do **not** commit:

| Ignored | Reason |
|---------|--------|
| `target/` | Build output, Surefire reports, Allure results, screenshots |
| `.allure/` | Downloaded Allure CLI (created by `mvn allure:serve`) |
| `allure-results/`, `allure-report/` | Generated report artifacts if outside `target/` |
| `.env`, `credentials.json` | Secrets |
| IDE/OS files | `.idea/`, `*.iml`, `.vscode/launch.json`, etc. |

`.gitignore` is in the repo root; keep it committed so GitHub stays clean.

## License

Learning project -- use and modify freely.
