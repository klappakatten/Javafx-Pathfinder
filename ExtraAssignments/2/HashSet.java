public class HashSet {

    private ArrayList[] values;
    private int size;

    public HashSet() {
        values = new ArrayList[20];
        size = 0;
    }

    public void add(Object o) {
        int position = o.hashCode() % values.length;
        ArrayList list = values[position];
        if (list == null) {
            list = new ArrayList();
            list.add(o);
            values[position] = list;
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(o)) {
                    return;
                }
            }
            list.add(o);
        }
        size += 1;
    }

    public int size() {
        return size;
    }

    public boolean contains(Object o) {
        int position = o.hashCode() % values.length;
        ArrayList list = values[position];
        if (list == null) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(o)) {
                return true;
            }
        }
        return false;
    }


}
