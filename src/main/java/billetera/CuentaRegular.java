package billetera;

public class CuentaRegular extends Cuenta {
	
	

	public CuentaRegular(String cvu, String alias, String idUsuarioPropietario, double saldoTotal) {
		
		super(cvu, alias,idUsuarioPropietario,saldoTotal);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "CuentaRegular [toString()=" + super.toString() + "]";
	}


}
