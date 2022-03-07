public class Claim {
    private int id;
    private Position startPoint;
    private int height;
    private int width;

    public Claim(int id, Position startPoint, int height, int width) {
        this.id = id;
        this.startPoint = startPoint;
        this.height = height;
        this.width = width;
    }

    public int getId() {
        return id;
    }

    public Position getStartPoint() {
        return startPoint;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass())
            return false;
        Claim cl = (Claim) obj;
        if (cl.getId() == this.getId())
            return true;
        return false;
    }

}
