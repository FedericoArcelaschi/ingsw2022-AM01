# ingsw2022-AM01

**Eriantys** — a digital adaptation of the board game by [Cranio Creations](https://craniocreations.it/en/product/eriantys/).

> Software Engineering project — Politecnico di Milano, A.Y. 2021/2022

Giovanni Arriciati, 10683631, giovanni.arriciati@mail.polimi.it.

Federico Arcelaschi, 10654781, federico.arcelaschi@mail.polimi.it

Lorenzo Aicardi, 10675881, lorenzo.aicardi@mail.polimi.it

---

## About the Game

*Eriantys* is a strategy board game for 2–4 players where each player controls a school of wizards and tries to conquer the islands around the board. Players move students, summon professors, place towers, and advance Mother Nature to gain influence over islands.

The Italian rulebook is included under `src/main/resources/rulebook_ITA.pdf`.

---

## Project specification

The project consists of a Java version of the board game *Eriantys*, made by Cranio Crations.

The final version includes:
* initial UML diagram;
* final UML diagram, generated from the code by automated tools;
* working game implementation, with full Expert rules;
* source code of the implementation;
* source code of unity tests.

---

## Features

The game supports two modes:

- **Simplified rules** — the base game without character cards.
- **Expert rules** — all 12 character cards with unique effects (Monk, Farmer, Guard, Mailman, Witch, Centaur, Jester, Knight, Cook, Storyteller, Queen, Taxman).

| Functionality        | Status |
|:---------------------|:------:|
| Simplified rules     | 🟢 |
| Expert rules         | 🟢 |
| 12 expert cards      | 🟢 |
| GUI                  | 🟢 |
| CLI                  | 🟢 |
| Multiple games       | 🟢 |
| 4 Players            | 🟢 |
| connection resiliency| 🔴 |
| game persistence     | 🔴 |

#### Legend
🔴 Not Implemented &nbsp; 🟢 Implemented

---

## Technology Stack

| Category      | Technology |
|:--------------|:-----------|
| Language      | Java 17 |
| Build System  | Apache Maven |
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
│  CLI / GUI   │ ◄─────────────► │     Server       │
│  (View)      │    (Gson)       │  ┌────────────┐  │
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

---

## How to Build & Run

### Prerequisites

- Java 17 JDK
- Apache Maven

### Build

```bash
mvn clean package
```

The build produces a fat JAR (`AM01.jar` with `jar-with-dependencies`) in the `target/` directory, with all dependencies included.

### Run

```bash
# Start the server (port 12345)
java -jar target/AM01.jar server

# Graphical client
java -jar target/AM01.jar g-client

# Textual client (CLI)
java -jar target/AM01.jar t-client
# or
java -jar target/AM01.jar tc
```

A pre-built JAR and platform-specific launch scripts are also available under `Deliveries/play/`:

| Script | Description |
|:-------|:------------|
| `serverEriantys.sh` | Launches the server |
| `GraphicalClient.sh` | Launches the GUI client |
| `TextualClient.sh` | Launches the CLI client |

---

## Testing & Coverage

```bash
mvn test
```

Tests are written with **JUnit 5** (Jupiter). Coverage is measured with **JaCoCo**.

All tests in model and controller cover 88% of the model and controller.

Coverage reports are generated at `target/site/jacoco/index.html`.

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
├── src/
│   ├── main/java/it/polimi/ingsw/
│   │   ├── startUp/               # Entry point (Main.java)
│   │   ├── server/
│   │   │   ├── communication/     # Server networking (TCP, lobby)
│   │   │   ├── controller/        # Game controller (Game.java)
│   │   │   └── model/             # Business logic (base + expert)
│   │   ├── communication/         # Shared protocol: messages, commands, DTOs
│   │   └── client/
│   │       ├── communication/     # Client networking
│   │       └── userInterface/     # CLI and GUI implementations
│   ├── main/resources/            # FXML, CSS, images, rulebook
│   └── test/java/it/polimi/ingsw/ # Unit tests
│
└── pom.xml                        # Maven build configuration
```

---

## UML Diagrams

Initial and final UML diagrams are located in `Deliveries/Model UML/`:

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


