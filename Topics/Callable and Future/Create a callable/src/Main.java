import java.util.Scanner;
import java.util.concurrent.Callable;

class CallableUtil {
    public static Callable<String> getCallable() {
        // implement method
        Scanner scanner = new Scanner(System.in);
        String value = scanner.nextLine();
        return () -> value;
    }
}