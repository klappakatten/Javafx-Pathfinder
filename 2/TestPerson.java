import java.util.Comparator;
import java.util.ArrayList;

public class TestPerson {

    static Person[] data = {
            new Person("530610-1309", "Eva", 48),
            new Person("611000-7543", "Stefan", 108),
            new Person("731030-3112", "Martin", 72),
            new Person("111110-9532", "Ela", 83),
            new Person("160320-9655", "Maria", 44),
            new Person("430420-1876", "Ina", 77),
            new Person("761200-4321", "Patrik", 84),
            new Person("101220-6201", "Marian", 53),
            new Person("530611-1309", "Anna", 48),
            new Person("611001-7543", "Zack", 108),
            new Person("731031-3112", "Mathilda", 72),
            new Person("111111-9532", "Ruben", 83),
            new Person("160321-9655", "Peter", 44),
            new Person("430421-1876", "Bodil", 63),
            new Person("761201-4321", "Åsa", 84)
    };

    static void befolka(ArrayList<Person> vilka){
        for(Person p : data)
            vilka.add(p);
    }

    public static void main(String[] args) {
        LinkedListPerson list = new LinkedListPerson();

        list.add(new Person("19061217-0001", "Grace Hopper", 5) );
        list.add(new Person("19220401-0002", "Alan Perlis", 10) );
        list.add(new Person("19511218-0003", "Radia Perlman", 15));
        list.add(new Person("19311012-0004", "Ole-Johan Dahl", 20) );
        list.add(new Person("19170307-0006", "Betty Snyder Holberton", 30) );
        list.add(new Person("19550519-0005", "James Gosling", 25) );

        for (int i = 0; i < list.getSize(); i++) {
            System.out.println(list.get(i).getNamn());
        }
        System.out.println();

        list.remove("Alan Perlis");

        if (list.contains("Alan Perlis")) {
            System.out.println("Alan Perlis is still there\n");
        } else {
            System.out.println("Alan Perlis has left the list\n");
        }

        list.remove("Grace Hopper");

        for (int i = 0; i < list.getSize(); i++) {
            System.out.println(list.get(i).getNamn());
        }

        System.out.println();

        list.remove("James Gosling");

        for (int i = 0; i < list.getSize(); i++) {
            System.out.println(list.get(i).getNamn());
        }
        System.out.println();

        ArrayList<Person> personer = new ArrayList<>();
        befolka(personer);
        personer.sort(Comparator.comparing(Person::getNamn));

        for (Person person : personer) {
            System.out.println(person.getNamn());
        }


        HashSet set = new HashSet();

        set.add(10);
        set.add(11);
        set.add("hello");
        set.add(10);

        System.out.println(set.contains(10));
        System.out.println(set.contains("e"));
        System.out.println(set.size());

        TreeSet treeSet = new TreeSet();
        treeSet.add(10);
        treeSet.add(2);
        treeSet.add(100);
        treeSet.add(1);
        treeSet.add(10);
        treeSet.add(3);

        System.out.println(treeSet.contains(10));
        System.out.println(treeSet.contains(32));
        System.out.println(treeSet.size());

    }
}
