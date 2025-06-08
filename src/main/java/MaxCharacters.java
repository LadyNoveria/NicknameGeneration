import java.util.Random;

public class MaxCharacters {
    private static final int TEXT_COUNT = 3333;
    private static final String LETTERS = "abc";
    private static final int MIN_LENGTH = 100_000;
    private static final int LENGTH_RANGE = 3;

    public void findMaxCharacters() throws InterruptedException {
        CharacterCounter counterA = new CharacterCounter('a');
        CharacterCounter counterB = new CharacterCounter('b');
        CharacterCounter counterC = new CharacterCounter('c');

        startGeneratorThreads(counterA, counterB, counterC);

        Thread threadA = createCounterThread(counterA);
        Thread threadB = createCounterThread(counterB);
        Thread threadC = createCounterThread(counterC);

        threadA.start();
        threadB.start();
        threadC.start();

        threadA.join();
        threadB.join();
        threadC.join();

        printResult(counterA, "'a'");
        printResult(counterB, "'b'");
        printResult(counterC, "'c'");
    }

    private void startGeneratorThreads(CharacterCounter... counters) {
        for (CharacterCounter counter : counters) {
            new Thread(() -> {
                Random random = new Random();
                for (int i = 0; i < TEXT_COUNT; i++) {
                    try {
                        counter.queue.put(generateText(LETTERS, MIN_LENGTH + random.nextInt(LENGTH_RANGE)));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("Generator thread interrupted", e);
                    }
                }
            }).start();
        }
    }

    private Thread createCounterThread(CharacterCounter counter) {
        return new Thread(() -> {
            try {
                for (int i = 0; i < TEXT_COUNT; i++) {
                    String text = counter.queue.take();
                    int count = (int) text.chars().filter(c -> c == counter.targetChar).count();
                    if (count > counter.maxCount.get()) {
                        counter.maxCount.set(count);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Counter thread interrupted", e);
            }
        });
    }

    private void printResult(CharacterCounter counter, String charName) {
        System.out.printf("Больше всего символов %s (%d штук)%n", charName, counter.maxCount.get());
    }

    private String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}