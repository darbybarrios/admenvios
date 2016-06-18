package controlador;

import general.Credenciales;

import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;

public class Informacion extends GenericForwardComposer{
	private Credenciales Credenciales;
	private Label lblNombre;
	private Label lblDireccion;
	private Label lblNombre1;
	private Label lblDireccion1;
	private Caption caption;
	private Groupbox group;
	public void onCreate$grContenedor(){
		Credenciales =(Credenciales) session.getAttribute("Credenciales");
		if(Credenciales.getId_destino() == 1){
			caption.setLabel("Información para el envio aereo");
		}else{
			group.setVisible(false);
		}
		
		
		lblNombre.setValue(Credenciales.getNombre()+" "+Credenciales.getApellido());
		lblDireccion.setValue("7230 NW 56th Street / "+Credenciales.getIndentificador());	
		
		lblNombre1.setValue(Credenciales.getNombre()+" "+Credenciales.getApellido());
		lblDireccion1.setValue("8020 NW 60th St / CargoBox");
	}
	public Informacion() {
		// TODO Auto-generated constructor stub
	}

}
