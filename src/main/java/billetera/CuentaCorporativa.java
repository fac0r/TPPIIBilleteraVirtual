package billetera;

public class CuentaCorporativa extends Cuenta {
	
	private String empresaAsociada;

    public CuentaCorporativa(String cvu, String alias, String idUsuarioPropietario,
            String empresaAsociada) {

    			super(cvu, alias, idUsuarioPropietario, ControlDeCuentas.INICIO, ControlDeCuentas.TIPOCORPORATIVA);
    				this.empresaAsociada = empresaAsociada;
    		}
    
    
    
    public static Cuenta crearCuentaCorporativa(String cvu, String alias, String dniUsuario, String cuitEmpresa) {
        return new CuentaCorporativa(cvu, alias, dniUsuario, cuitEmpresa);
    }

	private String getEmpresaAsociada() {
		return empresaAsociada;
	}

	@Override
	public String toString() {
	    return super.toString() + " CuentaCorporativa [empresaAsociada=" + empresaAsociada + "]";
	}



	@Override
	public boolean validarMonto(double monto, double saldoActual) {
		
		return true;
		// TODO Auto-generated method stub
		
	}
    

}

