package src.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import src.model.Process;

public class SchedulingService {

    // First Come First Serve (FCFS)
    public List<Process> fcfs(List<Process> processes) {
        int currentTime = 0;
        for (Process p : processes) {
            if (currentTime < p.arrivalTime) {
                currentTime = p.arrivalTime;
            }
            p.startTime = currentTime;
            p.completionTime = currentTime + p.burstTime;
            p.waitingTime = p.startTime - p.arrivalTime;
            p.turnaroundTime = p.completionTime - p.arrivalTime;
            currentTime = p.completionTime;
        }
        return processes;
    }

    // Shortest Job First (SJF)
    public List<Process> sjf(List<Process> processes) {
        PriorityQueue<Process> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.burstTime));
        int currentTime = 0;
        int completed = 0;
        List<Process> result = new ArrayList<>();

        while (completed < processes.size()) {
            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && !p.isCompleted) {
                    pq.add(p);
                }
            }
            if (!pq.isEmpty()) {
                Process currentProcess = pq.poll();
                currentProcess.startTime = currentTime;
                currentProcess.completionTime = currentTime + currentProcess.burstTime;
                currentProcess.waitingTime = currentProcess.startTime - currentProcess.arrivalTime;
                currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                currentProcess.isCompleted = true;
                currentTime += currentProcess.burstTime;
                completed++;
                result.add(currentProcess);
            } else {
                currentTime++;
            }
        }
        return result;
    }

    // Round Robin (RR)
    public List<Process> roundRobin(List<Process> processes, int timeQuantum) {
        Queue<Process> queue = new LinkedList<>();
        int currentTime = 0;
        List<Process> result = new ArrayList<>();

        for (Process p : processes) {
            if (p.arrivalTime <= currentTime) {
                queue.add(p);
            }
        }

        while (!queue.isEmpty()) {
            Process currentProcess = queue.poll();
            if (currentProcess.remainingTime > timeQuantum) {
                currentTime += timeQuantum;
                currentProcess.remainingTime -= timeQuantum;
                queue.add(currentProcess);
            } else {
                currentTime += currentProcess.remainingTime;
                currentProcess.remainingTime = 0;
                currentProcess.completionTime = currentTime;
                currentProcess.waitingTime = currentProcess.completionTime - currentProcess.arrivalTime - currentProcess.burstTime;
                currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                result.add(currentProcess);
            }
        }
        return result;
    }

    // Priority Scheduling
    public List<Process> priorityScheduling(List<Process> processes) {
        PriorityQueue<Process> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.priority));
        int currentTime = 0;
        List<Process> result = new ArrayList<>();

        for (Process p : processes) {
            if (p.arrivalTime <= currentTime && !p.isCompleted) {
                pq.add(p);
            }
        }

        while (!pq.isEmpty()) {
            Process currentProcess = pq.poll();
            currentProcess.startTime = currentTime;
            currentProcess.completionTime = currentTime + currentProcess.burstTime;
            currentProcess.waitingTime = currentProcess.startTime - currentProcess.arrivalTime;
            currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
            currentProcess.isCompleted = true;
            currentTime += currentProcess.burstTime;
            result.add(currentProcess);
        }
        return result;
    }

    // Method to calculate average waiting and turnaround times
    public void calculateAverageTimes(List<Process> processes) {
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        for (Process p : processes) {
            totalWaitingTime += p.waitingTime;
            totalTurnaroundTime += p.turnaroundTime;
        }

        System.out.println("Average Waiting Time: " + (totalWaitingTime / processes.size()));
        System.out.println("Average Turnaround Time: " + (totalTurnaroundTime / processes.size()));
    }
}
