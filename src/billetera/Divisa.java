package billetera;

import java.time.LocalDate;


public class Divisa extends Inversion {
	
		private String tipoDivisa;
	    private double precioDivisa;

	    public Divisa(String dniAsociado, String cvuAsociado, boolean precancelable,double tasaInteres,
	    		int plazoDias, LocalDate fecha, double monto, String detalle,
				 String tipoDivisa, double precioDivisa, LocalDate fechaDeVencimiento) {
			super(dniAsociado,  cvuAsociado, precancelable,tasaInteres,plazoDias , fecha, monto, detalle, TipoDeInversion.DIVISA, fechaDeVencimiento);
				
				//IREP
			
				if (tipoDivisa==null)
				{throw new IllegalArgumentException ("Se debe informar a qué divisa se encuentra sujeta la inversion");}
				if (precioDivisa<=0) {
					throw new IllegalArgumentException("El valor de la divisa no puede ser menor a cero");
				}
			
			     this.tipoDivisa = tipoDivisa;
		        this.precioDivisa = precioDivisa;
			
		}

	    public static Divisa crearInversion(String dni, String cvu, double monto, int plazoDias, String divisa, double tasa) {

	        return new Divisa(dni, cvu, TipoDeInversion.PRECANCELABLE, tasa, plazoDias, 
	                          Utilitarios.hoy(), monto, "compra de divisa", divisa, 
	                          Utilitarios.consultarCotizacion(divisa), calcularFechaDeVencimiento(plazoDias));
	    }
		

	    public double obtenerPrecioDivisa() {
	        return precioDivisa;
	    }

	    public void actualizarCotizacionDeDivisa(double nuevaCotizacion) {
	        this.precioDivisa = nuevaCotizacion;
	    }


	    public String obtenerTipoDivisa() {
	        return tipoDivisa;
	    }

	    @Override
	    public double calcularRentabilidadDeInversion(LocalDate fechaDeCancelacion) {
	        
	        double equivalenteEnDivisa = obtenerMonto() / obtenerPrecioDivisa();

	        int dias = cantidadDeDiasTranscurridosEnInversion(obtenerFecha(), fechaDeCancelacion);
	        double rentabilidadDeLaDivisa = equivalenteEnDivisa * (obtenerTasaInteres() / 365) * dias;

	        return rentabilidadDeLaDivisa; 
	    }
	
	    @Override
	    public double precancelar() {
	      
	        if (!esPrecancelable()) {
	            throw new RuntimeException("esta inversion vinculada a divisa no permite precancelacion");
	        }
	     
	        double rentabilidadEnDivisa = calcularRentabilidadDeInversion(Utilitarios.hoy());
	        cambiarEstadoDeInversion(EstadoInversion.PRECANCELADA);
	        
	        double rentabilidadEnPrecancelacion = rentabilidadEnDivisa / 2;
	        
	        double equivalenteEnDivisa = obtenerMonto() / obtenerPrecioDivisa(); 
	        double cotizacionActual = Utilitarios.consultarCotizacion(obtenerTipoDivisa());
		
	        double diferenciaDeCapitalEnPesos = cotizacionActual - obtenerPrecioDivisa();
	        double gananciaDeCapitalEnPesos = equivalenteEnDivisa * diferenciaDeCapitalEnPesos;
			
	        double interesesTotalesEnPesos = rentabilidadEnPrecancelacion * cotizacionActual;
			
	        return interesesTotalesEnPesos + gananciaDeCapitalEnPesos;
	    }

	    @Override
	    public double cancelar() {
	        double rentabilidadEnDivisa = calcularRentabilidadDeInversion(obtenerFechaDeVencimiento());
	        cambiarEstadoDeInversion(EstadoInversion.FINALIZADA);
		
	        double equivalenteEnDivisa = obtenerMonto() / obtenerPrecioDivisa(); 
	        double cotizacionActual = Utilitarios.consultarCotizacion(obtenerTipoDivisa());
			
	        double diferenciaDeCapitalEnPesos = cotizacionActual - obtenerPrecioDivisa();
	        double gananciaDeCapitalEnPesos = equivalenteEnDivisa * diferenciaDeCapitalEnPesos;

	        double interesesTotalesEnPesos = rentabilidadEnDivisa * cotizacionActual;
	
	        return interesesTotalesEnPesos + gananciaDeCapitalEnPesos;
	    }
	
		@Override
		public String toString() {
			return "Divisa [tipoDivisa=" + tipoDivisa + ", precioDivisa=" + precioDivisa + ", toString()="
					+ super.toString() + "]";
		}
}
