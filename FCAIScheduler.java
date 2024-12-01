import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class FCAIScheduler {
    private int contextSwitchTime;
    private int quantumTime;

    public void simulateFCAIScheduling(List<Process> processes, int quantumTime, int contextSwitchTime){
        this.contextSwitchTime = contextSwitchTime;
        this.quantumTime = quantumTime;
        schedule(processes);
    }
    private void schedule(List<Process> processes){
        System.out.println("\n=== FCAI Scheduling ===");
        int currentTime = 0, completed = 0, totalWaitingTime = 0, totalTurnaroundTime = 0;

        double v1 = (double) processes.stream().mapToInt(p -> p.getArrivalTime()).max().orElse(1) / 10;
        double v2 = (double) processes.stream().mapToInt(p -> p.getBurstTime()).max().orElse(1) / 10;

        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingDouble(
                p -> (10 - p.getPriority()) + (p.getArrivalTime() / v1) + (p.getRemainingTime() / v2)));

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
            int executionTime = Math.min((int) Math.ceil(quantumTime * 0.4), currentProcess.getRemainingTime());
            currentTime += executionTime;
            currentProcess.setRemainingTime(currentProcess.getRemainingTime() - executionTime);

            if (currentProcess.getRemainingTime() == 0) {
                completed++;
                currentProcess.setTurnaroundTime(currentTime - currentProcess.getArrivalTime());
                currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());
                totalWaitingTime += currentProcess.getWaitingTime();
                totalTurnaroundTime += currentProcess.getTurnaroundTime();

                System.out.println("Process " + currentProcess.name + ": Waiting Time = " + currentProcess.getWaitingTime()
                        + ", Turnaround Time = " + currentProcess.getTurnaroundTime());

                currentTime += contextSwitchTime;
            } else {
                readyQueue.add(currentProcess);
            }
        }

        System.out.println("Average Waiting Time = " + (double) totalWaitingTime / processes.size());
        System.out.println("Average Turnaround Time = " + (double) totalTurnaroundTime / processes.size());
    }
}
