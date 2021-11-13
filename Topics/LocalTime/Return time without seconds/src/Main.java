import java.util.Scanner;
import java.time.LocalTime;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        LocalTime time = LocalTime.parse(scanner.nextLine());
        System.out.println(time.withSecond(0));
    }
}