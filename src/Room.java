import java.util.HashMap;
import java.util.Map;

public class Room {
    private int myRow;
    private int myCol;
    private Map<String, Door> myDoors;
    private boolean myVisited;

    public Room(int row, int col) {
        myRow = row;
        myCol = col;
        myDoors = new HashMap<>();
        myVisited = false;
    }

    public Door getDoor(String direction) {
        return myDoors.get(direction);
    }

    public boolean isVisited() {
        return myVisited;
    }

    public void setVisited(boolean visited) {
        myVisited = visited;
    }
}