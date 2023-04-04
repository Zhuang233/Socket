import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
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
        return p.accessibleNode.IsFree();
    }

    // 发送数据并收取应答消息
    public void Send(byte destMac) throws IOException {
        List<Socket> sockets = null;
        //ToDo:禁用发送按钮

        //数据封装
        DataPackage dataPackage = new DataPackage(data,"data",new DataInfomation((byte) 0,(byte) 0));

        boolean gotoEnd = false;
        //检查信道是否空闲
        if(!Isfree()){
            // 非空闲，判断暴露站问题
            for(SendStatus status:p.sendStatusList){
                // 有邻点接收
                if(p.accessibleNode.IsAccessible(status.dest)){
                    System.out.println("发送失败");
                    gotoEnd = true;
                }
                if(status.src == destMac){
                    System.out.println("预约失败");
                    gotoEnd = true;
                }
            }
            if(!gotoEnd) System.out.println("暴露站问题");
        }

        if(!gotoEnd){


            // 向每个相邻节点发送RTS帧
            for(NodeInfomation node : p.accessibleNode.nodes){
                // 获取RTS帧
                DataInfomation i = new DataInfomation(p.mac, node.mac);
                byte[]rts = new DataPackage(null,"RTS",i).Pack();
                // 新建Socket
                Socket s = new Socket(node.ip,node.port);
                sockets.add(s);

                // 发送RTS帧
                new DataAnalyse(p,s).Send(rts);

                // 更新发送状态列表

                p.sendStatusList.add(new SendStatus(LocalTime.now(),false,p.mac,node.mac));
            }

            // 等待应答帧，最长等5s
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
        }


        // 发送按钮使能


            

            












    }

    public void SendMyInfo() throws IOException {
        for(NodeInfomation node:p.allNode){
            if(node.mac != p.mac) {
                Socket s = new Socket();
                s.connect(new InetSocketAddress(node.ip,node.port));

                DataInfomation info = new DataInfomation(p.mac,node.mac);
                DataPackage pa = new DataPackage("","EXCHANGE",info);
                byte[] msg = pa.Pack(p.info.x, p.info.y);
                OutputStream out = s.getOutputStream();
                out.write(msg);
            }

        }
    }

    //判断是否为暴露站
    public boolean IsExposed(){
        return false;
    }

    public SendPackage(String data, MobileNode p) {
        this.data = data;
        this.p = p;
    }

    public SendPackage(MobileNode p) {
        this.p = p;
    }
}
