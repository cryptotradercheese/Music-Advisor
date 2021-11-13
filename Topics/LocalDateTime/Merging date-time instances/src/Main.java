import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {

    public static LocalDateTime merge(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        // write your code here
        LocalDateTime dateTime = LocalDateTime.of(dateTime1.toLocalDate(), dateTime1.toLocalTime());

        if (dateTime2.getYear() > dateTime1.getYear()) {
            dateTime = dateTime.withYear(dateTime2.getYear());
        }

        if (dateTime2.getMonthValue() > dateTime1.getMonthValue()) {
            dateTime = dateTime.withMonth(dateTime2.getMonthValue());
        }

        if (dateTime2.getDayOfMonth() > dateTime1.getDayOfMonth()) {
            dateTime = dateTime.withDayOfMonth(dateTime2.getDayOfMonth());
        }

        if (dateTime2.getHour() > dateTime1.getHour()) {
            dateTime = dateTime.withHour(dateTime2.getHour());
        }

        if (dateTime2.getMinute() > dateTime1.getMinute()) {
            dateTime = dateTime.withMinute(dateTime2.getMinute());
        }

        if (dateTime2.getSecond() > dateTime1.getSecond()) {
            dateTime = dateTime.withSecond(dateTime2.getSecond());
        }

        return dateTime;
    }

    /* Do not change code below */
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final LocalDateTime firstDateTime = LocalDateTime.parse(scanner.nextLine());
        final LocalDateTime secondDateTime = LocalDateTime.parse(scanner.nextLine());
        System.out.println(merge(firstDateTime, secondDateTime));
    }
}