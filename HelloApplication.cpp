
package com.example.demojavafx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {

    private TableView<Process> table;
    private ObservableList<Process> processData;
    private Pane ganttChartPane; // Visualization pane for the execution order

    private Label avgWaitingTimeLabel; // Label to display average waiting time
    private Label avgTurnaroundTimeLabel; // Label to display average turnaround time
    int contextSwitch;

    @Override
    public void start(Stage primaryStage) {
        // Initialize process data and TableView
        processData = FXCollections.observableArrayList();
        table = new TableView<>(processData);

        // Define columns for the TableView
        TableColumn<Process, String> colorColumn = new TableColumn<>("Color");
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
        colorColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String color, boolean empty) {
                super.updateItem(color, empty);
                if (empty || color == null) {
                    setGraphic(null);
                } else {
                    Rectangle rectangle = new Rectangle(20, 20);
                    rectangle.setFill(Color.web(color));
                    setGraphic(rectangle);
                }
            }
        });

        TableColumn<Process, String> processIDColumn = new TableColumn<>("Process ID");
        processIDColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Process, Integer> burstTimeColumn = new TableColumn<>("Burst Time");
        burstTimeColumn.setCellValueFactory(new PropertyValueFactory<>("burstTime"));

        TableColumn<Process, Integer> arrivalTimeColumn = new TableColumn<>("Arrival Time");
        arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));

        TableColumn<Process, Integer> priorityColumn = new TableColumn<>("Priority");
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));

        TableColumn<Process, Integer> waitingTimeColumn = new TableColumn<>("Waiting Time");
        waitingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));

        TableColumn<Process, Integer> turnaroundTimeColumn = new TableColumn<>("Turnaround Time");
        turnaroundTimeColumn.setCellValueFactory(new PropertyValueFactory<>("turnaroundTime"));

        // Add columns to the TableView
        table.getColumns().addAll(colorColumn, processIDColumn, burstTimeColumn, arrivalTimeColumn, priorityColumn, waitingTimeColumn, turnaroundTimeColumn);

        // Visualization Pane (Gantt Chart)
        ganttChartPane = new Pane();
        ganttChartPane.setPrefHeight(150);
        ganttChartPane.setStyle("-fx-border-color: black; -fx-border-width: 1;");

        // ComboBox to select scheduling algorithm
        ComboBox<String> algorithmComboBox = new ComboBox<>();
        algorithmComboBox.getItems().addAll("Priority Scheduling", "Shortest Job First (SJF)", "Shortest Remaining Time First (SRTF)");
        algorithmComboBox.setValue("Priority Scheduling");

        // Button to run the selected algorithm
        Button runButton = new Button("Run Scheduling");
        runButton.setOnAction(event -> {
            String selectedAlgorithm = algorithmComboBox.getValue();

            List<Process> executionOrder = new ArrayList<>();
            if (selectedAlgorithm.equals("Priority Scheduling")) {
                PriorityScheduler priorityScheduler = new PriorityScheduler();
                executionOrder = priorityScheduler.simulatePriorityScheduling(processData, contextSwitch); // Context switch time = 1
            } else if (selectedAlgorithm.equals("Shortest Job First (SJF)")) {
                SJFScheduler sjfScheduler = new SJFScheduler();
                executionOrder = sjfScheduler.simulateSJF(processData, contextSwitch); // Context switch time = 1
            } else if (selectedAlgorithm.equals("Shortest Remaining Time First (SRTF)")) {
                SRTFScheduler srtfScheduler = new SRTFScheduler();
                executionOrder = srtfScheduler.simulateSRTF(processData, contextSwitch); // Context switch time = 1
            }

            // Refresh the table to show updated waiting and turnaround times
            table.refresh();

            // Draw Gantt Chart
            drawGanttChart(executionOrder);
            updateAverageTimes();
        });

        // ComboBox for Gantt Chart control
        ComboBox<String> ganttChartComboBox = new ComboBox<>();
        ganttChartComboBox.getItems().addAll("Show Gantt Chart", "Hide Gantt Chart");
        ganttChartComboBox.setValue("Show Gantt Chart");

        // Event handler for Gantt Chart ComboBox
        ganttChartComboBox.setOnAction(event -> {
            String selectedOption = ganttChartComboBox.getValue();
            ganttChartPane.setVisible(selectedOption.equals("Show Gantt Chart"));
        });

        // Input Section
        VBox inputSection = createInputSection();

        // VBox for scheduling algorithm
        VBox algorithmBox = new VBox(10);
        algorithmBox.setStyle("-fx-padding: 10;");
        algorithmBox.getChildren().addAll(ganttChartComboBox, algorithmComboBox, runButton);

        // VBox for Gantt Chart (separate container)
        VBox ganttChartBox = new VBox(10);
        ganttChartBox.setStyle("-fx-padding: 30; -fx-border-color: gray; -fx-border-width: 0;");
        ganttChartBox.getChildren().add(ganttChartPane);

        // Main VBox container to hold everything
        VBox mainContainer = new VBox(10);
        mainContainer.getChildren().addAll(inputSection, algorithmBox, ganttChartBox, table);

        // Set up the Scene and Stage
        Scene scene = new Scene(mainContainer, 1000, 800);
        primaryStage.setTitle("CPU Scheduling Visualizer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createInputSection() {
        VBox inputSection = new VBox(10);
        inputSection.setStyle("-fx-padding: 10; -fx-border-color: #000000; -fx-border-width: 1;");

        TextField nameField = new TextField();
        nameField.setPromptText("Process Name");

        ColorPicker colorField = new ColorPicker();

        TextField arrivalTimeField = new TextField();
        arrivalTimeField.setPromptText("Arrival Time");

        TextField burstTimeField = new TextField();
        burstTimeField.setPromptText("Burst Time");

        TextField priorityField = new TextField();
        priorityField.setPromptText("Priority");

        TextField contextSwitchField = new TextField();
        contextSwitchField.setPromptText("Context");

        Button addProcessButton = new Button("Add Process");
        addProcessButton.setOnAction(e -> {
            try {
                String name = nameField.getText();
                String color = colorField.getValue().toString();
                int arrivalTime = Integer.parseInt(arrivalTimeField.getText());
                int burstTime = Integer.parseInt(burstTimeField.getText());
                int priority = Integer.parseInt(priorityField.getText());
                contextSwitch = Integer.parseInt(contextSwitchField.getText());

                processData.add(new Process(name, color, arrivalTime, burstTime, priority));

                nameField.clear();
                arrivalTimeField.clear();
                burstTimeField.clear();
                priorityField.clear();
                contextSwitchField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter valid numeric values for Arrival Time, Burst Time, and Priority.");
            }
        });

        // Labels for average times
        avgWaitingTimeLabel = new Label("Average Waiting Time: -");
        avgTurnaroundTimeLabel = new Label("Average Turnaround Time: -");

        inputSection.getChildren().addAll(
                new Label("Process Name:"), nameField,
                new Label("Color:"), colorField,
                new Label("Arrival Time:"), arrivalTimeField,
                new Label("Burst Time:"), burstTimeField,
                new Label("Priority:"), priorityField,
                new Label("Context:"), contextSwitchField,
                addProcessButton,
                avgWaitingTimeLabel, avgTurnaroundTimeLabel
        );

        return inputSection;
    }

    private void drawGanttChart(List<Process> executionOrder) {
        ganttChartPane.getChildren().clear(); // Clear previous chart

        double x = 10; // Starting x position
        double y = 10; // Fixed y position
        double height = 50; // Height of each block

        for (Process p : executionOrder) {
            double width = p.getBurstTime() * 20; // Scale burst time for visualization
            Rectangle rect = new Rectangle(x, y, width, height);
            rect.setFill(Color.web(p.getColor()));
            rect.setStroke(Color.BLACK);

            Label label = new Label(p.getName());
            label.setLayoutX(x + width / 2 - 10);
            label.setLayoutY(y + height / 2 - 10);

            ganttChartPane.getChildren().addAll(rect, label);
            x += width + 5; // Move to the next position
        }
    }

    private void updateAverageTimes() {
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;

        for (Process p : processData) {
            totalWaitingTime += p.getWaitingTime();
            totalTurnaroundTime += p.getTurnaroundTime();
        }

        double avgWaitingTime = Math.ceil(totalWaitingTime / processData.size());
        double avgTurnaroundTime = Math.ceil(totalTurnaroundTime / processData.size());

        avgWaitingTimeLabel.setText(String.format("Average Waiting Time: %.2f", avgWaitingTime));
        avgTurnaroundTimeLabel.setText(String.format("Average Turnaround Time: %.2f", avgTurnaroundTime));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
