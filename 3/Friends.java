import java.util.*;
import java.util.ArrayList;

class Friends {
    public static void main(String[] args) {
        String[] minArr = { "Arvid", "Anna", "Adam", "Albert", "Ammar", "August", "Doris" };
        String[] dinArr = {"Stefan", "Sara", "Sune", "Hugo", "Lotta", "Anna", "Adam",
                "Albert", "Anton", "Zack"};

        String[] zacksArr = { "Hugo", "Albert", "Arvid", "Anna" };

        Set<String> minaVänner = new TreeSet<>(Arrays.asList(minArr));
        Set<String> dinaVänner = new TreeSet<>(Arrays.asList(dinArr));
        Set<String> zacksVänner = new TreeSet<>(Arrays.asList(zacksArr));

        Set<String> bjudna = new TreeSet<>();
        bjudna.addAll(minaVänner);
        bjudna.addAll(dinaVänner);
        bjudna.addAll(zacksVänner);
        System.out.println(bjudna);

        // Bjud in alla till fest (inga dubletter)
        System.out.println(bjudna);

        Set<String> gemensammaVänner = new TreeSet<>(minaVänner);
        gemensammaVänner.retainAll(dinaVänner);
        System.out.println(gemensammaVänner);

        // Men Zack får inte vara med
        bjudna.remove("Zack");

        // Och inte heller någon som är vän med Zack
        bjudna.removeAll(zacksVänner);
        System.out.println(bjudna);

        // Om både Doris och Wilhelm är bjudna så ta bort Wilhelm
        // Vi behöver inte kontrollera att han finns eftersom remove
        // inte gör något om det vi ville ta bort inte fanns
        if (bjudna.contains("Doris")) {
            bjudna.remove("Wilhelm");
        }
        System.out.println(bjudna);
        // Om Lotta är bjuden så ta bort alla som börjar på "A"
        if (bjudna.contains("Lotta")) {
            bjudna.removeIf(s -> s.startsWith("A"));
        }
        /*
        if (bjudna.contains("Lotta")) {
            Iterator iter = bjudna.iterator();
            while (iter.hasNext()) {
                if (next.startsWith("A")) {
                    next.remove();
                }
            }
        }
         */
        System.out.println(bjudna);


        // Gör en framslumpad placeringslista
        List<String> slump = new ArrayList<>(bjudna);
        Collections.shuffle(slump);
        System.out.println(slump.subList(0, 3));


    }
}
