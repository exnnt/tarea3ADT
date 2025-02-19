package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.Tarea3Ad2024baseApplication;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.services.CarnetService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.Perfil;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class ExportController implements Initializable {
	@Lazy
	@Autowired
	private StageManager stageManager;
	@Autowired
	private CarnetService carnetService;
	@Autowired
	private PeregrinoService peregrinoService;	
	@FXML
	private Label lblMecachis;
	@FXML
	private Button btnExportar;
	@FXML
	private Button btnVolver;
	@FXML
	private ImageView image;
	private Peregrino p = null;
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		// TODO Auto-generated method stub
		System.out.println("now we export");
		p = peregrinoService.findbyiduser( Tarea3Ad2024baseApplication.useractivo.getId());
		System.out.println(p.getId_user());
		lblMecachis.setText("Bienvenido "+p.getNombre());
		
	}
	@FXML
	private void volver(ActionEvent event) throws IOException {
	
		System.out.println("test");
		Tarea3Ad2024baseApplication.useractivo.setId(0);
		Tarea3Ad2024baseApplication.useractivo.setNombre("Invitado");
		Tarea3Ad2024baseApplication.useractivo.setPerfil(Perfil.INVITADO);
		stageManager.switchScene(FxmlView.INVITADO);
	}
	public void mostrarAyuda() {
        try {
            
            WebView webView = new WebView();

            String url = getClass().getResource("/help/html/peregrino.html").toExternalForm();
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
	private void exportCarnet(ActionEvent event) throws IOException {
	//logic aqui
		
		try {
			carnetService.exportCarnet(p);
			exportaAlert(p);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("coming soon xd");
		// alert con info?
	}
	private void exportaAlert(Peregrino p) {
		String name= p.getNombre().replaceAll(" ", "");
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Carnet exportado");
		alert.setHeaderText(null);
		alert.setContentText("Carnet exportado, esta en /src/main/resources/escritura/" +name+ "_peregrino.xml");
		alert.showAndWait();
	

}
}
