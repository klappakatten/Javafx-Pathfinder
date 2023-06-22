// PROG2 VT2023, Inlämningsuppgift, del 1
// Grupp 151
// Martin Nyman many5992
import java.util.Objects;

public class Edge<T> {

    private int weight;
    private final String name;
    private final T destination;

    public Edge(T destination, String name, int weight) {
        this.destination = destination;
        this.name = name;

        if (weight < 0) {
            throw new IllegalArgumentException();
        } else {
            this.weight = weight;
        }
    }

    public T getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        if (weight < 0) {
            throw new IllegalArgumentException();
        }
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Edge<?> && ((Edge<?>) obj).getDestination().equals(destination) && name.equals(((Edge<?>) obj).getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(destination, name);
    }

    @Override
    public String toString() {
        return "to " + destination + " by " + name + " takes " + weight;
    }
}
