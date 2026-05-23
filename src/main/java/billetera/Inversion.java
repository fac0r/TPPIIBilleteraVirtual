package billetera;

import java.time.LocalDate;


public abstract class Inversion extends Actividad {
	
	
	
	private String dniAsociado;
	private String cvuAsociado;
	private boolean precancelable;
	private String estadoInversion;
	private double tasaInteres;
	private int plazoDias;
	private String tipoDeInversion;
	private LocalDate fechaDeVencimiento;


	public Inversion(String dniAsociado, String cvuAsociado,boolean precancelable,double tasaInteres,int plazoDias,  LocalDate fecha, double monto, String detalle,  String tipoDeInversion, LocalDate fechaDeVencimiento) {
		super(generarId(), fecha, TipoOperacion.INVERSION, monto, detalle);
		
		this.dniAsociado=dniAsociado;
		this.cvuAsociado=cvuAsociado;
		this.precancelable=precancelable;
		this.estadoInversion=EstadoInversion.ACTIVA;
		this.tasaInteres=tasaInteres;
		this.plazoDias=plazoDias;
		this.tipoDeInversion=tipoDeInversion;
		this.fechaDeVencimiento=fechaDeVencimiento;
	
	}
	
	

	public static LocalDate calcularFechaDeVencimiento(int cantDias) {
		 
	    LocalDate fecha = Utilitarios.hoy().plusDays(cantDias);
	    return fecha;
	}

	

		public String getIdActividad () {
			
			return  mostrarIdActividad();
		}
		
	 

	@Override
	public String toString() {
		return "Inversion [cvuAsociado=" + cvuAsociado + ", precancelable=" + precancelable + ", estadoInversion="
				+ estadoInversion + ", tasaInteres=" + tasaInteres + ", plazoDias=" + plazoDias + ", tipoDeInversion="
				+ tipoDeInversion + ", toString()=" + super.toString() + "]";
	}


	
	 

}
