public interface Priceable {
    public abstract double getPrice();

    public abstract double getVAT();

    public default double getPriceWithVat() {
        return getPrice() + (getPrice() * getVAT());
    }
}
