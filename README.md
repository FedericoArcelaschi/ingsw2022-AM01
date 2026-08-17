# ingsw2022-AM01

![Java](https://img.shields.io/badge/Java-17-blue)
![Gradle](https://img.shields.io/badge/Gradle-9-blue)
![Coverage](https://img.shields.io/badge/Coverage-88%25-brightgreen)
![Build](https://github.com/FedericoArcelaschi-Polimi/ingsw2022-AM01/actions/workflows/report.yml/badge.svg)

**Eriantys** — a digital adaptation of the board game by [Cranio Creations](https://craniocreations.it/en/product/eriantys/).

> Software Engineering project — Politecnico di Milano, A.Y. 2021/2022

Giovanni Arriciati, 10683631, giovanni.arriciati@mail.polimi.it.

Federico Arcelaschi, 10654781, federico.arcelaschi@mail.polimi.it

Lorenzo Aicardi, 10675881, lorenzo.aicardi@mail.polimi.it

---

## About the Game

*Eriantys* is a strategy board game for 2–4 players where each player controls a school of wizards and tries to conquer the islands around the board. Players move students, summon professors, place towers, and advance Mother Nature to gain influence over islands.

The Italian rulebook is included under `desktop/src/main/resources/rulebook_ITA.pdf`.

---

## Project specification

The project consists of a Java version of the board game *Eriantys*, made by Cranio Creations.

The final version includes:
* initial UML diagram;
* final UML diagram, generated from the code by automated tools;
* working game implementation, with full Expert rules;
* source code of the implementation;
* source code of unit tests.

---

## Features

The game supports two modes:

- **Simplified rules** — the base game without character cards.
- **Expert rules** — all 12 character cards with unique effects (Monk, Farmer, Guard, Mailman, Witch, Centaur, Jester, Knight, Cook, Storyteller, Queen, Taxman).

| Functionality         | Status |
|:----------------------|:------:|
| Simplified rules      | 🟢     |
| Expert rules          | 🟢     |
| 12 expert cards       | 🟢     |
| GUI                   | 🟢     |
| CLI                   | 🟢     |
| Multiple games        | 🟢     |
| 4 Players             | 🟢     |
| connection resiliency | 🔴     |
| game persistence      | 🔴     |

#### Legend
🔴 Not Implemented &nbsp; 🟢 Implemented

---

## Game Rules Summary

### Simplified Rules (Base Game)
- 2–4 players, each controls a wizard school
- **Phases per turn**: Planning → Action → Mother Nature → Cloud
- Move students from entrance → dining room → islands
- Summon professors by having most students of a color in dining room
- Place towers on islands where you have influence
- Advance Mother Nature to score islands
- Win by placing all towers or having most when game ends

### Expert Rules (Full Game)
- All base rules +
- **12 Character Cards** with unique effects:
  - **Monk**: Move student from entrance to dining room
  - **Farmer**: Move student from entrance to island
  - **Guard**: Block an island
  - **Mailman**: Swap students between entrance and dining room
  - **Witch**: Move student from island to entrance
  - **Centaur**: Move student from dining room to island
  - **Jester**: Copy another character's effect
  - **Knight**: Move Mother Nature extra steps
  - **Cook**: Convert student color in dining room
  - **Storyteller**: Look at cloud cards
  - **Queen**: Gain coins
  - **Taxman**: Steal coins from others
- Character cards cost coins, drawn from tavern

See full Italian rulebook: [rulebook_ITA.pdf](./desktop/src/main/resources/rulebook_ITA.pdf)

---

## Technology Stack

| Category      | Technology |
|:--------------|:-----------|
| Language      | Java 17 |
| Build System  | Gradle (multi-module: `desktop`) |
| GUI Framework | JavaFX 18.0.1 (with FXML) |
| Serialization | Gson 2.9.0 |
| Logging       | Log4j 2.17.2 |
| Testing       | JUnit Jupiter 5.8.2 |
| Coverage      | JaCoCo 0.8.7 |
| CI            | GitHub Actions (scheduled SonarQube analysis) |

---

## Architecture Overview

Client-server architecture with an MVC-like separation:

```
┌─────────────┐     TCP/JSON     ┌──────────────────┐
│  CLI / GUI  │ ◄─────────────►  │     Server       │
│  (View)     │     (Gson)       │  ┌────────────┐  │
└─────────────┘                  │  │ Controller │  │
                                 │  ├────────────┤  │
                                 │  │   Model    │  │
                                 │  └────────────┘  │
                                 └──────────────────┘
```

- **Model** — pure game logic (Board, Island, Turn, etc.) in `server.model`.
- **Controller** — `Game` class validates commands and updates the model.
- **Communication** — TCP socket, JSON-serialized messages via Gson with a polymorphic `Message` hierarchy.
- **View** — two implementations (`CLI` and `GUI`) sharing the `UserInterface` contract.
- **Protocol** — half-duplex; clients send `Command` objects on their turn, server broadcasts `Update` to all players.

Key design patterns: **Command**, **Strategy** (influence computation), **Factory** (board creation), **Adapter** (`GameInterface` bridges communication and controller), **DTO** (lightweight data objects for network transfer).

The server manages multiple concurrent games through `GameManager`. Lobbies are handled by `LobbyManager` before a game starts.

### Turn Flow Sequence

```mermaid
sequenceDiagram
    participant C as Client
    participant S as Server
    participant LM as LobbyManager
    participant GM as GameManager
    participant G as Game
    participant M as Model (Board)

    C->>S: CONNECT
    S->>C: LOBBYINFO
    C->>S: PREFERENCES
    LM->>GM: createGame() when enough players
    GM->>G: new Game(type, usernames)
    G->>M: BoardFactory.getBoard()
    GM->>All Clients: UPDATE (initial board)
    
    loop Each Turn
        S->>Current Player: Prompt for COMMAND
        Current Player->>S: COMMAND
        S->>G: executeCommand(cmd)
        G->>G: validate turn, phase, rules
        alt Valid
            G->>M: mutate state
            G->>M: changePhase()
            GM->>All Clients: UPDATE
        else Invalid
            G->>Current Player: ERROR
        end
    end
    
    G->>M: isWinningState() / isEndGame()
    GM->>All Clients: END (winner)
```

### Key Components Detail

- **`GameManager`** — Registry of active `GameInterface` instances; supports multiple concurrent games
- **`LobbyManager`** — Queues clients by `GameType` (2/3/4 players × base/expert); creates games when lobby fills
- **`GameInterface`** — Adapter bridging network `Client` handlers and controller `Game`; handles per-client messaging
- **`Game`** — Command validator & executor; enforces turn order, phase transitions, move limits (3 students for 2/4p, 4 for 3p)
- **`BoardFactory`** — Factory pattern: creates `Board` (base) or `ExpertBoard` based on `GameType.expertMode`
- **Strategy Pattern** — `InfluenceComputing` / `ProfessorComputing` interfaces allow base vs expert influence algorithms
- **DTOs** — `BoardData`, `IslandData`, `CastleData`, `CharacterData` etc. for network serialization (Gson)

---

## How to Build & Run

### Prerequisites

- Java 17 JDK

No local Gradle install is required — use the checked-in wrapper (`./gradlew`).

### Build

```bash
./gradlew :desktop:shadowJar
```

The build produces a fat JAR (`desktop-3.0-all.jar`, all dependencies included) in `desktop/build/libs/`.

### Run

```bash
# Start the server (port 12345)
java -jar desktop/build/libs/desktop-3.0-all.jar server

# Graphical client
java -jar desktop/build/libs/desktop-3.0-all.jar g-client

# Textual client (CLI)
java -jar desktop/build/libs/desktop-3.0-all.jar t-client
# or
java -jar desktop/build/libs/desktop-3.0-all.jar tc
```

A pre-built JAR and platform-specific launch scripts are also available under `Deliveries/play/`:

| Script | Description |
|:-------|:------------|
| `serverEriantys.sh` | Launches the server |
| `GraphicalClient.sh` | Launches the GUI client |
| `TextualClient.sh` | Launches the CLI client |

---

## Development Setup

### IDE Configuration
- **IntelliJ IDEA**: Open as Gradle project (root `settings.gradle.kts`)
- **Eclipse**: Install Buildship (Gradle) + e(fx)clipse

### Running Tests
```bash
# All tests
./gradlew :desktop:test

# Single test class
./gradlew :desktop:test --tests "it.polimi.ingsw.server.model.baseLogic.BoardTest"

# With coverage report
./gradlew :desktop:test :desktop:jacocoTestReport
# Open desktop/build/reports/jacoco/test/html/index.html
```

### Code Style
- Google Java Format / IntelliJ default formatter
- Run `./gradlew :desktop:compileJava` to verify

---

## Testing & Coverage

```bash
./gradlew :desktop:test
```

Tests are written with **JUnit 5** (Jupiter). Coverage is measured with **JaCoCo**.

All tests in model and controller cover 88% of the model and controller.

Coverage reports are generated at `desktop/build/reports/jacoco/test/html/index.html`.

---

## Project Scope Notes

This project was required to implement **one** of three advanced features:
- ✅ **4 Players** (implemented — supports 2, 3, and 4 players)
- ❌ Connection Resiliency (reconnection, state recovery)
- ❌ Game Persistence (save/load, database)

The two unimplemented features are marked 🔴 in the Features table as they were out of scope for this delivery.

---

## Project Structure

```
ingsw2022-AM01/
├── Deliveries/
│   ├── Communication Protocol/    # Protocol specification & examples
│   ├── Model UML/                 # Initial & final UML diagrams
│   ├── play/                      # Pre-built JAR & launch scripts
│   ├── Review del gruppo 31/      # Peer review of another group
│   └── Review del nostro lavoro/  # Reviews of this project
│
├── desktop/                       # JavaFX/CLI client + server (Gradle module)
│   ├── src/
│   │   ├── main/java/it/polimi/ingsw/
│   │   │   ├── startUp/           # Entry point (Main.java)
│   │   │   ├── server/
│   │   │   │   ├── communication/ # Server networking: TCP, lobby
│   │   │   │   ├── controller/    # Game controller: Game.java & helper classes
│   │   │   │   └── model/         # Business logic: base + expert
│   │   │   ├── communication/     # Shared protocol: messages, commands, DTOs
│   │   │   └── client/
│   │   │       ├── communication/ # Client networking
│   │   │       └── userInterface/ # CLI and GUI implementations
│   │   ├── main/resources/        # FXML, CSS, images, rulebook
│   │   └── test/java/it/polimi/ingsw/ # Unit tests
│   └── build.gradle.kts
│
├── gradle/libs.versions.toml      # Gradle version catalog
├── settings.gradle.kts
└── build.gradle.kts               # Root Gradle build configuration
```

> An Android client (Kotlin + Jetpack Compose) sharing the protocol/DTO code with `desktop` is in progress — see `:shared`/`:android` modules once introduced.

---

## UML Diagrams

Initial and final UML diagrams are located in [Deliveries/Model UML/](./Deliveries/Model%20UML):

- **Initial model/** — design-time UML (`class_diagram.mdj`, rendered as `.jpg`).
- **Final model/** — 7 diagrams auto-generated from the final code, covering:
  - Logic model
  - Communication protocol
  - Controller
  - User interfaces (CLI & GUI)
  - Model types / light model
  - Expert logic extensions

---

## Communication Protocol

The full protocol specification is documented in `Deliveries/Communication Protocol/`:

- [`communication protocol design.md`](./Deliveries/Communication%20Protocol/communication%20protocol%20design.md)
- [`communication protocol use case with examples.txt`](./Deliveries/Communication%20Protocol/communication%20protocol%20use%20case%20with%20examples.txt)

Key characteristics:
- **Transport**: TCP
- **Serialization**: JSON (Gson with custom polymorphic adapter)
- **Flow**: half-duplex — server responds to client requests
- **Heartbeat**: Ping/Pong every 5–10 seconds for connection monitoring
- **Message types**: `PREFERENCES`, `PING`, `LOBBYINFO`, `COMMAND`, `UPDATE`, `ERROR`, `END`

### Protocol Quick Reference

#### Message Types
| Type | Direction | Description |
|------|-----------|-------------|
| `PREFERENCES` | C→S | Username, game mode (expert/base), player count |
| `LOBBYINFO` | S→C | Current lobby states per game type |
| `COMMAND` | C→S | Player action (see commands below) |
| `UPDATE` | S→C | Full board state broadcast |
| `ERROR` | S→C | Invalid command rejection |
| `PING`/`PONG` | S↔C | Heartbeat (5s interval, 5s timeout) |
| `END` | S→C | Game over, winner declared |

#### Command Types
| Command | Phase | Parameters |
|---------|-------|------------|
| `PLAY_CARD` | Planning | `cardId` (1-10) |
| `CHOOSE_CLOUD` | Cloud | `cloudId` (1-n) |
| `MOVE_STUDENT_TO_CASTLE` | Action | `students[]` (colors) |
| `MOVE_STUDENT_TO_ISLAND` | Action | `islandId`, `students[]` |
| `MOVE_MOTHER_NATURE` | Mother Nature | `shift` (1-3) |
| `PAY_CHARACTER` | Expert | `charId`, `islandId`, `students[]` |

Full spec: `Deliveries/Communication Protocol/communication protocol design.md`
