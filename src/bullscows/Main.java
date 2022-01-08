package bullscows;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Please, enter the secret code's length:");
        String inputLength = scanner.nextLine();
        int length = 0;

        try {
            length = Integer.parseInt(inputLength);
        } catch (NumberFormatException e) {
            System.out.println("Error: \"" + inputLength + "\" isn't a valid number.");
            return;
        }

        if (length > 36) {
            System.out.println("Error: can't generate a secret number with a length of " + length +
                    " because there aren't enough unique digits.");
            return;
        } else if (length < 1) {
            System.out.println("Error: length cannot be less than one.");
            return;
        }

        System.out.println("Input the number of possible symbols in the code:");
        int possibleChars = Integer.parseInt(scanner.nextLine());

        if (possibleChars > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return;
        }

        if (length > possibleChars) {
            System.out.println("Error: it's not possible to generate a code with a length of " + length +
                    " with " + possibleChars + " unique symbols.");
            return;
        }

        String code = generateSecretCode(length, possibleChars);
        System.out.println("Okay, let's start the game!");

        int i = 1;
        boolean guessed = false;
        while (!guessed) {
            System.out.println("Turn " + i++ + ":");
            String answer = scanner.nextLine();
            guessed = checkAnswer(length, code, answer);
        }


        /*if (length > 10) {
            System.out.println("Error: can't generate a secret number with a length of " +
                    length +
                    " because there aren't enough unique digits.");
            return;
        }

        String code = generateSecretCode(length);
        System.out.println("The random secret number is " + code);
        /*int cows = 0;
        int bulls = 0;
        for (int i = 0; i < 4; i++) {
            if (number.charAt(i) == code.charAt(i)) {
                bulls += 1;
            } else if (code.contains(String.valueOf(number.charAt(i)))) {
                cows += 1;
            }
        }
        if (cows == 0 && bulls == 0) {
            System.out.println("Grade: None. The secret code is " + code);
        } else if (cows == 0) {
            System.out.println("Grade: " + bulls + " bull(s). The secret code is " + code);
        } else if (bulls == 0) {
            System.out.println("Grade: " + cows + " cow(s). The secret code is " + code);
        }*/
    }

    public static String generateSecretCode(int length, int numberOfPossibleCharacter) {
        List<String> availableChars = "0123456789abcdefghijklmnopqrstuvwxyz".chars().mapToObj(Character::toString)
                .limit(numberOfPossibleCharacter)
                .collect(Collectors.toList());

        String rangeToPrint = "";
        if (numberOfPossibleCharacter > 10) {
            rangeToPrint = rangeToPrint + "(0-9, a-" + availableChars.get(numberOfPossibleCharacter - 1) + ")";
        } else {
            rangeToPrint = rangeToPrint + "(0-" + availableChars.get(numberOfPossibleCharacter - 1) + ")";
        }

        while ("0".equals(availableChars.get(0))) {
            Collections.shuffle(availableChars);
        }

        System.out.println("The secret is prepared: " + "*".repeat(length) + " " + rangeToPrint + ".");
        return availableChars.subList(0, length).stream().map(String::valueOf).reduce("", String::concat);
    }

    public static boolean checkAnswer(int length, String code, String answer) {
        int cows = 0;
        int bulls = 0;
        for (int i = 0; i < length; i++) {
            if (answer.charAt(i) == code.charAt(i)) {
                bulls += 1;
            } else if (code.contains(String.valueOf(answer.charAt(i)))) {
                cows += 1;
            }
        }
        if (bulls == length) {
            System.out.println("Grade: " + bulls + " bulls");
            System.out.println("Congratulations! You guessed the secret code.");
            return true;
        } else {
            System.out.println("Grade: " +
                    bulls + (bulls > 1 ? " bulls" : " bull") +
                    " and " +
                    cows + (cows > 1 ? " cows" : " cow"));
            return false;
        }
    }
}
