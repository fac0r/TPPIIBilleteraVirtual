package billetera;

import java.util.HashSet;
import java.util.Set;

public class Empresa {

	private String nombre;
	private String cuit;
	private String telefono;
	private String email;
	private String nombreResponsable;
    private Set<String> dniUsuariosAutorizados = new HashSet<>();
	
	
	public Empresa( String cuit, String nombre, String telefono, String mail, String nombreResponsable) {
		super();
		
		//IREP
	
		if(cuit==null)
		{throw new IllegalArgumentException ("Se debe informar el CUIT");}
			if (nombre==null)
			{throw new IllegalArgumentException("La empresa debe tener un nombre");}
		if(telefono==null)
		{throw new IllegalArgumentException ("La empresa debe informar un telefono de contacto");}
		if (mail==null)
		{throw new IllegalArgumentException("La empresa debe informar un mail de contacto");}
		if (nombreResponsable==null)
		{throw new IllegalArgumentException("Se debe informar el titular de la empresa");}
			
		this.nombre = nombre;
		this.cuit = cuit;
		this.telefono=telefono;
		this.email=mail;
		this.nombreResponsable=nombreResponsable;
		
	}
	
	public boolean usuarioAutorizado(String dni) {
    
        return dniUsuariosAutorizados.contains(dni);
    }
	
	public void validarUsuarioAutorizado(String dni) {
        
        if (!usuarioAutorizado(dni)) {
            throw new IllegalArgumentException("error: el usuario con DNI " + dni + " no esta autorizado");
        }
    }

	public void agregarPersonaAutorizada(String dni) {
    
        if (usuarioAutorizado(dni)) {
            throw new IllegalArgumentException("el usuario con DNI " + dni + " ya se encuentra autorizado");
        }	
  
        dniUsuariosAutorizados.add(dni);	
    }
	
	public String obtenerCuit() { 
		return cuit; 
	}
	
    public String obtenerNombre() { 
    	return nombre; 
    }
	
	@Override
	public String toString() {
		return "Empresa [nombre=" + nombre + ", cuit=" + cuit + ", usuariosAutorizados=" + dniUsuariosAutorizados + "]";
	}

}
