package application;

import application.model.Employee;
import application.model.Specialization;
import application.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.*;

public class MainController {
    @FXML
    public ComboBox<Employee> comboBox;
    public ListView<Employee> programmersField;
    public ListView<Employee> analystsField;
    public ListView<Employee> testersField;
    public ListView<Employee> PMField;
    public MenuBar menuBar;

    private void clearListViews(){
        programmersField.setItems(FXCollections.observableList(new ArrayList<>()));
        testersField.setItems(FXCollections.observableList(new ArrayList<>()));
        analystsField.setItems(FXCollections.observableList(new ArrayList<>()));
        PMField.setItems(FXCollections.observableList(new ArrayList<>()));
    }

    @FXML
    public void openFile() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter;
        filter = new FileChooser.ExtensionFilter("JSON file (*.json", "*.json");
        fileChooser.getExtensionFilters().add(filter);

        filter = new FileChooser.ExtensionFilter("CSV file (*.csv", "*.csv");
        fileChooser.getExtensionFilters().add(filter);

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                EmployeeRepository repository = new EmployeeRepository(file.getAbsolutePath());
                List<Employee> employees = repository.getEmployees();
                clearListViews();
                this.comboBox.setItems(FXCollections.observableList(employees));
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Oops...");
                alert.setContentText("There's something wrong with the file.");
                alert.show();
            }
        }
    }

    @FXML
    public void select() {
        Employee employee = this.comboBox.getSelectionModel().getSelectedItem();
        if (employee != null) {
            switch (employee.getSpecialization()) {
                case TESTER -> testersField.getItems().add(employee);
                case PROGRAMMER -> programmersField.getItems().add(employee);
                case ANALYST -> analystsField.getItems().add(employee);
                case PM -> PMField.getItems().add(employee);
            }
            this.comboBox.getItems().remove(employee);
        }
    }

    @FXML
    public void saveFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV file (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        extFilter = new FileChooser.ExtensionFilter("JSON file (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(null);
        if(file != null){
            String fileName = file.toString();

            if (fileName.endsWith(".json")){
                ObservableList<Employee> testers = testersField.getItems();
                ObservableList<Employee> programmers = programmersField.getItems();
                ObservableList<Employee> pms = PMField.getItems();
                ObservableList<Employee> analysts = analystsField.getItems();
                HashMap<Specialization, List<Employee>> map = new HashMap<>();
                map.put(Specialization.TESTER, testers);
                map.put(Specialization.PROGRAMMER, programmers);
                map.put(Specialization.ANALYST, analysts);
                map.put(Specialization.PM, pms);
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(file, map);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("File has been successfully saved.");
                alert.show();

            }else if (fileName.endsWith(".csv")){

                HashMap<Specialization, List<Employee>> map = new HashMap<>();
                map.put(Specialization.PROGRAMMER, programmersField.getItems().stream().toList());
                map.put(Specialization.PM, PMField.getItems().stream().toList());
                map.put(Specialization.TESTER, testersField.getItems().stream().toList());
                map.put(Specialization.ANALYST, analystsField.getItems().stream().toList());

                try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    for (var pair : map.entrySet()){
                        writer.write(pair.getKey().toString() + "\n");
                        for (Employee employee : pair.getValue()){
                            writer.write(employee.toCsv() + "\n");
                        }
                    }
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("File has been successfully saved.");
                alert.show();

            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Saving file error");
                alert.setContentText("Only '.json', '.csv' file extensions allowed.");
                alert.show();

                throw new FileSystemException("can't save in '" + fileName.substring(fileName.lastIndexOf(".")) + " ' format");
            }
        }
    }

    public void exit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("You're about to exit.");
        alert.setContentText("Are you sure?");
        if(alert.showAndWait().get() == ButtonType.OK){
            Stage stage = (Stage) menuBar.getScene().getWindow();
            stage.close();
        }
    }

}