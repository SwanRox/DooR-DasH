# 🚪 DooR DasH: Scare vs Laugh

A fully playable strategy board game built in Java with JavaFX, inspired by the Monsters Inc. universe. Two monsters race across a 100-cell board collecting energy from children's doors. 

Built across 3 progressive milestones covering OOP data structures, a custom game engine, and a fully responsive JavaFX GUI following the MVC architectural pattern.

---

## 🎯 Objective & New Balance Changes

Navigate the floor to land EXACTLY on Boo's Door (Cell 99) with at least **750 energy** to win. 
* **Wrap-Around Bonus:** Overshooting the final door wraps you back to the beginning of the board and grants a **250 energy bonus**.
* **Cheaper Powerups:** Character powerups now only cost **200 energy** to activate, encouraging more aggressive strategic play.

---

## 🏗️ Architecture & Engine

The project is organized with strict separation of concerns, ensuring the underlying model runs independently of the UI.

### Monster System (Polymorphism)
All 4 monster types extend the abstract `Monster` class, overriding movement and powerup execution to create asymmetric gameplay.

| Type | Passive Ability | Powerup Effect (Costs 200 Energy) |
| :--- | :--- | :--- |
| **Dasher** | Moves at 2× dice roll | **Momentum Rush:** 3× speed for 3 turns |
| **Dynamo** | Doubles all energy gains & losses | **Screech Freeze:** Skips opponent's next turn |
| **MultiTasker** | Moves at ½ dice roll; +200 on energy changes | **Focus Mode:** Normal speed for 2 turns |
| **Schemer** | +10 on every energy change | **Chain Attack:** Steals from opponent |

### Cell & Board System
A zigzag 10×10 grid handles alternating row traversal, collision detection, and dynamic effects.

| Cell Type | Effect |
| :--- | :--- |
| **Doors** | Energy gain/loss based on role match. Exhausts after one use. |
| **Conveyor Belts** | Transports monster forward safely. |
| **Contamination Socks** | Transports backward + drains 100 energy. |
| **Card Cells** | Draws a random mysterious card. |
| **Monster Cells** | Free powerup (role match) or energy swap (role mismatch). |

### Card Engine
Cards are drawn at random from a shuffled deck and execute immediately:
* **Swapper:** Swap positions with the opponent if you are behind.
* **Energy Steal:** Drain energy directly from the opponent.
* **Start Over:** Send either you or the opponent back to Cell 0.
* **Shield:** Block the next negative energy loss effect for your team.
* **Confusion:** Swaps the SCARER/LAUGHER roles of both monsters temporarily.

---

## 💻 Tech Stack
* **Language:** Java 17
* **UI Framework:** JavaFX (Fully responsive layout scaling)
* **Data:** CSV-driven entity loading pipeline (Decoupled configuration)
* **Version Control:** Git & GitHub

---

## 👥 Team
* **Zyad Amr Alsayed** ([@swanrox](https://github.com/swanrox))
* **Mohammed Hassan ElSayed** ([@Mohassan84](https://github.com/Mohassan84))
* **Anas Ossama Mohamed** ([@AnasMuharram](https://github.com/AnasMuharram))
* **Mohsen Mohamed Khafagy** ([@CorporalChicken](https://github.com/CorporalChicken))

*German International University (GIU) — Programming II Project*

---

## 🎮 How to Download & Play

You do not need an IDE to play this game. Just follow these steps:

1. **Install Java:** Download and install [Liberica JDK 17 Full](https://bell-sw.com/pages/downloads/#/java-17-lts). 
   * *⚠️ Crucial: You MUST download the "Full" version so it includes the JavaFX UI libraries.*
   * *Leave the installation path as the default (`C:\Program Files\BellSoft\...`).*
2. **Download the Game:** Go to the [Releases](../../releases) tab on the right side of this GitHub page and download `DoorDash_Game.zip`.
3. **Play:** Extract the ZIP folder, open it, and double-click `Play.bat`. The game will launch automatically!
