import java.time.LocalTime;
import java.util.List;

public class SendStatusListScaner extends Thread{
    public List<SendStatus> sendStatusList;

    public void run(){
        while(true){
            for (SendStatus status:sendStatusList){
                if(status.moment.plusSeconds(6).compareTo(LocalTime.now()) < 0)
                    delete(status);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //删除
    public void delete(SendStatus status){
        sendStatusList.remove(status);
    }

    public SendStatusListScaner(List<SendStatus> sendStatusList) {
        this.sendStatusList = sendStatusList;
    }
}
