public class Book extends Item implements PricableWithVat6 {

    private final String author;
    private final boolean bound;
    private final double price;

    public Book(String name, String author, double price, boolean bound) {
        super(name);
        this.author = author;
        this.bound = bound;
        this.price = price;
    }

    public String toString() {
        return "Book [author=" + author + ", bound=" + bound + ", price=" + price + "]";
    }

    public double getPrice() {
        if (bound) {
            return price + (price * 0.33);
        } else {
            return price;
        }
    }
}
