package com.luisdbb.tarea3AD2024base;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.luisdbb.tarea3AD2024base.view.FxmlView;
import com.luisdbb.tarea3AD2024base.config.StageManager;

import javafx.application.Application;
import javafx.stage.Stage;

import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.services.*;

@SpringBootApplication
public class Tarea3Ad2024baseApplication extends Application {

	protected ConfigurableApplicationContext springContext;
	protected StageManager stageManager;
	public static Sesion useractivo;

	public static Parada inicial = null;

	@Override
	public void init() throws Exception {

		springContext = springBootApplicationContext();
	}

	public static void main(final String[] args) {
		useractivo = new Sesion();
		useractivo.setId(0);
		useractivo.setNombre("Invitado");
		useractivo.setPerfil(Perfil.INVITADO);
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stageManager = springContext.getBean(StageManager.class, primaryStage);
		displayInitialScene();

	}

	/**
	 * Useful to override this method by sub-classes wishing to change the first
	 * Scene to be displayed on startup. Example: Functional tests on main window.
	 */
	protected void displayInitialScene() {
		stageManager.switchScene(FxmlView.LOGIN);
	}

	private ConfigurableApplicationContext springBootApplicationContext() {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(Tarea3Ad2024baseApplication.class);
		String[] args = getParameters().getRaw().stream().toArray(String[]::new);
		return builder.run(args);
	}

	public static void cambiarParada(Parada temp) {
		// TODO Auto-generated method stub
		setPinicial(temp);
	
	}
	
	public Parada getPinicial() {
		return inicial;
		
	}
	public static void setPinicial(Parada p) {
		inicial = p;
	}

}
