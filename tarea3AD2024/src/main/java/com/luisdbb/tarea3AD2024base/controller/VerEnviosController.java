package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.Tarea3Ad2024baseApplication;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;
import com.luisdbb.tarea3AD2024base.services.EnvioService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class VerEnviosController implements Initializable {
	@FXML
	private Label lblLogin1;
	@FXML
	private TableView<EnvioACasa> tableViews;
	@FXML
	private TableColumn<EnvioACasa, String> eDireccion;
	@FXML
	private TableColumn<EnvioACasa, String> eLocalidad;
	@FXML
	private TableColumn<EnvioACasa, String> eVolumen;
	@FXML
	private TableColumn<EnvioACasa, Double> ePeso;
	@FXML
	private TableColumn<EnvioACasa, Long> eId;
	@FXML
	private TableColumn<EnvioACasa, String> eUrgent;

	@Lazy
	@Autowired
	private StageManager stageManager;
	@Autowired
	private EnvioService envioService;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		lblLogin1.setText("Envios realizados desde: "+Tarea3Ad2024baseApplication.inicial.getNombre());
		eId.setCellValueFactory(new PropertyValueFactory<>("id"));
		eDireccion.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getDireccion().getDireccion()));
		eLocalidad.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getDireccion().getLocalidad()));
		ePeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
		eVolumen.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().volumenString()));
		eUrgent.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().urgenteString()));
		// logic rellenar cosas
		List<EnvioACasa> oe = envioService.getenviosParada(Tarea3Ad2024baseApplication.inicial.getId());
		tableViews.getItems().addAll(oe);
	}

	@FXML
	public void back() {
		stageManager.switchScene(FxmlView.PARADA);
	}

	public void mostrarAyuda() {
		try {

			WebView webView = new WebView();

			String url = getClass().getResource("/help/html/admin.html").toExternalForm();
		
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
