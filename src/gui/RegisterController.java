package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.dao.DaoFactory;
import model.dao.StudentDao;
import model.entities.Student;

public class RegisterController implements Initializable{

	@FXML
	private TextField txtRa;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtPass;
	@FXML
	private TextField txtCourse;
	@FXML
	private Label labeEmail;
	@FXML
	private Label labeCourse;
	@FXML
	private Button btRegister;
	
	private StudentDao studentDao = DaoFactory.createStudentDao();
	private List<TextField> list = new ArrayList<>();
	
	
	@FXML
	public void onBtRegister() {
		list.add(txtRa);
		list.add(txtName);
		list.add(txtEmail);
		list.add(txtPass);
		list.add(txtCourse);
		Boolean test = textFieldMandatory(list);
		Student std = new Student(null, txtRa.getText(), txtName.getText(), txtEmail.getText(), txtPass.getText(), txtCourse.getText(), 0.0, false);
		if(test && verification(txtEmail.getText())) {
			studentDao.insert(std);		
			Alerts.showAlert("Registro", "Registrado", txtName.getText() + " registrado com sucesso", AlertType.INFORMATION);
		}
	}
	
	private boolean textFieldMandatory(List<TextField> txt) {
		for(TextField tx : txt) {
			if(tx.getText().isEmpty()) {
				Alerts.showAlert("Aviso", "Campo nulo", "Todos os campos devem ser preenchidos", AlertType.WARNING);
				return false;
			}
		}
		if(!txtCourse.getText().toLowerCase().equals("sistemas de informação") && !txtCourse.getText().toLowerCase().equals("análise e desenvolvimento de sistemas")) {
			Alerts.showAlert("Aviso", "Curso inválido", "tente sistemas de informação ou análise e desenvolvimento de sistemas", AlertType.WARNING);
			return false;
		}
		return true;
	}
	
	private Boolean verification(String email) {
		Student std = studentDao.findByEmail(email);
		if(std != null) {
			Alerts.showAlert("Aviso", "Registro existente", "Já existe registro com esse e-mail", AlertType.WARNING);
			return false;
		}
		return true;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}

}
