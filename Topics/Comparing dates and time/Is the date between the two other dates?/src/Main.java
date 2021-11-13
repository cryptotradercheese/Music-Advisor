import java.util.Scanner;
import java.time.LocalDate;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        LocalDate x = LocalDate.parse(scanner.next());
        LocalDate m = LocalDate.parse(scanner.next());
        LocalDate n = LocalDate.parse(scanner.next());

        boolean result = false;
        if (m.isBefore(n)) {
            result = x.isAfter(m) && x.isBefore(n);
        } else if (m.isAfter(n)) {
            result = x.isAfter(n) && x.isBefore(m);
        }

        System.out.println(result);
    }
}