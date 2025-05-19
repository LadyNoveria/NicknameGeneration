import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final AtomicInteger BEAUTIFUL_WORDS_WITH_LENGTH_3 = new AtomicInteger(0);
    private static final AtomicInteger BEAUTIFUL_WORDS_WITH_LENGTH_4 = new AtomicInteger(0);
    private static final AtomicInteger BEAUTIFUL_WORDS_WITH_LENGTH_5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread_1 = new Thread(() -> {
            for (String text : texts) {
                if (text.length() == 3) {
                    if (isPalindrome(text) || doesItConsistOfOneLetter(text) || areLettersArrangedInAscOrder(text)) {
                        BEAUTIFUL_WORDS_WITH_LENGTH_3.incrementAndGet();
                    }
                }
            }
        });

        Thread thread_2 = new Thread(() -> {
            for (String text : texts) {
                if (text.length() == 4) {
                    if (isPalindrome(text) || doesItConsistOfOneLetter(text) || areLettersArrangedInAscOrder(text)) {
                        BEAUTIFUL_WORDS_WITH_LENGTH_4.incrementAndGet();
                    }
                }
            }
        });

        Thread thread_3 = new Thread(() -> {
            for (String text : texts) {
                if (text.length() == 5) {
                    if (isPalindrome(text) || doesItConsistOfOneLetter(text) || areLettersArrangedInAscOrder(text)) {
                        BEAUTIFUL_WORDS_WITH_LENGTH_5.incrementAndGet();
                    }
                }
            }
        });
        thread_1.start();
        thread_2.start();
        thread_3.start();

        thread_1.join();
        thread_2.join();
        thread_3.join();

        System.out.println("Красивых слов с длиной 3: " + BEAUTIFUL_WORDS_WITH_LENGTH_3.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + BEAUTIFUL_WORDS_WITH_LENGTH_4.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + BEAUTIFUL_WORDS_WITH_LENGTH_5.get() + " шт");
    }

    public static boolean isPalindrome(String text) {
        return text.contentEquals(new StringBuilder(text).reverse());
    }

    public static boolean doesItConsistOfOneLetter(String text) {
        String[] letters = text.split("");
        for (int i = 0; i < text.length() - 1; i++) {
            if (!Objects.equals(letters[i], letters[i + 1])) {
                return false;
            }
        }
        return true;
    }

    public static boolean areLettersArrangedInAscOrder(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        char prevChar = '\0';

        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);

            if (currentChar < prevChar) {
                return false;
            }
            prevChar = currentChar;
        }
        return true;
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
