import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class SJFScheduler {
    private int contextSwitchTime;

    public void simulateSJF(List<Process> processes, int contextSwitchTime) {
        this.contextSwitchTime = contextSwitchTime;
        schedule(processes);
    }


    private void schedule(List<Process> processes) {
        System.out.println("\n=== Non-Preemptive Shortest Job First (SJF) ===");
//        processes.sort(Comparator.comparingInt((Process p) -> p.getArrivalTime()).thenComparingInt(p -> p.getBurstTime()));
//        Collections.sort(processes, Comparator.comparingInt(Process::getArrivalTime));
        processes.sort(Comparator.comparingInt((Process p) -> p.getArrivalTime()));

        int currentTime = 0, completed = 0, totalWaitingTime = 0, totalTurnaroundTime = 0;
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(
                Comparator.comparingDouble((Process p) -> p.getSecondBurstTime() - p.getAge() / 10.0)
                        .thenComparingInt(Process::getArrivalTime));

        while (completed < processes.size()) {
            // Add processes to the queue based on their arrival time
            for (Process p : processes) {
                if (p.getArrivalTime() <= currentTime && p.getSecondBurstTime() > 0 && !readyQueue.contains(p)) {
                    readyQueue.add(p);
                }
            }
            // If no process is ready, increment time
            if (readyQueue.isEmpty()) {
                currentTime++;
                continue;
            }

            Process currentProcess = readyQueue.poll();
            // Check if a context switch is needed
            // Simulate the execution of the selected process
            int waitingTime = Math.max(0, currentTime - currentProcess.getArrivalTime());
            currentProcess.setWaitingTime(waitingTime);
            currentProcess.setTurnaroundTime(waitingTime + currentProcess.getSecondBurstTime());

            totalWaitingTime += waitingTime;
            totalTurnaroundTime += currentProcess.getTurnaroundTime();

            System.out.println("Process " + currentProcess.name + ": Waiting Time = " + waitingTime
                    + ", Turnaround Time = " + currentProcess.getTurnaroundTime());

            currentTime += currentProcess.getSecondBurstTime();
            currentProcess.setSecondBurstTime(0);
            completed++;
            if (completed < processes.size()) {
                currentTime += contextSwitchTime;
            }
//            simulateNonPreemptive(processes, contextSwitchTime);
            currentProcess.setAge(0);
            for (Process p : readyQueue) {
                p.setAge(p.getAge() + 1);
            }
        }

        System.out.println("Average Waiting Time = " + (double) totalWaitingTime / processes.size());
        System.out.println("Average Turnaround Time = " + (double) totalTurnaroundTime / processes.size());

    }
}