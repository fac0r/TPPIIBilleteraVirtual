package billetera;

import java.time.LocalDate;

public abstract class Actividad {
	
	private static int contadorActividad = 0;
	private String idActividad;
	private LocalDate fecha;
	private String tipoOperacion;  //debe ser ("INVERSION O TRANSFERENCIA") 
	private double monto; 
	private String detalle;
	
	public Actividad(String idActividad, LocalDate fecha, String tipoOperacion, double monto, String detalle) {
		
		//IREP
		
		if (idActividad == null) 
		{throw new IllegalArgumentException("El id de la actividad no puede ser null");}
		if (fecha ==null) {
			throw new IllegalArgumentException("La inversion debe tener una fecha asociada");
		}
		
		if (tipoOperacion==null) 
		{throw new IllegalArgumentException("Se debe especificar el tipo de operacion");}
		
		if (monto<=0) {throw new IllegalArgumentException
			("Toda actividad debe tener un monto mayor a cero ");}
		
		
		this.idActividad = idActividad;
		this.fecha = fecha;
		this.tipoOperacion = tipoOperacion;
		this.monto = monto;
		this.detalle = detalle;
	}

	public static String generarId() {
	    contadorActividad++;
	    String id = String.valueOf(contadorActividad);
	    return id;
	}
	
	public String obtenerIdActividad() {
        return idActividad;
    }
   
	public String obtenerTipoOperacion() {
        return tipoOperacion;
    }
	
    public double obtenerMonto() {
        return monto;
    }

	public LocalDate obtenerFecha() {
        return fecha;
    }

	public String obtenerDetalle() {
        return detalle;
    }

	@Override
	public String toString() {
		return "Actividad [idActividad=" + idActividad + ", fecha=" + fecha + ", tipoOperacion=" + tipoOperacion
				+ ", monto=" + monto + ", detalle=" + detalle + "]";
	}



}
