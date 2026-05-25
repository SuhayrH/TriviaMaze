#GitHub Repository:
https://github.com/SuhayrH/TriviaMaze

## Team Members
- Suhayr Hassan
- Jinal Thummar
- Roman Pavlyshyn

## Project Description
A Java-based trivia maze game where players navigate 
through a maze by answering trivia questions.

## Technologies
- Java
- SQLite
- Java Swing (JFrame)
- SQLite JDBC Driver
- GitHub
- YouTrack
- VS Code

# Iteration 1 Summary:
During Iteration 1, the team focused on setting up the project, creating user stories and tasks in YouTrack, setting up the GitHub repository, and beginning implementation of the main classes for the Trivia model.Maze game.

# Issues / Problems During Iteration 1:
- Some features are still incomplete because this was the first iteration.
- SQLite database setup has started, but it still needs to be fully connected to the trivia question system.
- model.GameMemento save/load functionality has started, but it still needs to be connected to the complete game state.
- File menu Save, Load, and Exit still need full GUI integration.
- More testing is needed.
- The project still needs more MVC organization in future iterations.


# Iteration 2 Summary:
During Iteration 2, the team continued working on the Trivia Maze game by improving the maze logic, connecting SQLite questions to the gameplay system, adding more trivia question data, and updating the project documentation. Jinal worked on SQLite question integration, question data verification, and README updates.

# Issues / Problems During Iteration 2:
- Jinal spent a long time figuring out the SQLite JDBC driver issue. The code compiled, but the database connection did not work at first because Java could not find the SQLite driver.
- The SQLite JDBC `.jar` file had to be added to the project and included in the compile/run classpath.
- The database schema and `QuestionFactory` did not fully match at first, so `QuestionFactory` had to be updated to use the correct database column names.
- `Maze.java` and `Room.java` needed to be moved into the `src` folder so they were organized with the rest of the Java source files.
- TM-39 and TM-40 are working through backend and terminal tests, but the GUI does not yet fully show the database question gameplay.
- Full save/load testing and File menu improvements still need more work.
- The project still needs more MVC organization in future iterations.

#Iteration 3 summary:

During Iteration 3, the team focused on improving the Trivia Maze game by creating and updating the GUI, adding character selection, sprites, and better project package organization. The team also improved File menu Save Game and Load Game integration, tested GameMemento save/load behavior, resolved SQLite driver and questions.db setup issues, and continued cleaning up database and persistence-related code.

Iteration 4 Summary:

During Iteration 4, the team focused on polishing gameplay features and improving the overall user experience of the Trivia Maze game. The team implemented a QuestionTimer.java class in the view package that displays a 20-second countdown when a trivia question appears, automatically locking the door if the player does not answer in time. A RoomFlasher.java class was also added to flash the current room cell green for a correct answer and red for a wrong answer. A hint button was added to MazeGUI, sound effects were generated for game events, character selection was fixed, keyboard shortcuts for maze navigation were added, and the end game flow including new game and exit options was implemented.
Issues / Problems During Iteration 4:

MazeGUI has become very cluttered with too many features handled in a single class.
The team is actively working on separating features into their own dedicated classes and is close to completing this refactor.
Keyboard shortcuts required handling edge cases where key bindings conflicted with existing input listeners.
The QuestionTimer required careful synchronization to ensure it cancelled correctly when a player answered before time ran out.



