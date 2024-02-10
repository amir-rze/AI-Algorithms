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

public class SimulatedAnnealing {

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
        // start simulated annealing
        int max_iteration = 19900; // (n*(n-1)) / 2 ********** n = number of states(here 200)
        int currentIndex = random.nextInt(200);
        map.put(currentIndex, array[currentIndex].value);
        int nextIndex;
        int range;
        boolean goalIsFound = false;
        int counter = 0;
        int t = 200;
        for (int i = 0; i < max_iteration; i++) {
            if (currentIndex == goalIndex) {
                goalIsFound = true;
                break;
            }
            nextIndex = random.nextInt(200);// successor ha ra random entekhab mikonim
            if (array[currentIndex].heuristic > array[nextIndex].heuristic) {
                currentIndex = nextIndex;
                map.put(currentIndex, array[currentIndex].value);
                counter++;
            } else {
                double sub = (double) Math.abs(array[nextIndex].heuristic - array[currentIndex].heuristic);
                double p = Math.pow(Math.E, sub / t * (-1));
                double rand = Math.random();
                if (rand < p) {
                    currentIndex = nextIndex;
                    map.put(currentIndex, array[currentIndex].value);
                    counter++;
                }
                t--;
            }
        }

        if (goalIsFound == true)
            System.out.println("the goal is found");
        else
            System.out.println("the goal is not found with this number of iterations");
        System.out.println(map);
    }
}