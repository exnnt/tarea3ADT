package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.Tarea3Ad2024baseApplication;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.services.EstanciaService;
import com.luisdbb.tarea3AD2024base.services.Perfil;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class ParadasController implements Initializable {

	@FXML
	private Label lblLogin;
	@FXML
	private TextField username1;
	@FXML
	private TextField region;
	@FXML
	private TextField responsable;
	public boolean cu3 = false;
	@FXML
	private Button btnLogout;
	@FXML
	private Button btnVer;
	@Lazy
	@Autowired
	private StageManager stageManager;

	@FXML
	private void logout(ActionEvent event) throws IOException {
		Tarea3Ad2024baseApplication.useractivo.setPerfil(Perfil.INVITADO);
		stageManager.switchScene(FxmlView.INVITADO);
	}

	@FXML
	private void goExport(ActionEvent event) throws IOException {
		cu3 = true;
		stageManager.switchScene(FxmlView.EXPORT);
	}

	@FXML
	private void gover() {
		stageManager.switchScene(FxmlView.VERENVIOS);
	}

	public void setcu3(boolean f) {
		cu3 = f;
	}

	public boolean getC3() {
		return cu3;
	}

	@FXML
	private void goSellar(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.SELLAR);
	}

	public void mostrarAyuda() {
		try {

			WebView webView = new WebView();

			String url = getClass().getResource("/help/html/parada.html").toExternalForm();

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		System.out.println(Tarea3Ad2024baseApplication.inicial.getNombre());

		lblLogin.setText("Bienvenido a " + Tarea3Ad2024baseApplication.inicial.getNombre());

	}

}
