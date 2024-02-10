import java.util.*;
class Node{
    long value;
    int index;
    int heuristic;
    public Node(long value , int index){
        this.value = value;
        this.index= index;
    }
    public void set_heuristic(int index){
        this.heuristic = Math.abs((this.index-index));
    }
}

public class HillClimbing {

    public static long f(int index , long preValue){
        return (long)(((1103515242L*preValue)+12345L) % (long)Math.pow(2,31));
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Random random = new Random();
        Map<Integer , Long> map = new LinkedHashMap<>();
        Node [] array = new Node[200];
        long min = Long.MAX_VALUE;
        // initializing
        int minIndex=0;
        for (int i = 0; i <200 ; i++) {
            if(i == 0){
                array[0] = new Node(1L,i);
                continue;
            }
            array[i]=new Node(f(i,array[i-1].value),i);
        }
        for (int i = 0; i <200 ; i++) {
            if(array[i].value < min){
                min = array[i].value;
                minIndex = i;
            }
        }
        for (int i = 0; i <200 ; i++) {
            array[i].set_heuristic(minIndex);
        }
        int goalIndex = minIndex;
        // start the hill climbing
        int currentIndex = random.nextInt(200);
        int leftsuccessor;
        int rightsuccessor;
        int step;
        boolean goalIsFound = false;
        while(goalIsFound == false){
            if(currentIndex==goalIndex){
                goalIsFound=true;
                continue;
            }
            step = (random.nextInt(array[currentIndex].heuristic+1)/10)+1;
            if(currentIndex+step>200) // az bazeh biroon nazanim
                step = 1;
            if(currentIndex-step<0) // az bazeh biroon nazanim
                step = 0;
            if(array[currentIndex-step].heuristic < array[currentIndex].heuristic )
                leftsuccessor = currentIndex-step;
            else
                leftsuccessor = currentIndex;
            if(array[currentIndex+step].heuristic < array[currentIndex].heuristic)
                rightsuccessor = currentIndex+ step;
            else
                rightsuccessor = currentIndex;
            if(rightsuccessor < leftsuccessor)
                currentIndex = rightsuccessor;
            else if(rightsuccessor> leftsuccessor)
                currentIndex = leftsuccessor;
            else {
                if(currentIndex == goalIndex){
                    goalIsFound = true ;
                    map.put(currentIndex,array[currentIndex].value);
                    continue;
                }
                if(leftsuccessor == currentIndex || rightsuccessor == currentIndex) {
                    step = 25; // big step for going out from local extremum
                    currentIndex = currentIndex-25;
                }
            }
            map.put(currentIndex,array[currentIndex].value);
        }
        System.out.println("The Goal is Found");
        System.out.println(map);
    }
}