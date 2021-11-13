import java.util.*;
import java.util.concurrent.*;


class FutureUtils {

    public static int howManyIsDone(List<Future> items) {
        // write your code here
        return (int) items.stream()
                .filter(item -> item.isDone())
                .count();
    }

}