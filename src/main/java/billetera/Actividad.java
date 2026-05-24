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

	
	
	
	private String getIdActividad() {
		return idActividad;
	}

	public String mostrarIdActividad() {
		return getIdActividad();
	}
   
	



	private String getTipoOperacion() {
		return tipoOperacion;
	}


	public String mostrarTipoOperacion() {
		return getTipoOperacion();
	}



	private double getMonto() {
		return monto;
	}

	public String mostrarMonto() {
		 return String.valueOf(getMonto());
	} 

	public double  mostrarMontoDouble() {
		 return getMonto();
	} 


	private LocalDate getFecha() {
		return fecha;
	}
	
	public LocalDate mostrarFecha() {
		return getFecha();
	}





	private String getDetalle() {
		return detalle;
	}
	
	public String mostrarDetalle() {
		return getDetalle();
	}









	@Override
	public String toString() {
		return "Actividad [idActividad=" + idActividad + ", fecha=" + fecha + ", tipoOperacion=" + tipoOperacion
				+ ", monto=" + monto + ", detalle=" + detalle + "]";
	}

	
	
	
	

}
