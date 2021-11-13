import java.util.Locale;
import java.util.Scanner;

class StringProcessor extends Thread {

    final Scanner scanner = new Scanner(System.in); // use it to read string from the standard input

    @Override
    public void run() {
        // implement this method
        String str = scanner.nextLine();
        while (!str.equals(str.toUpperCase())) {
            System.out.println(str.toUpperCase());
            str = scanner.nextLine();
        }

        System.out.println("FINISHED");
    }
}