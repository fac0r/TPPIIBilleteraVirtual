package billetera;

import java.util.Date;

public class RentaFija extends Inversion {
	
	public static final double TASA_INTERES = 0.20;
	private Date fechaDeVencimiento;
	

	public RentaFija(String cvuAsociado, boolean precancelable,int plazoDias, Date fecha, double monto, String detalle,
			 Date fechaDeVencimiento) {
		super( cvuAsociado, precancelable, TASA_INTERES ,plazoDias , fecha, monto, detalle, TipoDeInversion.RENTAFIJA); 
		
			this.fechaDeVencimiento=fechaDeVencimiento;
			
			
	}



	@Override
	public String toString() {
		return "RentaFija [fechaDeVencimiento=" + fechaDeVencimiento + ", toString()=" + super.toString() + "]";
	}}
