public class Main {
    public static void main(String[] args) {
        Size size = new Size();
        Game game = new Game(size);
        new Menu(game, size);
        new Instructions();
    }
}
