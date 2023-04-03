import java.io.InputStream;
import java.io.OutputStream;

public class AccessibleNode extends Process{
    int[] status = new int[32];

    //计算邻点
    public void Caculate(){

    }

    //添加邻点
    public void Add(){

    }

    //根据mac地址查询
    public void QueryByMac(){

    }

    //判断信道是否空闲
    public boolean IsFree(){
        return true;
    }

    @Override
    public OutputStream getOutputStream() {
        return null;
    }

    @Override
    public InputStream getInputStream() {
        return null;
    }

    @Override
    public InputStream getErrorStream() {
        return null;
    }

    @Override
    public int waitFor() {
        return 0;
    }

    @Override
    public int exitValue() {
        return 0;
    }

    @Override
    public void destroy() {

    }
}
