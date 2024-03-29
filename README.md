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

-----------------------------------------------------------------------------------------------------------------------------------------------------------

Sprint 2
=======
###### *Contributions:*
- Group 1 | Sviatoslav Ruthovskyi, Hariroop Singh | Board API, board PCS, and integrate TetrisGUI with model update methods.
- Group 2 | Zachary C Anderson, Zac Andersen | TetrisPanel draw game board, listen for keyboard events, window resizing, and 2D sprite drawing.

###### *Meetings:*
- Link to meeting minutes:
https://docs.google.com/document/d/1G6BOdyywWmPLVqbj0jY5JbFcdA2nuRqWD1WLyxQZb8o/edit
- Alternate communication:
We tend to communicate through discord text channel, also the discord voice channel if we are really stuck, and in person when we are all at campus. When we communicate its about issues we are having with the project, going over code and functions of said code, giving each other advice on coding and how to work together as a team and resolving issues with in the group in a respectful and positive manner.

###### *Comments:*
- None at the moment.

-----------------------------------------------------------------------------------------------------------------------------------------------------------

Sprint 3
=======
###### *Contributions:*
- Group 1 | Sviatoslav Ruthovskyi, Zachary C Anderson | Levels and game state changes, stat panel, sprite image. 
- Group 2 | Hariroop Singh, Zac Andersen | General GUI features and drawing, sound effects and music.

###### *Meetings:*
- Link to meeting minutes:
https://docs.google.com/document/d/1p2bRscX68EkTv-gLyV2z_zNubv3aU7kblcVNC6qj9Eg/edit
- Alternate communication:
We tend to communicate through discord text channel, also the discord voice channel if we are really stuck, and in person when we are all at campus. When we communicate its about issues we are having with the project, going over code and functions of said code, giving each other advice on coding and how to work together as a team and resolving issues with in the group in a respectful and positive manner.

###### *Special Features:*
- Transparent sprite image over tetris blocks
- Free resizing
- Music and sound effects
- High score tracking
- Light and dark mode selection
- Executable jar file

###### *Comments:*
- Per Issue #14 HiDPI scaling may cause errors in some circumstances.
- Jar file may not run from file explorer/finder in all environments, but consistently works when executed from a terminal.
