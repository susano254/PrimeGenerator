import javax.swing.*;

//producer consumer class (main code)
public class PC {
    PrimeGeneratorForm primeGeneratorForm;

    PC(PrimeGeneratorForm primeGeneratorForm){
        this.primeGeneratorForm = primeGeneratorForm;
    }

    public void run() {
        //take the user inputs values
        String Ntxt = primeGeneratorForm.nTxt.getText();
        String bufferSizeTxt = primeGeneratorForm.bufSizeTxt.getText();

        //if user didn't input anything prompt a message and return
        if(Ntxt.length() == 0 || bufferSizeTxt.length() == 0){
            JOptionPane.showMessageDialog(primeGeneratorForm, "Not valid input");
            primeGeneratorForm.startBtn.setEnabled(true);
            return;
        }

        int N = Integer.valueOf(primeGeneratorForm.nTxt.getText());
        int bufferSize = Integer.valueOf(primeGeneratorForm.bufSizeTxt.getText());
        String outputFileName = primeGeneratorForm.outputFileTxt.getText();

        //if user entered invalid input in N and bufferSize prompt and return
        if(N < 0 || bufferSize < 0) {
            JOptionPane.showMessageDialog(primeGeneratorForm, "Not valid input");
            primeGeneratorForm.startBtn.setEnabled(true);
            return;
        }

        //shared resources instance to be passed to both producer and consumer
        Shared sharedResources = new Shared(bufferSize);

        //instance of both producer and consumer
        Producer producer = new Producer(sharedResources, N);
        Consumer consumer = new Consumer(sharedResources, outputFileName, primeGeneratorForm);

        //start both threads
        producer.start();
        consumer.start();
    }
}
