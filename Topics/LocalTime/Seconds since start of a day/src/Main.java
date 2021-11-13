import java.util.Scanner;
import java.time.LocalTime;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int secondOfDay = scanner.nextInt();
        LocalTime time = LocalTime.ofSecondOfDay(secondOfDay);
        System.out.println(time);
    }
}