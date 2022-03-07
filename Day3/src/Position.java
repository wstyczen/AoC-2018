

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass())
            return false;
        Position p = (Position) obj;
        if (this.getX() == p.getX() && this.getY() == p.getY())
            return true;
        return false;
    }
}
