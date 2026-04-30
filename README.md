# Dungeon Crawler

A 2D dungeon crawler game built with JavaFX where players fight through waves of enemies, face powerful bosses, and unlock upgrades as they progress through levels.

---

## 🎮 Gameplay Overview

Choose your class, survive waves of enemies, and defeat boss encounters every 3 levels. The game progressively increases in difficulty — enemies shoot faster, waves grow larger, and bosses arrive with full guard formations.

---

## 🕹️ Controls

| Key | Action |
|-----|--------|
| `W` | Move Up |
| `A` | Move Left |
| `S` | Move Down |
| `D` | Move Right |
| `SPACE` | Attack |
| `ESC` | Open/Close Menu |

---

## 🧙 Classes

### Archer
- Armed with **100 arrows**
- Fires a single arrow per shot
- **Upgrade at Level 3:** Shoots 3 arrows simultaneously (spread shot)

### Ninja
- Armed with **100 shurikens**
- Fires spinning shurikens that travel across the screen
- **Upgrade at Level 3:** Shoots 3 shurikens simultaneously (spread shot)

---

## 👾 Enemies

### Regular Enemy
- Dark red sphere with an angry face
- Marches left and fires bullets at the player
- Worth **25 points** on kill

### Super Enemy (Boss)
- Appears every **3 levels**
- Twice the size of regular enemies
- Surrounded by a **5×6 guard formation**
- Fires bullets and moves left like regular enemies but is significantly tougher
- Worth **50 points** on kill

---

## 📈 Progression System

- **Waves** increase in size every 3 waves by adding an extra column of enemies
- **Levels** advance after all waves are cleared; enemy count and shooting speed scale with level
- **Boss levels** (every 3rd level) replace normal waves with a boss + guard formation
- Player **health is restored** to 100 at the start of each new level
- **Class upgrades** unlock at Level 3, enabling spread shots

---

## 🗂️ Project Structure
DungeonCrawler/
├── Main.java          # Application entry point, game loop, UI, collision logic
├── Player.java        # Abstract base class for all player characters
├── Archer.java        # Archer player class
├── Ninja.java         # Ninja player class
├── Enemy.java         # Base enemy class
└── SuperEnemy.java    # Boss enemy class extending Enemy
---

## ⚙️ Technical Details

- **Language:** Java
- **Framework:** JavaFX
- **Rendering:** Canvas-based via `GraphicsContext`
- **Game Loop:** `AnimationTimer` running at ~60fps
- **Architecture:** Abstract class hierarchy (`Player` → `Archer`/`Ninja`, `Enemy` → `SuperEnemy`)
- **Design Patterns:** Polymorphism for player and enemy behaviors, iterator-safe collision detection

---

## 🚀 Getting Started

### Prerequisites
- Java 11 or higher
- JavaFX SDK

### Running the Game
1. Clone the repository
```bash
   git clone https://github.com/yourusername/dungeon-crawler.git
```
2. Add JavaFX to your classpath
```bash
   javac --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.graphics *.java
   java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.graphics Main
```

---

## 🔮 Planned Features

- [ ] Save and load game state
- [ ] Additional player classes
- [ ] More boss types per level
- [ ] Sound effects and background music
- [ ] Leaderboard system
- [ ] Additional levels and enemy patterns

---

## 📝 Notes

- Place `mainmenu.png` in the root project directory for the main menu background to render correctly
- Username entered on the class selection screen is displayed above the player character during gameplay
