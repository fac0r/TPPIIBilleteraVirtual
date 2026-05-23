package billetera;

import java.time.LocalDate;


public class FondoLiquidez extends Inversion {
	
	public static final double MONTO_MINIMO = 20000000.0;

	public FondoLiquidez(String dniAsociado,String cvuAsociado,double tasaInteres,int plazoDias,
			LocalDate fecha, double monto, String detalle, LocalDate fechaDeVencimiento
			) {
		super(dniAsociado, cvuAsociado, false,tasaInteres,plazoDias , fecha, monto, detalle, TipoDeInversion.FND, fechaDeVencimiento); {
			
		}
		
	
	
	
             

}
	
	public static FondoLiquidez crearInversion(String dni, String cvu, double monto, int plazoDias) {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public String toString() {
		return "FondoLiquidez [toString()=" + super.toString() + "]";
	}





	}
