package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.Tarea3Ad2024baseApplication;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Direccion;
import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;
import com.luisdbb.tarea3AD2024base.services.EnvioService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class EnvioController implements Initializable {
	@FXML
	private Label lblEnvio;
	@FXML
	private ImageView img;
	@FXML
	private Button btnAyuda;
	@FXML
	private TextField address;
	@FXML
	private CheckBox urgente;
	@FXML
	private TextField local;
	@FXML
	private TextField largo;
	@FXML
	private TextField alto;
	@FXML
	private TextField ancho;
	@FXML
	private TextField peso;
	@FXML
	private Button btnConfirm;
	@Lazy
	@Autowired
	private StageManager stageManager;
	@Autowired
	private EnvioService envioService;

	@FXML
	private void crea(ActionEvent event) throws IOException {

		Direccion direccion = new Direccion();
		direccion.setDireccion(address.getText());
		direccion.setLocalidad(local.getText());
		direccion.setId(getLastEnvioId());

		EnvioACasa envio = new EnvioACasa();
		envio.setPeso(Double.parseDouble(peso.getText()));
		// control errores aqui
		envio.setLargo(Integer.parseInt(largo.getText()));
		envio.setAncho(Integer.parseInt(ancho.getText()));
		envio.setAlto(Integer.parseInt(alto.getText()));
		envio.setUrgente(urgente.isSelected());
		envio.setDireccion(direccion);
		envio.setIdParada(Tarea3Ad2024baseApplication.inicial.getId());
		envioService.registrarEnvio(envio);
		System.out.println("done id: " + envio.getId());
		List<EnvioACasa> envios = envioService.getenvios();
		if (envios.isEmpty()) {
			System.out.println("No EnvioACasa objects found in the database.");
		} else {
			System.out.println("EnvioACasa objects in the database:");
			for (EnvioACasa e : envios) {
				System.out.println("ID: " + e.getId() + ", Localidad: " + e.getDireccion().getLocalidad());
			}

		}
		stageManager.switchScene(FxmlView.PARADA);
	}

	public Long getLastEnvioId() {

		List<EnvioACasa> envios = envioService.getenvios();

		if (envios.isEmpty()) {
			return 1L;
		}

		EnvioACasa lastEnvio = envios.stream().max((e1, e2) -> Long.compare(e1.getId(), e2.getId())).orElse(null);
		Long id = lastEnvio.getId() + 1;
		return lastEnvio != null ? id : 1L;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		envioService.abrir();

	}

	public void mostrarAyuda() {
		try {

			WebView webView = new WebView();

			String url = getClass().getResource("/help/html/admin.html").toExternalForm();
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

}
