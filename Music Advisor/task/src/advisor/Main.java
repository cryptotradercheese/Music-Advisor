package advisor;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean accessGranted = false;
        while (true) {
            String input = scanner.nextLine();
            if ("auth".equals(input)) {
                accessGranted = true;
                //vijigi7382@secbuf.com
                System.out.println("https://accounts.spotify.com/authorize?client_id=f938a23eaa914af89b3c4c60c1fbd9a4&response_type=code&redirect_uri=http://localhost");
                System.out.println("---SUCCESS---");
            } else if ("exit".equals(input)) {
                System.out.println("---GOODBYE!---");
                break;
            }

            if (accessGranted) {
                if ("new".equals(input)) {
                    System.out.println("---NEW RELEASES---\n" +
                            "Mountains [Sia, Diplo, Labrinth]\n" +
                            "Runaway [Lil Peep]\n" +
                            "The Greatest Show [Panic! At The Disco]\n" +
                            "All Out Life [Slipknot]");
                } else if ("featured".equals(input)) {
                    System.out.println("---FEATURED---\n" +
                            "Mellow Morning\n" +
                            "Wake Up and Smell the Coffee\n" +
                            "Monday Motivation\n" +
                            "Songs to Sing in the Shower");
                } else if ("categories".equals(input)) {
                    System.out.println("---CATEGORIES---\n" +
                            "Top Lists\n" +
                            "Pop\n" +
                            "Mood\n" +
                            "Latin");
                } else if ("playlists Mood".equals(input)) {
                    System.out.println("---MOOD PLAYLISTS---\n" +
                            "Walk Like A Badass  \n" +
                            "Rage Beats  \n" +
                            "Arab Mood Booster  \n" +
                            "Sunday Stroll");
                }
            } else {
                System.out.println("Please, provide access for application.");
            }
        }
    }
}
