public class Main {

    public static void main(String[] args) {

        // create threads and start them using the class RunnableWorker
        RunnableWorker worker = new RunnableWorker();
        Thread t1 = new Thread(worker, "worker-1");
        Thread t2 = new Thread(worker, "worker-2");
        Thread t3 = new Thread(worker, "worker-3");

        t1.start();
        t2.start();
        t3.start();
    }
}

// Don't change the code below       
class RunnableWorker implements Runnable {

    @Override
    public void run() {
        final String name = Thread.currentThread().getName();

        if (name.startsWith("worker-")) {
            System.out.println("too hard calculations...");
        } else {
            return;
        }
    }
}