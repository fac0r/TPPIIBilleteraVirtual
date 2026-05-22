package billetera;

public class CuentaCorporativa extends Cuenta {
	
	private String empresaAsociada;

    public CuentaCorporativa(String cvu, String alias, String idUsuarioPropietario,
            String empresaAsociada) {

    			super(cvu, alias, idUsuarioPropietario, 0);
    				this.empresaAsociada = empresaAsociada;
    		}

	private String getEmpresaAsociada() {
		return empresaAsociada;
	}

	@Override
	public String toString() {
	    return super.toString() + " CuentaCorporativa [empresaAsociada=" + empresaAsociada + "]";
	}
    

}

