import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrimeGeneratorForm extends JFrame{
    public JTextField nTxt;
    public JTextField bufSizeTxt;
    public JTextField outputFileTxt;
    public JButton startBtn;
    public JLabel timeElapsedTxt;
    public JLabel numOfElementsTxt;
    public JLabel maxPrimeTxt;
    private JPanel mainPanel;
    private JPanel subPanel;

    PrimeGeneratorForm(){
        //set the window parameters and start it
        this.setContentPane(this.mainPanel);
        this.setTitle("Prime Generator App");
        this.setSize(400,200);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //start the main code when the button is pressed
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                timeElapsedTxt.setText("0 ms");
                numOfElementsTxt.setText("0");
                maxPrimeTxt.setText("NaN");
                //disable the button during the execution
                startBtn.setEnabled(false);
                //producer cosumer class instance
                PC pc = new PC(PrimeGeneratorForm.this);
                //finally run the main code
                pc.run();
            }
        });
    }

    //a callback to update the UI every Consumer iteration
    public void updateUI(int maxPrime, int numOfPrimes,long timeElapsed){
        maxPrimeTxt.setText(String.valueOf(maxPrime));
        numOfElementsTxt.setText(String.valueOf(numOfPrimes));
        timeElapsedTxt.setText(String.valueOf(timeElapsed) + " ms");
    }
}
