import java.util.ArrayList;
import java.util.List;

public class AccessibleNode {
    private MobileNode p;
    public List<NodeInfomation> nodes;
    final int AccessibleDistance = 5;

    //计算邻点
    public boolean IsAccessible(Byte x, Byte y){
        double dx = x - p.info.x;
        double dy = y - p.info.y;
        if (Math.sqrt(dx*dx+dy*dy) < AccessibleDistance)
            return true;
        return false;
    }

    //添加邻点
    public void Add(NodeInfomation node){
        nodes.add(node);
    }

    //根据mac地址查询
    public boolean IsAccessible(byte mac){
        for(NodeInfomation node:nodes){
            if(node.mac == mac){
                return true;
            }
        }
        return  false;
    }

    //判断信道是否空闲
    public boolean IsFree(){
        // 发送状态列表为空
        if(p.sendStatusList.isEmpty())
            return true;
        return false;
    }

    public AccessibleNode(MobileNode p) {
        this.p = p;
        this.nodes = new ArrayList<>();
    }
}
