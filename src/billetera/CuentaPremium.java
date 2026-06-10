package billetera;

public class CuentaPremium extends Cuenta {


	public CuentaPremium(String cvu, String alias, String idUsuarioPropietario, double depositoInicial) {
    			super(cvu, alias, idUsuarioPropietario, depositoInicial,ControlDeCuentas.TIPOPREMIUM);	
    }
	
	public static Cuenta crearCuentaPremium(String cvu, String alias, String dniUsuario, double depositoInicial ) {
		
		//IREP
		
		 if (depositoInicial < ControlDeCuentas.MINIMOCUENTAPREMIUM) {
		        throw new IllegalArgumentException("el deposito inicial no cumple el minimo requerido");
		    }
		
	    return new CuentaPremium(cvu, alias, dniUsuario, depositoInicial);
	}

	@Override
	public boolean validarMonto(double monto, double montoActual) {
		// la cuenta premium no tiene limite de dinero
		return true;
	}

	@Override
	public String toString() {
		return "CuentaPremium [" + super.toString() + "]";
	}

	

}
