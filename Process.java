public class Process {
    String name, color;
    private int arrivalTime, burstTime, priority, remainingTime, quantum, waitingTime, turnaroundTime, age, secondBurstTime;
    double FCAIFactor = 0.0;

    public Process(String name, String color, int arrivalTime, int burstTime, int priority,int quantum) {
        this.name = name;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingTime = burstTime;
        this.quantum = quantum;
        this.age = 0;
        this.secondBurstTime = burstTime;
    }

    public void calculateFCAIFactor(double V1, double V2) {
        FCAIFactor = (10 - priority) + (arrivalTime / V1) + (remainingTime / V2);
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSecondBurstTime() {
        return secondBurstTime;
    }

    public void setSecondBurstTime(int secondBurstTime) {
        this.secondBurstTime = secondBurstTime;
    }
}


