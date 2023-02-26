![image](https://user-images.githubusercontent.com/125701586/221374970-4c6776c4-06c5-435b-9325-730bac6cf7b7.png)

G4Tetris
======
#### group4-tetris | TCSS 305B | UW Tacoma

## About
The G4Tetris project is being created as practicum assignment for TCSS305 with professor Charles Bryan. The goal of the project is to have a fully functional and playable game of Tetris.

This project is being written in `Java SE 19` using `Swing` and implementing the observer design pattern.

### Team:
Contributor            | Role
 --------------------- | ----------------------
Zachary C Anderson     | Admin Ops & planning
Zac Andersen           | Scrum leader & DevOps
Sviatoslav Ruthovskyi  | GUI Developer
Hariroop Singh         | GUI Developer

Sprint 1
======
###### *Contributions:*
- Group 1 | Sviatoslav Ruthovskyi, Hariroop Singh | Main GUI region layout.
- Group 2 | Zachary C Anderson, Zac Andersen | Menu structure & project scaffolding.

###### *Comments*
- We've chosen to use the `gridBagLayout` and implement a resizable window. This has presented some unique and interesting challenges. The gridBag is more complicated than the other layouts but seems more powerful and flexible in the long term. We will likely need to implement a component listener for `TetrominoPanel` to keep it square under free form resizing, in time we may choose to have resolution options for the game to ease the complexity of scaling the GUI components.
- Java's internal logging has not been working well for us, and we've had problems getting it to respect a defined logging.properties file, we will likely switch to Log4J at the top of sprint 2.
