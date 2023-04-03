import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalTime;
import java.util.List;

public class MobileNode extends Process{
    private String ip;
    private int port;
    public byte mac;
    private ReceiveThread receiveThread;
    private SendPackage sendPackage;
    public  AccessibleNode accessibleNode;
    public List<SendStatus> sendStatusList;
    public NodeInfomation info;
    public String status;

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
