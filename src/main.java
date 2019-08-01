import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class main {

    public static void main(String[] args) throws FileNotFoundException {

        HashSet<String> words = new HashSet<String>(50);

        File file = new File("/Users/josh/dev/328Lab3/trump_speech.txt");
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            String string = scanner.next().replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
            if (!string.equals("")) {
                words.add(string);
            }
        }
        System.out.println(words.find("add"));
    }
}
