import java.util.List;

public class MobileNode {
    private String ip;
    private int port;
    public String mac;
    private ReceiveThread receiveThread;
    private SendPackage sendPackage;
    public  AccessibleNode accessibleNode;
    public List<SendStatus> sendStatusList;
    public String status;

    //获取邻节点
    public void getAccessibleNode(){

    }

    //获取正在发送数据的节点
    public void getSendingNode(){

    }

    //信息展示
    public void ShowInfo(){

    }

}
