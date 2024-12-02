import java.util.*;

public class SRTFScheduler{
    private int contextSwitchTime;
    public void simulateSRTF(List<Process> processes,int contextSwitchTime){
        this.contextSwitchTime = contextSwitchTime;
        schedule(processes);
    }

    private void schedule(List<Process> processes) {
        System.out.println("\n=== Shortest Remaining Time First (SRTF) ===");
//        Collections.sort(processes, Comparator.comparingInt(Process::getArrivalTime));
        processes.sort(Comparator.comparingInt((Process p) -> p.getArrivalTime()));

        int currentTime = 0, completed = 0, totalWaitingTime = 0, totalTurnaroundTime = 0;
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(
                Comparator.comparingDouble((Process p) -> p.getRemainingTime() - p.getAge() / 10.0)
                        .thenComparingInt(Process::getArrivalTime));

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
                currentProcess.setAge(0);
            }
            
            else {
                // If not completed, re-add the process to the queue
                readyQueue.add(currentProcess);


            }
            for (Process p : readyQueue) {
                p.setAge(p.getAge()+1);
            }
        }

        System.out.println("Average Waiting Time = " + (double) totalWaitingTime / processes.size());
        System.out.println("Average Turnaround Time = " + (double) totalTurnaroundTime / processes.size());
    }
}
