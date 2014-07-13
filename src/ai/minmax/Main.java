package ai.minmax;

/**
 *
 * @author AMILA
 */

public class Main {

    public static void main(String[] args) {
        Integer[] minPos = {3,4};
        Integer[] minPrevPos = {2,4};
        MaxNode maxRoot = new MaxNode(minPos, minPrevPos, 2);
        System.out.println(maxRoot.getVal(-999999999, 999999999));
    }
}
