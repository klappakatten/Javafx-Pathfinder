public class LinkedListPerson {

    private static class Node {
        private  Person value;
        private Node next;
        private Node(Person value){ this.value = value; }
    }

    private Node first;
    private int size = 0;

    public LinkedListPerson() {
        this.first = null;
    }
    public int getSize() { return size; }

    public void add(Person value) {
        Node tmp = new Node (value);
        tmp.next = first;
        first = tmp;
        size += 1;
    }

    public boolean contains(String value) {
        Node tmp = first;
        while (tmp != null) {
            if (tmp.value.getNamn().equals(value)) {
                return true;
            }
            tmp = tmp.next;
        }
        return false;
    }

    public void remove(String value) {
        if (first.value.getNamn().equals(value)) {
            first = first.next;
            size -= 1;
        } else {
            Node node = first;
            while (node != null) {
                if (node.next != null && node.next.value.getNamn().equals(value)) {
                    node.next = node.next.next;
                    size -= 1;
                    break;
                }
                node = node.next;
            }
        }
    }

    public Person get(int index) {
        Node last = first;
        int i = 0;
        while (i < index) {
            last = last.next;
            i += 1;
        }
        return last.value;
    }
}
