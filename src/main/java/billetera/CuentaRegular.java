package billetera;

public class CuentaRegular extends Cuenta {
	
	

	public CuentaRegular(String cvu, String alias, String idUsuarioPropietario) {
		
		super(cvu, alias,idUsuarioPropietario,ControlDeCuentas.INICIO, ControlDeCuentas.TIPOREGULAR);
		
		
	}
	
	public static Cuenta crearCuentaRegular(String cvu, String alias, String dniUsuario) {
	    return new CuentaRegular(cvu, alias, dniUsuario);
	}

	@Override
	public String toString() {
		return "CuentaRegular [" + super.toString() + "]";
	}

	@Override
	public boolean validarMonto(double monto, double saldoActual) {
		
		return  saldoActual+monto < ControlDeCuentas.MAXIMOCUENTAREGULAR;
		
		
	}


}
