import java.util.HashSet;

public class Main {
    public static void main(String[] args) {

        Item book1 = new Book("A guide to modern jazz", "Unknown author", 100, false);
        Item book2 = new Book("Beethoven: a biography", "Holmqvist", 400, false);
        Item book2bound = new Book("Beethoven: a biography", "Holmqvist", 400, true);

        Item item1 = new LongPlay("Giant Steps", "John Coltrane", 1959, 10, 100);
        Item cd2 = new CompactDisc("Kind of Blue", "Miles Davis", 1959, 5, 100);
        Item lp1 = new CompactDisc("Punisher", "Phoebe Bridgers", 2020, 10, 200);
        Item lp2 = new LongPlay("What Kinda Music", "Tom Misch", 2020, 10, 150);
        Item lp3 = new LongPlay("Little Oblivions", "Julien Baker", 2021, 10, 120);

        Order order1 = new Order(book1, book2bound);
        System.out.println(order1.getReceipt());
        /*
         * Receipt for order #1
         * -----------
         * Book { name='A guide to modern jazz', author=Unknown author, bound=false,
         * price=100.0, price+vat=106.0 }
         * Book { name='Beethoven: a biography', author=Holmqvist, bound=true,
         * price=532.0, price+vat=563.92 }
         * Total excl. VAT: 632.0
         * Total incl. VAT: 669.92
         */

        Order jazz = new Order(book2, item1, cd2);
        System.out.println(jazz.getReceipt());
        /*
         * Receipt for order #2
         * -----------
         * Book { name='Beethoven: a biography', author=Holmqvist, bound=false,
         * price=400.0, price+vat=424.0 }
         * Recording { name=Giant Steps, artist=John Coltrane, year=1959, type=LP,
         * condition=10, original price=100.0, price=420.0, price+vat=525.0 }
         * Recording { name=Kind of Blue, artist=Miles Davis, year=1959, type=CD,
         * condition=5, original price=100.0, price=50.0, price+vat=62.5 }
         * Total excl. VAT: 870.0
         * Total incl. VAT: 1011.5
         */

        Order modernIndie = new Order(lp1, lp2, lp3);
        System.out.println(modernIndie.getReceipt());
        /*
         * Receipt for order #3
         * -----------
         * Recording { name=Punisher, artist=Phoebe Bridgers, year=2020, type=CD,
         * condition=10, original price=200.0, price=200.0, price+vat=250.0 }
         * Recording { name=What Kinda Music, artist=Tom Misch, year=2020, type=LP,
         * condition=10, original price=150.0, price=165.0, price+vat=206.25 }
         * Recording { name=Little Oblivions, artist=Julien Baker, year=2021, type=LP,
         * condition=10, original price=120.0, price=130.0, price+vat=162.5 }
         * Total excl. VAT: 495.0
         * Total incl. VAT: 618.75
         */

    }
}
