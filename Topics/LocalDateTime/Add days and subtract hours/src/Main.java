import java.util.Scanner;
import java.time.LocalDateTime;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String[] input = scanner.nextLine().split(" ");
        LocalDateTime output = LocalDateTime.parse(input[0])
                .plusDays(Integer.parseInt(input[1]))
                .minusHours(Integer.parseInt(input[2]));

        System.out.println(output);
    }
}