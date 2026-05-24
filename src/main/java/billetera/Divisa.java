package billetera;

import java.time.LocalDate;


public class Divisa extends Inversion {
	

	 	
		private String tipoDivisa;
	    private double precioDivisa;
	    
	
	    
	    public Divisa(String dniAsociado, String cvuAsociado, boolean precancelable,double tasaInteres,
	    		int plazoDias, LocalDate fecha, double monto, String detalle,
				 String tipoDivisa, double precioDivisa, LocalDate fechaDeVencimiento) {
			super(dniAsociado,  cvuAsociado, precancelable,tasaInteres,plazoDias , fecha, monto, detalle, TipoDeInversion.DIVISA, fechaDeVencimiento);
			
			     this.tipoDivisa = tipoDivisa;
		        this.precioDivisa = precioDivisa;
			
		}


	
		public static Divisa crearInversion(String dni, String cvu, double monto, int plazoDias, String divisa, double tasa) {
			
			return new Divisa (dni, cvu,TipoDeInversion.NOPRECANCELABLE, 
					tasa, plazoDias, Utilitarios.hoy(),monto ,"Detalle", divisa, Utilitarios.consultarCotizacion(divisa),
					calcularFechaDeVencimiento(plazoDias));
		}
		
		
		

		private double getPrecioDivisa() {
			return precioDivisa;
		}

		public double obtenerPrecioDivisa() {
			return getPrecioDivisa();
		}

		



		private void setPrecioDivisa(double precioDivisa) {
			this.precioDivisa = precioDivisa;
		}

		public void actualizarCotizacionDeDivisa (double nuevaCotizacion) {
			setPrecioDivisa(nuevaCotizacion);
		}


		private String getTipoDivisa() {
			return tipoDivisa;
		}
		
		public String obtenerTipoDivisa () {
			return getTipoDivisa();
		}



		@Override
		public String toString() {
			return "Divisa [tipoDivisa=" + tipoDivisa + ", precioDivisa=" + precioDivisa + ", toString()="
					+ super.toString() + "]";
		}



		@Override
		public double calcularRentabilidadDeInversion(LocalDate fechaDeCancelacion) {

		    
		    double equivalenteEnDivisa = mostrarMontoDouble() / obtenerPrecioDivisa();//Equivalencia en divisa

		   
		    double rentabilidadDeLaDivisa = equivalenteEnDivisa * (mostrarTasaInteres() / 365) 
		                          * cantidadDeDiasTranscurridosEnInversion(mostrarFecha(), fechaDeCancelacion);

		    return rentabilidadDeLaDivisa;
		}
		
		
		@Override
		public double precancelar() {
			
		double rentabilidad = calcularRentabilidadDeInversion(Utilitarios.hoy());
		cambiarEstadoDeInversion(EstadoInversion.PRECANCELADA);
		
		double rentabilidadEnPrecancelacion = rentabilidad/2;
		
		
		
		double equivalenteEnDivisa = mostrarMontoDouble() / obtenerPrecioDivisa(); //Equivalencia en dolares
		double cotizacionActual = Utilitarios.consultarCotizacion(obtenerTipoDivisa());
		
		
		double gananciaCapital = equivalenteEnDivisa * (cotizacionActual - obtenerPrecioDivisa());

		double interesesEnPesos= rentabilidadEnPrecancelacion * cotizacionActual;
		
		double retornoFinal= interesesEnPesos + gananciaCapital;
		
		return  retornoFinal;
		 
			
		}






	    
	    
}
