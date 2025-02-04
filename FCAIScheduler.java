package com.example.demojavafx;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class FCAIScheduler {
    private List<Process> processes;
    private List<Process> FinishTime;

    public FCAIScheduler(List<Process> processes){
        this.processes = processes;
        this.FinishTime = new ArrayList<>();
      
    }

    public List<Process> simulateFCAIScheduling() {
        // processes.sort(Comparator.comparingInt(Process::getArrivalTime)); // Sort by arrival time
        System.out.println("\n=== FCAI Scheduling ===");
        double v1 = (double) processes.stream().mapToInt(Process::getArrivalTime).max().orElse(1) / 10;
        double v2 = (double) processes.stream().mapToInt(Process::getBurstTime).max().orElse(1) / 10;
        Process p = processes.remove(0);
        int currentTime = p.getArrivalTime();
        List<Process> executionOrder = new ArrayList<>();


        boolean pExec = false;

        //  PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingDouble(p->p.FCAIFactor));
        // first process will work 40% of the quantum
        while (!processes.isEmpty()||pExec) {
            if(p.ExecutionAmount == 0){
                double workQuantum = (double)p.getQuantum();
                double Quper40 = workQuantum*(40.0 / 100.0);
                int exeTime = Math.min((int)Math.ceil(Quper40),p.getRemainingTime());
                currentTime+=exeTime;
                p.ExecutionAmount = exeTime;
                int remtime = p.getRemainingTime();
                remtime-=exeTime;
                p.setRemainingTime(remtime);

                executionOrder.add(p);
            }

            else{
                // if the 40% of the quantum = the burst time needed by process
                if((p.ExecutionAmount==p.getQuantum()&&p.getRemainingTime()<=0) || p.getRemainingTime()<=0){
                    p.setTurnaroundTime(currentTime - p.getArrivalTime());
                    p.setWaitingTime(p.getTurnaroundTime() - p.getBurstTime());
                    FinishTime.add(p);
                    System.out.println(p.name + " executes for " + p.ExecutionAmount + " units of time");
                    System.out.println(p.name + " completed");

                    if(!processes.isEmpty()){
                        p = processes.remove(0);
                        pExec = true;
                        continue;  }

                    else {
                        PrintProcesses();
                        System.out.println("\nExecution Order:");
                        for (Process executedProcess : executionOrder) {
                            System.out.println(executedProcess.name);
                        }
                        return executionOrder;
                    }
                }

                else if(p.ExecutionAmount==p.getQuantum()){  // 2!=4

                    int oldQ= p.getQuantum();
                    int newQ = oldQ+2;
                    System.out.println(p.name + " executes for " + p.ExecutionAmount + " units of time");
                    System.out.println("Remaining time is " + p.getRemainingTime());
                    // System.out.println("Current time is " + currentTime);
                    System.out.println("Process " + p.name + ": Quantum updated from " + oldQ + " to " + newQ);
                    System.out.println("=====================================================================");

                    p.setQuantum(newQ);
                    double  FCAIFactor = (10 - p.getPriority()) + ((double)p.getArrivalTime() / v1) + ((double)p.getRemainingTime() / v2);
                    p.FCAIFactor= (int) Math.ceil(FCAIFactor);
                    p.ExecutionAmount = 0;
                    Process curr = p;

                    if(!processes.isEmpty()){
                        p = processes.remove(0);
                        boolean Readd = false;
                        for(int i = 0; i<processes.size();i++){
                            if(processes.get(i).getArrivalTime()>currentTime){
                                processes.add(i, curr);
                                Readd = true;
                                break;
                            }
                        }
                        if(!Readd){
                            processes.add(curr);
                        }
                        continue;
                    }
                }


                p.ExecutionAmount++;
                int remTime = p.getRemainingTime();
                --remTime;
                p.setRemainingTime(remTime);
                ++currentTime;

                executionOrder.add(p);
            }

            boolean flag = true;
            while (flag && !processes.isEmpty()) {
                boolean flag2 = false;
                Process turn = null;
                int current = -1;
                for(int i = 0; i<processes.size();i++){ // take each process to check factor
                    Process one = processes.get(i);
                    if(one.FCAIFactor<p.FCAIFactor&&one.getArrivalTime()<=currentTime){
                        flag = false;
                        if(!flag2){
                            current  = i;
                            flag2=true;
                            turn = one; }
                        else{
                            if(one.FCAIFactor< turn.FCAIFactor){
                                current = i;
                                turn = one;
                            }
                            else if(one.FCAIFactor==turn.FCAIFactor&&one.getArrivalTime()<turn.getArrivalTime()){
                                turn = one;
                                current = i;
                            }
                        }
                    }
                }
                if(!flag){
                    int quantumUnused = p.getQuantum();
                    int unusedQ = quantumUnused-p.ExecutionAmount;
                    int newq = quantumUnused+unusedQ;
                    System.out.println(p.name + " executes for " + p.ExecutionAmount + " units of time");
                    System.out.println("Remaining time is " + p.getRemainingTime());
                    //System.out.println("Current time is " + currentTime);
                    System.out.println("Process " + p.name + ": Quantum updated from " + quantumUnused + " to " + newq);
                    System.out.println("=====================================================================");
                    p.setQuantum(newq);

                    double  FCAIFactor = (10 - p.getPriority()) + ((double)p.getArrivalTime() / v1) + ((double)p.getRemainingTime() / v2);
                    p.FCAIFactor= (int) Math.ceil(FCAIFactor);
                    p.ExecutionAmount = 0;
                    Process x = p;
                    p = processes.remove(current);
                    boolean readd = false;
                    for (int i = 0; i < processes.size(); i++) {
                        if (processes.get(i).getArrivalTime() > currentTime) {
                            processes.add(i, x);
                            readd = true;
                            break;
                        }
                    }
                    if(!readd){
                        processes.add(x);
                    }
                }
                else{
                    flag=false;  }

            }
        }
        System.out.println("\nExecution Order:");
        for (Process executedProcess : executionOrder) {
            System.out.println(executedProcess.name);
        }
        return executionOrder;
    }

    public void PrintProcesses(){
        System.out.println("\n");
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;
        for(Process val : FinishTime){
            System.out.println(val.name+" Trunaround Time is: "+val.getTurnaroundTime()+" and Waiting Time is: "+val.getWaitingTime());
            totalWaitingTime+=val.getWaitingTime();
            totalTurnaroundTime+=val.getTurnaroundTime();
        }
        int avgWait = (int) Math.ceil((double) totalWaitingTime / FinishTime.size());
        int avgTurn = (int) Math.ceil((double) totalTurnaroundTime / FinishTime.size());
        System.out.println("Average Waiting Time = " + avgWait);
        System.out.println("Average Turnaround Time = " + avgTurn);

    }
}
