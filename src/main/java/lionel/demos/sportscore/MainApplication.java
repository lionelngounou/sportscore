package lionel.demos.sportscore;

import java.util.Arrays;

/**
 * @author lionel.ngounou
 */
public class MainApplication {
   
    public static void main(String[] args) {
        //Arrays.asList(12,3,44).stream().forEach(System.out::println);
        //System.out.println("Prime number till 10 ::: " + getPrimeNum(1000));
        //System.out.println("Result ::: " + binarySearch(12, 2,12,3,4,4,14,40,11,145,13,34,4,4,1,40,411,15,15,14,8,20,19,35,6,10,5,5));
        //sort(12,2,1,5,10,10,10,7,1,6,12,3,4,4,15,2,0);
        fibonacciNumbers(50);
        //System.out.println(Integer.valueOf((int)1836311903 + (int)1134903170));
    }
    
    
    public static int getPrimeNum(int arg){
        int seq = 1; 
        int num = 2;
        x : while(seq<arg){
            if(++num%2==0)
                continue;
            int loop = num>>1;
            y : for(int i=3; i<loop; i++)
                if(num%i==0)
                    continue x;
            seq++;
            System.out.println("loop till: "+loop+ ", seq: "+seq+ ", prime: " + num);
        }
        return num;
    }
    
    public static int binarySearch(int num, int... numbers){
        System.out.println("Binary search " + num);
        final int length = numbers.length, notfound = -1;
        if(length==0)
            return notfound;
        
        int minIndex = 0, maxIndex = length -1;
        Arrays.sort(numbers);
        System.out.println("Sort numbers ::: " + Arrays.toString(numbers));
        
        while(minIndex!=maxIndex){
            int minNum = numbers[minIndex], maxNum = numbers[maxIndex];
            if(num<minNum || num>maxNum) //not within range
                return notfound;
            if(minNum==num) //matches min
                return minIndex;
            if(maxNum==num) //matches max
                return maxIndex;
            int diff = maxIndex-minIndex;
            if(diff<=1) 
                return notfound; //should have been within range or matched min or max
            int midIndex = maxIndex - (diff/2);
            if(numbers[midIndex]>num)
                maxIndex = midIndex--;
            else minIndex = midIndex;
        }
        return notfound;
    }
    
    public static void sort(int... numbers){
        System.out.println("Sort numbers ::: \t" + Arrays.toString(numbers)); 
        if(numbers.length <=1)
            return;
        for (int i = 0;  i< numbers.length; i++) {
            int next = numbers[i];
            int swapIndex = i;
            for (int j = i; j < numbers.length; j++) {
                if(numbers[j]<next){
                    next = numbers[j];
                    swapIndex = j;
                }
            }
            numbers[swapIndex] = numbers[i];
            numbers[i] = next;
        }
        System.out.println("Sorted numbers ::: \t" + Arrays.toString(numbers));        
    }
    
    public static long fibonacciNumbers(int seq){
        //watch out for number overflow error
        if(seq==1) return 0;
        long n0 = 0, n1 = 1; 
        int count = 2;
        System.out.print(" " + n0);
        System.out.print(" " + n1);
        while(count<seq){
            n1 = n0 + n1;
            n0 = n1 - n0;
            count ++;                    
            System.out.print(" " + n1);
        }
        System.out.println("");
        return n1;
    }
    
}
