package billetera;

import java.util.Date;

public abstract class Actividad {
	
	private String idActividad;
	private Date fecha;
	private String tipoOperacion;  //debe ser ("INVERSION O TRANSFERENCIA") 
	private double monto; 
	private String detalle;
	
	
	public Actividad(String idActividad, Date fecha, String tipoOperacion, double monto, String detalle) {
		
		this.idActividad = idActividad;
		this.fecha = fecha;
		this.tipoOperacion = tipoOperacion;
		this.monto = monto;
		this.detalle = detalle;
	}


	@Override
	public String toString() {
		return "Actividad [idActividad=" + idActividad + ", fecha=" + fecha + ", tipoOperacion=" + tipoOperacion
				+ ", monto=" + monto + ", detalle=" + detalle + "]";
	}

	
	
	
	

}
