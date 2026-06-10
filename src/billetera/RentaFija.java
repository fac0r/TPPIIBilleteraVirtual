package billetera;

import java.time.LocalDate; 

public class RentaFija extends Inversion {
	
	public static final double TASA_INTERES = 0.20;
	
	public RentaFija(String dniAsociado, String cvuAsociado, boolean precancelable,int plazoDias, LocalDate fecha, double monto, String detalle,
			 LocalDate fechaDeVencimiento) {
		super(dniAsociado, cvuAsociado, precancelable, TASA_INTERES ,plazoDias , fecha, monto, detalle, TipoDeInversion.RENTAFIJA,fechaDeVencimiento); 
				
	}
		
	public  static RentaFija crearInversion(String dni, String cvu, double monto, int plazoDias) {

		return new RentaFija(dni, cvu, TipoDeInversion.PRECANCELABLE,
				plazoDias,Utilitarios.hoy(),monto,"Renta Fija", calcularFechaDeVencimiento(plazoDias));
	}

	
	

	@Override
    public String toString() {
     
        return "RentaFija " + super.toString();
    }

}
