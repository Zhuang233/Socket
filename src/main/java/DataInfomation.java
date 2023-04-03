public class DataInfomation {
    public byte srcMac;
    public byte destMac;

    public DataInfomation(byte srcMac, byte destMac) {
        this.srcMac = srcMac;
        this.destMac = destMac;
    }

    public Byte getSrcMacByte() {
        // Todo:Mac字符串转换为字节
        return 1;
    }

    public Byte getDestMacByte() {
        // Todo:Mac字符串转换为字节
        return 2;
    }

}
