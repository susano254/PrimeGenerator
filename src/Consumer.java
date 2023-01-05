import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;



public class Consumer extends Thread {
    String outputFileName;      //output file name
    FileWriter file;            //output file reference
    int numberOfPrimes = 0;     //number of primes generated so far
    long startMillis;           //millis at the start of the program
    Shared sharedResources;     //shared resources reference
    PrimeGeneratorForm form;    //main gui form reference

    //constructor to take the needed parameters
    Consumer(Shared sharedResources, String outputFileName, PrimeGeneratorForm form){
        this.sharedResources = sharedResources;
        this.outputFileName = outputFileName;
        this.form = form;

        //init program start millis
        startMillis = System.currentTimeMillis();

        //open empty file for the first time only then just append to it in the below run()
        try {
            this.file = new FileWriter(outputFileName, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //the thread run function
    @Override
    public void run() {
        super.run();
        try {
            //open the file for appending
            file = new FileWriter(outputFileName, true);
            //debugging message
            System.out.println("Consumer working...");
            //while condition to detect when program has finished execution and prompt user
            while(!(sharedResources.finished) || (sharedResources.primesQueue.size() > 0)){
                //take one element off the buffer
                int p = syncTake();
                //increment number of primes found so far
                numberOfPrimes++;
                //write the prime to the file
                file.write(String.valueOf(p) + ", ");
                //add it to consumed primes (this queue only for debugging not part of the program and should be commented out)
                //sharedResources.consumedPrimes.add(p);
                //finally call the callback to update the UI
                form.updateUI(p, numberOfPrimes, System.currentTimeMillis() - startMillis);
            }
            //close file
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //prompt the user that the program finsihed
        JOptionPane.showMessageDialog(form, "Finished execution");
        //enable the button again for another start if the user wish
        form.startBtn.setEnabled(true);
    }

    private int syncTake(){
        sharedResources.fullSlots.P();
        sharedResources.mutex.P();
        int i = sharedResources.primesQueue.remove();
        sharedResources.mutex.V();
        sharedResources.emptySlots.V();
        return i;
    }
}
