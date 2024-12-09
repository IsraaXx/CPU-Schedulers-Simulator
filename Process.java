package com.example.demojavafx;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Process {
    public SimpleStringProperty name, color;
    public SimpleIntegerProperty arrivalTime, burstTime, priority, remainingTime, quantum, waitingTime, turnaroundTime, age, secondBurstTime;

    public double FCAIFactor = 0.0;
    int ExecutionAmount ;

    public Process(String name, String color, int arrivalTime, int burstTime, int priority, int quantum) {
        this.name = new SimpleStringProperty(name);
        this.color = new SimpleStringProperty(color);
        this.arrivalTime = new SimpleIntegerProperty(arrivalTime);
        this.burstTime = new SimpleIntegerProperty(burstTime);
        this.priority = new SimpleIntegerProperty(priority);
        this.remainingTime = new SimpleIntegerProperty(burstTime);
        this.age = new SimpleIntegerProperty(0);
        this.secondBurstTime = new SimpleIntegerProperty(burstTime);
        // Initialize the missing SimpleIntegerProperty fields
        this.waitingTime = new SimpleIntegerProperty(0); // Initialize with default value 0
        this.turnaroundTime = new SimpleIntegerProperty(0);
        this.quantum = new SimpleIntegerProperty(quantum);
        this.ExecutionAmount = 0;// Initialize with default value 0
    }

    // Getters and Setters using the new property types

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getColor() {
        return color.get();
    }

    public void setColor(String color) {
        this.color.set(color);
    }

    public int getArrivalTime() {
        return arrivalTime.get();
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime.set(arrivalTime);
    }

    public int getBurstTime() {
        return burstTime.get();
    }

    public void setBurstTime(int burstTime) {
        this.burstTime.set(burstTime);
    }

    public int getPriority() {
        return priority.get();
    }

    public void setPriority(int priority) {
        this.priority.set(priority);
    }

    public int getRemainingTime() {
        return remainingTime.get();
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime.set(remainingTime);
    }

    public int getQuantum() {
        return quantum.get();
    }

    public void setQuantum(int quantum) {
        this.quantum.set(quantum);
    }

    public int getWaitingTime() {
        return waitingTime.get();
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime.set(waitingTime);
    }

    public int getTurnaroundTime() {
        return turnaroundTime.get();
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime.set(turnaroundTime);
    }

    public int getAge() {
        return age.get();
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public int getSecondBurstTime() {
        return secondBurstTime.get();
    }

    public void setSecondBurstTime(int secondBurstTime) {
        this.secondBurstTime.set(secondBurstTime);
    }
}
