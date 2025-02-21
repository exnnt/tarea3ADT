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
public class ServiController implements Initializable {

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private ParadaService paradaService;
	@Autowired
	private ServicioService servicioService;
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
	private AnchorPane root;
	@FXML
	private TableView<Parada> tableParadas;
	@FXML
	private TableColumn<Parada, Long> colId;
	@FXML
	private TableColumn<Parada, String> colNombre;
	@FXML
	private TextField name;
	@FXML
	private TextField precio;
	@FXML
	private TableView<Servicio> tableServicios; 
	@FXML
	private TableColumn<Servicio, Long> idservicio;
	@FXML
	private TableColumn<Servicio, String> nameservicio;
	@FXML
	private TableColumn<Servicio, Double> precioservicio;
	private Servicio serviciopasiempre;
	private ObservableList<Parada> paradas = FXCollections.observableArrayList();
	private ObservableList<Servicio> servicios = FXCollections.observableArrayList();
	@FXML
	private TableColumn<Servicio, String> paradasservicio;
	@FXML
	private void back(ActionEvent event) throws IOException {
		System.out.println("test");
		stageManager.switchScene(FxmlView.ADMIN1);
	}

	@FXML
	private void paradas(ActionEvent event) throws IOException {
		System.out.println("test paradas");
		stageManager.switchScene(FxmlView.ADMIN);
	}

	@FXML
	private void logout(ActionEvent event) throws IOException {
		System.out.println("test");
		Tarea3Ad2024baseApplication.useractivo.setPerfil(Perfil.INVITADO);
		stageManager.switchScene(FxmlView.INVITADO);
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
		 colId.setCellValueFactory(new PropertyValueFactory<>("id"));
	     colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
	     idservicio.setCellValueFactory(new PropertyValueFactory<>("id"));
	     nameservicio.setCellValueFactory(new PropertyValueFactory<>("nombre"));
	     precioservicio.setCellValueFactory(new PropertyValueFactory<>("precio"));
	     paradasservicio.setCellValueFactory(cellData -> {
	         return new SimpleStringProperty(cellData.getValue().getparadasString());
	     });
		  getparadas();
		  getservicios();
	}
	 private void getparadas() {
		 
	        List<Parada> paradasFromDB = paradaService.findAll();
	        
	        paradas.setAll(paradasFromDB);

	        tableParadas.setItems(paradas);
	    }
	 public void addservicios() {
		 
		 servicioService.serviciar(name.getText(), Double.parseDouble(precio.getText()));
		 System.out.println("LETSGO");
		 getservicios();

	 }
	 public void getservicios() {
		   
		    List<Servicio> serviciosdb = servicioService.listarServicios();  
		    servicios.setAll(serviciosdb);  
		    tableServicios.setItems(servicios);  
		}
	
	 @FXML
	 public void addParada() {
		 Parada sel = tableParadas.getSelectionModel().getSelectedItem();
		 Long id =   sel.getId();
		 System.out.println(id);
		 
		 if(id!=null) {
			 if(serviciopasiempre.getParadas()!=null) {
			for(Long x: serviciopasiempre.getParadas()) {
				if(id==x) {
					System.out.println("repe");
					return;
				}}}
			System.out.println("no repe");
				serviciopasiempre.addParada(id);
				 System.out.println("Lista nueva");
				 for(Long x: serviciopasiempre.getParadas())
					 System.out.println(x);
				 servicioService.editar1(serviciopasiempre);
				
				getservicios();
			
		 }
	 }
	 @FXML
	 public void lastSelected() {
		 Servicio sel = tableServicios.getSelectionModel().getSelectedItem();
		 System.out.println(sel.getId());
	     serviciopasiempre = sel;
	 }


}
