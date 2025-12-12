## Playwright Test Automation Framework

## Running the tests

### Pre-requisite

Firstly, make sure that Docker is running locally either via Colima or Docker desktop

Run command =>

```zsh
./gradlew clean test -Drelease="testRun" -Denv="dev" --info
```
## running specific test using suite file
To run a specific test suite file, use the following command:

```zsh
./gradlew clean test -Drelease="testRun" -Denv="dev" -PsuiteFile="resources/testRunners/testng.xml"
```
## Playwright CodeGen Commands

Command to run Playwright Codegen:

```zsh
./gradlew runPlaywrightCodegen --info
```
## Playwright run encryptor
Command to run Playwright Encryptor:

```zsh
./gradlew runPlaywrightEncryptor --info
```



