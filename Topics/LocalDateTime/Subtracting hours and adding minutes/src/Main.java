import java.util.Scanner;
import java.time.LocalDateTime;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        LocalDateTime dateTime = LocalDateTime.parse(scanner.nextLine());
        int hours = scanner.nextInt();
        int minutes = scanner.nextInt();

        dateTime = dateTime
                .minusHours(hours)
                .plusMinutes(minutes);
        System.out.println(dateTime);
    }
}