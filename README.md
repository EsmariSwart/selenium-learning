# Selenium Learning Project (Java + Maven)

A hands-on practice project for learning browser automation with **Selenium 4**, **Java**, **JUnit 5**, **TestNG**, and **Maven**. Tests run against the public [Sauce Demo](https://www.saucedemo.com/) site (Swag Labs).

The layout follows common enterprise patterns: reusable framework code in `src/main`, tests in `src/test`, page objects, feature-based tests, external config and test data, parallel execution, tags for suites, and Allure reporting.

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

Framework code (reusable, could be packaged as a JAR) lives in `src/main`. Test code and test-only dependencies live in `src/test`.

```
src/main/java/com/learning/
  pages/             Page Object Model (Login, Inventory, Cart, Checkout, BasePage)
  utils/             ConfigReader, DriverFactory, WaitUtils, ScreenshotUtils

src/main/resources/
  config.properties         browser, headless
  config-dev.properties     dev base.url, username, password
  config-qa.properties      qa environment

src/test/java/com/learning/
  base/              BaseTest - per-test driver setup (@BeforeEach)
  extensions/        JUnit extensions (screenshot + driver quit on failure)
  reporting/         AllureReporter (bridges framework screenshots to Allure)
  tests/             Feature-based JUnit test classes
  tests/support/     Shared flows (TestFlows.loginAsStandardUser)
  utils/             TestDataReader, CheckoutData (test data only)

src/test/resources/
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
- **A locally installed browser**:
  - Chrome (default) at `C:\Program Files\Google\Chrome\Application\chrome.exe`
  - Edge at `C:\Program Files (x86)\Microsoft\Edge\Application\msedge.exe`
  - Firefox (auto-detected by Selenium Manager)

`DriverFactory` points Chrome and Edge at the **system-installed browser binary** via `options.setBinary(...)`. This avoids Selenium Manager downloading its own "Chrome for Testing" build into the Selenium cache (`%USERPROFILE%\.cache\selenium\chrome\...`), which can be flagged by endpoint protection such as Microsoft Defender Attack Surface Reduction rules.

If your browser is installed somewhere else, override the path (see [Configuration](#configuration)):

```powershell
mvn test "-Dchrome.binary=C:\Path\To\chrome.exe"
```

Selenium Manager still resolves the matching `chromedriver` / `msedgedriver` automatically into the user cache (`%USERPROFILE%\.cache\selenium\chromedriver\...`). If that path is also blocked by your endpoint protection, ask your admin to whitelist `%USERPROFILE%\.cache\selenium\`.

## Configuration

| File | Purpose |
|------|---------|
| `src/main/resources/config.properties` | `browser`, `headless`, `chrome.binary`, `edge.binary` |
| `src/main/resources/config-dev.properties` | Dev `base.url`, username, password |
| `src/main/resources/config-qa.properties` | QA settings |
| `src/test/resources/testdata/checkout.json` | Checkout first name, last name, postal code |

Credentials are the public Sauce Demo users only.

`chrome.binary` and `edge.binary` point Selenium at the system-installed browser instead of letting Selenium Manager download Chrome / Edge. Defaults assume the standard Windows install paths; override them per machine if needed.

Runtime overrides:

```bash
mvn test "-Denv=qa"
mvn test "-Dheadless=true"
mvn test "-Dbrowser=firefox"
mvn test "-Dchrome.binary=C:\Path\To\chrome.exe"
```

## Running tests

### PowerShell (Windows)

Quote `-D` and `-P` arguments so PowerShell does not mis-parse them:

```powershell
mvn clean test "-Pheadless"
mvn test "-Dtest=LoginTest"
mvn test "-Dtest=LoginTest#successfulLogin"
```

### All feature tests (diagnostic excluded)

```bash
mvn clean test
```

Headless:

```bash
mvn clean test "-Pheadless"
```

Single class or method:

```bash
mvn test "-Dtest=LoginTest"
mvn test "-Dtest=LoginTest#successfulLogin"
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
mvn test "-Psmoke" "-Pheadless"
mvn test "-Pregression" "-Pheadless"
mvn test "-Pqa" "-Pheadless"
mvn test "-Dbrowser=firefox"
```

### Verify screenshots and Allure attachments (intentional failures)

Runs only `FailureDemoTest` (three tests that fail on purpose). **Expect BUILD FAILURE** -- that is correct.

```powershell
mvn clean test "-Pdiagnostic" "-Pheadless"
```

What should happen on each failure:

1. `ScreenshotUtils.capture(...)` writes a PNG under `target/screenshots/`
2. `AllureReporter.attachScreenshot(...)` adds **Failure screenshot** to Allure results

Then open the report:

```powershell
mvn allure:serve
```

In Allure: open a failed test and look for the **Failure screenshot** attachment.

### TestNG suite (optional)

Runs `LoginTestNG` via `testng.xml` only (JUnit tests are not run in this mode):

```bash
mvn test "-DsuiteXmlFile=testng.xml" "-Pheadless"
```

On PowerShell, quote the `-D` argument so `.xml` is not parsed as a lifecycle phase.

### Parallel execution

Surefire runs test **methods** in parallel (`threadCount=3`) with one `WebDriver` per test via `BaseTest` + `TestResultExtension`. Do not share a static driver across tests when parallel is enabled.

## Test reports

### Allure (recommended)

1. Run tests:

```bash
mvn clean test "-Pheadless"
```

2. Open the report (no separate Allure CLI install required; the Maven plugin handles it):

```bash
mvn allure:serve
```

`allure:serve` builds HTML and opens it in your browser (local temporary server).

Static HTML (no server):

```bash
mvn allure:report
```

Open `target/site/allure-maven-plugin/index.html`.

Full workflow after diagnostic failures:

```powershell
mvn clean test "-Pdiagnostic" "-Pheadless"
mvn allure:serve
```

### Report and artifact locations

| Artifact | Location |
|----------|----------|
| Disk screenshots (on failure) | `target/screenshots/` |
| Allure raw results | `target/allure-results/` |
| Allure HTML (served) | `mvn allure:serve` (browser) |
| Allure HTML (static) | `target/site/allure-maven-plugin/index.html` after `mvn allure:report` |
| Surefire JUnit XML | `target/surefire-reports/` |
| Surefire HTML | `target/site/surefire-report.html` after `mvn surefire-report:report` |

Failed tests attach a **Failure screenshot** in the Allure report when the browser session started successfully and the test failed during execution.

### Surefire (built-in)

```bash
mvn surefire-report:report
```

Open `target/site/surefire-report.html`.

## What this project covers

- Maven `src/main` vs `src/test` split (framework vs tests)
- Page Object Model with shared `BasePage` and explicit waits
- Feature-based test classes with JUnit `@Tag` suites (smoke / regression)
- Parameterized tests (`@ParameterizedTest` + `@CsvSource`)
- External JSON test data (`TestDataReader` + `testdata/checkout.json`)
- Parallel Surefire execution with isolated drivers per test
- TestNG sample class and `testng.xml` for legacy pack patterns
- Screenshots on failure (disk under `target/screenshots/` + Allure via `AllureReporter`)
- Allure HTML reporting
- Environment profiles (`dev` / `qa`) and headless mode

## License

Learning project -- use and modify freely.
