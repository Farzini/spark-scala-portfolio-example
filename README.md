# spark-scala-portfolio-example

A clean, production-ready Spark project in Scala that you can use in your portfolio.
It showcases two simple jobs:

1. **WordCount** — reads a text file and outputs the top N words.
2. **Sessionize** — demonstrates basic DataFrame operations (grouping, filtering, aggregations).

## Requirements
- Java 8 or 11 (LTS)
- [sbt](https://www.scala-sbt.org/)
- Apache Spark 3.5.x locally (optional; sbt will handle dependencies for tests)
- Scala 2.12.x

## Quick start
```bash
# 1) Compile & test
sbt clean test

# 2) Run WordCount locally (uses data/sample.txt)
sbt "runMain com.example.WordCount --input data/sample.txt --top 10"

# 3) Run Sessionize locally (uses data/events.json)
sbt "runMain com.example.Sessionize --input data/events.json"

# 4) Create a fat JAR (for spark-submit)
sbt assembly
# Example submit (local mode):
spark-submit --class com.example.WordCount target/scala-2.12/spark-scala-portfolio-example-assembly-0.1.0.jar --input data/sample.txt --top 10
```

## Project layout
```
spark-scala-portfolio-example/
├─ build.sbt
├─ project/
│  └─ plugins.sbt
├─ src/
│  ├─ main/
│  │  ├─ resources/
│  │  └─ scala/com/example/
│  │     ├─ WordCount.scala
│  │     └─ Sessionize.scala
│  └─ test/
│     └─ scala/com/example/
│        └─ WordCountSpec.scala
├─ data/
│  ├─ sample.txt
│  └─ events.json
├─ LICENSE
└─ .gitignore
```

## Notes
- The Spark dependencies are marked **provided** so your fat JAR stays lean for cluster use.
- For quick local testing via `sbt run`, the provided scope is fine.

---

### PowerShell: Create a GitHub repo and push
Below is a minimal, copy-pasteable sequence. Replace `YOUR_GITHUB_USERNAME` with your username and `DESCRIPTION HERE` with a good description.

```powershell
# 0) From PowerShell, cd to the project folder
cd .\spark-scala-portfolio-example

# 1) Initialize git and create initial commit
git init
git add .
git commit -m "Initial commit: Spark Scala portfolio example"

# 2) Create a new GitHub repo via CLI (optional if you prefer web)
# Requires GitHub CLI: https://cli.github.com/
gh repo create YOUR_GITHUB_USERNAME/spark-scala-portfolio-example `
  --public `
  --description "A production-ready Spark project in Scala with word count and DataFrame examples." `
  --source . `
  --remote origin `
  --push

# If you don't use GitHub CLI, create the repo on GitHub first, then:
# git remote add origin https://github.com/YOUR_GITHUB_USERNAME/spark-scala-portfolio-example.git
# git branch -M main
# git push -u origin main
```

### Suggested repository description
> A production-ready Spark project in Scala (2.12) targeting Spark 3.5. It includes a WordCount job, a small DataFrame pipeline, tests with ScalaTest, sbt-assembly config, and runnable examples.

---
