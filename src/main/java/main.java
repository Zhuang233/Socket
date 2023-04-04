import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] argv) throws IOException {
        List<NodeInfomation> mNodeList = new ArrayList<>();
        MobileNode nodeA = new MobileNode(mNodeList,"127.0.0.1",22221,(byte) 'A');
        mNodeList.add(nodeA.info);
        MobileNode nodeB = new MobileNode(mNodeList,"127.0.0.1",22222,(byte) 'B');
        mNodeList.add(nodeB.info);

//        MobileNode nodeC = new MobileNode("127.0.0.1",22223,(byte) 'C');
//        MobileNode nodeD = new MobileNode("127.0.0.1",22224,(byte) 'D');
        nodeA.info.x = 1;
        nodeA.info.y = 2;
        nodeB.info.x = 1;
        nodeB.info.y = 3;

        nodeA.ShowInfo();
        nodeB.ShowInfo();

        nodeA.sendPackage.SendMyInfo();
        nodeB.sendPackage.SendMyInfo();

        System.out.println("A: "+nodeA.accessibleNode.nodes.get(0).toString());
        System.out.println("B: "+nodeB.accessibleNode.nodes.get(0).toString());
        System.exit(0);



    }

}
