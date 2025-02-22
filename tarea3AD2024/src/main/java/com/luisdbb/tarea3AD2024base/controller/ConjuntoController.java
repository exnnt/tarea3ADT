package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.Tarea3Ad2024baseApplication;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.Perfil;
import com.luisdbb.tarea3AD2024base.services.ServicioService;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class ConjuntoController implements Initializable {

	@Lazy
	@Autowired
	private StageManager stageManager;
	@Autowired
	private ServicioService servicioService;
	@Autowired
	private CU3and5Controller cu35;
	@Autowired
	private Tarea3Ad2024baseApplication main;
	@FXML
	private Button btnLogin;

	@FXML
	private Button btnParadas;

	@FXML
	private Button btnServicios;

	@FXML
	private Button btnAyuda;
	@FXML
	private Button edit1;
	@FXML
	private Button edit11;

	@FXML
	private AnchorPane root;
	@FXML
	private TableView<Servicio> tableServicios;
	@FXML
	private TableColumn<Servicio, Long> idservicio;
	@FXML
	private TableColumn<Servicio, String> nameservicio;
	@FXML
	private TableColumn<Servicio, Double> precioservicio;
	private Servicio serviciopasiempre;
	private ObservableList<Servicio> servicios = FXCollections.observableArrayList();
	@FXML
	private TableColumn<Servicio, String> paradasservicio;
	private Long idEstancia;
	
	@FXML
	private void back(ActionEvent event) throws IOException {
		System.out.println("test");
		stageManager.switchScene(FxmlView.ADMIN1);
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idEstancia = cu35.getidEstancia();
		idservicio.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameservicio.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		precioservicio.setCellValueFactory(new PropertyValueFactory<>("precio"));
		getservicios();
		
	}

	public void getservicios() {
	// xddd	System.out.println(idEstancia);
		System.out.println(main.inicial.getId());
		List<Servicio> serviciosdb = servicioService.listarServicios();
		List<Servicio> disponible = new ArrayList<>();
		for(Servicio s: serviciosdb) {
			System.out.println(s.getparadasString());
		if(s.getparadasString().contains(String.valueOf(main.inicial.getId()))) {
			System.out.println(s.getNombre());
			disponible.add(s);
		}
		System.out.println("next");
		}
		servicios.setAll(disponible);
		tableServicios.setItems(servicios);
	}


	@FXML
	public void lastSelected() {
		Servicio sel = tableServicios.getSelectionModel().getSelectedItem();
		System.out.println(sel.getId());
		System.out.println(sel.getparadasString());
		serviciopasiempre = sel;
	}


}
