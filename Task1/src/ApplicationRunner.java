
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ApplicationRunner {

    static int totalChars = 0;
    static char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    static int[] frequency = new int[26];

    public static void main(String[] args) {
        readFile();
        createOutput();
    }
    
    static void readFile(){
        String dataFile = System.getProperty("user.dir") + File.separator + "play.txt";
        try (FileReader fr = new FileReader(dataFile)) {
            int i;
            //go through each char
            while ((i = fr.read()) != -1) {
                // if char is a letter, change it to lower case and add one to appropriate frequency in array and one to total
                if (Character.isLetter((char) i)) {
                    totalChars += 1;
                    char lowerChar = Character.toLowerCase((char) i);

                    for (int j = 0; j < alphabet.length; j++) {
                        if (alphabet[j] == lowerChar) {
                            frequency[j] = frequency[j] + 1;
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException");
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }
    
    static void createOutput(){
        String outputFile = System.getProperty("user.dir") + File.separator + "results.txt";
        String outputString = "Total Number of Letters = " + Integer.toString(totalChars);
        // for length of frequency array, ,  
        for (int x = 0; x < 26; x++) { //26 letters
            int highest = 0;
            int index = 0;
            // find the highest frequency in the array
            for (int y = 0; y < frequency.length; y++) { //iterate through array
                if (frequency[y] > highest) {
                    highest = frequency[y];
                    index = y;
                }
            }
            //cons the value and matching letter to a string
            outputString = outputString + "\n" + alphabet[index] + "-->" + Integer.toString(highest);
            // and set freq to -1
            frequency[index] = -1;
        }

        System.out.println(outputString);
        
        try (FileWriter fw = new FileWriter(outputFile)) {
            fw.write(outputString);
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }
}
