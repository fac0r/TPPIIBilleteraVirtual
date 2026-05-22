package billetera;

import java.util.HashMap;
import java.util.Map;

public class Empresa {
	
	
	private String nombre;
	private String cuit;
	private Map <String, String> usuariosAutorizados = new HashMap<> (); //Es String String porque el usuario puede no ser cliente del banco.
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
	
	public void usuarioAutorizado (String dni) {
		
		if (!usuariosAutorizados.containsKey(dni)) {throw new IllegalArgumentException("El usuario no está autorizado");}
	
		
		
	}
	
	public  void agregarPersonaAutorizada(String dni) {
		if (usuariosAutorizados.containsKey(dni)) throw new IllegalArgumentException("El usuario ya está autorizado");
		usuariosAutorizados.put(dni, dni);
		
	}
	


	@Override
	public String toString() {
		return "Empresa [nombre=" + nombre + ", cuit=" + cuit + ", usuariosAutorizados=" + usuariosAutorizados + "]";
	}
	
	
	
	
}
