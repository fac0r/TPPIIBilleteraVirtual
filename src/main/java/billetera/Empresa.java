package billetera;

import java.util.HashMap;
import java.util.Map;

public class Empresa {
	
	
	private String nombre;
	private String cuit;
	private Map <String, Usuario> usuariosAutorizados = new HashMap<> ();
	private String telefono;
	private String email;
	private String nombreResponsable;
	
	
	
	public Empresa( String cuit, String nombre, String telefono, String mail, String nombreResponsable) {
		super();
		this.nombre = nombre;
		this.cuit = cuit;
		this.telefono=telefono;
		this.email=mail;
		this.nombreResponsable=nombreResponsable;
		
	}
	
	


	@Override
	public String toString() {
		return "Empresa [nombre=" + nombre + ", cuit=" + cuit + ", usuariosAutorizados=" + usuariosAutorizados + "]";
	}
	
	
	
	
}
