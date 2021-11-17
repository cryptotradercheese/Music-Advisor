package advisor;

import advisor.controller.Controller;

import java.util.Scanner;

public class Main {
    //vijigi7382@secbuf.com
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean accessGranted = false;
        Controller controller = new Controller(args);

        while (true) {
            String input = scanner.nextLine();

            if (accessGranted) {
                controller.setStrategy(controller.new AccessGranted(input));
                controller.execute();
            } else if ("auth".equals(input)) {
                controller.setStrategy(controller.new Auth());
                controller.execute();
                accessGranted = true;
            } else if ("exit".equals(input)) {
                controller.setStrategy(controller.new Exit());
                controller.execute();
            } else {
                controller.setStrategy(controller.new NoAccess());
                controller.execute();
            }
        }
    }
}