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
		
	
		
		return new FondoLiquidez(dni, cvu, TipoDeInversion.FLE,plazoDias, Utilitarios.hoy(),monto, 
				"DETALLE", calcularFechaDeVencimiento(plazoDias) );
	}
	
	
	

	



	@Override
	public String toString() {
		return "FondoLiquidez [toString()=" + super.toString() + "]";
	}




	@Override
	public double calcularRentabilidadDeInversion(LocalDate fechaDeCancelacion) {
		// TODO Auto-generated method stub
		return 0;
	}




	@Override
	public double precancelar() {
		return 0;
	}





	}
