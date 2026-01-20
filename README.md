# Intelligent Regression Release Risk Platform ( QE 3.0 )

### (Selenium + TestNG + POM + Allure + API + Risk Engine + CI/CD Governance)

This is a **Production-Grade Intelligent QA Automation Platform** designed for **UI + API Testing with Risk-Based Release Governance**.

Unlike traditional automation frameworks, this platform goes beyond pass/fail validation by introducing:

* Flaky test detection
* Risk scoring per test suite
* Automated **GO / HOLD / NO-GO** release decisions
* CI/CD quality gates enforced directly from test intelligence

All decisions are **visible inside Allure Reports and enforced in CI pipelines**.

---

## 🔥 Live Allure Report Dashboard

🔗 **[https://debasish-87.github.io/intelligent-release-risk-platform/](https://debasish-87.github.io/intelligent-release-risk-platform/)**

> 📊 Allure Report + 🚦 Release Decision Status is available after every pipeline run.

---

## 🎥 QE 3.0 – Intelligent Release Decision Demo

▶️ **Click below to watch the demo video**

https://github.com/user-attachments/assets/d035c94b-4465-4188-bdd0-6f0548aa50a1

---

## ✅ Key Features

| Feature                      | Description                                         |
| ---------------------------- | --------------------------------------------------- |
| Selenium Web UI Automation   | Automated coverage for end-to-end UI flows          |
| TestNG Test Execution        | Group-based execution (Smoke, Critical, Regression) |
| Page Object Model (POM)      | Clean, modular & scalable architecture              |
| Allure Reporting             | Rich HTML reports with steps, logs & screenshots    |
| API Testing (REST-Assured)   | CRUD operations using ReqRes public API             |
| Data-Driven Testing          | Excel + JSON based execution                        |
| Flaky Test Detection         | Identifies unstable tests using execution history   |
| Risk Score Engine            | Calculates risk per test suite                      |
| Intelligent Release Decision | Automated GO / HOLD / NO-GO decision                |
| CI/CD Governance             | Pipeline fails automatically on **NO_GO**           |
| GitHub Pages Dashboard       | Allure report published with trends & history       |

---

## 🧠 QE 3.0 – What Makes This Different?

Unlike traditional automation frameworks, this platform:

* Treats **tests as release signals**, not just checks
* Introduces **Release Governance into QA**
* Converts automation data into **business-ready release decisions**

> 🚦 **Quality Engineering, not just Test Automation**

---

## 🏗️ Architecture & Tech Stack

| Layer         | Tools                               |
| ------------- | ----------------------------------- |
| Language      | Java 17                             |
| Test Runner   | TestNG                              |
| UI Automation | Selenium WebDriver                  |
| API Testing   | REST-Assured                        |
| Reporting     | Allure                              |
| Logging       | Log4j2                              |
| Build Tool    | Maven                               |
| Data Input    | Excel (Apache POI) + JSON (Jackson) |
| CI/CD         | GitHub Actions                      |
| Dashboard     | GitHub Pages                        |

---

## 📂 Project Folder Structure

```
intelligent-release-risk-platform
│
├── pom.xml                                         # Maven dependencies & plugins
├── testng.xml                                      # TestNG master suite
├── README.md                                       # Project documentation
│
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── base                                # WebDriver & Test base layer
│   │   │   │   ├── BaseTest.java
│   │   │   │   └── DriverManager.java
│   │   │   │
│   │   │   ├── pages                               # Page Object Model (UI layer)
│   │   │   │   ├── LoginPage.java
│   │   │   │   ├── InventoryPage.java
│   │   │   │   ├── CartPage.java
│   │   │   │   ├── CheckoutInfoPage.java
│   │   │   │   ├── CheckoutOverviewPage.java
│   │   │   │   └── OrderSuccessPage.java
│   │   │   │
│   │   │   ├── api                                 # API service & client layer
│   │   │   │   ├── ApiClient.java
│   │   │   │   └── ReqResService.java
│   │   │   │
│   │   │   ├── intelligence                        # QE 3.0 Release Intelligence Engine
│   │   │   │   ├── FlakyTestDetector.java
│   │   │   │   ├── RiskScoreCalculator.java
│   │   │   │   ├── ReleaseDecisionEngine.java
│   │   │   │   ├── ReleaseSummaryReporter.java
│   │   │   │   ├── TestHistoryManager.java
│   │   │   │   └── TestMetadataReader.java
│   │   │   │
│   │   │   └── utils                               # Common reusable utilities
│   │   │       ├── ExcelUtils.java
│   │   │       ├── JsonUtils.java
│   │   │       ├── LoggerUtil.java
│   │   │       ├── ScreenshotUtils.java
│   │   │       └── WaitUtils.java
│   │   │
│   │   └── resources                               # Framework configuration
│   │       ├── config.properties
│   │       ├── environment.properties
│   │       └── log4j2.xml
│   │
│   └── test
│       ├── java
│       │   ├── tests
│       │   │   ├── ui                              # UI automation tests
│       │   │   │   ├── LoginTest.java
│       │   │   │   └── CheckoutFlowTests.java
│       │   │   │
│       │   │   └── api                             # API automation tests
│       │   │       ├── ReqResApiTests.java
│       │   │       └── ReqResTests.java
│       │   │
│       │   └── listeners                           # TestNG & Allure listeners
│       │       ├── TestListener.java
│       │       └── ReleaseDecisionListener.java
│       │
│       └── resources
│           └── testdata                            # Test data files
│               ├── login_data_clean.xlsx
│               └── createUser.json
│
├── allure-results                                  # Allure raw execution results
├── history                                         # Test execution history (risk analysis)
├── logs                                            # Framework execution logs
└── .github
    └── workflows                                   # CI/CD pipelines
        ├── ci.yml
        └── allure-deploy.yml

```
---

## 🚀 Test Execution

### Run All Tests

```bash
mvn clean test
```

### Run Only Critical Tests

```bash
mvn clean test -Dgroups=Critical
```

### Headless Mode (CI/CD)

```bash
mvn clean test -Dheadless=true
```

### Generate Allure Report Locally

```bash
mvn allure:serve
```

---

## 🚦 Intelligent Release Decision (Core Feature)

At the end of every execution, the framework:

1. Reads historical test data
2. Detects flaky tests
3. Calculates risk scores per test suite
4. Makes a final release decision

### 📌 Decision Rules

* Any **Critical test risk ≥ 7** → **NO_GO**
* **API risk ≥ 6** → **NO_GO**
* **Average risk ≥ 5** → **HOLD**
* Otherwise → **GO**

The decision appears as a **synthetic Allure test**:

```
🚦 Release Decision
FINAL DECISION → NO_GO
```

> ❌ CI pipeline **fails automatically** on **NO_GO**

---

## 🧪 Test Coverage

### UI Tests (SauceDemo)

| Scenario                         | Status |
| -------------------------------- | ------ |
| Login (Data-Driven – Excel)      | ✅      |
| Checkout – Valid & Invalid Flows | ✅      |
| Edge Cases (Empty, Invalid Data) | ✅      |

### API Tests (ReqRes)

| Endpoint             | Method     | Purpose      | Status |
| -------------------- | ---------- | ------------ | ------ |
| /api/users           | POST       | Create User  | ✅      |
| /api/users/{id}      | GET        | Fetch User   | ✅      |
| PATCH / PUT / DELETE | Validation | API Coverage | ✅      |

---

## 🔁 CI/CD – GitHub Actions

This project automatically:

* Runs tests on every push / PR
* Applies **release governance**
* Generates Allure report
* Publishes report to GitHub Pages
* Preserves execution **history & trends**

### Workflow Files

```
.github/workflows/ci.yml
.github/workflows/allure-deploy.yml
```

---

## 💬 How to Explain This in an Interview

> “This is not just an automation framework.
> It is an **Intelligent Release Governance Platform** that converts test execution data into actionable release decisions.
> It demonstrates real-world SDET practices like flaky test detection, risk-based quality gates, CI/CD enforcement, and production-ready reporting.”

---

## 🎯 Who Is This For?

This project demonstrates **enterprise-grade QA engineering**:

* SDET / Senior QA Engineers
* QA Architects
* DevOps-integrated QA teams
* Organizations practicing **Release Quality Governance**

---

## 👤 Author

**Debasish**

SDET / Quality Engineer | QA Automation

📧 Email: **[debasishm8765@gmail.com](mailto:debasishm8765@gmail.com)**
🔗 GitHub: **[https://github.com/Debasish-87](https://github.com/Debasish-87)**

---

✨ *If this project helped you or inspired you — give it a ⭐ on GitHub.*

---
