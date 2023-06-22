public abstract class Recording extends Item implements PriceableWithVat25 {
    private int year;
    private double price;
    private int condition;
    private String artist;

    public Recording(String name, String artist, int year, int condition, double price) {
        super(name);
        this.year = year;
        this.price = price;
        this.condition = condition;
        this.artist = artist;
    }

    public double getPrice() {
        double value = price * (condition / 10);
        if (value < 10) {
            value = 10;
        }

        return value;
    }

    public int getYear() {
        return year;
    }

    public String getArtist() {
        return artist;
    }

    public int getCondition() {
        return condition;
    }

    public abstract String getType();

    public String toString() {
        return "Recording(" + year + ", " + price + ", " + condition + ")";
    }

}
