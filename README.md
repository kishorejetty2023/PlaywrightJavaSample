## Playwright Test Automation Framework

## Running the tests

### Pre-requisite

Firstly, make sure that Docker is running locally either via Colima or Docker desktop

Run command =>

```zsh
./gradlew clean test -Drelease="testRun" -Denv="dev" --info
```

### Node.js (required for Playwright)

Playwright requires Node.js to be available on the machine (or a path to a Node executable provided via the `PLAYWRIGHT_NODE_PATH` environment variable). If Node.js is not installed the build will fail early.

Official download and docs:
- Node.js: https://nodejs.org/

Quick install options (select one based on your OS):

- Windows
  - Official installer: download and run the Windows installer from https://nodejs.org/
  - Chocolatey: `choco install nodejs -y` (if you use Chocolatey)
  - winget: `winget install OpenJS.NodeJS.LTS`
  - nvm for Windows: https://github.com/coreybutler/nvm-windows (if you need multiple Node versions)

- macOS
  - Homebrew: `brew install node`
  - nvm: https://github.com/nvm-sh/nvm (install via the nvm script and then `nvm install --lts`)

- Linux
  - Debian/Ubuntu (NodeSource):
    ```bash
    curl -fsSL https://deb.nodesource.com/setup_lts.x | sudo -E bash -
    sudo apt-get install -y nodejs
    ```
  - Or use your distro's package manager or nvm for per-user installs.

Verify Node installation
- Check version:
  - PowerShell / Windows: `node --version`
  - macOS / Linux: `node --version`
- Locate the executable:
  - PowerShell: `Get-Command node` or `where.exe node`
  - macOS/Linux: `which node`

Setting the `PLAYWRIGHT_NODE_PATH` environment variable (optional)
- PowerShell (session only):
  ```powershell
  $env:PLAYWRIGHT_NODE_PATH = 'C:\\Program Files\\nodejs\\node.exe'
  .\gradlew build
  ```
- PowerShell (permanent for current user):
  ```powershell
  setx PLAYWRIGHT_NODE_PATH "C:\\Program Files\\nodejs\\node.exe"
  # Restart your terminal session for setx changes to take effect
  ```

CI notes
- On GitHub Actions use `actions/setup-node@v3` to ensure Node is available on the runner.
- On Azure Pipelines use the `NodeTool@0` task.

Gradle verification task
- This project provides a Gradle task `verifyNode` that fails the build if Node isn't found. Run:

```powershell
.\gradlew verifyNode
```

If `verifyNode` fails, follow the install steps above or set `PLAYWRIGHT_NODE_PATH` to the path of the Node executable.

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
