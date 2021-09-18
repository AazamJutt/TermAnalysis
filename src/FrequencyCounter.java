import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

//This is Part-B of the Task
public class FrequencyCounter {
    static void print_N_MostFrequentTermsToFile(String filename,
                                                List<String> terms,
                                                int k) {

        Map<String, Integer> mp = new HashMap<>();

        // Put count of all the
        // distinct elements in Map
        // with element as the key &
        // count as the value.
        for (int i = 0; i < terms.size(); i++) {
            // Get the count for the
            // element if already present in the
            // Map or get the default value which is 0.
            mp.put(terms.get(i),mp.getOrDefault(terms.get(i), 0) + 1);
        }

        // Create a list from elements of HashMap
        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(mp.entrySet());

        // Sort the list
        Collections.sort(list,
                            (o1, o2) -> {
                                if (o1.getValue().equals(o2.getValue()))
                                    return o2.getKey().compareTo(o1.getKey());
                                else
                                    return o2.getValue() - o1.getValue();
                            });

        //print first k to file
        try {
            PrintWriter writer = new PrintWriter(filename);
            Formatter fmt = new Formatter();
            fmt.format("%13s | %10s","Term","Frequency");

            writer.println("--------------------------");
            writer.println(fmt);
            writer.println("--------------+-----------");
            for (int i = 0; i < k; i++) {
                writer.println(String.format("%13s | %10s",list.get(i).getKey(),list.get(i).getValue()));
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Tokenizer tokenizer = new Tokenizer("mobydick.txt");
        tokenizer.tokenize();
        List<String> terms = tokenizer.getTokens();
        print_N_MostFrequentTermsToFile("terms.txt",terms,200);
    }
}
