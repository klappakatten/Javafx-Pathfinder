
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class Place extends Circle {

    private final String name;
    private final double x;
    private final double y;

    private boolean toggle = false;

    public Place(String name, double x, double y) {
        super(10);
        this.name = name;
        this.x = x;
        this.y = y;
        setFill(Color.BLUE);
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    // Toggle color
    public void toggle() {
        if (!toggle) {
            setFill(Color.RED);
        } else {
            setFill(Color.BLUE);
        }
        toggle = !toggle;
    }

    // Return toggle for selection
    public boolean isToggled() {
        return toggle;
    }

    @Override
    public String toString() {
        return getName() + " " + getX() + " " + getY();
    }
}
