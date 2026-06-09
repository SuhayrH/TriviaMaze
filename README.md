# GitHub Repository
https://github.com/SuhayrH/TriviaMaze

## Team Members
- Suhayr Hassan
- Jinal Thummar
- Roman Pavlyshyn

## Project Description
A Java-based trivia maze game where players navigate through a maze by answering trivia questions.

## Technologies
- Java
- SQLite
- Java Swing (JFrame)
- SQLite JDBC Driver
- GitHub
- YouTrack
- VS Code

---

## Iteration 1 Summary
During Iteration 1, the team focused on setting up the project, creating user stories and tasks in YouTrack, setting up the GitHub repository, and beginning implementation of the main classes for the Trivia Maze game.

### Issues / Problems During Iteration 1
- Some features are still incomplete because this was the first iteration.
- SQLite database setup has started, but it still needs to be fully connected to the trivia question system.
- GameMemento save/load functionality has started, but it still needs to be connected to the complete game state.
- File menu Save, Load, and Exit still need full GUI integration.
- More testing is needed.
- The project still needs more MVC organization in future iterations.

---

## Iteration 2 Summary
During Iteration 2, the team continued working on the Trivia Maze game by improving the maze logic, connecting SQLite questions to the gameplay system, adding more trivia question data, and updating the project documentation.

### Issues / Problems During Iteration 2
- The SQLite JDBC driver issue took time to resolve. The code compiled, but the database connection did not work at first because Java could not find the SQLite driver.
- The SQLite JDBC `.jar` file had to be added to the project and included in the compile/run classpath.
- The database schema and `QuestionFactory` did not fully match at first, so `QuestionFactory` had to be updated to use the correct database column names.
- `Maze.java` and `Room.java` needed to be moved into the `src` folder so they were organized with the rest of the Java source files.
- TM-39 and TM-40 are working through backend and terminal tests, but the GUI does not yet fully show the database question gameplay.
- Full save/load testing and File menu improvements still need more work.
- The project still needs more MVC organization in future iterations.

---

## Iteration 3 Summary
During Iteration 3, the team focused on improving the Trivia Maze game by creating and updating the GUI, adding character selection, sprites, and better project package organization. The team also improved File menu Save Game and Load Game integration, tested GameMemento save/load behavior, resolved SQLite driver and questions.db setup issues, and continued cleaning up database and persistence-related code.

### Issues / Problems During Iteration 3
- No major issues were reported this iteration.

---

## Iteration 4 Summary
During Iteration 4, the team focused on polishing gameplay features and improving the overall user experience of the Trivia Maze game. A `QuestionTimer.java` class was implemented in the view package that displays a 20-second countdown when a trivia question appears, automatically locking the door if the player does not answer in time. A `RoomFlasher.java` class was also added to flash the current room cell green for a correct answer and red for a wrong answer. A hint button was added to MazeGUI, sound effects were generated for game events, character selection was fixed, keyboard shortcuts for maze navigation were added, and the end game flow including new game and exit options was implemented.

### Issues / Problems During Iteration 4
- MazeGUI became very cluttered with too many features handled in a single class. The team began working on separating features into their own dedicated classes.
- Keyboard shortcuts required handling edge cases where key bindings conflicted with existing input listeners.
- The QuestionTimer required careful synchronization to ensure it cancelled correctly when a player answered before time ran out.

---

## Iteration 5 Summary
During Iteration 5, the team focused on code organization, visual improvements, and testing. The MazeGUI refactor started in Iteration 4 was completed, breaking the large class into 5 separate view classes: `GameColors.java`, `MazePanel.java`, `QuestionPanel.java`, `DpadPanel.java`, and `CharacterPanel.java`. A locked room visual indicator was added, turning a neighboring room red and displaying a prison bars icon when a door gets permanently locked, giving players clear feedback on which paths are blocked. A current room info label was added to the maze header showing the player's coordinates and whether the room has been visited. Invalid d-pad movement buttons were disabled so players can only press directions that are actually available. The MVC structure was improved by adding a `GameController` class and updating the `TriviaMaze` launcher. A `test` package was set up and over 60 unit tests were written covering `Room`, `Door`, `Maze`, all three question types, `GameMemento`, and `QuestionFactory` using JUnit 5, with all tests passing.

### Issues / Problems During Iteration 5
- Refactoring MazeGUI on Mac caused file naming issues due to the case-insensitive file system, requiring terminal commands to fix the casing.
- Splitting MazeGUI into separate classes required careful wiring of callbacks between panels to keep game logic centralized.

---

## Iteration 6 Summary
During Iteration 6, the team focused on finalizing the project for submission. The sound system was overhauled and improved, with `SoundManager.java` and `SoundEvent.java` refactored to support distinct audio events including correct answer, incorrect answer, win, game over, hint used, and exit game. View folder class files were renamed to follow proper Java naming conventions (e.g. `Mazegui.java` to `MazeGUI.java`, `Mazepanel.java` to `MazePanel.java`). The SRS document was updated to reflect the final implemented architecture, resolved all TBD items, confirmed Java Swing as the GUI framework, documented the sound system classes, and filled in the previously incomplete Software Interfaces section. The UML class diagram was also reviewed and updated to match the final class structure. Final integration testing was performed to verify all core features including maze navigation, SQLite question loading, door locking, save/load, and win/loss detection are working correctly.

### Issues / Problems During Iteration 6
- After pulling the latest changes from the team, `SoundManager.java` had a build error due to a missing `EXIT_GAME` value in the `SoundEvent` enum. This was resolved by adding the missing enum constant.
- File rename conflicts occurred when pulling updated class names from GitHub due to macOS case-insensitive file system behavior. These were resolved using `git stash` before pulling.

## Final Summary:

We successfully fulfilled all required project specifications and extended the project beyond the minimum requirements.

Extra Features Implemented:
- Sound effects for major game events
- Start Game button
- Victory
- Game Over
- Hint system for assisting players with trivia questions
- Dynamic scoring system
- Custom game graphics and sprites

Overall, our project demonstrates object-oriented design principles, MVC architecture, design patterns, database integration, unit testing, and a polished graphical user interface.

## How to run:

1. Complie:
    javac -cp "lib/*" -d out $(find src -name "*.java")

2. Run:
    java -cp "out:lib/*" view.TriviaMaze

3. Run all JUnit test class: 
    java -cp "lib/*:out" org.junit.platform.console.ConsoleLauncher --scan-classpath