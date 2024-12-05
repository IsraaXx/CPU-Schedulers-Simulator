import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CPUSchedulersSimulator {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Process> processes = new ArrayList<>();

        // Input
        System.out.println("Welcome To CPU Schedulers Simulator ");
        System.out.println("===================================");
        System.out.println("Enter number of processes: ");
        int n = sc.nextInt();
        // System.out.println("Enter Round Robin Quantum Time: ");
        // int quantumTime = sc.nextInt();
        System.out.println("Enter Context Switching Time: ");
        int contextSwitchTime = sc.nextInt();


        // Execute all scheduling algorithms
//        simulatePriorityScheduling(processes, contextSwitchTime);
//        simulateSJF(processes, contextSwitchTime);
//        simulateSRTF(processes, contextSwitchTime);
//        simulateFCAIScheduling(processes, quantumTime, contextSwitchTime);

        PrioirtyScheduler priorityScheduler = new PrioirtyScheduler();
        SJFScheduler sjfScheduler = new SJFScheduler();
        FCAIScheduler fc = new FCAIScheduler(processes);
        SRTFScheduler srtfScheduler = new SRTFScheduler();
        while (true) {
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
                for (int i = 0; i < n; i++) {
                    System.out.println("Enter details for Process " + (i + 1) + ":");
                    System.out.print("Name: ");
                    String name = sc.next();
                    System.out.print("Arrival Time: ");
                    int arrivalTime = sc.nextInt();
                    System.out.print("Burst Time: ");
                    int burstTime = sc.nextInt();
                    System.out.print("Priority: ");
                    int priority = sc.nextInt();
                    processes.add(new Process(name, "", arrivalTime, burstTime, priority,0));
                }
                    System.out.println("\nRunning Priority Scheduling...");
                    priorityScheduler.simulatePriorityScheduling(processes,contextSwitchTime);
                    break;

                case 2:
                for (int i = 0; i < n; i++) {
                    System.out.println("Enter details for Process " + (i + 1) + ":");
                    System.out.print("Name: ");
                    String name = sc.next();
                    System.out.print("Arrival Time: ");
                    int arrivalTime = sc.nextInt();
                    System.out.print("Burst Time: ");
                    int burstTime = sc.nextInt();
                    System.out.print("Priority: ");
                    int priority = sc.nextInt();
                    processes.add(new Process(name, "", arrivalTime, burstTime, priority,0));
                }
                    System.out.println("\nRunning SJF Scheduling...");
                    sjfScheduler.simulateSJF(processes, contextSwitchTime);
                    break;

                case 3:
                for (int i = 0; i < n; i++) {
                    System.out.println("Enter details for Process " + (i + 1) + ":");
                    System.out.print("Name: ");
                    String name = sc.next();
                    System.out.print("Arrival Time: ");
                    int arrivalTime = sc.nextInt();
                    System.out.print("Burst Time: ");
                    int burstTime = sc.nextInt();
                    System.out.print("Priority: ");
                    int priority = sc.nextInt();
                    System.out.println("Enter Round Robin Quantum Time: ");
                   int  quantumTime = sc.nextInt();
                    processes.add(new Process(name, "", arrivalTime, burstTime, priority,quantumTime));
                }
                    System.out.println("\nRunning FCAI Scheduling...");
                    fc.simulateFCAIScheduling(processes);
                    break;

                case 4:
                for (int i = 0; i < n; i++) {
                    System.out.println("Enter details for Process " + (i + 1) + ":");
                    System.out.print("Name: ");
                    String name = sc.next();
                    System.out.print("Arrival Time: ");
                    int arrivalTime = sc.nextInt();
                    System.out.print("Burst Time: ");
                    int burstTime = sc.nextInt();
                    System.out.print("Priority: ");
                    int priority = sc.nextInt();
                    processes.add(new Process(name, "", arrivalTime, burstTime, priority,0));
                }
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
        }
    
        }
           
    }

    // Non-preemptive Priority Scheduling
//    static void simulatePriorityScheduling(List<Process> processes, int contextSwitchTime) {
//        System.out.println("\n=== Non-Preemptive Priority Scheduling ===");
//        processes.sort(Comparator.comparingInt((Process p) -> p.priority).thenComparingInt(p -> p.arrivalTime));
//        simulateNonPreemptive(processes, contextSwitchTime);
//    }

    // Non-preemptive Shortest Job First (SJF)
//    static void simulateSJF(List<Process> processes, int contextSwitchTime) {
//        System.out.println("\n=== Non-Preemptive Shortest Job First (SJF) ===");
//        processes.sort(Comparator.comparingInt((Process p) -> p.burstTime).thenComparingInt(p -> p.arrivalTime));
//        simulateNonPreemptive(processes, contextSwitchTime);
//    }

    // Helper for Non-Preemptive Schedulers
//    static void simulateNonPreemptive(List<Process> processes, int contextSwitchTime) {
//        int currentTime = 0, totalWaitingTime = 0, totalTurnaroundTime = 0;
//
//        for (Process p : processes) {
//            int waitingTime = Math.max(0, currentTime - p.arrivalTime);
//            p.waitingTime = waitingTime;
//            p.turnaroundTime = waitingTime + p.burstTime;
//            totalWaitingTime += waitingTime;
//            totalTurnaroundTime += p.turnaroundTime;
//
//            System.out.println("Process " + p.name + ": Waiting Time = " + waitingTime
//                    + ", Turnaround Time = " + p.turnaroundTime);
//
//            currentTime += p.burstTime + contextSwitchTime;
//        }
//
//        System.out.println("Average Waiting Time = " + (double) totalWaitingTime / processes.size());
//        System.out.println("Average Turnaround Time = " + (double) totalTurnaroundTime / processes.size());
//    }

    // Shortest Remaining Time First (SRTF)
//    static void simulateSRTF(List<Process> processes, int contextSwitchTime) {
//        System.out.println("\n=== Shortest Remaining Time First (SRTF) ===");
//        int currentTime = 0, completed = 0, totalWaitingTime = 0, totalTurnaroundTime = 0;
//        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingInt(p -> p.remainingTime));
//
//        while (completed < processes.size()) {
//            for (Process p : processes) {
//                if (p.arrivalTime <= currentTime && p.remainingTime > 0 && !readyQueue.contains(p)) {
//                    readyQueue.add(p);
//                }
//            }
//
//            if (readyQueue.isEmpty()) {
//                currentTime++;
//                continue;
//            }
//
//            Process currentProcess = readyQueue.poll();
//            currentTime++;
//            currentProcess.remainingTime--;
//
//            if (currentProcess.remainingTime == 0) {
//                completed++;
//                currentProcess.turnaroundTime = currentTime - currentProcess.arrivalTime;
//                currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
//                totalWaitingTime += currentProcess.waitingTime;
//                totalTurnaroundTime += currentProcess.turnaroundTime;
//
//                System.out.println("Process " + currentProcess.name + ": Waiting Time = " + currentProcess.waitingTime
//                        + ", Turnaround Time = " + currentProcess.turnaroundTime);
//
//                currentTime += contextSwitchTime;
//            }
//        }
//
//        System.out.println("Average Waiting Time = " + (double) totalWaitingTime / processes.size());
//        System.out.println("Average Turnaround Time = " + (double) totalTurnaroundTime / processes.size());
//    }

    // FCAI Scheduling
//    static void simulateFCAIScheduling(List<Process> processes, int quantumTime, int contextSwitchTime) {
//        System.out.println("\n=== FCAI Scheduling ===");
//        int currentTime = 0, completed = 0, totalWaitingTime = 0, totalTurnaroundTime = 0;
//
//        double v1 = (double) processes.stream().mapToInt(p -> p.getArrivalTime()).max().orElse(1) / 10;
//        double v2 = (double) processes.stream().mapToInt(p -> p.getBurstTime()).max().orElse(1) / 10;
//
//        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingDouble(
//                p -> (10 - p.getPriority()) + (p.getArrivalTime() / v1) + (p.getRemainingTime() / v2)));
//
//        while (completed < processes.size()) {
//            for (Process p : processes) {
//                if (p.getArrivalTime() <= currentTime && p.getRemainingTime() > 0 && !readyQueue.contains(p)) {
//                    readyQueue.add(p);
//                }
//            }
//
//            if (readyQueue.isEmpty()) {
//                currentTime++;
//                continue;
//            }
//
//            Process currentProcess = readyQueue.poll();
//            int executionTime = Math.min((int) Math.ceil(quantumTime * 0.4), currentProcess.getRemainingTime());
//            currentTime += executionTime;
//            currentProcess.setRemainingTime(currentProcess.getRemainingTime() - executionTime);
//
//            if (currentProcess.getRemainingTime() == 0) {
//                completed++;
//                currentProcess.setTurnaroundTime(currentTime - currentProcess.getArrivalTime());
//                currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());
//                totalWaitingTime += currentProcess.getWaitingTime();
//                totalTurnaroundTime += currentProcess.getTurnaroundTime();
//
//                System.out.println("Process " + currentProcess.name + ": Waiting Time = " + currentProcess.getWaitingTime()
//                        + ", Turnaround Time = " + currentProcess.getTurnaroundTime());
//
//                currentTime += contextSwitchTime;
//            } else {
//                readyQueue.add(currentProcess);
//            }
//        }
//
//        System.out.println("Average Waiting Time = " + (double) totalWaitingTime / processes.size());
//        System.out.println("Average Turnaround Time = " + (double) totalTurnaroundTime / processes.size());
//    }
