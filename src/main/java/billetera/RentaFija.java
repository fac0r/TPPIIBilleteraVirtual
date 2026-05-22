package billetera;

import java.time.LocalDate;


public class RentaFija extends Inversion {
	
	public static final double TASA_INTERES = 0.20;
	private LocalDate fechaDeVencimiento;
	

	public RentaFija(String cvuAsociado, boolean precancelable,int plazoDias, LocalDate fecha, double monto, String detalle,
			 LocalDate fechaDeVencimiento) {
		super( cvuAsociado, precancelable, TASA_INTERES ,plazoDias , fecha, monto, detalle, TipoDeInversion.RENTAFIJA); 
		
			this.fechaDeVencimiento=fechaDeVencimiento;
			
			
	}



	@Override
	public String toString() {
		return "RentaFija [fechaDeVencimiento=" + fechaDeVencimiento + ", toString()=" + super.toString() + "]";
	}}
