package billetera;

import java.time.LocalDate;


public class Divisa extends Inversion {
	

	 	
		private String tipoDivisa;
	    private double precioDivisa;
	    
	
	    
	    public Divisa(String cvuAsociado, boolean precancelable,double tasaInteres,int plazoDias, LocalDate fecha, double monto, String detalle,
				 String tipoDivisa, double precioDivisa) {
			super( cvuAsociado, precancelable,tasaInteres,plazoDias , fecha, monto, detalle, TipoDeInversion.DIVISA);
			
			     this.tipoDivisa = tipoDivisa;
		        this.precioDivisa = precioDivisa;
			
		}



		@Override
		public String toString() {
			return "Divisa [tipoDivisa=" + tipoDivisa + ", precioDivisa=" + precioDivisa + ", toString()="
					+ super.toString() + "]";
		}

	    
	    
}
