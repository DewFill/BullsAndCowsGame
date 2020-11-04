import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        chooseDifficulty();
    }
    public static void visualiseSecretCode(int length, int possibleSymbols, String secretCode){
        System.out.print("The secret is prepared: ");
        for (int i = 0; i < length; i++) {
            System.out.print("*");
        }
        System.out.print(" (0-9, a-");
        System.out.print((char) (possibleSymbols + 86));
        System.out.print(").");
        System.out.println();

        checkUserAnswer(length, secretCode);
    }
    public static void chooseDifficulty () {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input the length of the secret code:");
        String lengthStr = sc.nextLine();
        int length;
        int possibleSymbols;
        if (lengthStr.matches("\\d+")) {
            length = Integer.parseInt(lengthStr);
            if (!(length < 1)) {
                System.out.println("Input the number of possible symbols in the code:");
                String possibleSymbolsStr = sc.nextLine();
                if (possibleSymbolsStr.matches("\\d+")) {
                    possibleSymbols = Integer.parseInt(possibleSymbolsStr);
                    if (length > possibleSymbols) {
                        System.out.println("Error: it's not possible to generate a code with a length of " + length + " with " + possibleSymbols + " unique symbols.");
                    } else if (possibleSymbols > 36) {
                        System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                    } else {
                        generateSecretCode(length, possibleSymbols);
                    }
                } else {
                    System.out.println("Error: \"" + possibleSymbolsStr + "\" isn't a valid number.");

                }
            } else {
                System.out.println("Error: it's not possible to generate a code with a length less than 1.");
            }
        } else {
            System.out.println("Error: \"" + lengthStr + "\" isn't a valid number.");
        }

    }
    public static void generateSecretCode(int length, int possibleSymbols){
        LinkedHashMap<Character, Boolean> hashMap = new LinkedHashMap<>();
        while (hashMap.size() != length) {
            Random rand = new Random();
            int randomInt = rand.nextInt(possibleSymbols);
            char random;
            if (randomInt > 9) {
                random = (char) (87 + randomInt);
            } else {

                random = (char) (57 - randomInt);
            }
            hashMap.put(random, true);
        }
        String secretCode = hashMap.keySet().toString().replaceAll(", ", "").replaceAll("\\[", "").replaceAll("]", "");
        visualiseSecretCode(length, possibleSymbols, secretCode);
    }
    public static void checkUserAnswer(int length, String secretCode) {
        System.out.println("Okay, let's start a game!");
        Scanner sc = new Scanner(System.in);
        boolean checker = true;
        int turns = 1;
        while (checker) {
            int bulls = 0;
            int cows = 0;
            System.out.println("Turn " + turns + ":");
            turns++;
            String userCode = sc.nextLine();

            for (char userValue : userCode.toCharArray()) {
                for (char secretValue : secretCode.toCharArray()) {
                    if (userValue == secretValue) {
                        cows++;
                    }
                }
            }
            for (int i = 0; i < length; i++) {
                if (userCode.charAt(i) == secretCode.charAt(i)) {
                    bulls++;
                }
            }
            cows -= bulls;
            if (cows == 0 && bulls == length) {
                System.out.println("Grade: " + bulls + " bulls");
                System.out.println("Congratulations! You guessed the secret code.");
                checker = false;
            } else if (cows == 0 && bulls == 0) {
                System.out.println("Grade: None.");
            } else if (cows > 0 && bulls == 0) {
                System.out.println("Grade: " + cows + " cow(s)");
            } else if (cows == 0 && bulls > 0) {
                System.out.println("Grade: " + bulls + " bull(s)");
            } else if (cows > 0 && bulls > 0) {
                System.out.println("Grade: " + bulls + " bull(s) and " + cows + " cow(s)");
            }
        }
    }
}