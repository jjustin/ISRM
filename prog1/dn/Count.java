
import java.util.ArrayList;
import java.util.Arrays;

public class Count
{
    public static void main(String[] args)
    {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("1");
        arrayList.add("2");
        arrayList.add("a");
        arrayList.add("4");
        arrayList.add("5");
        arrayList.subList(3, arrayList.size()).clear();
        System.out.println(Arrays.toString(arrayList.toArray()));
    }
} 