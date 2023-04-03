import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

public class DataPackage {
    private String data;
    private String type;
    private DataInfomation infomation;

    // 报文类型：数据帧，数据应答帧，交换地理位置帧，RTS帧，CTS帧
    public enum TYPE{DATA, ANSWER, EXCHANGE, RTS, CTS}

    //封装帧
    public byte[] Pack(){
        // 数据帧
        if(Objects.equals(type, "data")){
            // 计算帧长
            short dataLen = (short) data.length();
            byte[] dataPackage = new byte[dataLen + 5];

            // 字节转换
            ByteBuffer buffer = ByteBuffer.allocate(2);
            buffer.putShort(dataLen);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte packType = (byte) TYPE.DATA.ordinal();

            // 填充数据帧
            dataPackage[0] = packType;
            dataPackage[1] = infomation.getDestMacByte();
            dataPackage[2] = infomation.getSrcMacByte();
            dataPackage[3] = buffer.get(0);
            dataPackage[4] = buffer.get(1);
            int dataOffest = 5;
            for(byte b:dataBytes){
                dataPackage[dataOffest] = b;
                dataOffest ++;
            }
            return dataPackage;
        }

        return new byte[0];
    }

    //数据帧解析
    public void UnPack(byte[] rawPackage){
        // 数据帧
        if(rawPackage[0] == (byte) TYPE.DATA.ordinal()){
            type = "data";
            byte[] dataLenBytes = {rawPackage[3],rawPackage[4] };
            short dataLen = ByteBuffer.wrap(dataLenBytes).getShort();
            byte[] dataBytes = Arrays.copyOfRange(rawPackage, 5, 5+dataLen);
            data = new String(dataBytes);

            //todo:
            infomation = new DataInfomation("1","2");


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

    public DataInfomation getInfomation() {
        return infomation;
    }
}
