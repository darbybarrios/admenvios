package controlador;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import modelo.Destino;
import modelo.Pais;
import modelo.TipoUsuario;
import modelo.Usuarios;

import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;



import general.*;
 

public class RegistrarUsuario extends GenericForwardComposer{
	private Combobox cmbPais;
	private Textbox txtCi;
	private Textbox txtNombre;
	private Textbox txtApellido;
	private Textbox txtCorreo;
	private Textbox txtTelefono;
	private Textbox txtDireccion;
	private Textbox txtClave;
	private Radio ra1;
	private Radio ra2;
	private Window windregistro;
	private Credenciales Credenciales;
	
	
	public void onCreate$windregistro(){
		Credenciales =(Credenciales) session.getAttribute("Credenciales");
		LlenarComboPais();
	}
	
	public void onClick$btnRegistrar()throws Exception{
		Guardar();
	}
	public RegistrarUsuario() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void LlenarComboPais(){
		try {			
			Pais objPais = new Pais();
			List<Object[]> lista = null;
			lista = objPais.ConsultarPaisActivo();
			if (lista.size() > 0) {
				int i = 0;
				while (i < lista.size()) {
					Comboitem combo = new Comboitem();
					combo.setValue((Integer)lista.get(i)[0]);
					combo.setLabel((String)lista.get(i)[1]);
					cmbPais.appendChild(combo);
					++i;
				}
				
				
			} else {
				Comboitem combo = new Comboitem();
				combo.setValue(0);
				combo.setLabel("- No existen estados registradas -");
				cmbPais.appendChild(combo);
			}
		} catch (Exception ex) {
			System.out.println("Excepcion - LlenarComboPais - " + ex.toString());
		}
		
	}
	
	public void Guardar()throws Exception{
		try{
			Date fecha = new Date();
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
			Usuarios objUsuario = new Usuarios();
			Pais objPais = new Pais();
			Destino objDestino = new Destino();
			TipoUsuario objTipo = new TipoUsuario();
			
			if(cmbPais.getSelectedItem() != null){				
			
				objPais.setIdPais(new Integer (cmbPais.getSelectedItem().getValue().toString()));
				
				objUsuario.setPais(objPais);
				objUsuario.setUsuario(txtCi.getText().trim());
				objUsuario.setNombre(txtNombre.getText().trim());
				objUsuario.setApellido(txtApellido.getText().trim());
				objUsuario.setCorreo(txtCorreo.getText().trim());
				objUsuario.setTelefono(txtTelefono.getText().trim());
				objUsuario.setDireccion(txtDireccion.getText().trim());
				objUsuario.setClave(EncriptarClave.Encriptar(txtClave.getText()));
				objUsuario.setEstatus(true);
				objUsuario.setActivo(false);
				objUsuario.setFechaRegistro(formato.format(fecha));
				objTipo.setIdTipoUsuario(2);
				objUsuario.setTipoUsuario(objTipo);
				if(Validar(txtCi.getText().trim())){
					if (ra1.isChecked()){
						objDestino.setIdDestino(new Integer (ra1.getValue().toString()));
						objUsuario.setDestino(objDestino);				
						objUsuario.incluir(objUsuario);	
						EnviarCorreo();
						Messagebox.show("Usuario registrado con exito. Muy pronto le activaremos el usuario", "Mensaje",Messagebox.OK, Messagebox.INFORMATION);				
						windregistro.detach();
					}else if(ra2.isChecked()){
						objDestino.setIdDestino(new Integer (ra2.getValue().toString()));
						objUsuario.setDestino(objDestino);				
						objUsuario.incluir(objUsuario);
						EnviarCorreo();
						Messagebox.show("Usuario registrado con exito. Muy pronto le activaremos el usuario", "Mensaje",Messagebox.OK, Messagebox.INFORMATION);				
						windregistro.detach();
					}else{
						Messagebox.show("Debe seleccionar un destino", "Mensaje",Messagebox.OK, Messagebox.INFORMATION);
					}
				}else{
					Messagebox.show("Ya esta cedula se encuentra registrada", "Mensaje",Messagebox.OK, Messagebox.INFORMATION);
					txtCi.setFocus(true);
				}
			}else{
				Messagebox.show("Debe seleccionar un Pais", "Mensaje",Messagebox.OK, Messagebox.INFORMATION);
			}
		
			
		}catch (Exception ex) {
			throw ex;
		}
		
	}
	
	private void jbInit() throws Exception {
		
	}
	
	private Boolean Validar(String cedula){
		Boolean existe = true;
		List<Object[]> listaUsuario = null;
		Usuarios objUsuario = new Usuarios();
		listaUsuario = objUsuario.ConsultarUsuarioXLoginActivo(cedula);
		if(listaUsuario.size()>0){			
			existe = false;
			
		}
		return existe;
	}
	
	
	public void EnviarCorreo() {
		  
		// Recipient's email ID needs to be mentioned.
	      String to = txtCorreo.getText().trim();
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
	         message.setSubject("Detalles de la cuenta de "+txtNombre.getText()+" "+txtApellido.getText()+" en CargoBox Panama (pendiente de aprobación administrativa)");

	         // Now set the actual message
	         
	         mensaje = "Hola "+txtNombre.getText()+" "+txtApellido.getText()+", \n";
	         mensaje += " \n";
	         mensaje += "Gracias por registrarse en CARGO BOX. Estamos revisando su información. \n";
	         mensaje += " \n";
	         mensaje += "Una vez haya sido aprobado, recibirá un correo electrónico con su código \n";
	         mensaje += "de cliente y los detalles para el uso del casillero. \n";
	         mensaje += " \n";
	         mensaje += "Att, \n";
	         mensaje += "-- El equipo de CARGO BOX \n";
	         
	         
	         message.setText(mensaje);

	         // Send message
	         Transport t = session.getTransport("smtp");
	         t.connect("contacto@cargoboxpanama.com","18736830");
	         t.sendMessage(message,message.getAllRecipients());
	         t.close();
	         
	         MimeMessage message1 = new MimeMessage(session);
	         

	         // Set From: header field of the header.
	         message1.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.

	         // Set Subject: header field
	         message1.setSubject("Registro de Nuevo Usuario");
	         
	         mensaje ="Nuevo usuario registrado \n";
	         
	         
	         message1.addRecipient(Message.RecipientType.TO,
                     new InternetAddress("contacto@cargoboxpanama.com"));
	         message1.setText(mensaje);
	         Transport d = session.getTransport("smtp");
	         d.connect("contacto@cargoboxpanama.com","18736830");
	         d.sendMessage(message1,message1.getAllRecipients());
	         
	         
	         d.close();
	         //Transport.send(message);
	         System.out.println("Sent message successfully....");



        } catch (Exception e) {
    	    e.printStackTrace();
    	}
    }
	
	

}
