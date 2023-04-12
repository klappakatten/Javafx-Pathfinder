
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class TestSearch {

    static Random random = new Random();

    public static void main(String[] args) {
        final int SIZE = 25_000_000;

        ArrayList<String> arr = new ArrayList(SIZE);

        for (int i = 0; i < SIZE; i++) {
            arr.add(""+ random.nextInt(SIZE/4));
        }

        Collections.sort(arr);
        System.out.println("Done sorting");
        //System.out.println(arr.indexOf(arr.get(SIZE-1)));
        System.out.println(Collections.binarySearch(arr, arr.get(SIZE-1)));
    }
}