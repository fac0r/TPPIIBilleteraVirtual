package billetera;

public class CuentaPremium extends Cuenta {
	
	

	public CuentaPremium(String cvu, String alias, String idUsuarioPropietario,
            double saldoDisponible) {

    			super(cvu, alias, idUsuarioPropietario, saldoDisponible);
    			
    		}

	@Override
	public String toString() {
		return "CuentaPremium [toString()=" + super.toString() + "]";
	}

	
	

}
