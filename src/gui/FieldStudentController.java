package gui;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.util.Alerts;
import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.dao.DaoFactory;
import model.dao.StudentDao;
import model.entities.Student;

public class FieldStudentController implements Initializable{

	@FXML
	private TextField txtHours;
	@FXML
	private TextArea txtRelatorio;
	@FXML
	private Button btEnviar;
	
	private static StudentDao sd = DaoFactory.createStudentDao();
	
	@FXML
	public void onBtEnviarAction() {
		LocalDate localDate = LocalDate.now();
		java.util.Date date = java.sql.Date.valueOf(localDate);
		java.sql.Date ld = new java.sql.Date(date.getTime());
		Double hours = 0.0;
		try {
			hours = Double.parseDouble(txtHours.getText());
		}catch (NumberFormatException e){
			Alerts.showAlert("aviso", "campo nulo", "todos os campos devem ser preenchidos", AlertType.WARNING);
			return;
		}
		Student st = MainViewController.getStudent();
		Student stWithDate = (sd.selectDate(st.getId()));
		st.setDate(stWithDate.getDate());
		if(hours <= 0) {
			Alerts.showAlert("horas", "minimo de horas", "impossivel registrar 0 horas", AlertType.INFORMATION);
			return;
		}
		if(hours > 8.0) {
			Alerts.showAlert("horas", "maximo de horas", "limite de horas é 8 por dia", AlertType.INFORMATION);
			return;
		}
		if(hours < 8.0 && hours > 0 && st.getDate().compareTo((java.sql.Date)ld) < 0) {
			sd.updateHours(st, hours);
			sd.updateDate(st, ld);
			Alerts.showAlert("AVISO", "Registro realizado", "horas enviadas com sucesso", AlertType.INFORMATION);
			return;
		}
		if(st.getDate().compareTo((java.sql.Date)ld) == 0) {
			Alerts.showAlert("AVISO", "Você já fez seu registro diário", "impossível realizar dois envios no dia", AlertType.INFORMATION);
			return;
		}
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Constraints.setTextFieldDouble(txtHours);
	}

}
