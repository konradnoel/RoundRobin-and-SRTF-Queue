import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Array;
import java.util.*;


public class Main {

    public static List<Task> tasks = new LinkedList<>();
    public static List<Task> finishedTasks = new LinkedList<>();
    public static final RR RRQ = new RR();
    public static final SRTF SRTFQ = new SRTF();

    public static Task activeTask;

    public static void main(String[] args) {
        //Later used variables needed for the queues
        int RRTime = 0; //Time of the RR Queue
        int Time = 0; //The time simulated of the whole process
        boolean RRChange = false; //If this is true, we change task

        String line;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Check if input is available
            if (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line == null || line.length() <= 1) {
                    break;
                }
                String[] splitLine = line.split(",");
                if (splitLine.length >= 4) {
                    tasks.add(new Task(splitLine[0],
                            Integer.parseInt(splitLine[1]),
                            Integer.parseInt(splitLine[2]),
                            Integer.parseInt(splitLine[3])));
                }
            } else {
                break;
            }
        }

        //A cycle to decide which task goes to which queue
        while(!(RR.isEmpty==true && SRTF.isEmpty==true&& tasks.isEmpty())){
           int helper = 0;
            while(helper < tasks.size()){
                if(tasks.get(helper).getStartTime()==Time){
                    Task tmp = tasks.get(helper);
                    helper = 0;
                    if (tmp.getPriority()==1){
                        RRQ.addTask(tmp);
                        tasks.remove(tmp);
                    }
                    else{
                        SRTFQ.addTask(tmp);
                        tasks.remove(tmp);
                    }
                }
                else helper++;
            }

            //We select wether we use RRQ, SRTFQ or neither
            if(!RRQ.isEmpty){
                //If we reach the time slice limit (2), then we change task.
                if(RRTime == RRQ.timeslice){
                    RRChange = true;
                    RRTime = 0;
                }

                //If we haven't reached the time limit, we keep on processing our task
                else RRChange = false;

                activeTask = RRQ.process(activeTask, SRTFQ, finishedTasks, RRChange);

                //If no task is being processed, then we reset the time, as there will be a new one
                if(activeTask == null)
                    RRTime = 0;

                //Increasing the time passed in the RRQ, if this reaches 2, we change
                RRTime++;
            }

            //If we select the SRTFQ, then we go into this branch
            else if(!SRTFQ.isEmpty)
                activeTask = SRTFQ.process(activeTask, finishedTasks);

            //We monitor the time as it is passing each cycle
            Time++;
        }

        Collections.sort(finishedTasks, Comparator.comparing(Task::getStartTime)
                .thenComparing(Task::getName));


        System.out.print("\n"+finishedTasks.get(0).getName()+":"+finishedTasks.get(0).getWaitingTime());
        for(int i = 1; i < finishedTasks.size(); i++){
            Task temp = finishedTasks.get(i);
            System.out.print(","+temp.getName()+":"+temp.getWaitingTime());
        }
    }
}
