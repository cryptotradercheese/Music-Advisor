package advisor;

import java.util.Scanner;

public class Main {
    //vijigi7382@secbuf.com
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ClientCode.parseArgs(args);

        while (true) {
            String input = scanner.nextLine();

            if (ClientCode.isAccessGranted()) {
                if ("new".equals(input)) {
                    ClientCode.retrieveNew();
                } else if ("featured".equals(input)) {
                    ClientCode.retrieveFeatured();
                } else if ("categories".equals(input)) {
                    ClientCode.retrieveCategories();
                } else if (input.matches("^playlists .+")) {
                    String name = input.replace("playlists ", "");
                    ClientCode.retrievePlaylists(name);
                }
            } else if ("auth".equals(input)) {
                ClientCode.auth();
            } else if ("exit".equals(input)) {
                ClientCode.exit();
                break;
            } else {
                System.out.println("Please, provide access for application.");
            }
        }
    }
}