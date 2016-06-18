package controlador;

import general.Credenciales;

import java.util.Date;
import java.util.List;

import modelo.Destino;
import modelo.Envios;
import modelo.Estatus;
import modelo.TipoEnvio;
import modelo.Usuarios;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

public class ListaEnvioGeneral extends GenericForwardComposer{
	private Listbox listaEnvios;
	private Credenciales Credenciales;;
	private Listbox listaEnviosEntregados;
	
	
	public void onCreate$grContenedor(){
		Credenciales =(Credenciales) session.getAttribute("Credenciales");
		CargarLista();
	}
	public ListaEnvioGeneral() {
		// TODO Auto-generated constructor stub
	}
	public void onSelect$tab1(){
		LimpiarList(listaEnvios);
		CargarLista();
	}
	
	public void onSelect$tab2(){
		LimpiarList(listaEnviosEntregados);
		CargarListaEntregados();
	}
	
	public void CargarLista(){
		List<Object[]> Envios = null;
		List<Object[]> Estatus = null;
		List<Object[]> Usuario = null;
		Integer idTipo = 0;
		String Tipo = "";
		Envios objEnvios = new Envios();
		Estatus objEstatus = new Estatus();
		Usuarios objUsuario = new Usuarios();
		if (Credenciales.getTipo().equals("AD")||Credenciales.getTipo().equals("ROOT")){
			Envios = objEnvios.ConsultarEnvios();
		}else if(Credenciales.getTipo().equals("EM")||Credenciales.getTipo().equals("ADP")||Credenciales.getTipo().equals("ADV")){
			Envios = objEnvios.ConsultarEnviosxEmpleado(Credenciales.getId_destino());
		}
		
		if(Envios.size()>0){			
				for (int i = 0; i < Envios.size(); i++) {
					final Listitem item = new Listitem();					
					item.appendChild(new Listcell((String) Envios.get(i)[0].toString()));					
					item.appendChild(new Listcell((String) Envios.get(i)[5].toString()));
					Usuario = objUsuario.ConsultarUsuariosxId((String) Envios.get(i)[1].toString());
					if(Usuario.size()>0){			
						for (int e = 0; e < Usuario.size(); e++) {
							item.appendChild(new Listcell((String) Usuario.get(e)[5]));
							item.appendChild(new Listcell((String) Usuario.get(e)[2]+" "+(String) Usuario.get(e)[3]));
						}
					}
					idTipo = (Integer) Envios.get(i)[12];
					if(idTipo == 1){
						Tipo = "Lib";
					}else{
						Tipo = "Ft3";
					}
					item.appendChild(new Listcell((String) Envios.get(i)[8].toString()+" "+Tipo));
					item.appendChild(new Listcell((String) Envios.get(i)[9].toString()+ " $"));
					Estatus = objEstatus.ConsultarEstatus(Envios.get(i)[2].toString());
					if(Estatus.size()>0){			
						for (int e = 0; e < Estatus.size(); e++) {
							item.appendChild(new Listcell((String) Estatus.get(e)[1]));
						}
					}
					listaEnvios.appendChild(item);
				}
		}
	}
	
	public void CargarListaEntregados(){
		List<Object[]> Envios = null;
		List<Object[]> Estatus = null;
		List<Object[]> Usuario = null;
		Integer idTipo = 0;
		String Tipo = "";
		Envios objEnvios = new Envios();
		Estatus objEstatus = new Estatus();
		Usuarios objUsuario = new Usuarios();
		if (Credenciales.getTipo().equals("AD")||Credenciales.getTipo().equals("ROOT")){
			Envios = objEnvios.ConsultarEnviosEntregados();
		}else if(Credenciales.getTipo().equals("EM")||Credenciales.getTipo().equals("ADP")||Credenciales.getTipo().equals("ADV")){
			Envios = objEnvios.ConsultarEnviosEntregadosxEmpleado(Credenciales.getId_destino());
		}
		
		if(Envios.size()>0){			
				for (int i = 0; i < Envios.size(); i++) {
					final Listitem item = new Listitem();					
					item.appendChild(new Listcell((String) Envios.get(i)[0].toString()));					
					item.appendChild(new Listcell((String) Envios.get(i)[5].toString()));
					Usuario = objUsuario.ConsultarUsuariosxId((String) Envios.get(i)[1].toString());
					if(Usuario.size()>0){			
						for (int e = 0; e < Usuario.size(); e++) {
							item.appendChild(new Listcell((String) Usuario.get(e)[5]));
							item.appendChild(new Listcell((String) Usuario.get(e)[2]+" "+(String) Usuario.get(e)[3]));
						}
					}
					idTipo = (Integer) Envios.get(i)[12];
					if(idTipo == 1){
						Tipo = "Lib";
					}else{
						Tipo = "Ft3";
					}
					item.appendChild(new Listcell((String) Envios.get(i)[8].toString()+" "+Tipo));
					item.appendChild(new Listcell((String) Envios.get(i)[9].toString()+" $"));
					Estatus = objEstatus.ConsultarEstatus(Envios.get(i)[2].toString());
					if(Estatus.size()>0){			
						for (int e = 0; e < Estatus.size(); e++) {
							item.appendChild(new Listcell((String) Estatus.get(e)[1]));
						}
					}
					listaEnviosEntregados.appendChild(item);
				}
		}
	}
	
	
	public void onSelect$listaEnvios(){
		List<Object[]> Envio = null;
		Envios objEnvios = new Envios();
		Usuarios objUsuario = new Usuarios();
		Destino objDestino = new Destino();
		Estatus objEstatus = new Estatus();
		TipoEnvio objTipoEnvio = new TipoEnvio();
		
		
		if (listaEnvios.getSelectedItem() != null) {
			List hijo = listaEnvios.getSelectedItem().getChildren();
			Envio = objEnvios.ConsultarEnviosxId(((Listcell) hijo.get(0)).getLabel());
			if(Envio.size()>0){			
				for (int i = 0; i < Envio.size(); i++) {
					
					objEnvios.setIdEnvios((Integer) Envio.get(i)[0]);
					objUsuario.setIdUsuario((Integer) Envio.get(i)[1]);
					objEnvios.setUsuarios(objUsuario);
					objEstatus.setIdEstatus((Integer) Envio.get(i)[2]);
					objEstatus.setSiglas((String) Envio.get(i)[12]);
					objEnvios.setEstatus(objEstatus);
					objDestino.setIdDestino((Integer) Envio.get(i)[3]);
					objEnvios.setDestino(objDestino);					
					objEnvios.setFechaRegistro((Date) Envio.get(i)[4]);
					objEnvios.setFechaModificacion((Date) Envio.get(i)[5]);
					objEnvios.setTracking((String) Envio.get(i)[6]);
					objEnvios.setDescripcion((String) Envio.get(i)[7]);					
					objEnvios.setPeso((Double) Envio.get(i)[8]);
					objEnvios.setValor((Double) Envio.get(i)[9]);
					objEnvios.setListo((Boolean) Envio.get(i)[10]);
					objEnvios.setEmpresa((String) Envio.get(i)[11]);
					objTipoEnvio.setIdTipoEnvio((Integer) Envio.get(i)[13]);
					objEnvios.setTipoEnvio(objTipoEnvio);
					
			
				
				}
			}		
		}
		
		
		final Window ventana = (Window) Executions.createComponents("Envio.zul", null, null);
		ventana.setMaximizable(false);
		ventana.setClosable(true);
		ventana.setAttribute("Envio",objEnvios);
		ventana.doModal();
		LimpiarList(listaEnvios);
		CargarLista();
	}
	
	public void onSelect$listaEnviosEntregados(){
		List<Object[]> Envio = null;
		Envios objEnvios = new Envios();
		Usuarios objUsuario = new Usuarios();
		Destino objDestino = new Destino();
		Estatus objEstatus = new Estatus();
		TipoEnvio objTipoEnvio = new TipoEnvio();
		
		
		if (listaEnviosEntregados.getSelectedItem() != null) {
			List hijo = listaEnviosEntregados.getSelectedItem().getChildren();
			Envio = objEnvios.ConsultarEnviosxId(((Listcell) hijo.get(0)).getLabel());
			if(Envio.size()>0){			
				for (int i = 0; i < Envio.size(); i++) {
					
					objEnvios.setIdEnvios((Integer) Envio.get(i)[0]);
					objUsuario.setIdUsuario((Integer) Envio.get(i)[1]);
					objEnvios.setUsuarios(objUsuario);
					objEstatus.setIdEstatus((Integer) Envio.get(i)[2]);
					objEstatus.setSiglas((String) Envio.get(i)[12]);
					objEnvios.setEstatus(objEstatus);
					objDestino.setIdDestino((Integer) Envio.get(i)[3]);
					objEnvios.setDestino(objDestino);					
					objEnvios.setFechaRegistro((Date) Envio.get(i)[4]);
					objEnvios.setFechaModificacion((Date) Envio.get(i)[5]);
					objEnvios.setTracking((String) Envio.get(i)[6]);
					objEnvios.setDescripcion((String) Envio.get(i)[7]);					
					objEnvios.setPeso((Double) Envio.get(i)[8]);
					objEnvios.setValor((Double) Envio.get(i)[9]);
					objEnvios.setListo((Boolean) Envio.get(i)[10]);
					objEnvios.setEmpresa((String) Envio.get(i)[11]);
					objTipoEnvio.setIdTipoEnvio((Integer) Envio.get(i)[13]);
					objEnvios.setTipoEnvio(objTipoEnvio);
			
				
				}
			}		
		}
		
		
		final Window ventana = (Window) Executions.createComponents("Envio.zul", null, null);
		ventana.setMaximizable(false);
		ventana.setClosable(true);
		ventana.setAttribute("Envio",objEnvios);
		ventana.doModal();
		LimpiarList(listaEnviosEntregados);
		CargarListaEntregados();
	}
	
	void LimpiarList(Listbox lista) {
		int n = 0;
		
		while (lista.getItems().size() > n) {
			lista.removeItemAt(n);
		}
		lista.setRows(0);
	}
	
	
}
