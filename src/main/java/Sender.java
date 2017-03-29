import java.net.DatagramPacket;
import java.util.concurrent.BlockingQueue;

public abstract class Sender {
    private BlockingQueue<DatagramPacket> queue;

    public Sender(BlockingQueue<DatagramPacket> queue) {
        this.queue = queue;
    }

    public abstract void send();

 }
