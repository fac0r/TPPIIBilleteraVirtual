package billetera;

public class CuentaPremium extends Cuenta {
	
	double encaje= ControlDeCuentas.MINIMOCUENTAPREMIUM;  //Esto nos va a servir para las extracciones
	
	

	public CuentaPremium(String cvu, String alias, String idUsuarioPropietario, double depositoInicial
           ) {

    			super(cvu, alias, idUsuarioPropietario, depositoInicial);
    			
    		}
	
	
	public static Cuenta crearCuentaPremium(String cvu, String alias, String dniUsuario, double depositoInicial ) {
		
		 /*if (depositoInicial < ControlDeCuentas.MINIMOCUENTAPREMIUM) {
		        throw new IllegalArgumentException("El depósito inicial no cumple el mínimo requerido");
		    }*/
		
		
	    return new CuentaPremium(cvu, alias, dniUsuario, depositoInicial);
	}

	@Override
	public String toString() {
		return "CuentaPremium [toString()=" + super.toString() + "]";
	}

	
	

}
