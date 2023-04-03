public class DataInfomation {
    public String srcMac;
    public String destMac;

    public DataInfomation(String srcMac, String destMac) {
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
