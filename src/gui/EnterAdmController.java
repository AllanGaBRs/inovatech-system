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
import javafx.scene.control.TextField;
import model.dao.DaoFactory;
import model.dao.StudentDao;
import model.entities.Student;

public class EnterAdmController implements Initializable{

	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtPass;
	@FXML
	private Button btEnter;
	
	private static Student student;
	private List<TextField> list = new ArrayList<>();
	private static StudentDao dao = DaoFactory.createStudentDao();
	
	public static Student getStudent() {
		return student;
	}

	public void onBtEnter() {
		list.add(txtEmail);
		list.add(txtPass);
		Boolean test = textFieldMandatory(list);
		Student st = null;
		if(test) {
			st = userVerification(txtEmail.getText(), txtPass.getText());			
		}
		if(st != null) {		
			if(!st.getAdm()) {
				Alerts.showAlert("Aviso", "Login de adm negado", "Usuário não tem permissão para logar como adm", AlertType.WARNING);
			}
		}
		if(st != null && st.getAdm()) {
			MainViewController.setStudent(st);
			Alerts.showAlert("Adm logado", "login", "login feito com sucesso", AlertType.INFORMATION);
		}
			
	}
	
	private Student userVerification(String email, String senha) {
		Student std = dao.findByEmailPass(email, senha);
		if(std == null) {
			Alerts.showAlert("Adm não encontrado", "Erro", "Tente novamente ou logue como Aluno", AlertType.ERROR);
			return null;
		}
		return std;
	}
	
	private boolean textFieldMandatory(List<TextField> txt) {
		for(TextField tx : txt) {
			if(tx.getText().isEmpty()) {
				Alerts.showAlert("Aviso", "Campo nulo", "Todos os campos devem ser preenchidos", AlertType.WARNING);
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
