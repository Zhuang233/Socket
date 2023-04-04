import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

public class DataPackage {
    private String data;
    private String type;
    private DataInfomation infomation;
    private byte x,y,mac;


    // 报文类型：数据帧，数据应答帧，交换地理位置帧，RTS帧，CTS帧
    public enum TYPE{DATA, ANSWER, EXCHANGE, RTS, CTS, FAIL}

    //封装帧
    public byte[] Pack(){
        // 数据帧
        if(Objects.equals(type, "DATA")){
            // 计算帧长
            short dataLen = (short) data.length();
            byte[] Package = new byte[dataLen + 5];

            // 字节转换
            ByteBuffer buffer = ByteBuffer.allocate(2);
            buffer.putShort(dataLen);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte packType = (byte) TYPE.DATA.ordinal();

            // 填充数据帧
            Package[0] = packType;
            Package[1] = infomation.getDestMacByte();
            Package[2] = infomation.getSrcMacByte();
            Package[3] = buffer.get(0);
            Package[4] = buffer.get(1);
            int dataOffest = 5;
            for(byte b:dataBytes){
                Package[dataOffest] = b;
                dataOffest ++;
            }
            return Package;
        }
        //RTS帧
        else if(Objects.equals(type, "RTS")){
            byte[] Package = new byte[3];
            byte packType = (byte) TYPE.RTS.ordinal();

            // 填充数据帧
            Package[0] = packType;
            Package[1] = infomation.getDestMacByte();
            Package[2] = infomation.getSrcMacByte();

            return Package;
        }
        //CTS帧
        else if(Objects.equals(type, "CTS")){
            byte[] Package = new byte[3];
            byte packType = (byte) TYPE.CTS.ordinal();

            // 填充数据帧
            Package[0] = packType;
            Package[1] = infomation.getDestMacByte();
            Package[2] = infomation.getSrcMacByte();
            return Package;
        }
        //预约失败帧
        else if(Objects.equals(type, "FAIL")){
            byte[] Package = new byte[3];
            byte packType = (byte) TYPE.FAIL.ordinal();

            // 填充数据帧
            Package[0] = packType;
            Package[1] = infomation.getDestMacByte();
            Package[2] = infomation.getSrcMacByte();
            return Package;
        }
        return new byte[0];
    }

    //封装帧
    public byte[] Pack(Byte x, Byte y){
        //交换地理位置帧
        if(Objects.equals(type, "EXCHANGE")){
            byte[] Package = new byte[4];
            byte packType = (byte) TYPE.EXCHANGE.ordinal();

            // 填充数据帧
            Package[0] = packType;
            Package[1] = infomation.getSrcMacByte();
            Package[2] = x;
            Package[3] = y;

            return Package;
        }
        return new byte[0];
    }

    //数据应答帧
    public byte[] Pack(boolean receiveStatus, byte mac1, byte mac2){
        if(Objects.equals(type, "ANSWER")){
            byte[] Package = new byte[6];
            byte packType = (byte) TYPE.ANSWER.ordinal();

            // 填充数据帧
            Package[0] = packType;
            Package[1] = infomation.getDestMacByte();
            Package[2] = infomation.getSrcMacByte();
            if(receiveStatus){
                Package[3] = 1;
                Package[4] = 0;
                Package[5] = 0;
            }
            else {
                Package[3] = 0;
                Package[4] = mac1;
                Package[5] = mac2;
            }
            return Package;
        }
        return new byte[0];
    }




    //数据帧解析
    public void UnPack(byte[] rawPackage){
        // 数据帧
        if(rawPackage[0] == (byte) TYPE.DATA.ordinal()){
            type = "DATA";
            byte[] dataLenBytes = {rawPackage[3],rawPackage[4] };
            short dataLen = ByteBuffer.wrap(dataLenBytes).getShort();
            byte[] dataBytes = Arrays.copyOfRange(rawPackage, 5, 5+dataLen);
            data = new String(dataBytes);

            //todo:
            infomation = new DataInfomation((byte) 0,(byte)0);
        }
        // CTS帧
        if(rawPackage[0] == (byte) TYPE.CTS.ordinal()){
            type = "CTS";
            infomation = new DataInfomation(rawPackage[2],rawPackage[1]);
        }
        // 地理位置交换帧
        if(rawPackage[0] == (byte) TYPE.EXCHANGE.ordinal()){
            type = "EXCHANGE";
            x = rawPackage[2];
            y = rawPackage[3];
            mac = rawPackage[1];
        }

    }

    public DataPackage(String data, String type, DataInfomation information) {
        this.data = data;
        this.type = type;
        this.infomation = information;
    }

    public DataPackage() {
    }

    public String getData() {
        return data;
    }

    public String getType() {
        return type;
    }

    public byte getX(){
        return x;
    }

    public byte getY(){
        return y;
    }
    public byte getMac(){
        return mac;
    }

    public DataInfomation getInfomation() {
        return infomation;
    }
}
