package src.model;

public class Process {
    public int id;
    public int arrivalTime;
    public int burstTime;
    public int priority;
    public int remainingTime;
    public int startTime;
    public int completionTime;
    public int waitingTime;
    public int turnaroundTime;
    public boolean isCompleted;

    public Process(int id, int arrivalTime, int burstTime, int priority) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingTime = burstTime;
        this.isCompleted = false;
    }
}