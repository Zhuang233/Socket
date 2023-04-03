import java.util.List;

public class AccessibleNode {
    MobileNode p;
    List<NodeInfomation> nodes;
    final int AccessibleDistance = 5;

    //计算邻点
    public Boolean IsAccessible(Byte x, Byte y){
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
    public void QueryByMac(){

    }

    //判断信道是否空闲
    public boolean IsFree(){
        return true;
    }

}
