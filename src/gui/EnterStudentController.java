package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.dao.DaoFactory;
import model.dao.StudentDao;
import model.entities.Student;

public class EnterStudentController implements Initializable{

	@FXML
	private TextField txtEmailStudent;
	@FXML
	private TextField txtPassStudent;
	@FXML
	private Button btEnterStudent;
	
	private static Student student;
	private List<TextField> list = new ArrayList<>();
	private static StudentDao dao = DaoFactory.createStudentDao();
	
	public static Student getStudent() {
		return student;
	}
	
	@FXML
	public void onBtEnterStudent() {
		list.add(txtEmailStudent);
		list.add(txtPassStudent);
		Boolean test = textFieldMandatory(list);
		Student st = null;
		if(test) {
			st = userVerification(txtEmailStudent.getText(), txtPassStudent.getText());			
		}
		if(st != null) {		
			if(st.getAdm()) {
				Alerts.showAlert("aviso", "login de aluno negado", "usuário não tem permissão para logar como aluno", AlertType.WARNING);
			}
		}
		StringTokenizer str = new StringTokenizer(st.getName());
		if(st != null && !st.getAdm()) {
			MainViewController.setStudent(st);
			Alerts.showAlert(str.nextToken(" ") + " logado", "login", "login feito com sucesso", AlertType.INFORMATION);
		}
	}
	
	private Student userVerification(String email, String senha) {
		Student std = dao.findByEmailPass(email, senha);
		if(std == null) {
			Alerts.showAlert("aluno não encontrado", "erro", "tente novamente", AlertType.ERROR);
			return null;
		}
		return std;
	}
	
	private boolean textFieldMandatory(List<TextField> txt) {
		for(TextField tx : txt) {
			if(tx.getText().isEmpty()) {
				Alerts.showAlert("aviso", "campo nulo", "todos os campos devem ser preenchidos", AlertType.WARNING);
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
