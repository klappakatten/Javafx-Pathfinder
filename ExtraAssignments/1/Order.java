import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Item> items = new ArrayList<>();
    // private long orderNumber;
    private static long counter;

    public Order(Item... items) {
        for (Item item : items) {
            this.items.add(item);
        }

        counter = counter + 1;
    }

    public String getReceipt() {
        String str = "";
        for (Item item : items) {
            str += item.getName();
            str += " Price=" + item.getPrice() + " Vat=" + (item.getPricePlusVat() - item.getPrice()) + " total price: "
                    + getTotalPlusVAT() + " ";
        }
        return str;
    }

    public double getTotalPlusVAT() {
        int sum = 0;
        for (Item item : items) {
            sum += item.getPricePlusVat();
        }
        return sum;
    }

    public double getTotalValue() {
        int sum = 0;
        for (Item item : items) {
            sum += item.getPrice();
        }
        return sum;
    }

}
