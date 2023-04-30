import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RR {
    public int timeslice = 2;
    public static boolean isEmpty;
    public static List<Task> tasks;

    public RR(){
        tasks = new LinkedList<Task>();
        isEmpty=true;
    }

    public static void addTask(Task task){
        tasks.add(task);
        isEmpty=false;
    }

    public void moveCurrentTaskBack(){
        Task tmp = tasks.get(0);
        tasks.remove(tasks.get(0));
        tasks.add(tmp);
    }

    public Task process(Task activeTask, SRTF SRTFQ, List<Task> finished, boolean swap){
        if(swap){
            moveCurrentTaskBack();
        }


        if(activeTask == null || tasks.get(0).getName() != activeTask.getName()){
            activeTask = tasks.get(0);
            System.out.print((tasks.get(0).getName()));
        }

        //Csökkentjük hogy mennyit kell még foglalkozni a task-al
        tasks.get(0).burstTime--;
        //Minden, az SRTFQ-ban várakozó taskon végigmegyünk, és növeljük a várt idejüket 1-el
        SRTFQ.waitForRR();

        //Ugyanez minden egyéb task-ra ami ebben az RRQ-ban áll
        for(int j = 1; j < tasks.size(); j++){
            tasks.get(j).waitingTime++;
        }

        //ha végeztünk a taskkal, akkor átrakjuk a készek listájába
        if(tasks.get(0).getBurstTime() <= 0){
            finish(tasks.get(0), finished);
            activeTask = null;
        }

        //Ha nem végeztünk vele, akkor még visszadobjuk
        return activeTask;
    }

    public void finish(Task task, List<Task> finished){
        tasks.remove(task);
        if(tasks.size() > 0) isEmpty = false;
        else isEmpty = true;
        finished.add(task);
    }
}
