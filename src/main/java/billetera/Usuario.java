package billetera;

import java.util.HashMap;
import java.util.Map;

public class Usuario {
	
	

	private String dni;
	 private String nombre;
	 private String telefono;
	 private String email;
	 private Map<String, Cuenta> cuentas = new HashMap<>();
	

	 
	 public Usuario(String dni, String nombre, String telefono, String email) {
		
		this.dni = dni;
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
	}
	 
	 
	private String getDni() {
		return dni;
	}
	private String getNombre() {
		return nombre;
	}
	private String getTelefono() {
		return telefono;
	}
	private String getEmail() {
		return email;
	}
	private Map<String, Cuenta> getCuentas() {
		return cuentas;
	}
	
	public void agregarCuenta(String alias, Cuenta cuenta) {
	    this.cuentas.put(alias, cuenta);
	}

	public void eliminarCuenta(String alias) {
	    this.cuentas.remove(alias);
	}
	
	public String getInfoCompleta() {
	    return null;  //Aca podemos hacer algunos metodos para obtener los get que nos interesen sin exponer directamente los get.
	}

	
	@Override
	public String toString() {
		return "Usuario [dni=" + dni + ", nombre=" + nombre + ", telefono=" + telefono + ", email=" + email
				+ ", cuentas=" + cuentas + "]";
	}

}
