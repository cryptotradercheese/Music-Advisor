class Singleton {
    // write your code here
    private static Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance != null) {
            return instance;
        }

        synchronized(Singleton.class) {
            if (instance == null) {
                instance = new Singleton();
            }

            return instance;
        }
    }
}