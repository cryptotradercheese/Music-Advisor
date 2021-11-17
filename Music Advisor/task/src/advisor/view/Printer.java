package advisor.view;

public class Printer {
    public static void link(String access, String clientId, String redirectUri) {
        System.out.println("use this link to request the access code:");
        System.out.println(access + "/authorize?client_id=" + clientId +
                "&response_type=code&redirect_uri=" + redirectUri);
        System.out.println("waiting for code...");
    }

    public static void makingRequest() {
        System.out.println("code received");
        System.out.println("making http request for access_token...");
    }

    public static void success() {
        System.out.println("---SUCCESS---");
    }

    public static void noAccess() {
        System.out.println("Please, provide access for application.");
    }

    public static void exit() {
        System.out.println("---GOODBYE!---");
    }

    public static void displayError(Exception e) {
        System.out.println(e.getMessage());
    }

    public static void displayIdError() {
        System.out.println("Unknown category name.");
    }
}
