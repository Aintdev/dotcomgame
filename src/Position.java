public class Position {
    public int x;
    public int y;

    public Position(String position) {
        this.x = position.charAt(0) - 'A';
        this.y = Character.getNumericValue(position.charAt(1));
    }
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
