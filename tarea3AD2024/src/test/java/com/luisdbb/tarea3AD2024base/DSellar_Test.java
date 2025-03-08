package com.luisdbb.tarea3AD2024base;

import static org.mockito.Mockito.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.controller.CU3and5Controller;
import com.luisdbb.tarea3AD2024base.controller.ParadasController;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.services.EstanciaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.RutaService;
import com.luisdbb.tarea3AD2024base.services.Sesion;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

@ExtendWith(MockitoExtension.class)
public class DSellar_Test {

	@InjectMocks
	private CU3and5Controller controller;

	@Mock
	private UserService userService;

	@Mock
	private PeregrinoService peregrinoService;

	@Mock
	private RutaService rutaService;

	@Mock
	private ParadasController pcontrol;

	@Mock
	private StageManager stageManager;

	@Mock
	private EstanciaService estanciaService;

	@Mock
	private CheckBox estanciarCheckBox;

	@Mock
	private TextField userId;

	@Mock
	private PasswordField pass;

	@BeforeAll
	static void setUpAll() {

		Platform.startup(() -> {
		});
	}

	@BeforeEach
	void setUp() {

		MockitoAnnotations.openMocks(this);

		controller = spy(controller);
		controller.userId = userId;
		controller.pass = pass;

		Tarea3Ad2024baseApplication.useractivo = new Sesion();
		Tarea3Ad2024baseApplication.useractivo.setId(4);
		Tarea3Ad2024baseApplication.useractivo.setNombre("ferrol");
		Tarea3Ad2024baseApplication.useractivo.setPerfil(com.luisdbb.tarea3AD2024base.services.Perfil.PARADA);

	}

	@Test
	void testSellarSuccess() throws Exception {
		when(userId.getText()).thenReturn("luis");
		when(pass.getText()).thenReturn("luis");
		when(userService.authenticate("luis", "luis")).thenReturn(3);
		Platform.runLater(() -> {

			ActionEvent event = mock(ActionEvent.class);

			try {
				controller.Sellar(event);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		Thread.sleep(500);

		verify(userService).authenticate("luis", "luis");

		verify(estanciaService, never()).creaEstancia(any(), any(), anyBoolean());

		verify(controller, never()).saveAlert();

	}

	@Test
	void testSellarFailure() throws Exception {
		lenient().when(userId.getText()).thenReturn("luis");
		lenient().when(pass.getText()).thenReturn("MALALA");
		lenient().when(userService.authenticate("luis", "MALALA")).thenReturn(0);
		Platform.runLater(() -> {

			ActionEvent event = mock(ActionEvent.class);

			try {
				controller.Sellar(event);
				verify(controller).saveAlert();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		
	

	}

}
