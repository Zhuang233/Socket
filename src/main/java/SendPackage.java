

public class SendPackage {
    private String data;
    private AccessibleNode p;

    // 查看信道是否空闲
    public void Isfree(){

    }

    // 发送数据并收取应答消息
    public void Send(){
        //ToDo:禁用发送按钮

        //数据封装
        DataPackage dataPackage = new DataPackage(data,"default",new DataInfomation("1","2"));


    }

    //判断是否为暴露站
    public boolean IsExposed(){
        return false;
    }
}
