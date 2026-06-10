package billetera;

import java.time.LocalDate;


public class FondoLiquidez extends Inversion {
	
	public static final double MONTO_MINIMO = 20000000.0;
	
	public FondoLiquidez(String dniAsociado,String cvuAsociado,double tasaInteres,int plazoDias,
			LocalDate fecha, double monto, String detalle, LocalDate fechaDeVencimiento
			) {
		super(dniAsociado, cvuAsociado, false,tasaInteres,plazoDias ,
				fecha, monto, detalle, TipoDeInversion.FND, fechaDeVencimiento); {
		}
	}
	

	public static FondoLiquidez crearInversion(String dni, String cvu, double monto, int plazoDias) {
		
		//IREP
		
		if (monto <MONTO_MINIMO) {throw new IllegalArgumentException("EL Fondo de Liquidez requiere un monto minemo a invertir de 20000000 ");}
		
		return new FondoLiquidez(dni, cvu, TipoDeInversion.FLE,plazoDias, Utilitarios.hoy(),monto, 
				"fondo liquidez", calcularFechaDeVencimiento(plazoDias) );
	}
	


		
		@Override
		public String toString() {
			return "FondoLiquidez [toString()=" + super.toString() + "]";
		}

}
