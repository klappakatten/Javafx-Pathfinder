import java.util.*;

public class SortedSetsAndMaps {

    public static void main(String[] args) {
        SortedMap<Integer, Person> persons = new TreeMap<>();

        Person p1 = new Person(Persnr.parsePersnr("870410-1111"), "Isak", 90);
        Person p2 = new Person(Persnr.parsePersnr("810410-1111"), "Isak", 22);
        Person p3 = new Person(Persnr.parsePersnr("880410-1111"), "Olle", 22);
        Person p4 = new Person(Persnr.parsePersnr("820410-1111"), "Anna", 22);

        List<Person> personList = Arrays.asList(p1, p2, p3, p4);

        for (Person person : personList) {
            persons.put(person.getPnr().getÅr(), person);
        }

        System.out.println(persons.subMap(82, 88));
        System.out.println(persons.tailMap(86));
        System.out.println(persons.headMap(87));

    }
}
