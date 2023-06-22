import java.util.Iterator;
//import java.util.Arrays;

public class ArrayListTest {
    public static void main(String[] args) {

        ArrayList lista = new ArrayList();
        lista.add("Alpha");
        lista.add("Bravo");
        lista.add("Charlie");
        lista.add("Delta");
        lista.add("Echo");
        lista.add("Foxtrot");

        System.out.println("explicit iterator");
        // iterator()-metoden finns i interfacet Iterable, måste implementeras
        Iterator iter = lista.iterator();

        while(iter.hasNext()){
            System.out.println(iter.next());
        }

        System.out.println("\nfor()");

        for (Object element : lista){
            System.out.println(element);
        }

        System.out.println("\nforEach()");

        // forEach()-metoden finns i interfacet Iterable, är default-implementerad
        lista.forEach(o -> System.out.println(o));

        String s = "!";
        System.out.println("\nforEach() med flera statements och åtkomst till lokalt scope");
        lista.forEach(o -> {
            System.out.print(o);
            System.out.println(s);
            });

        System.out.println("\nforEach() med referens till metod");
        lista.forEach(System.out::println);

    }
}
