# How to Run the Program:
`gradle clean build run`

# Design Patterns in the Project

This project implements several key design patterns, including the **Factory Method**, **Singleton**, **Command**, and **Observer** patterns. These patterns are utilized to ensure flexibility, maintainability, and scalability of the codebase. Below are the participants and structures for each pattern used in the project.

---

## 1. Observer Pattern

### Purpose:
The Observer Pattern is implemented to update the UI of the game window dynamically, including:
- The score
- The number of lives Pac-Man has left
- Displaying the "GAME OVER", "YOU WIN", and "READY" screens

### Participants:

- **Subject**: `GameStateSubject`
    - **Observer**: `GameStateObserver`
    - **Concrete Subject**: `GameEngine`
    - **Concrete Observer**: `GameWindow`

- **Subject**: `LivesSubject`
    - **Observer**: `LivesObserver`
    - **Concrete Subject**: `LevelImpl`
    - **Concrete Observer**: `GameWindow`

- **Subject**: `ScoreSubject`
    - **Observer**: `ScoreObserver`
    - **Concrete Subject**: `Pacman`
    - **Concrete Observer**: `GameWindow`

### Functionality:
- The `GameWindow` listens for changes in game state (e.g., win/loss/ready state) and updates the UI accordingly.
- It also observes changes in Pac-Man’s lives and score and updates the respective UI elements in real-time.

---

## 2. Factory Method Pattern

### Purpose:
The Factory Method Pattern is used to construct all game entities dynamically. This includes:
- Walls
- Pellets
- Pac-Man
- Ghosts

### Participants:
- **Product**: `Renderable`
    - Represents the base interface for all renderable objects in the game.

- **Concrete Products**:
    - `StaticEntityImpl`: Represents static entities in the game.
    - `Pellet`: Represents pellets in the maze.
    - `GhostImpl`: Represents ghosts in the maze.
    - `Pacman`: Represents the player-controlled Pacman.

- **Creator**: `EntityFactory`
    - Provides an interface to create objects of type `Renderable`.

- **Concrete Creators**:
    - `GhostsFactory`: Creates ghost entities.
    - `PlayerFactory`: Creates Pacman entities.
    - `EmptyCellFactory`: Creates empty cells in the maze.
    - `PelletsFactory`: Creates pellet entities.
    - `WallsFactory`: Creates wall entities.

### Functionality:
Each entity type has a dedicated factory class that produces instances based on game requirements. This ensures flexibility in creating different types of game objects and avoids tight coupling between the game logic and the specific entity implementations.

---

## 3. Singleton Pattern

### Purpose:
The Singleton Pattern is used to ensure that only one instance of certain classes exists throughout the application, as they provide global access to resources required by multiple parts of the system.

### Participants:

#### **Factory Collection Singleton**
- **Singleton**: `FactoryCollectionSingleton`
    - Centralizes access to various entity factories within the game, ensuring consistent factory management.

- **Client**: `MazeCreator`
    - Uses the `FactoryCollectionSingleton` to retrieve the correct factory for creating maze elements.

#### **Image Collection Singleton**
- **Singleton**: `ImageCollectionSingleton`
    - Manages all images used in the game, ensuring that images are loaded only once and shared across the application.

- **Clients**:
    - `FactoryCollectionSingleton`: Uses the images for rendering entities.
    - `PlayerFactory`: Uses specific player images for creating Pacman visuals.

### Functionality:
By using the Singleton Pattern, the application ensures efficient resource management. Factories and images are created only once and shared across various parts of the game logic, reducing memory overhead and ensuring consistency.

---

## 4. Command Pattern

### Purpose:
The Command Pattern handles keyboard inputs from the player to control Pac-Man’s movements, including moving up, down, left, and right.

### Participants:
- **Command**: `Command`
    - Defines the interface for executing an action based on input.

- **Concrete Commands**:
    - `ConcreteDownCommand`: Moves Pacman down.
    - `ConcreteLeftCommand`: Moves Pacman left.
    - `ConcreteNoCommand`: Represents no command (null object pattern).
    - `ConcreteRightCommand`: Moves Pacman right.
    - `ConcreteUpCommand`: Moves Pacman up.

- **Client**: `GameWindow`
    - Sends commands based on player input.

- **Invoker**: `RemoteControl`
    - Sends the appropriate command to be executed based on the input received.

- **Receiver**: `KeyboardInputHandler`
    - Executes the commands, moving Pac-Man based on the input received.

### Functionality:
The Command Pattern decouples the input handling logic from the movement execution logic, making the system more modular. Each direction (up, down, left, right) is treated as a separate command, simplifying the addition of new commands in the future.

---