package com.luisdbb.tarea3AD2024base.view;

import java.util.ResourceBundle;

public enum FxmlView {
	INVITADO {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("user.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/MenuInvitado.fxml";
		}
	},
	ADMIN1 {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("admin.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/MenuMainAdmin.fxml";
		}
	},
	ADMIN2 {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("servicios.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/ServiciosSeVen.fxml";
		}
	},
	ENVIAR{
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("envio.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/EnvioCasa.fxml";
		}
	},
	LOGIN {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("login.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Login.fxml";
		}
	},
	CONJUNTO {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("servicios.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/ConjuntoServicios.fxml";
		}
	},
	VERENVIOS {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("ver.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/VerEnvios.fxml";
		}
	},
	
	
	ADMIN{
		@Override
		public String getTitle() {
		return getStringFromResourceBundle("admin.title");
	}

	@Override
	public String getFxmlFile() {
		return "/fxml/MenuAdmin.fxml";
	}
},
	EXPORT{
	@Override
	public String getTitle() {
	return getStringFromResourceBundle("exportar.title");
}

@Override
public String getFxmlFile() {
	return "/fxml/CU3.fxml";
}
},
	PEREGRINO{
	@Override
	public String getTitle() {
	return getStringFromResourceBundle("peregrino.title");
}

@Override
public String getFxmlFile() {
	return "/fxml/ExportPeregrino.fxml";
}
},
	PARADA{
	@Override
	public String getTitle() {
	return getStringFromResourceBundle("paradas.title");
	//change esto pa pulir
}
	

@Override
public String getFxmlFile() {
	return "/fxml/MenuParada.fxml";
}
},
	EDIT{
	@Override
	public String getTitle() {
	return getStringFromResourceBundle("edit.title");
	
}
	

@Override
public String getFxmlFile() {
	return "/fxml/EditarPeregrino.fxml";
}
},VERPEREGRINOS{
	@Override
	public String getTitle() {
	return getStringFromResourceBundle("peregrino.title");
	
}
	

@Override
public String getFxmlFile() {
	return "/fxml/CU11.fxml";
}
},
	SELLAR{
		@Override
		public String getTitle() {
		return getStringFromResourceBundle("sellar.title");
	
	}
		

	@Override
	public String getFxmlFile() {
		return "/fxml/CU5.fxml";
	}
	};
	public abstract String getTitle();

	public abstract String getFxmlFile();

	String getStringFromResourceBundle(String key) {
		return ResourceBundle.getBundle("Bundle").getString(key);
	}
}
