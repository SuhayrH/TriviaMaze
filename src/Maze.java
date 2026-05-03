public class Maze {
    private Room[][] myRooms;
    private int myCurrentRow;
    private int myCurrentCol;
    private int mySize;

    public Maze(int size) {
        mySize = size;
        myRooms = new Room[size][size];
        myCurrentRow = 0;
        myCurrentCol = 0;
        initializeRooms();
    }

    private void initializeRooms() {
        for (int row = 0; row < mySize; row++) {
            for (int col = 0; col < mySize; col++) {
                myRooms[row][col] = new Room(row, col);
            }
        }
    }

    public Room getCurrentRoom() {
        return myRooms[myCurrentRow][myCurrentCol];
    }

    public boolean move(String direction) {
        return false; // TODO: implement in future iteration
    }

    public boolean isGameOver() {
        return false; // TODO: implement in future iteration
    }

    public boolean isGameWon() {
        return false; // TODO: implement in future iteration
    }

    public Room getRoom(int row, int col) {
        return myRooms[row][col];
    }
}