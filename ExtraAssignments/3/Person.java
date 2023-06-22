import java.util.Objects;

public class Person {
    private final Persnr pnr;
    private final String name;
    private final int vikt;


    public Person(Persnr pnr, String name, int vikt) {
        this.pnr = pnr;
        this.name = name;
        this.vikt = vikt;
    }

    public Persnr getPnr() {
        return pnr;
    }

    public String getName() {
        return name;
    }

    public int getVikt() {
        return vikt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return vikt == person.vikt &&
                Objects.equals(pnr, person.pnr) &&
                Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pnr, name, vikt);
    }

    @Override
    public String toString() {
        return "Person{" +
                "pnr=" + pnr +
                ", name='" + name + '\'' +
                ", vikt=" + vikt +
                '}';
    }
}
