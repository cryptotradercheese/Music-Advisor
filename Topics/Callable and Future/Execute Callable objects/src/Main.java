import java.util.List;
import java.util.concurrent.*;


class FutureUtils {

    public static int executeCallableObjects(List<Future<Callable<Integer>>> items) {
        // write your code here
        int sum = 0;
        for (int i = items.size() - 1; i >= 0 ; i--) {
            try {
                sum += items.get(i).get().call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return sum;
    }

}