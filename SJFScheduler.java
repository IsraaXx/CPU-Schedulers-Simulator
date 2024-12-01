import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class SJFScheduler{
    private int contextSwitchTime;
    public void simulateSJF(List<Process> processes, int contextSwitchTime) {
        this.contextSwitchTime = contextSwitchTime;
        schedule(processes);
    }


    private void schedule(List<Process> processes) {
        System.out.println("\n=== Non-Preemptive Shortest Job First (SJF) ===");
        processes.sort(Comparator.comparingInt((Process p) -> p.getBurstTime()).thenComparingInt(p -> p.getArrivalTime()));
        simulateNonPreemptive(processes, contextSwitchTime);
    }
    void simulateNonPreemptive(List<Process> processes, int contextSwitchTime) {
        int currentTime = 0, totalWaitingTime = 0, totalTurnaroundTime = 0;

        for (Process p : processes) {
            int waitingTime = Math.max(0, currentTime - p.getArrivalTime());
            p.setWaitingTime(waitingTime);
            p.setTurnaroundTime(waitingTime + p.getBurstTime());
            totalWaitingTime += waitingTime;
            totalTurnaroundTime += p.getTurnaroundTime();

            System.out.println("Process " + p.name + ": Waiting Time = " + waitingTime
                    + ", Turnaround Time = " + p.getTurnaroundTime());

            currentTime += p.getBurstTime() + contextSwitchTime;
        }

        System.out.println("Average Waiting Time = " + (double) totalWaitingTime / processes.size());
        System.out.println("Average Turnaround Time = " + (double) totalTurnaroundTime / processes.size());
    }
}
