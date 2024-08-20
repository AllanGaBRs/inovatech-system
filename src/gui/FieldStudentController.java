package gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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

public class FieldStudentController implements Initializable {

	@FXML
	private TextField txtHours;
	@FXML
	private TextArea txtRelatorio;
	@FXML
	private Button btEnviar;

	private static StudentDao sd = DaoFactory.createStudentDao();

	@FXML
	public void onBtEnviarAction() {
		if (!textFieldMandatory(txtRelatorio)) {
			return;
		}
		Double hours = 0.0;
		try {
			hours = Double.parseDouble(txtHours.getText());
		} catch (NumberFormatException e) {
			Alerts.showAlert("aviso", "campo nulo", "todos os campos devem ser preenchidos", AlertType.WARNING);
			return;
		}
		LocalDate localDate = LocalDate.now();
		java.util.Date date = java.sql.Date.valueOf(localDate);
		java.sql.Date ld = new java.sql.Date(date.getTime());
		Student st = MainViewController.getStudent();
		Student stWithDate = (sd.selectDate(st.getId()));
		st.setDate(stWithDate.getDate());
		if (hours <= 0) {
			Alerts.showAlert("horas", "minimo de horas", "impossivel registrar 0 horas", AlertType.INFORMATION);
			return;
		}
		if (hours > 8.0) {
			Alerts.showAlert("horas", "maximo de horas", "limite de horas é 8 por dia", AlertType.INFORMATION);
			return;
		}
		if (hours <= 8.0 && hours > 0 && st.getDate().compareTo((java.sql.Date) ld) < 0) {
			if (!savedReport(txtRelatorio)) {
				return;
			}
			sd.updateHours(st, hours);
			sd.updateDate(st, ld);
			Alerts.showAlert("AVISO", "Registro realizado", "horas enviadas com sucesso", AlertType.INFORMATION);
			return;
		}
		if (st.getDate().compareTo((java.sql.Date) ld) == 0) {
			Alerts.showAlert("AVISO", "Você já fez seu registro diário", "impossível realizar dois envios no dia",
					AlertType.INFORMATION);
			return;
		}
	}

	private boolean savedReport(TextArea relatorio) {
		Student st = MainViewController.getStudent();
		String path = "C:\\Relatorios";
		String pathUser = path + "\\" + st.getName();
		File theDir = new File(pathUser);
		if (!theDir.exists()) {
			boolean result = false;
			try {
				theDir.mkdir();
				result = true;
			} catch (SecurityException se) {
				Alerts.showAlert("ERRO", "Erro ao criar pasta do aluno", "falha", AlertType.ERROR);
				return false;
			}
		}
		Student stWithDate = (sd.selectDate(st.getId()));
		String pathFile = pathUser + "\\" + stWithDate.getDate().toString();
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathFile))) {

			String text = relatorio.getText();
			bw.write(st.getName() + ", " + "Quantidade de horas: " + txtHours.getText());
			bw.newLine();
			bw.write(text);

		} catch (IOException e) {
			Alerts.showAlert("ERRO", "Erro ao enviar o relatório", "falha", AlertType.ERROR);
			return false;
		}
		return true;
	}

	private boolean textFieldMandatory(TextArea txtArea) {
		if (txtArea.getText().isEmpty()) {
			Alerts.showAlert("Aviso", "Campo nulo", "Todos os campos devem ser preenchidos", AlertType.WARNING);
			return false;
		}
		return true;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Constraints.setTextFieldDouble(txtHours);
	}

}
