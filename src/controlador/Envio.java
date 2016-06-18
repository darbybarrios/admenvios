package controlador;


import general.Credenciales;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import modelo.Envios;
import modelo.Estatus;
import modelo.TipoEnvio;
import modelo.TipoUsuario;
import modelo.Usuarios;

public class Envio extends GenericForwardComposer {
	private Window windEnvio;
	private Envios envio;
	private Textbox txtCasillero;
	private Textbox txtUsuario;
	private Textbox txtNombre;
	private Textbox txtCorreo;
	private Textbox txtTracking;
	private Textbox txtDescripcion;
	private Textbox txtEmpresa;
	private Textbox txtTipoEnvio;
	private Datebox txtFecha;
	private Doublebox txtVolumen;
	private Doublebox txtValor;
	private Combobox cmbEstatus;
	private Button btnModificar;	
	private Credenciales Credenciales;
	private Label lblVolumen;
	
	
	public Envio() {
		// TODO Auto-generated constructor stub
	}
	
	public void onCreate$windEnvio(){
		Credenciales =(Credenciales) session.getAttribute("Credenciales");
		envio = (Envios) windEnvio.getAttribute("Envio", true);
		LlenarComboEstatus();
		CargarEnvio();
		
	}
	
	public void CargarEnvio(){
		Integer idTipo  = 0;
		Usuarios objUsuario = new Usuarios();
		TipoEnvio objTipoEnvio = new TipoEnvio();
		List<Object[]> listaUsuario = null;
		List<Object[]> listaEnvio = null;
		listaUsuario = objUsuario.ConsultarUsuariosxId(String.valueOf(envio.getUsuarios().getIdUsuario()));
		if(listaUsuario.size()>0){			
			for (int i = 0; i < listaUsuario.size(); i++) {
				txtCasillero.setText((String)listaUsuario.get(i)[5]);
				txtUsuario.setText((String)listaUsuario.get(i)[1]);
				txtNombre.setText((String)listaUsuario.get(i)[2]+" "+(String)listaUsuario.get(i)[3]);
				txtCorreo.setText((String)listaUsuario.get(i)[13]);
				
			}
		}
		listaEnvio = objTipoEnvio.ConsultarTipoEnvioDesc(envio.getTipoEnvio().getIdTipoEnvio());
		if(listaEnvio.size()>0){
			for (int i = 0; i < listaUsuario.size(); i++) {
				txtTipoEnvio.setText((String)listaEnvio.get(i)[1]);
				idTipo = (Integer)listaEnvio.get(i)[0];
				
			}
		}
		if(idTipo == 1){
			lblVolumen.setValue("Libras");
		}else{
			lblVolumen.setValue("Pie Cubico");
		}
		
		
		txtFecha.setValue(envio.getFechaModificacion());
		txtEmpresa.setText(envio.getEmpresa());
		txtTracking.setText(envio.getTracking());
		txtDescripcion.setText(envio.getDescripcion());
		txtVolumen.setValue(envio.getPeso());
		txtValor.setValue(envio.getValor());
		posicionarCombo(cmbEstatus,String.valueOf(envio.getEstatus().getIdEstatus()));
		
		
	}
	
	public void onClick$btnModificar() throws Exception{
		Date fecha = new Date();
		
		Estatus estatus = new Estatus();
		estatus.setIdEstatus(new Integer(cmbEstatus.getSelectedItem().getValue().toString()));
		envio.setFechaModificacion(fecha);
		envio.setEmpresa(txtEmpresa.getText());
		envio.setTracking(txtTracking.getText());
		envio.setValor(txtValor.getValue());
		envio.setPeso(txtVolumen.getValue());
		envio.setEstatus(estatus);
		envio.modificar(envio);
		if(envio.getEstatus().getIdEstatus() == 5 || envio.getEstatus().getIdEstatus() == 11){
			EnviarCorreo();	
		}
		
		Messagebox.show("Envío modificado con exito.", "Mensaje",Messagebox.OK, Messagebox.INFORMATION);
		windEnvio.detach();
	}
	
	public void LlenarComboEstatus(){
		try {			
			Estatus objEstatus= new Estatus();
			List<Object[]> lista = null;
			lista = objEstatus.ConsultarTodoEstatusDestino(envio.getDestino().getIdDestino());
			
			if (lista.size() > 0) {
				int i = 0;
				while (i < lista.size()) {
					Comboitem combo = new Comboitem();
					combo.setValue((Integer)lista.get(i)[0]);
					combo.setLabel((String)lista.get(i)[1]);
					cmbEstatus.appendChild(combo);
					++i;
				}
				
				
			} else {
				Comboitem combo = new Comboitem();
				combo.setValue(0);
				combo.setLabel("- No existen estados registradas -");
				cmbEstatus.appendChild(combo);
			}
		} catch (Exception ex) {
			System.out.println("Excepcion - LlenarComboEstatus - " + ex.toString());
		}
		
	}
	
	public int SeleccionItem(Combobox combo,String valor) {
		int indice=0;
        if (valor == null) {
  			combo.setSelectedIndex(-1);
  		} else {
  			for (int i = 0; combo.getItemCount()>i; ++i) {
  				org.zkoss.zul.Comboitem item = combo.getItemAtIndex(i);
				if (valor.equals(item.getValue().toString())) {  					
					indice=i;
  					break;
  				}
  			}
  		}
        return indice;
  	}
	
	void posicionarCombo(Combobox combo, String cadena){
		 int registro=0;			 
		 registro = SeleccionItem(combo,cadena);			 
		 combo.setSelectedIndex(registro);
	}
	
	
	public void EnviarCorreo() {
		  
		// Recipient's email ID needs to be mentioned.
	      String to = txtCorreo.getText();
	      String mensaje ="";
		
		  //String to = "darbybarrios@gmail.com";

	      // Sender's email ID needs to be mentioned
	      String from = "contacto@cargoboxpanama.com";
	        
	      

	      // Assuming you are sending email from localhost
	      String host = "mail.cargoboxpanama.com";

	      // Get system properties
	      Properties properties = System.getProperties();

	      // Setup mail server
	     //properties.setProperty("mail.smtp.user", "contacto@cargoboxpanama.com"); 
	    // properties.setProperty("mail.smtp.password", "18736830");
	     properties.setProperty("mail.smtp.host", host);
	     properties.setProperty("mail.smtp.port", "25");
	     properties.setProperty("mail.smtp.auth", "true");
	     properties.setProperty("mail.smtp.starttls.enable", "true");

	      // Get the default Session object.
	      Session session = Session.getDefaultInstance(properties);

	      try{
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO,
	                                  new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject("NOTIFICACiÓN DE ENVIO");

	         // Now set the actual message
	         
	         mensaje="Le informamos que usted ha recibido un envio identificado con los siguientes datos, el cual esta listo para ser retirado: \n";
	         mensaje+="Referencia: "+envio.getTracking()+"  \n";
	         mensaje+="Descripción: "+envio.getDescripcion()+"  \n";
	         
	         if(envio.getDestino().getIdDestino() == 2){
	        	 mensaje+="LUGAR DE ENTREGA \n";
		         mensaje+="Av Balboa entrando por PH Los Delfines 2da calle a mano derecha duplex 7 \n";
	         }else{
	        	 mensaje+="LUGAR DE ENTREGA \n";
		         mensaje+="Carrera 19 entre Av. Moran y calle 6 Barquisimeto. \n";
	         }
	         message.setText(mensaje);

	         // Send message
	         Transport t = session.getTransport("smtp");
	         t.connect("contacto@cargoboxpanama.com","18736830");
	         t.sendMessage(message,message.getAllRecipients());
	         t.close();
	         //Transport.send(message);
	         System.out.println("Sent message successfully....");



        } catch (Exception e) {
    	    e.printStackTrace();
    	}
    }
}
