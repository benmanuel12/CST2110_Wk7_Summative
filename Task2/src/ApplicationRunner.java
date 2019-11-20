
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ApplicationRunner {

    // create list to store possible words in
    static ArrayList<String> choices = new ArrayList<>();
    // create variable to hold chosen word
    static String targetWord;
    // create list of letters that were tried and failed/
    static ArrayList<Character> charsNotInWord = new ArrayList<>();
    // create list of letters that were tried and succeeded/
    static ArrayList<Character> charsInWord = new ArrayList<>();
    // create list to hold chosen word characters
    static ArrayList<Character> targetWordArray = new ArrayList<>();
    // create list to hold the state of the word as it is being guessed
    static ArrayList<Character> wordState = new ArrayList<>();
    // create boolean to test if game is won
    static boolean won = false;
    // create boolean to poll for user quit
    static boolean quitCheck = false;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("Play (1) or Exit (0) > ");
            char playAgain = input.next().charAt(0);
            if (playAgain == '1') {
                gameLoop();
            }
            else if (playAgain == '0') {
                System.exit(0);
            }
        }
    }

    public static void gameLoop() {
        readFromFile();
        pickWordAndGenerateArray();

        // for 10 loops or until bool is true
        quitCheck = false;
        won = false;
        int i = 0;
        while ((i < 10) && (won == false) && (quitCheck == false)) {
            System.out.println("Starting Round " + (i + 1));
            oneRound();
            i++;
        }
        if (won == true) {
            System.out.println("You win, the word was " + targetWord.toUpperCase());
        } else if (quitCheck == true) {
            System.out.println("You gave up, the word was " + targetWord.toUpperCase());
        } else {
            System.out.println("You lose, the word was " + targetWord.toUpperCase());
        }
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("Play (1) or Exit (0) > ");
            char playAgain = input.next().charAt(0);
            if (playAgain == '1') {
                gameLoop();
            }
            else if (playAgain == '0') {
                System.exit(0);
            }
        }
    }

    public static void readFromFile() {
        //Read wordlist into list in storage
        String dataFile = System.getProperty("user.dir") + File.separator + "wordlist.txt";
        File file = new File(dataFile);
        try {
            Scanner input = new Scanner(file);
            while (input.hasNext()) {
                choices.add(input.next());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public static void pickWordAndGenerateArray() {
        // Select word from list
        int randomIndex = (int) (Math.random() * choices.size());
        targetWord = choices.get(randomIndex);
        for (int i = 0; i < targetWord.length(); i++) {
            targetWordArray.add(targetWord.charAt(i));
        }
        for (int i = 0; i < targetWord.length(); i++) {
            wordState.add('_');
        }
    }

    public static char askForLetter() {
        Scanner userInput = new Scanner(System.in);
        while (true) {
            // ask for a letter
            System.out.print("\nPlease enter a letter or * to give up: ");
            char nextLetter = userInput.next().charAt(0);
            // confirm its a letter and it isnt in the list of letters already tried, if not ask again
            if ((charsNotInWord.contains(nextLetter) == false) && (charsInWord.contains(nextLetter) == false)) {
                if (Character.isLetter(nextLetter) || (nextLetter == '*')) {
                    return nextLetter;
                }
            }
        }
    }

    public static void oneRound() {
        System.out.println("\n" + charsNotInWord.size() + " wrong guesses so far " + String.valueOf(charsNotInWord));
        for (int i = 0; i < wordState.size(); i++) {
            System.out.print(String.valueOf(wordState.get(i)) + ' ');
        }
        
        char nextLetter = askForLetter();
        if (nextLetter == '*') {
            won = false;
            quitCheck = true;
            return;
        }
        //if that letter is in the word, swap all appropriate _ for it and add to successful letters
        if (targetWordArray.contains(nextLetter)) {
            System.out.println("Good guess");
            while (targetWordArray.contains(nextLetter)) {
                int indexToChange = targetWordArray.indexOf(nextLetter);
                wordState.set(indexToChange, nextLetter);
                targetWordArray.set(indexToChange, '_');
            }
            charsInWord.add(nextLetter);
        } // else add it to failed letters
        else {
            System.out.println("Bad Guess");
            charsNotInWord.add(nextLetter);
        }
        isGameWon();
    }

    public static void isGameWon() {
        won = !wordState.contains('_');
    }
}
