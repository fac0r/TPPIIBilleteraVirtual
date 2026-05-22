package billetera;

import java.time.LocalDate;


public abstract class Inversion extends Actividad {
	
	
	private static int  contadorInversiones=0;
	private String cvuAsociado;
	private boolean precancelable;
	private String estadoInversion;
	private double tasaInteres;
	private int plazoDias;
	private String tipoDeInversion;


	public Inversion(String cvuAsociado,boolean precancelable,double tasaInteres,int plazoDias,  LocalDate fecha, double monto, String detalle,  String tipoDeInversion) {
		super(generarId(), fecha, TipoOperacion.INVERSION, monto, detalle);
		
		this.cvuAsociado=cvuAsociado;
		this.precancelable=precancelable;
		this.estadoInversion=EstadoInversion.ACTIVA;
		this.tasaInteres=tasaInteres;
		this.plazoDias=plazoDias;
		this.tipoDeInversion=tipoDeInversion;
	
	}


	 public  static  String generarId() {
		 contadorInversiones++;
		 return "I" + (contadorInversiones );
	 }


	@Override
	public String toString() {
		return "Inversion [cvuAsociado=" + cvuAsociado + ", precancelable=" + precancelable + ", estadoInversion="
				+ estadoInversion + ", tasaInteres=" + tasaInteres + ", plazoDias=" + plazoDias + ", tipoDeInversion="
				+ tipoDeInversion + ", toString()=" + super.toString() + "]";
	}


	
	 

}
