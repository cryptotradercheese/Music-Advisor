import java.util.Scanner;
import java.time.LocalDate;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        LocalDate date = LocalDate.parse(scanner.nextLine());
        System.out.println(date.plusWeeks(2));
    }
}