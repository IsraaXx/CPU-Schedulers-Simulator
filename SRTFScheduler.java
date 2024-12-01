import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class SRTFScheduler{
    private int contextSwitchTime;
    public void simulateSRTF(List<Process> processes,int contextSwitchTime){
        this.contextSwitchTime = contextSwitchTime;
        schedule(processes);
    }

    private void schedule(List<Process> processes) {
        System.out.println("\n=== Shortest Remaining Time First (SRTF) ===");
        int currentTime = 0, completed = 0, totalWaitingTime = 0, totalTurnaroundTime = 0;
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingInt(p -> p.getRemainingTime()));

        while (completed < processes.size()) {
            for (Process p : processes) {
                if (p.getArrivalTime() <= currentTime && p.getRemainingTime() > 0 && !readyQueue.contains(p)) {
                    readyQueue.add(p);
                }
            }

            if (readyQueue.isEmpty()) {
                currentTime++;
                continue;
            }

            Process currentProcess = readyQueue.poll();
            currentTime++;
            int currentProcessRemainingTime = currentProcess.getRemainingTime();
            currentProcess.setRemainingTime(currentProcessRemainingTime-1);

            if (currentProcess.getRemainingTime() == 0) {
                completed++;
                currentProcess.setTurnaroundTime(currentTime - currentProcess.getArrivalTime());
                currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());
                totalWaitingTime += currentProcess.getWaitingTime();
                totalTurnaroundTime += currentProcess.getTurnaroundTime();

                System.out.println("Process " + currentProcess.name + ": Waiting Time = " + currentProcess.getWaitingTime()
                        + ", Turnaround Time = " + currentProcess.getTurnaroundTime());

                currentTime += contextSwitchTime;
            }
        }

        System.out.println("Average Waiting Time = " + (double) totalWaitingTime / processes.size());
        System.out.println("Average Turnaround Time = " + (double) totalTurnaroundTime / processes.size());
    }
}
