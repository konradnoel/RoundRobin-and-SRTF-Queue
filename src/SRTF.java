import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class SRTF {
    public static List<Task> tasks;
    public static boolean isEmpty;

    public SRTF(){
        tasks = new LinkedList<Task>();
        isEmpty = true;
    }

    public static void addTask(Task task){
        tasks.add(task);
        isEmpty = false;
    }
    public void waitForRR(){
        if(!isEmpty){
            for(int i = 0; i < tasks.size(); i++){
                tasks.get(i).waitingTime++;
            }
        }
    }
    public void finish(Task task, List<Task> finished){
        tasks.remove(task);

        if(tasks.size() > 0 ) isEmpty = false;
        else isEmpty = true;

        finished.add(task);
    }

    private void getQuickesTask(){
        tasks.sort(Comparator.comparing(Task::getBurstTime));
    }

    public Task process(Task activeTask, List<Task> finished){
        getQuickesTask();

        if(activeTask == null || tasks.get(0).getName() != activeTask.getName()){
            activeTask = tasks.get(0);
            System.out.print(tasks.get(0).getName());
        }

        tasks.get(0).burstTime--;
        for (int i = 1; i < tasks.size(); i++) {
            tasks.get(i).waitingTime++;
        }
        if (tasks.get(0).getBurstTime() <= 0)
            finish(tasks.get(0), finished);

        return activeTask;
    }
}
