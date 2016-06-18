package controlador;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import general.Credenciales;
import general.EncriptarClave;

import modelo.Destino;
import modelo.Envios;
import modelo.Estatus;
import modelo.TipoEnvio;
import modelo.Usuarios;

import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;


public class RegistrarEnvios extends GenericForwardComposer{
	private Credenciales Credenciales;
	private Datebox txtFecha;
	private Textbox txtTracking;
	private Textbox txtDescripcion;
	private Textbox txtEmpresa;
	private Label lblTracking;
	private Label lblEmpresa;
	private Radio ra1,ra2;
	private Combobox cmbEnvio;
	
	public RegistrarEnvios() {
		// TODO Auto-generated constructor stub
	}
	
	public void onCreate$grContenedor(){
		Credenciales =(Credenciales) session.getAttribute("Credenciales");
		LlenarComboEnvio();
	}
	
	public void onClick$btnRegistrar() throws Exception{
		Guardar();
	}
	
	public void LlenarComboEnvio(){
		try {
			TipoEnvio objTipoEnvio = new TipoEnvio();
			List<Object[]> lista = null;
			
			lista = objTipoEnvio.ConsultarTipoEnvioActivo();
			
			if (lista.size() > 0) {
				int i = 0;
				while (i < lista.size()) {
					Comboitem combo = new Comboitem();
					combo.setValue((Integer)lista.get(i)[0]);
					combo.setLabel((String)lista.get(i)[1]);
					cmbEnvio.appendChild(combo);
					++i;
				}
				
				
			} else {
				Comboitem combo = new Comboitem();
				combo.setValue(0);
				combo.setLabel("- No existen estados registradas -");
				cmbEnvio.appendChild(combo);
			}
		} catch (Exception ex) {
			System.out.println("Excepcion - LlenarComboEnvio - " + ex.toString());
		}
		
	}
	
	
	
	public void Guardar() throws Exception{
		txtFecha.setConstraint("no empty");		
		txtDescripcion.setConstraint("no empty");
		
		Envios objEnvio = new Envios();
		Usuarios objUsuario = new Usuarios();
		Destino objDestino = new Destino();
		Estatus objEstatus = new Estatus();
		TipoEnvio objTipoEnvio = new TipoEnvio();
		
		if(cmbEnvio.getSelectedItem() != null){
			objUsuario.setIdUsuario(Credenciales.getId_usuario());
			objEnvio.setUsuarios(objUsuario);
			objDestino.setIdDestino(Credenciales.getId_destino());
			objEnvio.setDestino(objDestino);
			objEnvio.setFechaRegistro(txtFecha.getValue());
			objEnvio.setFechaModificacion(txtFecha.getValue());
			if(ra1.isChecked()){
				objEnvio.setTracking(txtTracking.getText());
				objEnvio.setEmpresa(txtEmpresa.getText());
			}
			objEnvio.setPeso(0.00);
			objEnvio.setValor(0.00);
			objEnvio.setDescripcion(txtDescripcion.getText());
			objEstatus.setIdEstatus(1);
			objEnvio.setEstatus(objEstatus);
			objEnvio.setListo(true);
			objTipoEnvio.setIdTipoEnvio(Integer.valueOf(cmbEnvio.getSelectedItem().getValue().toString()));
			objEnvio.setTipoEnvio(objTipoEnvio);
			
			objEnvio.incluir(objEnvio);
			EnviarCorreo();
			Messagebox.show("Envio registrado con exito.", "Mensaje",Messagebox.OK, Messagebox.INFORMATION);
			Limpiar();
		}else{
			Messagebox.show("Debe seleccionar un Tipo de Envio", "Mensaje",Messagebox.OK, Messagebox.INFORMATION);
		}
		
		
	}
	
	public void Limpiar(){
		txtFecha.setConstraint("");
		txtTracking.setConstraint("");
		txtDescripcion.setConstraint("");
		txtEmpresa.setConstraint("");
		txtEmpresa.setText("");
		ra1.setChecked(false);
		ra2.setChecked(false);		
		txtFecha.setText("");
		txtTracking.setText("");
		txtDescripcion.setText("");
		lblTracking.setVisible(false);
		txtTracking.setVisible(false);
		lblEmpresa.setVisible(false);
		txtEmpresa.setVisible(false);
		
		
	}

	public void onCheck$ra1(){
		txtTracking.setConstraint("no empty");
		txtEmpresa.setConstraint("no empty");
		lblTracking.setVisible(true);
		txtTracking.setVisible(true);
		lblEmpresa.setVisible(true);
		txtEmpresa.setVisible(true);
		
	}
	
	public void onCheck$ra2(){
		txtTracking.setConstraint("");
		txtEmpresa.setConstraint("");
		lblTracking.setVisible(false);
		txtTracking.setVisible(false);
		lblEmpresa.setVisible(false);
		txtEmpresa.setVisible(false);
		
	}
	
	
	public void EnviarCorreo() {
		String to;
		// Recipient's email ID needs to be mentioned.
		  if(Credenciales.getId_destino() == 2){
			  to = "contacto@cargoboxpanama.com"; 
		  }else{
			  to = "admin@cargoboxpanama.com";
		  }
		  
	      
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
	         
	         mensaje=""+Credenciales.getNombre()+" "+Credenciales.getApellido() +" ("+Credenciales.getIndentificador()+") ha notificado un envio ";
	         
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
