import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.time.LocalTime;
import java.util.List;

public class SendPackage {
    private String data;
    private MobileNode p;

    // 查看信道是否空闲
    public boolean Isfree(){
//        if(p.getSendingNode()[0] == null){
//            return true;
//        }
        return false;
    }

    // 发送数据并收取应答消息
    public void Send() throws IOException {
        List<Socket> sockets = null;
        //ToDo:禁用发送按钮

        //数据封装

        DataPackage dataPackage = new DataPackage(data,"data",new DataInfomation((byte) 0,(byte) 0));

        //检查信道是否空闲
        if(Isfree()){
            // 空闲,向每个相邻节点发送RTS帧
            for(NodeInfomation node : p.accessibleNode.nodes){
                // 获取RTS帧
                DataInfomation i = new DataInfomation(p.mac, node.mac);
                byte[]rts = new DataPackage(null,"RTS",i).Pack();
                // 
                Socket s = new Socket(node.ip,node.port);
                sockets.add(s);
                
                // 发送RTS帧
                new DataAnalyse(s).Send(rts);

                // 更新发送状态列表

                p.sendStatusList.add(new SendStatus(LocalTime.now(),false,p.mac,node.mac));
            }

            // 等待应答帧
            boolean ctsCome = false;
            LocalTime timeOut = LocalTime.now().plusSeconds(5);
            while (timeOut.compareTo(LocalTime.now()) < 0){
                for(SendStatus status:p.sendStatusList){
                    if(status.cts) {
                        ctsCome = true;
                        break;
                    }
                    if(ctsCome) break;
                }
            }
            if(ctsCome){
                // 发送数据帧

            }
            else {
                System.out.println("发送错误");
            }
            
            // 发送按钮使能


            

            





        }
        else {

        }






    }

    //判断是否为暴露站
    public boolean IsExposed(){
        return false;
    }
}
