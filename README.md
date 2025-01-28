# CPU Scheduling Simulator

This project simulates various CPU scheduling algorithms to explore their impact on system performance and resource utilization. Implemented in Java, it includes both traditional and innovative approaches to scheduling, with detailed outputs for analysis.

---

## **Features**
- **Non-Preemptive Priority Scheduling:** Simulates scheduling based on process priority with context switching.
- **Non-Preemptive Shortest Job First (SJF):** Prevents starvation and schedules the shortest job first.
- **Shortest Remaining Time First (SRTF):** Handles dynamic process execution with starvation prevention.
- **FCAI Scheduling Algorithm:**  
  - Combines priority, arrival time, and remaining burst time into a dynamic scheduling factor.  
  - Dynamically adjusts quantum values to improve efficiency and fairness.  
  - Includes both preemptive and non-preemptive execution phases.

---

## **Input Parameters**
The program accepts the following inputs:
- Number of processes.
- Round Robin Time Quantum (for FCAI Scheduling).
- Context Switching Time.
- For each process:
  - **Process Name**: Identifier for the process.
  - **Process Color**: Graphical representation.
  - **Arrival Time**: Time when the process enters the system.
  - **Burst Time**: Total execution time required.
  - **Priority Number**: Process priority.

---

## **Output Details**
The simulation generates the following outputs for each scheduling algorithm:
1. **Execution Order**: Sequence in which processes are executed.
2. **Waiting Time**: Time spent waiting in the ready queue.
3. **Turnaround Time**: Total time from arrival to completion.
4. **Average Times**: Average waiting and turnaround times across all processes.
5. **Quantum History Updates (FCAI Scheduling)**: Detailed updates for each process's quantum value over time.

---

## **Technologies Used**
- **Java**: Core programming language.
- **Math Functions**: For dynamic calculations like `ceil()`.
- **Threading**: Simulates concurrent process execution.

---

## **Key Concepts**
- **CPU Scheduling**: Allocation of CPU time to processes to maximize performance.
- **Starvation Prevention**: Ensures fairness in process execution.
- **Dynamic Quantum Adjustment**: Optimizes quantum values to balance responsiveness and efficiency.
- **Thread Synchronization**: Manages process execution without conflicts.

---
