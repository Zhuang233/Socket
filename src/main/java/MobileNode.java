import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalTime;
import java.util.List;

public class MobileNode extends Process{
    private String ip;
    private int port;
    public byte mac;
    private ReceiveThread receiveThread;
    public SendPackage sendPackage;
    public List<NodeInfomation> allNode;
    public  AccessibleNode accessibleNode;
    public List<SendStatus> sendStatusList;
    public NodeInfomation info;
    public int status;

    final int transmissionTime = 5;

    //获取邻节点
    public void getAccessibleNode(){


    }

    //获取正在发送数据的节点
    public byte[] getSendingNode(){
        byte[] macs = new byte[32];
//        int i = 0;
//        for(SendStatus s:sendStatusList){
//            LocalTime endTime = s.moment.plusSeconds(transmissionTime);
//            if( s.Sending && endTime.compareTo(LocalTime.now()) > 0){
//                macs[i++] = s.src;
//            }
//        }

        return macs;
    }


    //信息展示
    public void ShowInfo(){
        receiveThread.start();

    }

    public MobileNode(List<NodeInfomation> allNode, String ip, int port, byte mac) throws IOException {
        this.allNode = allNode;
        this.ip = ip;
        this.port = port;
        this.mac = mac;
        this.status = 0;
        this.info = new NodeInfomation();
        this.info.ip = ip;
        this.info.port = port;
        this.info.mac = mac;
        this.receiveThread = new ReceiveThread(this);
        this.sendPackage = new SendPackage(this);
        this.accessibleNode = new AccessibleNode(this);


    }

    @Override
    public OutputStream getOutputStream() {
        return null;
    }

    @Override
    public InputStream getInputStream() {
        return null;
    }

    @Override
    public InputStream getErrorStream() {
        return null;
    }

    @Override
    public int waitFor() throws InterruptedException {
        return 0;
    }

    @Override
    public int exitValue() {
        return 0;
    }

    @Override
    public void destroy() {

    }
}
