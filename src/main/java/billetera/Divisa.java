package billetera;

import java.time.LocalDate;


public class Divisa extends Inversion {
	

	 	
		private String tipoDivisa;
	    private double precioDivisa;
	    
	
	    
	    public Divisa(String dniAsociado, String cvuAsociado, boolean precancelable,double tasaInteres,int plazoDias, LocalDate fecha, double monto, String detalle,
				 String tipoDivisa, double precioDivisa, LocalDate fechaDeVencimiento) {
			super(dniAsociado,  cvuAsociado, precancelable,tasaInteres,plazoDias , fecha, monto, detalle, TipoDeInversion.DIVISA, fechaDeVencimiento);
			
			     this.tipoDivisa = tipoDivisa;
		        this.precioDivisa = precioDivisa;
			
		}


	
		public static Divisa crearInversion(String dni, String cvu, double monto, int plazoDias) {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public String toString() {
			return "Divisa [tipoDivisa=" + tipoDivisa + ", precioDivisa=" + precioDivisa + ", toString()="
					+ super.toString() + "]";
		}




	    
	    
}
