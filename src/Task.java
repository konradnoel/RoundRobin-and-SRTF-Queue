class Task {
    String name;
    int priority;
    int startTime;
    int burstTime;
    int waitingTime;
    boolean completed;
    int incomeID;

    public static int nextId = 0;

    public Task(String name, int priority, int startTime, int burstTime) {
        this.name = name;
        this.priority = priority;
        this.startTime = startTime;
        this.burstTime = burstTime;
        this.waitingTime = 0;
        this.completed = false;
        int incomeID = nextId++;
    }

    public String getName() {return name;}

    public int getPriority() {return priority;}

    public int getStartTime() {return startTime;}

    public int getBurstTime() {return burstTime;}

    public int getWaitingTime(){return waitingTime;}
    public  int getIncomeID(){return incomeID;}
}
