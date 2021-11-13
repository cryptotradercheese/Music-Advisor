import java.util.Scanner;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExecutorService executor = Executors
                .newFixedThreadPool(Runtime.getRuntime().availableProcessors()); // assign an object to it

        while (scanner.hasNext()) {
            int number = scanner.nextInt();
            // create and submit tasks
            executor.submit(new PrintIfPrimeTask(number));
        }

        executor.shutdown();
    }
}

class PrintIfPrimeTask implements Runnable {
    private final int number;

    public PrintIfPrimeTask(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        // write code of task here
        boolean result = this.number > 1 && IntStream.range(2, this.number)
                .noneMatch((number) -> this.number % number == 0);
        if (result) {
            System.out.println(this.number);
        }
    }
}