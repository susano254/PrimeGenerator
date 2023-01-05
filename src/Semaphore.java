public class Semaphore {
    public int value;

       Semaphore(int value){ this.value = value; }

    public synchronized void P(){
        value--;
        //if value less than 0 then thread must wait
        if(value < 0){
            try {
               wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void V(){
        value++;
        //if value less than or equal 0 after increment then there's some threads to notify else there isn't and you can just increment value
        if(value <= 0){
            notify();
        }
    }
}
