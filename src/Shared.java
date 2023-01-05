import java.util.LinkedList;
import java.util.Queue;

//a class to hold shared data
public class Shared {
    public Queue<Integer> primesQueue;                  //represents the primes buffer pushed by producer and taken by consumer
    //public Queue<Integer> consumedPrimes;               //only used for debugging
    public Semaphore emptySlots, fullSlots, mutex;      //the locks
    public boolean finished;                            //handle end of execution when both producer and consumer finished to prompt user

    //constructor
    Shared(int bufferSize){
        primesQueue = new LinkedList<>();
        //consumedPrimes = new LinkedList<>();
        emptySlots = new Semaphore(bufferSize);
        fullSlots = new Semaphore(0);
        mutex = new Semaphore(1);
        finished = false;
    }
}
