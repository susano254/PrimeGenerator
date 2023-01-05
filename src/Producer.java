import java.util.ArrayList;

import static java.lang.Math.sqrt;

public class Producer extends Thread{
    int N, limit;
    Shared sharedResources;             //shared resources reference
    ArrayList<Boolean> primesBuffer;    //generate primesBuffer for the algorithm (not the prime buffer shared by consumer)

    Producer(Shared sharedResources, int N){
        this.sharedResources = sharedResources;
        this.N = N;
        limit = (int) sqrt(N);          //algorithm stops at sqrt(N) store it in a variable to increase performance

        //init the primes buffer for the algorithm
        primesBuffer = new ArrayList<>();
        for(int i = 0; i < N; i++) primesBuffer.add(i, true); //all members init to being primes
    }

    //thread run function
    @Override
    public void run() {
        super.run();
        System.out.println("Producer working...");
        generatePrime();
    }

    //a function that generates primes numbers based on sieve of eratosthenes algorithm
    private void generatePrime(){
        //from i = 2 ( first prime ) to Sqrt(N)
        for(int i = 2; i < limit; i++){
            if(primesBuffer.get(i)) {           //if the number is not marked then it's a prime add to the queue
                syncAdd(i);                     //add the prime number to the queue buffer
            }
            for(int j = i*i; j < N; j += i){    //mark all multiple of current number as false (not primes)
                primesBuffer.set(j, false);
            }
        }

        //algorithm finished
        //add all remaining unmarked primes
        for(int i = limit; i < N; i++) {
            if(primesBuffer.get(i))
                syncAdd(i);
        }
        sharedResources.finished = true;
    }

    private void syncAdd(int i){
        sharedResources.emptySlots.P();
        sharedResources.mutex.P();
        sharedResources.primesQueue.add(i);
        sharedResources.mutex.V();
        sharedResources.fullSlots.V();
    }
}
