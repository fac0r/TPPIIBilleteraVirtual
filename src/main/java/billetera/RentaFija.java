package billetera;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class RentaFija extends Inversion {
	
	public static final double TASA_INTERES = 0.20;
	
	

	public RentaFija(String dniAsociado, String cvuAsociado, boolean precancelable,int plazoDias, LocalDate fecha, double monto, String detalle,
			 LocalDate fechaDeVencimiento) {
		super(dniAsociado, cvuAsociado, precancelable, TASA_INTERES ,plazoDias , fecha, monto, detalle, TipoDeInversion.RENTAFIJA,fechaDeVencimiento); 
		
		
			
			
	}
	
	
	
	public  static RentaFija crearInversion(String dni, String cvu, double monto, int plazoDias) {

		return new RentaFija(dni, cvu, TipoDeInversion.PRECANCELABLE,
				plazoDias,Utilitarios.hoy(),monto,"DETALLE", calcularFechaDeVencimiento(plazoDias));
	}

	

	@Override
	public double calcularRentabilidadDeInversion(LocalDate fechaDeCancelacion) {

		double rentabilidad = mostrarMontoDouble() *( mostrarTasaInteres()/365) * (cantidadDeDiasTranscurridosEnInversion (mostrarFecha(), fechaDeCancelacion));

        // monto_invertido x (taza_interes / 365 dias_del_año) * cant_dias

		
		return rentabilidad;
	}

	
	
	
	@Override
	public double precancelar() {
		
	double rentabilidad = calcularRentabilidadDeInversion(Utilitarios.hoy());
	cambiarEstadoDeInversion(EstadoInversion.PRECANCELADA);
	
	double rentabilidadEnPrecancelacion = rentabilidad/2;
	
	return rentabilidadEnPrecancelacion;
		
	}



	@Override
	public String toString() {
		return "=" + super.toString() + "]";
	}

















}
