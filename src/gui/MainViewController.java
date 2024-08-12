package gui;


import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.dao.DaoFactory;
import model.dao.StudentDao;
import model.entities.Student;
import model.services.StudentService;

public class MainViewController implements Initializable{
	
	@FXML
	private MenuItem menuItemRegistrarAluno;
	@FXML
	private MenuItem menuItemEntrarAluno;
	@FXML
	private MenuItem menuItemEntrarAdm;
	@FXML
	private MenuItem menuItemVizualizarAlunos;
	@FXML
	private MenuItem menuItemAjudaSobre;
	
	private StudentDao sd = DaoFactory.createStudentDao();
	private Student student = sd.findById(1);
	
	@FXML
	public void onMenuItemRegistrarAlunoAction() {
		if(!student.getAdm()) {
			Alerts.showAlert("Apenas Administradores podem acessar", "Acesso negado", "Erro", AlertType.ERROR);
		}else {
			loadView("/gui/Register.fxml", x -> {});			
		}
	}
	@FXML
	public void onMenuItemEntrarAlunoAction() {
		loadView("/gui/EnterStudent.fxml", x -> {});
	}
	@FXML
	public void onMenuItemEntrarAdmAction() {
		loadView("/gui/EnterAdm.fxml", x -> {});
	}
	@FXML
	public void onMenuItemVizualizarAlunosAction() {
		loadView("/gui/StudentList.fxml", (StudentListController controller) -> {;
		controller.setStudentService(new StudentService());
		controller.updateTableView();
	});
	}
	@FXML
	public void onmenuItemAjudaSobre() {
		loadView("/gui/About.fxml", x -> {});  
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
