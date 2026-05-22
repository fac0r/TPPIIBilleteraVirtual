package billetera;

import java.util.Date;

public class FondoLiquidez extends Inversion {
	
	public static final double MONTO_MINIMO = 20000000.0;

	public FondoLiquidez(String cvuAsociado,double tasaInteres,int plazoDias, Date fecha, double monto, String detalle
			) {
		super( cvuAsociado, false,tasaInteres,plazoDias , fecha, monto, detalle, TipoDeInversion.FND); {
			
		}
		
	
	
	
             

}

	@Override
	public String toString() {
		return "FondoLiquidez [toString()=" + super.toString() + "]";
	}}
