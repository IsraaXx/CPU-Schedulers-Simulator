import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class FCAIScheduler {
    private List<Process> processes;
   // private List<String> executionOrder;
   
    public FCAIScheduler(List<Process> processes){
        this.processes = processes;
    //    this.executionOrder = new ArrayList<>();
    }
    public void simulateFCAIScheduling(List<Process> processes) {
        processes.sort(Comparator.comparingInt(Process::getArrivalTime)); // Sort by arrival time
        System.out.println("\n=== FCAI Scheduling ===");
        int currentTime = 0, completed = 0, totalWaitingTime = 0, totalTurnaroundTime = 0;

        double v1 = (double) processes.stream().mapToInt(Process::getArrivalTime).max().orElse(1) / 10;
        double v2 = (double) processes.stream().mapToInt(Process::getBurstTime).max().orElse(1) / 10;

         PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingDouble(p->p.FCAIFactor));
        int curr = 0;
        while (!readyQueue.isEmpty() || curr < processes.size()) {
            while (curr < processes.size() && processes.get(curr).getArrivalTime() <= currentTime) {
                    Process p = processes.get(curr);
                    p.calculateFCAIFactor(v1,v2);
                    readyQueue.add(p);
                    curr++;
                }

                if (readyQueue.isEmpty()) {
                    if (curr < processes.size()) {
                        currentTime = processes.get(curr).getArrivalTime();
                    } else {
                        break; // Exit if no more processes remain
                    }
                    continue;
                }

            //final int current = currentTime;
            Process currentProcess = readyQueue.poll();
            int quantum = currentProcess.getQuantum();
            int executionTime = (int) Math.ceil(quantum * 0.4);
            int startTime = currentTime;
            int endTime;
            if (currentProcess.getRemainingTime() <= executionTime) {
                // process finished at once , finished it's burst time and it's done
                currentTime+= currentProcess.getRemainingTime();
                endTime = currentTime;
                completed++;
              //  executionOrder.add(currentProcess.name);
                currentProcess.setTurnaroundTime(currentTime - currentProcess.getArrivalTime());
                currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());
                //completed++;
                currentProcess.setRemainingTime(0);
            }
            else {
                // only done 40% and still has remaining time , does not finish it's burst time yet
                // update quantum as well , check for the unused and add it to quantum
                // or add +2 if quantum is finished and also calc FCAI Factor
                // add this process at the end of readyqueue
                currentTime+= executionTime;
                endTime = currentTime;
                currentProcess.setRemainingTime(currentProcess.getRemainingTime()-executionTime);
               int quantumUnused = currentProcess.getQuantum()- executionTime;
               if(currentProcess.getRemainingTime()>0){
               if(quantumUnused>0){
                   currentProcess.setQuantum(currentProcess.getQuantum()+quantumUnused);}
                else {
                currentProcess.setQuantum(currentProcess.getQuantum()+2); }

                currentProcess.calculateFCAIFactor(v1, v2);
                 readyQueue.add(currentProcess);
            }
        }
        System.out.println("Process " + currentProcess.name + " executed from " + startTime + " to " + endTime);

        //executionOrder.add(currentProcess.name);
                       
                  
           
        }
        PrintProcesses();
    }
    public void PrintProcesses(){

        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;
        for(Process p : processes){
            totalTurnaroundTime+= p.getTurnaroundTime();
            totalWaitingTime+=p.getWaitingTime();
            System.err.println(p.name+": waiting time = "+ p.getWaitingTime() +" , Trunaround time = "+ p.getTurnaroundTime());
      
    }
    int avgWait = (int) Math.ceil((double) totalWaitingTime / processes.size());
    int avgTurn = (int) Math.ceil((double) totalTurnaroundTime / processes.size());
    System.out.println("Average Waiting Time = " + avgWait);
        System.out.println("Average Turnaround Time = " + avgTurn);

    }
}
