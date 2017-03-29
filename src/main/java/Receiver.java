import java.net.DatagramPacket;
import java.util.concurrent.BlockingQueue;

public abstract class Receiver {
    private BlockingQueue<DatagramPacket> queue;

    public Receiver(BlockingQueue<DatagramPacket> queue) {
        this.queue = queue;
    }

    public abstract void receive();
}
