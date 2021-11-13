import java.util.Scanner;
import java.time.LocalDate;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int year = scanner.nextInt();
        int[] yearDays = new int[3];

        for (int i = 0; i < yearDays.length; i++) {
            yearDays[i] = scanner.nextInt();
        }

        for (int i = 0; i < yearDays.length; i++) {
            LocalDate date = LocalDate.ofYearDay(year, yearDays[i]);
            System.out.println(date);
        }
    }
}