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

		double rentabilidad = mostrarMontoDouble() *( mostrarTasaInteres()/365) * (cantidadDeDiasTranscurridosEnInversion (mostrarFecha(), fechaDeCancelacion));

        // monto_invertido x (taza_interes / 365 dias_del_año) * cant_dias

		
		return rentabilidad;
	}


	@Override
	public double cancelar() {
		
		
		double rentabilidad = calcularRentabilidadDeInversion(Utilitarios.hoy());
		cambiarEstadoDeInversion(EstadoInversion.FINALIZADA);
		
		
		
		return rentabilidad;
		
	}

	@Override
	public double precancelar() {
		return 0;
	}





	}
