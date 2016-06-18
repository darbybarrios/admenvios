package controlador;

import general.Credenciales;

import java.util.Date;
import java.util.List;

import modelo.Envios;
import modelo.Estatus;
import modelo.Usuarios;

import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

public class ListaEnvio extends GenericForwardComposer{
	private Listbox listaEnvios;
	private Credenciales Credenciales;
	
	public void onCreate$grContenedor(){
		Credenciales =(Credenciales) session.getAttribute("Credenciales");
		CargarLista();
	}
	public ListaEnvio() {
		// TODO Auto-generated constructor stub
	}
	
	public void CargarLista(){
		List<Object[]> Envios = null;
		List<Object[]> Estatus = null;
		Integer idTipo = 0;
		String Tipo = "";
		Envios objEnvios = new Envios();
		Estatus objEstatus = new Estatus();
		Envios = objEnvios.ConsultarEnvioxUsuario(Credenciales.getId_usuario().toString());
		if(Envios.size()>0){			
				for (int i = 0; i < Envios.size(); i++) {
					final Listitem item = new Listitem();					
					item.appendChild(new Listcell((String) Envios.get(i)[5].toString()));					
					item.appendChild(new Listcell((String) Envios.get(i)[6]));
					item.appendChild(new Listcell((String) Envios.get(i)[7]));
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

}
