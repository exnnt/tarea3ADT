package com.luisdbb.tarea3AD2024base.services;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Ruta;
import com.luisdbb.tarea3AD2024base.repositorios.CarnetRepository;
import com.luisdbb.tarea3AD2024base.repositorios.ParadaRepository;
import com.luisdbb.tarea3AD2024base.repositorios.PeregrinoRepository;

import jakarta.transaction.Transactional;

@Service
public class CarnetService {
	@Autowired
	CarnetRepository carnetRepository;
	@Autowired
	private ParadaRepository paradaRepository;
	@Autowired
	private ParadaService paradaService;
	@Autowired
	private PeregrinoService peregrinoService;
	@Autowired
	private EstanciaService estanciaService;
	@Autowired
	private RutaService rutaService;

	@Transactional
	public Carnet creaCarnet(Peregrino p, Parada user) {
		Carnet carnetet = new Carnet(p, user);
		return carnetRepository.save(carnetet);
	}

	public List<Carnet> getTodosCarnets() {
		return carnetRepository.findAll();
	}

	public void actualizarCarnet(Long Id, Float distancia, int nvips) {
		Carnet carnet = carnetRepository.findById(Id).orElse(null);
		if (carnet != null) {
			carnet.setDistancia((float) (carnet.getDistancia() + distancia));

			carnet.setNvips(carnet.getNvips() + nvips);
			carnetRepository.save(carnet);

		} else {
			System.out.println("sadgi");
		}
	}

	public void actualizarCarnet(Long Id, int nvips) {
		Carnet carnet = carnetRepository.findById(Id).orElse(null);
		if (carnet != null) {

			carnet.setNvips(carnet.getNvips() + nvips);
			carnetRepository.save(carnet);

		} else {
			System.out.println("sadgi");
		}
	}

	public String exportCarnet(Peregrino yo) throws SQLException {

		try {
			Carnet mio = carnetRepository.findById(yo.getId()).orElse(null);
			if (estanciaService.findbyPeregrino(yo.getId()) != null) {

				yo.setEstancias(estanciaService.findbyPeregrino(yo.getId()));
				for (Estancia e : yo.getEstancias()) {
					e.setParada(paradaService.find(e.getParadaId()));
				}

			}
			List<Ruta> rutas = rutaService.obtenerRutasPorPeregrino(yo.getId());
			// con esto ordena por ruta
			rutas.sort(Comparator.comparingInt(Ruta::getOrden));
			List<Parada> peiredes = new ArrayList<>();
			for (Ruta r : rutas) {
				Parada temporalidad = paradaService.find(r.getParadaId());
				peiredes.add(temporalidad);
			}

			yo.setParadas(peiredes);

			if (mio == null || yo == null) {
				System.out.println("Error: No se pudo encontrar el carnet o peregrino asociado al ID del usuario.");
				return null;
			}

			// Create XML Document
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document documento = db.newDocument();

			// Root element
			Element root = documento.createElement("carnet");
			documento.appendChild(root);

			// Carnet ID
			Element ID_ELEMENT = documento.createElement("id");
			ID_ELEMENT.appendChild(documento.createTextNode(String.valueOf(mio.getId())));
			root.appendChild(ID_ELEMENT);

			// Fecha de Expedición
			DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			String FECHA_FORMATADA = mio.getFechaexp().format(FORMATO_FECHA);
			Element FECHA_EXP = documento.createElement("fechaexp");
			FECHA_EXP.appendChild(documento.createTextNode(FECHA_FORMATADA));
			root.appendChild(FECHA_EXP);

			// Parada Inicial
			Element EXPEDIDO_EN = documento.createElement("expedidoen");
			EXPEDIDO_EN.appendChild(documento.createTextNode(mio.getParada_inicial().getNombre()));
			root.appendChild(EXPEDIDO_EN);

			// Peregrino Information
			Element PEREGRINO_ELEMENT = documento.createElement("peregrino");
			root.appendChild(PEREGRINO_ELEMENT);

			Element NOMBRE = documento.createElement("nombre");
			NOMBRE.appendChild(documento.createTextNode(yo.getNombre()));
			PEREGRINO_ELEMENT.appendChild(NOMBRE);

			Element NACIONALIDAD = documento.createElement("nacionalidad");
			NACIONALIDAD.appendChild(documento.createTextNode(yo.getNacionalidad()));
			PEREGRINO_ELEMENT.appendChild(NACIONALIDAD);

			// Current Date
			Element HOY = documento.createElement("hoy");
			HOY.appendChild(documento.createTextNode(new SimpleDateFormat("dd-MM-yyyy").format(new Date())));
			root.appendChild(HOY);

			// Distancia Total
			Element DISTANCIA_TOTAL_ELEMENT = documento.createElement("distanciatotal");
			DISTANCIA_TOTAL_ELEMENT.appendChild(documento.createTextNode(String.format("%.1f", mio.getDistancia())));
			root.appendChild(DISTANCIA_TOTAL_ELEMENT);

			// Paradas
			Element PARADAS_ELEMENT = documento.createElement("paradas");
			root.appendChild(PARADAS_ELEMENT);

			int ORDEN = 1;
			for (Parada parada : yo.getParadas()) {
				Element PARADA_ELEMENT = documento.createElement("parada");

				Element ORDEN_ELEMENT = documento.createElement("orden");
				ORDEN_ELEMENT.appendChild(documento.createTextNode(String.valueOf(ORDEN++)));
				PARADA_ELEMENT.appendChild(ORDEN_ELEMENT);

				Element NOMBRE_PARADA = documento.createElement("nombre");
				NOMBRE_PARADA.appendChild(documento.createTextNode(parada.getNombre()));
				PARADA_ELEMENT.appendChild(NOMBRE_PARADA);

				Element REGION = documento.createElement("region");
				REGION.appendChild(documento.createTextNode(String.valueOf(parada.getRegion())));
				PARADA_ELEMENT.appendChild(REGION);

				PARADAS_ELEMENT.appendChild(PARADA_ELEMENT);
			}

			// Estancias
			if (yo.getEstancias() != null) {
				Element ESTANCIAS_ELEMENT = documento.createElement("estancias");
				root.appendChild(ESTANCIAS_ELEMENT);

				for (Estancia ESTANCIA : yo.getEstancias()) {
					Element ELEMENTO_ESTANCIA = documento.createElement("estancia");

					Element ID_ESTANCIA = documento.createElement("id");
					ID_ESTANCIA.appendChild(documento.createTextNode(String.valueOf(ESTANCIA.getId())));
					ELEMENTO_ESTANCIA.appendChild(ID_ESTANCIA);

					String FECHA_FORM = ESTANCIA.getFecha().format(FORMATO_FECHA);
					Element FECHA = documento.createElement("fecha");
					FECHA.appendChild(documento.createTextNode(FECHA_FORM));
					ELEMENTO_ESTANCIA.appendChild(FECHA);

					Element PARADA_ESTANCIA = documento.createElement("parada");
					PARADA_ESTANCIA.appendChild(documento.createTextNode(ESTANCIA.getParada().getNombre()));
					ELEMENTO_ESTANCIA.appendChild(PARADA_ESTANCIA);

					if (ESTANCIA.isVip()) {
						Element VIP = documento.createElement("vip");
						ELEMENTO_ESTANCIA.appendChild(VIP);
					}

					ESTANCIAS_ELEMENT.appendChild(ELEMENTO_ESTANCIA);
				}
			}

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer;
			try {
				transformer = tf.newTransformer();
				// configuro el transofrmer para identar el xml
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

				// creo origen DOM y le doy el destino para guardar el xml
				DOMSource ds = new DOMSource(documento);
				String name = yo.getNombre().replaceAll(" ", "");
				StreamResult sr = new StreamResult(new File("src/main/resources/escritura/" + name + "_peregrino.xml"));
				// guardado en la ruta especificada
				transformer.transform(ds, sr);
				return "src/main/resources/escritura/" + name + "_peregrino.xml";

			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}

}
