public class GameMemento {

    private static String savedState = "";

    public static void save(String state) {
        savedState = state;
        System.out.println("Game Saved: " + state);
    }

    public static String load() {
        System.out.println("Game Loaded: " + savedState);
        return savedState;
    }
}