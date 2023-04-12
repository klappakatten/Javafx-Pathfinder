public class LongPlay extends Recording {

    public LongPlay(String name, String artist, int year, int condition, double price) {
        super(name, artist, year, condition, price);

    }

    @Override
    public String getType() {
        return "LP";
    }

    @Override
    public double getPrice() {
        return super.getPrice() + (2023 - super.getYear()) * 5;
    }
}
