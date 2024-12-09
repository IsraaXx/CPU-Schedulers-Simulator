package com.example.demojavafx;

import java.util.*;
import java.util.List;

public class CPUSchedulersSimulator {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Process> processes = new ArrayList<>();

        // Input
        System.out.println("Enter number of processes: ");
        int n = sc.nextInt();
//        System.out.println("Enter Round Robin Quantum Time: ");
//        int quantumTime = sc.nextInt();
        System.out.println("Enter Context Switching Time: ");
        int contextSwitchTime = sc.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.println("Enter details for Process " + (i + 1) + ":");
            System.out.print("Name: ");
            String name = sc.next();
            System.out.print("Color: ");
            String color = sc.next();
            System.out.print("Arrival Time: ");
            int arrivalTime = sc.nextInt();
            System.out.print("Burst Time: ");
            int burstTime = sc.nextInt();
            System.out.print("Priority: ");
            int priority = sc.nextInt();
            processes.add(new Process(name, "", arrivalTime, burstTime, priority,0));
        }
        PriorityScheduler priorityScheduler = new PriorityScheduler();
        SJFScheduler sjfScheduler = new SJFScheduler();
        SRTFScheduler srtfScheduler = new SRTFScheduler();
        // Display the menu
        System.out.println("\n=== Scheduler Menu ===");
        System.out.println("1. Priority Scheduling");
        System.out.println("2. SJF Scheduling");
        System.out.println("3. FCAI Scheduling");
        System.out.println("4. SRTF Scheduling");
        System.out.println("5. Exit");
        // Get user choice
        System.out.println("Enter type of schedule you want: ");
        int type = sc.nextInt();

        // Execute corresponding scheduling algorithm
        switch (type) {
            case 1:
                System.out.println("\nRunning Priority Scheduling...");
                priorityScheduler.simulatePriorityScheduling(processes,contextSwitchTime);
                break;

            case 2:

                System.out.println("\nRunning SJF Scheduling...");
                sjfScheduler.simulateSJF(processes, contextSwitchTime);
                break;

            case 3:
                for (Process process: processes){
                    System.out.println("Enter the quantum time for process "+process.getName()+".");
                    int quantumTime = sc.nextInt();
                    process.setQuantum(quantumTime);
                }

                double v1 = (double) processes.stream().mapToInt(Process::getArrivalTime).max().orElse(1) / 10;
                double v2 = (double) processes.stream().mapToInt(Process::getBurstTime).max().orElse(1) / 10;
                for(Process p : processes){
                    double  FCAIFactor = (10 - p.getPriority()) + ((double)p.getArrivalTime() / v1) + ((double)p.getRemainingTime() / v2);
                    p.FCAIFactor= (int) Math.ceil(FCAIFactor);
                }
                FCAIScheduler fc = new FCAIScheduler(processes);
                System.out.println("\nRunning FCAI Scheduling...");
                fc.simulateFCAIScheduling();
                break;

            case 4:

                System.out.println("\nRunning SRTF Scheduling...");
                srtfScheduler.simulateSRTF(processes,contextSwitchTime);
                break;

            case 5:
                System.out.println("Exiting...");
                sc.close();
                System.exit(0);

            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                break;
        }

        sc.close();
    }


}



