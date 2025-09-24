package hangman;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Hangman {
    private static final String[] WORDS = {"сенла", "мирэа", "программирование", "джава", "виселица"};
    private static final int MAX_ATTEMPTS = 6;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        String secretWord = WORDS[random.nextInt(WORDS.length)];
        char[] currentState = new char[secretWord.length()];
        Arrays.fill(currentState, '_');

        int attemptsLeft = MAX_ATTEMPTS;
        boolean wordGuessed = false;
        Set<Character> guessedLetters = new HashSet<>();
        Set<Character> incorrectLetters = new HashSet<>();

        System.out.println("Добро пожаловать в игру 'Виселица'!");
        System.out.println("Загаданное слово: " + String.valueOf(currentState));
        System.out.println("У вас есть " + MAX_ATTEMPTS + " попыток.");

        while (attemptsLeft > 0 && !wordGuessed) {
            System.out.print("Введите букву: ");
            String input = scanner.nextLine().trim().toLowerCase();

            // Проверка на пустой ввод
            if (input.isEmpty()) {
                System.out.println("Ошибка: Вы ничего не ввели. Пожалуйста, введите букву.");
                continue;
            }

            // Проверка на ввод более одного символа
            if (input.length() > 1) {
                System.out.println("Ошибка: Пожалуйста, введите только одну букву.");
                continue;
            }

            char guess = input.charAt(0);

            // Проверка на то, что введен символ буквы
            if (!Character.isLetter(guess)) {
                System.out.println("Ошибка: Пожалуйста, введите букву (цифры и другие символы не допускаются).");
                continue;
            }

            // Проверка на повторный ввод буквы
            if (guessedLetters.contains(guess) || incorrectLetters.contains(guess)) {
                System.out.println("Вы уже вводили эту букву. Попробуйте другую.");
                continue;
            }

            boolean isCorrectGuess = false;
            for (int i = 0; i < secretWord.length(); i++) {
                if (secretWord.charAt(i) == guess) {
                    currentState[i] = guess;
                    isCorrectGuess = true;
                }
            }

            if (isCorrectGuess) {
                guessedLetters.add(guess);
                System.out.println("Верно! Буква '" + guess + "' есть в слове.");
            } else {
                attemptsLeft--;
                incorrectLetters.add(guess);
                System.out.println("Неверно! Буквы '" + guess + "' нет в слове.");
                System.out.println("Осталось попыток: " + attemptsLeft);
                drawHangman(attemptsLeft);
            }

            System.out.println("Текущее состояние: " + String.valueOf(currentState));
            System.out.println("Использованные буквы: " + getUsedLetters(guessedLetters, incorrectLetters));

            if (String.valueOf(currentState).equals(secretWord)) {
                wordGuessed = true;
                System.out.println("\nПоздравляем! Вы угадали слово: " + secretWord);
                System.out.println("Вы выиграли! Осталось попыток: " + attemptsLeft);
            }
        }

        if (!wordGuessed) {
            System.out.println("\nИгра окончена. Загаданное слово было: " + secretWord);
        }

        scanner.close();
    }

    private static void drawHangman(int attemptsLeft) {
        String[] stages = {
                "  -----\n  |   |\n  O   |\n /|\\  |\n / \\  |\n      |\n=========",  // 0 попыток осталось
                "  -----\n  |   |\n  O   |\n /|\\  |\n /    |\n      |\n=========",  // 1 попытка осталась
                "  -----\n  |   |\n  O   |\n /|\\  |\n      |\n      |\n=========",  // 2 попытки осталось
                "  -----\n  |   |\n  O   |\n /|   |\n      |\n      |\n=========",  // 3 попытки осталось
                "  -----\n  |   |\n  O   |\n  |   |\n      |\n      |\n=========",  // 4 попытки осталось
                "  -----\n  |   |\n  O   |\n      |\n      |\n      |\n=========",  // 5 попыток осталось
                "  -----\n  |   |\n      |\n      |\n      |\n      |\n========="   // 6 попыток осталось (начальное состояние)
        };
        System.out.println(stages[attemptsLeft]);
    }

    private static String getUsedLetters(Set<Character> correct, Set<Character> incorrect) {
        StringBuilder sb = new StringBuilder();

        if (!correct.isEmpty()) {
            sb.append("Правильные: ");
            for (char c : correct) {
                sb.append(c).append(" ");
            }
        }

        if (!incorrect.isEmpty()) {
            if (sb.length() > 0) sb.append(" | ");
            sb.append("Неправильные: ");
            for (char c : incorrect) {
                sb.append(c).append(" ");
            }
        }

        return sb.length() > 0 ? sb.toString() : "Пока нет использованных букв";
    }
}