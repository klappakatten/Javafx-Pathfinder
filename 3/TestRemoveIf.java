import java.util.ArrayList;
import java.util.Arrays;

public class TestRemoveIf {

    public static void main(String[] args) {
        ArrayList<String> lista = new ArrayList(Arrays.asList("Ett", "Två", "Tre", "Fyra", "Fem"));
        lista.removeIf(o->o.charAt(0) == 'T');
        lista.forEach(System.out::println);
    }
}
