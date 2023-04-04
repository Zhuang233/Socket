public class DataInfomation {
    public byte srcMac;
    public byte destMac;

    public DataInfomation(byte srcMac, byte destMac) {
        this.srcMac = srcMac;
        this.destMac = destMac;
    }

    public byte getSrcMacByte() {
        return srcMac;
    }

    public Byte getDestMacByte() {
        return destMac;
    }

}
