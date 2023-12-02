package year2023.day02;

public class CubeSet {
    int blue;
    int green;
    int red;

    public CubeSet(int blue, int green, int red) {
        super();
        this.blue = blue;
        this.green = green;
        this.red = red;
    }

    public boolean isValid(int blueThresold, int greenThresold, int redThresold) {
        return (blue <= blueThresold) && (green <= greenThresold) && (red <= redThresold);
    }

    @Override
    public String toString() {
        return "CubeSet [blue=" + blue + ", green=" + green + ", red=" + red + "]";
    }

    int power() {
        return blue * green * red;
    }

}
