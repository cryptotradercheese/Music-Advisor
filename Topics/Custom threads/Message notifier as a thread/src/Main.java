class MessageNotifier extends Thread {

    // write fields to store variables here
    private String msg;
    private int repeats;

    public MessageNotifier(String msg, int repeats) {
        // implement the constructor
        this.msg = msg;
        this.repeats = repeats;
    }

    @Override
    public void run() {
        // implement the method to print the message stored in a field
        for (int i = 0; i < this.repeats; i++) {
            System.out.println(msg);
        }
    }
}