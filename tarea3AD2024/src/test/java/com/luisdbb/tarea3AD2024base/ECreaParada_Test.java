package com.luisdbb.tarea3AD2024base;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.ArgumentMatchers.eq;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.controller.AdminController;
import com.luisdbb.tarea3AD2024base.services.ParadaService;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ECreaParada_Test {
	@Mock
	private ParadaService paradaService;

	@Mock
	private StageManager stageManager;

	@Mock
	private TextField name;

	@Mock
	private TextField reg;

	@Mock
	private TextField respons;

	@Mock
	private PasswordField pass;

	@InjectMocks
	private AdminController controller;

	@Mock
	private ActionEvent event;

	@BeforeAll
	static void setUpAll() {

		Platform.startup(() -> {
		});
	}

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		controller = spy(controller);

	}

	@Test
	void testCreaSuccess() throws Exception {

		when(name.getText()).thenReturn("Parada Noseque");
		when(reg.getText()).thenReturn("R");
		when(respons.getText()).thenReturn("testunitario");
		when(pass.getText()).thenReturn("unitario");

		;

		Platform.runLater(() -> {
			try {
				controller.crea(event);

			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		Thread.sleep(1000);

		verify(controller).showAlert2(name.getText(), respons.getText(), pass.getText());
	}

	@Test
	void testCreaFailureMissingName() throws Exception {

		lenient().when(reg.getText()).thenReturn("R");
		lenient().when(respons.getText()).thenReturn("testunitario");
		lenient().when(pass.getText()).thenReturn("unitario");

		Platform.runLater(() -> {
			try {
				controller.crea(event);

			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		Thread.sleep(1000);

		verify(controller).showAlert();
	}

	@Test
	void testCreaFailureMissingRegion() throws Exception {

		when(name.getText()).thenReturn("Parada Noseque");
		when(respons.getText()).thenReturn("testunitario");
		when(pass.getText()).thenReturn("unitario");

		Platform.runLater(() -> {
			try {
				controller.crea(event);

			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		Thread.sleep(1000);

		verify(controller).showAlert();
	}

	@Test
	void testCreaFailureMissingRespons() throws Exception {

		lenient().when(name.getText()).thenReturn("Parada Noseque");
		lenient().when(reg.getText()).thenReturn("R");
		lenient().when(pass.getText()).thenReturn("unitario");

		Platform.runLater(() -> {
			try {
				controller.crea(event);

			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		Thread.sleep(1000);

		verify(controller).showAlert();
	}

	@Test
	void testCreaFailureMissingPassword() throws Exception {

		when(name.getText()).thenReturn("Parada Noseque");
		when(respons.getText()).thenReturn("testunitario");
		when(reg.getText()).thenReturn("R");

		Platform.runLater(() -> {
			try {
				controller.crea(event);

			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		Thread.sleep(1000);

		verify(controller).showAlert();
	}

	@Test
	void testCreaFailureMissingTodo() throws Exception {

		Platform.runLater(() -> {
			try {
				controller.crea(event);

			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		Thread.sleep(1000);

		verify(controller).showAlert();
	}

}
