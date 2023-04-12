import java.util.*;

public class BasicPersonRegister implements Iterable<Persnr> {

    private final Map<Persnr, Person> persons = new HashMap<>();
    private final Map<String, Set<Person>> personsByName = new HashMap<>();

    public void add(Person person) {
        persons.put(person.getPnr(), person);
        Set<Person> byName = personsByName.get(person.getName());
        if (byName == null) {
            byName = new HashSet<>();
            personsByName.put(person.getName(), byName);
        }
        byName.add(person);
    }

    public void remove(Persnr pnr) {
        Person person = persons.remove(pnr);
        if (person != null) {
            Set<Person> byName = personsByName.get(person.getName());
            if (byName != null) {
                byName.remove(person);
                if (byName.isEmpty()) {
                    personsByName.remove(person.getName());
                }
            }
        }
    }

    public void remove(String name) {
        Set<Person> byName = personsByName.get(name);
        if (byName != null) {
            for (Person person : byName) {
                persons.remove(person.getPnr());
            }
            personsByName.remove(name);
        }
    }

    public Person get(Persnr persnr) {
        return persons.get(persnr);
    }

    public void addAll(Collection<? extends Person> persons) {
        for (Person person : persons) {
            add(person);
        }
    }

    public Set<Person> get(String name) {
        return personsByName.get(name);
    }

    public Iterator<Persnr> iterator() {
        return persons.keySet().iterator();
    }
    public static void main(String[] args) {

        BasicPersonRegister register = new BasicPersonRegister();

        Person p1 = new Person(Persnr.parsePersnr("870410-1111"), "Isak", 90);
        Person p2 = new Person(Persnr.parsePersnr("810410-1111"), "Isak", 22);
        Person p3 = new Person(Persnr.parsePersnr("880410-1111"), "Olle", 22);
        Person p4 = new Person(Persnr.parsePersnr("820410-1111"), "Anna", 22);

        register.addAll(Arrays.asList(p1, p2, p3, p4));

        for (Persnr persnr : register) {
            Person person = register.get(persnr);
            if (person != null) {
                System.out.println(person);
            }
        }
        register.remove("Isak");

        System.out.println("-----------");
        for (Persnr persnr : register) {
            Person p = register.get(persnr);
            if (p != null) {
                System.out.println(p);
            }
        }
    }
}
