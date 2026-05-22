package billetera;

public class CuentaRegular extends Cuenta {
	
	

	public CuentaRegular(String cvu, String alias, String idUsuarioPropietario) {
		
		super(cvu, alias,idUsuarioPropietario,ControlDeCuentas.INICIO);
		
		
	}
	
	public static Cuenta crearCuentaRegular(String cvu, String alias, String dniUsuario) {
	    return new CuentaRegular(cvu, alias, dniUsuario);
	}

	@Override
	public String toString() {
		return "CuentaRegular [toString()=" + super.toString() + "]";
	}


}
