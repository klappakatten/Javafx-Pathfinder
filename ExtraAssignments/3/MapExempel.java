import java.util.*;
import java.util.ArrayList;

public class MapExempel {
    public static void main(String[] args) {
        Map<String, List<String>> lösen = new HashMap<>();
        lösen.put("Isak", new ArrayList<>(Arrays.asList("8888", "1223")));
        lösen.put("Mamma", Arrays.asList("9999", "2231"));
        lösen.put("Anna", Arrays.asList("1111", "2312"));


        String namn = "Isak";
        String lösenord = "XXXXX";

        List<String> ord = lösen.get(namn);
        if (ord == null) {
            ord = new ArrayList<>();
            lösen.put(namn, ord);
        }
        ord.add(lösenord);


        for (Map.Entry<String, List<String>> kv : lösen.entrySet()) {
            System.out.println(kv.getKey() + ": " + kv.getValue());
        }

        for (String s : lösen.keySet()) {
            System.out.println(s + ": " + lösen.get(s));
        }

        lösen.keySet().remove("Isak");

        System.out.println(lösen);

    }
}
