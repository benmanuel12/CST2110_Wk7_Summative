
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ApplicationRunner {

    static ArrayList<String> words = new ArrayList<String>();
    static String playerInput = "";
    static ArrayList<Character> inputAsArray = new ArrayList<Character>();
    static ArrayList<String> anagrams = new ArrayList<String>();
    static ArrayList<String> results = new ArrayList<String>();

    public static void main(String[] args) {
        mainloop();
    }

    static void pullFromFile() {
        try {
            String dataFile = System.getProperty("user.dir") + File.separator + "lexicon.txt";
            File file = new File(dataFile);
            Scanner input = new Scanner(file);

            while (input.hasNext()) {
                String word = input.next();
                words.add(word);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not Found");
        }
    }

    static void takeInput() {
        System.out.print("Please enter a string (single word or phrase) ");
        Scanner input = new Scanner(System.in);
        playerInput = input.nextLine();
        playerInput = playerInput.toLowerCase().replaceAll("\\s+", "");
        for (int i = 0; i < playerInput.length(); i++) {
            inputAsArray.add(playerInput.charAt(i));
        }
    }

    static void generatePermutations(String str, String ans) {
        if (str.length() == 0) {
            anagrams.add(ans);
            return;
        }

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            String rest = str.substring(0, i) + str.substring(i + 1);
            generatePermutations(rest, ans + ch);
        }
    }
    
    static void findMatches() {
        results.clear();
        generatePermutations(playerInput, "");

        for (char i : inputAsArray) {
            for (int j = 0; j < words.size(); j++) {
                if (words.get(j).indexOf(i) == -1) {
                    words.remove(j);
                }
            }
        }
        for (String i : anagrams) {
            if ((words.contains(i)) && (results.contains(i) == false)) {
                results.add(i);
            }
        }

        if (results.isEmpty()) {
            results.add(playerInput);
        }
        System.out.println("Possible anagrams for " + playerInput + ": " + results.toString());
    }

    static void mainloop() {
        pullFromFile();
        takeInput();
        findMatches();
        System.out.print("Try again (1) or Exit (0): ");
        Scanner input = new Scanner(System.in);
        int goAgain = input.nextInt();
        if (goAgain == 1) {
            mainloop();
        } else {
            System.exit(0);
        }
    }
}
