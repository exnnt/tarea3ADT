package com.luisdbb.tarea3AD2024base.controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.Tarea3Ad2024baseApplication;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
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
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Ram Alapure
 * @since 05-04-2017
 */

@Controller
public class LoginController implements Initializable{

	@FXML
    private Button btnLogIn;
	@FXML
    private Button btnVolver;

    @FXML
    private PasswordField password;
    
    @FXML
    private TextField username;

    @FXML
    private Label lblLogin;
    
    @Autowired
    private UserService userService;
    @Autowired
    private PeregrinoService peregrinoService;
    @Autowired 
    private ParadaService paradaService;
    @Lazy
    @Autowired
    private StageManager stageManager;
        
	@FXML
    private void login(ActionEvent event) throws IOException{
		int usr = userService.authenticate(getUsername(), getPassword());
    	if(usr>0){
    		if(usr>1) {
    		Usuario u = userService.findByName(getUsername());
    		Tarea3Ad2024baseApplication.useractivo.setId(u.getId());   		
    		System.out.println(u.getId());}
    		switch(usr) {
    		case 1:
    			Tarea3Ad2024baseApplication.useractivo.setPerfil(Perfil.ADMIN);
    			stageManager.switchScene(FxmlView.ADMIN);
    			break;
    		case 2:
    			Parada p = paradaService.findbyrespons(getUsername())
    			;    			
    			    			
    			    			Tarea3Ad2024baseApplication.setPinicial(p);
    			    			System.out.println(p.getNombre());
    			stageManager.switchScene(FxmlView.PARADA);
    			
    			Tarea3Ad2024baseApplication.useractivo.setPerfil(Perfil.PARADA);
    			break;
    		case 3:
    			System.out.println("before ");
    			Peregrino pp = peregrinoService.findbyiduser( Tarea3Ad2024baseApplication.useractivo.getId());
    				
    			System.out.println(pp.getNombre());
    			System.out.println("after");
    			 stageManager.switchScene(FxmlView.PEREGRINO);
    			
    			Tarea3Ad2024baseApplication.useractivo.setPerfil(Perfil.PEREGRINO);
    			break;
    		}
    		
    		
    	}else{
    		updateAlert();
    	}
    }
	
	public String getPassword() {
		return password.getText();
	}

	public String getUsername() {
		return username.getText();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	@FXML
	private void volver(ActionEvent event) throws IOException {
	
		System.out.println("test");
		Tarea3Ad2024baseApplication.useractivo.setId(0);
		Tarea3Ad2024baseApplication.useractivo.setNombre("Invitado");
		Tarea3Ad2024baseApplication.useractivo.setPerfil(Perfil.INVITADO);
		stageManager.switchScene(FxmlView.INVITADO);
	}
	private void updateAlert() {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Credenciales Incorrectas");
		alert.setHeaderText(null);
		alert.setContentText("Nombre de usuario o contraseña incorrectas");
		alert.showAndWait();
	}
	public void mostrarAyuda() {
        try {
            
            WebView webView = new WebView();

            String url = getClass().getResource("/help/html/login.html").toExternalForm();
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
