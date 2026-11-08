# DAA Assignment 4 - City Search & Spell-Check

This is my Java project for comparing edit distance and string search algorithms.

## Requirements
- Java 17 or newer
- Maven 3.8+
- Python 3 with matplotlib (only to recreate plots)

## Run
```bash
mvn test
mvn compile exec:java
python3 plot_results.py
```
The experiment saves all five individual runs to `results/results.csv`. The random generator uses seed 42.

## Project files
- `src/main/java/daa`: algorithms and experiment
- `src/test/java/daa`: JUnit 5 tests
- `results/results.csv`: measured runs
- `results/plots`: charts
- `REPORT.md`: experiment design and discussion

## Git
Create a GitHub repository, push the project to `main`, and create the `v1.0` tag:
```bash
git init
git add .
git commit -m "feat: add edit distance and string search"
git branch -M main
git remote add origin YOUR_REPOSITORY_URL
git push -u origin main
git tag v1.0
git push origin v1.0
```

## AI use acknowledgement
I used ChatGPT to help prepare the initial implementation, tests and report. I reviewed the algorithms and should be able to explain the code and results.
