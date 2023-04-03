import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
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

        } catch (IOException e) {
            e.printStackTrace();
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

    public DataAnalyse(Socket socket) {
        this.socket = socket;
    }
}
