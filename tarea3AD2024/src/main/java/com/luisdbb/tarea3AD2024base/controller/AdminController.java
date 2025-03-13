package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.Tarea3Ad2024baseApplication;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.config.existdb.ExistDBManageante;
import com.luisdbb.tarea3AD2024base.config.mongodb.MongoDB;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.services.CarnetService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.Perfil;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class AdminController implements Initializable {

	@FXML
	Button btnParadas;
	@FXML
	Button btnServicios;
	@FXML
	private Label lblAdmin;
	@FXML
	private TextField name;
	@FXML
	private TextField reg;
	@FXML
	private TextField respons;
	@FXML
	private PasswordField pass;
	@FXML
	private Button btnLogout;
	@FXML
	private Button btnLogin;
	@FXML
	private ImageView img;
	@FXML
	private Button btnBackup;
	@Lazy
	@Autowired
	private StageManager stageManager;
	@Autowired
	private ParadaService paradaService;
	@Autowired
	private CarnetService carnetService;
	
	boolean par = false;

	@FXML
	private void back(ActionEvent event) throws IOException {
		par = false;
		stageManager.switchScene(FxmlView.ADMIN1);
	}

	@FXML
	private void paradas(ActionEvent event) throws IOException {
		par = true;
		stageManager.switchScene(FxmlView.ADMIN);
	}

	@FXML
	private void servicios(ActionEvent event) throws IOException {

		stageManager.switchScene(FxmlView.ADMIN2);
	}

	@FXML
	private void logout(ActionEvent event) throws IOException {
		Tarea3Ad2024baseApplication.useractivo.setPerfil(Perfil.INVITADO);
		stageManager.switchScene(FxmlView.INVITADO);
	}
	@FXML
	private void backup() {
		MongoDB m = new MongoDB(carnetService);
		m.backupCarnets();
	}

	public void mostrarAyuda() {
		try {
			String url = "";
			WebView webView = new WebView();
			if (par) {
				url = getClass().getResource("/help/html/creaparadas.html").toExternalForm();

			} else {
				url = getClass().getResource("/help/html/admin.html").toExternalForm();
			}
			System.out.println(url);
			webView.getEngine().load(url);

			Stage helpStage = new Stage();
			helpStage.setTitle("Ayuda");

			StackPane root = new StackPane(webView);
			Scene helpScene = new Scene(root, 600, 400);
			helpStage.setScene(helpScene);

			helpStage.initModality(Modality.APPLICATION_MODAL);
			helpStage.setResizable(true);
			helpStage.show();

		} catch (NullPointerException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Archivo de Ayuda no encontrado");

			alert.showAndWait();
		}
	}

	@FXML
	public void crea(ActionEvent event) throws IOException {
		try {
			if (name.getText().isEmpty() || reg.getText().isEmpty() || respons.getText().isEmpty()
					|| pass.getText().isEmpty()) {

				showAlert();
				return;
			}

			String nombre = name.getText();
			char r = reg.getText().charAt(0);
			String responsable = respons.getText();
			responsable = responsable.replace(" ", "");
			String passw = pass.getText();
			passw = passw.replace(" ", "");
			Parada temp = new Parada(nombre, r, responsable);
			paradaService.crearParada(temp, passw);
			System.out.println("");
			System.out.println(temp.getNombre());
			showAlert2(temp.getNombre(), responsable, passw);
			ExistDBManageante.parada(nombre);
			stageManager.switchScene(FxmlView.ADMIN1);
		} catch (Exception e) {
			showAlert();

		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	public void showAlert() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error de entrada");
		alert.setHeaderText("Campos vacíos");
		alert.setContentText("Todos los campos son obligatorios");
		alert.showAndWait();
	}

	public void showAlert2(String a, String b, String c) {
		Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
		successAlert.setTitle("Parada creada");
		successAlert.setHeaderText(null);
		successAlert
				.setContentText("La parada " + a + " con responsable " + b + " y contraseña " + c + " ha sido creada.");
		successAlert.showAndWait();
	}

}
