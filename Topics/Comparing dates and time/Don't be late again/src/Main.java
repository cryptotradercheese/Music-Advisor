import java.util.Scanner;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        LocalTime goOutTime = LocalTime.parse("19:30");
        List<String> validStores = new ArrayList<>();

        for (int i = 0, numberOfStores = Integer.parseInt(scanner.nextLine()); i < numberOfStores; i++) {
            String[] storeRow = scanner.nextLine().split(" ");
            LocalTime closingTime = LocalTime.parse(storeRow[1]);

            if (goOutTime.plusMinutes(30).isBefore(closingTime)) {
                validStores.add(storeRow[0]);
            }
        }

        validStores.stream().forEach(System.out::println);
    }
}