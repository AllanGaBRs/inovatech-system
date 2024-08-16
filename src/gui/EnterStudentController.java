package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
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
		StringTokenizer str = null;
		if(st != null) {		
			if(st.getAdm()) {
				Alerts.showAlert("aviso", "login de aluno negado", "usuário não tem permissão para logar como aluno", AlertType.WARNING);
			}
			str = new StringTokenizer(st.getName());
		}
		if(st != null && !st.getAdm()) {
			MainViewController.setStudent(st);
			Alerts.showAlert(str.nextToken(" ") + " logado", "login", "login feito com sucesso", AlertType.INFORMATION);
			loadView("/gui/FieldStudent.fxml", x -> {}); 
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
	
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initilizingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox)((ScrollPane)mainScene.getRoot()).getContent();
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox);
			
			T controller = loader.getController();
			initilizingAction.accept(controller);
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
}
