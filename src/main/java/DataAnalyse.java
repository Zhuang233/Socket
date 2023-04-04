import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Objects;

public class DataAnalyse extends Thread{
    private MobileNode p;
    private Socket socket;

    public void run(){
        byte[] buf = new byte[1024];
        try {
            InputStream input = socket.getInputStream();
            int r_num = input.read(buf);
            DataPackage recvp = new DataPackage();
            recvp.UnPack(buf);
            String type = recvp.getType();

            // 是RTS帧
            if(Objects.equals(type, "RTS")){
                // TODO：处理RTS,看情况发送CTS或预约失败,获取处理返回值

                int ret = 0;

                if(ret == 1){
                    // 预约成功，接收数据帧
                    input.read(buf);
                    // 返回数据应答帧
                    DataPackage p2 = new DataPackage();
                    p2.Pack(true,(byte) 0,(byte)0);
                    // todo:广播数据应答帧

                }
                else return;
            }
            // 是CTS帧
            else if(Objects.equals(type, "CTS")){
                // 将对应cts置1
                for(SendStatus status:p.sendStatusList){
                    if(status.src == recvp.getInfomation().srcMac){
                        status.cts = true;
                    }
                }
            }
            // 是预约失敗帧
            else if(Objects.equals(type, "FAIL")){
                // 将对应cts置0
                for(SendStatus status:p.sendStatusList){
                    if(status.src == recvp.getInfomation().srcMac){
                        status.cts = false;
                    }
                }

            }
            // 是数据帧
            else if(Objects.equals(type, "DATA")){
                //todo:
            }
            // 是数据应答帧
            else if(Objects.equals(type, "ANSWER")){
                //todo:
            }
            // 是交换地理位置帧
            else if(Objects.equals(type, "EXCHANGE")){
                ExchangeAnalyse(buf);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    // RTS帧处理
    private int RTSAnalyse(byte[] rts) throws IOException, InterruptedException {
        // 解析源地址和目的地址
        byte dest = rts[1];
        byte src = rts[2];

        // 本节点正在发送RTS,回复预约失败帧
        if(p.status == 1){
            OutputStream out = socket.getOutputStream();
            byte[]fail = new DataPackage(null,"FAIL",new DataInfomation(p.mac,src)).Pack();
            out.write(fail);
            System.out.println("当前节点正在发送RTS，其他节点预约失败");
            return -1;
        }
        // 本节点正在接收数据帧,回复预约失败帧
        else if(p.status == 2){
            OutputStream out = socket.getOutputStream();
            byte[]fail = new DataPackage(null,"FAIL",new DataInfomation(p.mac,src)).Pack();
            out.write(fail);
            System.out.println("当前节点正在接收数据，其他节点预约失败");
            return -1;
        }
        // 本节点正在发送数据帧,来了这个预约本节点的RTS,回复预约失败帧
        else if(p.status == 3 && dest == p.mac){
            OutputStream out = socket.getOutputStream();
            byte[]fail = new DataPackage(null,"FAIL",new DataInfomation(p.mac,src)).Pack();
            out.write(fail);
            System.out.println("当前节点正在发送数据，其他节点预约本节点失败");
            return -1;
        }
        // 本节点正在接收上一个RTS,又来了这个预约本节点的RTS
        else if(p.status == 4 && dest == p.mac){
            OutputStream out = socket.getOutputStream();
            byte[]fail = new DataPackage(null,"FAIL",new DataInfomation(p.mac,src)).Pack();
            out.write(fail);
            p.status = 5;
            System.out.println("产生隐蔽站问题");
            return 0;
        }
        // 本节点正在发送数据帧，来了个预约其他节点的RTS
        else  if(p.status == 3 && dest != p.mac){
            p.sendStatusList.add(new SendStatus(LocalTime.now(),false,src,dest));
            System.out.println("本节点正在发送数据帧，RTS不是给本节点");
            return 0;
        }
        // 本节点正在接收上一个RTS,来了个预约其他节点的RTS
        else if(p.status == 4 && dest != p.mac){
            p.status = 5;
            return 0;
        }
        // 正常情况
        else {
            LocalTime recvTime = LocalTime.now();
            if(dest == p.mac){
                Thread.sleep(1000);
                // 存在隐蔽站
                if (p.status == 5){
                    OutputStream out = socket.getOutputStream();
                    byte[]fail = new DataPackage(null,"FAIL",new DataInfomation(p.mac,src)).Pack();
                    out.write(fail);
                    p.status = 0;
                    return 0;
                }
                else {
                    OutputStream out = socket.getOutputStream();
                    byte[]cts = new DataPackage(null,"CTS",new DataInfomation(p.mac,src)).Pack();
                    out.write(cts);
                    return  1;
                }
            }
            else {
                System.out.println("RTS不是给本节点");
                return 0;
            }
        }
    }

    private void ExchangeAnalyse(byte[] xyPackage){
        DataPackage dp = new DataPackage();
        dp.UnPack(xyPackage);
        AccessibleNode aNode = p.accessibleNode;
        byte x = dp.getX();
        byte y = dp.getY();
        if(aNode.IsAccessible(x, y)){
            NodeInfomation nodeI = new NodeInfomation();
            nodeI.x = x;
            nodeI.y = y;
            nodeI.mac = dp.getMac();
            nodeI.port = socket.getPort();
            nodeI.ip = socket.getInetAddress().getHostAddress();
            aNode.Add(nodeI);
        }
    }

    //判断是否为隐蔽站
    public boolean IsHidden(){
        return false;
    }

    //发送
    public void Send(byte[] frame) throws IOException {
        OutputStream out = socket.getOutputStream();
        out.write(frame);
    }

    //广播
    public void Broadcast(){

    }

    public DataAnalyse(MobileNode p,Socket socket) {
        this.socket = socket;
        this.p = p;
    }
}
