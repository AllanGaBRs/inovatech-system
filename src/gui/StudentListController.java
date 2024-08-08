package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Student;
import model.services.StudentService;

public class StudentListController implements Initializable {
	
	private StudentService service;
	
	@FXML
	private TableView<Student> tableViewStudent;
	@FXML
	private TableColumn<Student, Integer> tableColumnId;
	@FXML
	private TableColumn<Student, String> tableColumnRa;
	@FXML
	private TableColumn<Student, String> tableColumnName;
	@FXML
	private TableColumn<Student, String> tableColumnEmail;
	@FXML
	private TableColumn<Student, String> tableColumnCourse;
	@FXML
	private TableColumn<Student, Double> tableColumnHours;
	
	private ObservableList<Student> obsList;
	
	public void setStudentService(StudentService ss) {
		this.service = ss;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		InitializeNodes();
	}

	private void InitializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnRa.setCellValueFactory(new PropertyValueFactory<>("ra"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tableColumnCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
		tableColumnHours.setCellValueFactory(new PropertyValueFactory<>("hours"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewStudent.prefHeightProperty().bind(stage.heightProperty());
		tableViewStudent.prefWidthProperty().bind(stage.widthProperty());
	}

	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Student> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewStudent.setItems(obsList);
	}
}
