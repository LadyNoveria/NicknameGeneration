import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class CharacterCounter {

    private static final int QUEUE_CAPACITY = 100;
    final BlockingQueue<String> queue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
    final AtomicInteger maxCount = new AtomicInteger(0);
    final AtomicReference<String> maxText = new AtomicReference<>("");
    final char targetChar;

    CharacterCounter(char targetChar) {
        this.targetChar = targetChar;
    }
}