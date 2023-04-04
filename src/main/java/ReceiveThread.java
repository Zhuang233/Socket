import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ReceiveThread extends Thread{
    private MobileNode p;
    private ServerSocket serverSocket;

    public void run(){
        while (true) {
            try {
                Socket s = serverSocket.accept();
                new DataAnalyse(p,s).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ReceiveThread(MobileNode p) throws IOException {
        this.p = p;
        this.serverSocket = new ServerSocket(p.info.port);
    }
}
