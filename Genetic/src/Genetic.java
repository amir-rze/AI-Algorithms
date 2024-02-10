import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

class Node {
    long value;
    int index;
    int heuristic;

    public Node(long value, int index) {
        this.value = value;
        this.index = index;
    }

    public void set_heuristic(int index) {
        this.heuristic = Math.abs((this.index - index));
    }
}

public class Genetic {

    public static long f(int index, long preValue) {
        return (long) (((1103515242L * preValue) + 12345L) % (long) Math.pow(2, 31));
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Random random = new Random();
        Node[] array = new Node[200];
        Map<Integer, Long> map = new LinkedHashMap<>();

        long min = Long.MAX_VALUE;
        int minIndex = 0;
        for (int i = 0; i < 200; i++) {
            if (i == 0) {
                array[0] = new Node(1L, i);
                continue;
            }
            array[i] = new Node(f(i, array[i - 1].value), i);
        }
        for (int i = 0; i < 200; i++) {
            if (array[i].value < min) {
                min = array[i].value;
                minIndex = i;
            }
        }
        for (int i = 0; i < 200; i++) {
            array[i].set_heuristic(minIndex);
        }
        int goalIndex = minIndex;
        // start genetic algorithm

    }
}