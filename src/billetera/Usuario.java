package billetera;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Usuario {

	 private String dni;
	 private String nombre;
	 private String telefono;
	 private String email;
	 private double totalInvertido;
	 private Map<String, Cuenta> cuentas = new HashMap<>(); // CVU y CUENTA
 
	 public Usuario(String dni, String nombre, String telefono, String email) {
		
		 //IREP
		 
		 if (dni==null)
		 {throw new IllegalArgumentException ("Se debe informar el dni para registrarse");}
		if (nombre==null)
		 {throw new IllegalArgumentException ("Se debe informar el nombre para registrarse");}
		if(telefono==null)
		 {throw new IllegalArgumentException ("Se debe informar un telefono de contacto");}
				
		if (email == null)
		 {throw new IllegalArgumentException ("Se debe infromar un mail de contacto");}
					
		this.dni = dni;
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
		this.totalInvertido=0;
	}

	
	 private Map<String, Cuenta> getCuentas() {
		return cuentas;
	 }
	
	 public Map <String, Cuenta> devolverGetCuentas() {
		return getCuentas();
	 }
	
	 public void agregarCuenta(String cvu, Cuenta cuenta) {
	    this.cuentas.put(cvu, cuenta);
	 }
 
	 public void eliminarCuenta(String cvu) {
	    this.cuentas.remove(cvu);
	 }
	
	 private StringBuilder devolverFormatoMisCuentasUsuario (String tipo, String alias, String cvu) {

		StringBuilder cuenta = new StringBuilder(); 
		cuenta.append(tipo);
		cuenta.append(": ");
		cuenta.append(alias);
		cuenta.append(": ");
		cuenta.append(cvu);
		
		return cuenta;
	 }
	
	 public List<String> obtenerMisCuentas() {
		
		List<String> misCuentas = new ArrayList<>();
		
		for(Cuenta c : cuentas.values() ) {
			String tipo= c.obtenerTipoDeCuenta();
			String alias= c.obtenerAlias();
			String cvu= c.obtenerCvu();
			StringBuilder datosCuenta= devolverFormatoMisCuentasUsuario(tipo, alias, cvu);
			
			misCuentas.add(datosCuenta.toString());
		}
		
		
		return  misCuentas ;
	 }

	 
	
	 public double obtenerTotalInvertido () {
		return totalInvertido;
	 }
	 

		public void agregarMontoInvertido(double monto) {
			
			this.totalInvertido= totalInvertido + monto;
			
		}


		public void restarMontoInvertido(double monto) {
			this.totalInvertido= totalInvertido - monto;
			
		}
	 
	 public String obtenerDni() { 
		 return dni; 
	}
	 
	 public String obtenerNombre() { 
	    return nombre; 
	 }
	
	

	public List<String> consultarHistorialUsuario() {

		
		
		
		Map<String, Cuenta> cuentasDelUsuario= devolverGetCuentas();
		List <String>  historialUsuario=new ArrayList<>();
		
		

		
		
		for (Cuenta c : cuentasDelUsuario.values()) {
			 Map <String, Actividad> historialCuentas= c.obtenerHistorialCuenta(); 
			 for(Actividad act: historialCuentas.values()) {
				 historialUsuario.add(act.obtenerIdActividad());
			 }
			 
			}
		
		
		
		return historialUsuario;
		
	}

	 @Override
	 public String toString() {
		return "Usuario [dni=" + dni + ", nombre=" + nombre + ", telefono=" + telefono + ", email=" + email
				+ ", cuentas=" + cuentas + "]";
	 }


}
