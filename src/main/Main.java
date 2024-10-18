package src.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import src.model.Process;
import src.service.SchedulingService;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SchedulingService scheduler = new SchedulingService();
        List<Process> processes = new ArrayList<>();

        System.out.println("Enter the number of processes: ");
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.println("Enter details for Process " + (i + 1) + ": ");
            System.out.print("Arrival Time: ");
            int arrival = sc.nextInt();
            System.out.print("Burst Time: ");
            int burst = sc.nextInt();
            System.out.print("Priority: ");
            int priority = sc.nextInt();

            processes.add(new Process(i + 1, arrival, burst, priority));
        }

        System.out.println("\nSelect Scheduling Algorithm:");
        System.out.println("1. First Come First Serve (FCFS)");
        System.out.println("2. Shortest Job First (SJF)");
        System.out.println("3. Round Robin (RR)");
        System.out.println("4. Priority Scheduling");

        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                processes = scheduler.fcfs(processes);
                break;
            case 2:
                processes = scheduler.sjf(processes);
                break;
            case 3:
                System.out.println("Enter Time Quantum for Round Robin: ");
                int quantum = sc.nextInt();
                processes = scheduler.roundRobin(processes, quantum);
                break;
            case 4:
                processes = scheduler.priorityScheduling(processes);
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }

        System.out.println("\nProcess Execution Results:");
        for (Process p : processes) {
            System.out.println("Process " + p.id + " | Waiting Time: " + p.waitingTime + " | Turnaround Time: " + p.turnaroundTime);
        }

        scheduler.calculateAverageTimes(processes);
        sc.close();
    }
}