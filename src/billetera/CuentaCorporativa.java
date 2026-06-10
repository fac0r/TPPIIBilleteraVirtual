package billetera;

public class CuentaCorporativa extends Cuenta {
	
	//private String empresaAsociada;
	private String cuitEmpresa;

    public CuentaCorporativa(String cvu, String alias, String idUsuarioPropietario,
            String cuitEmpresa) {

    			super(cvu, alias, idUsuarioPropietario, ControlDeCuentas.INICIO, ControlDeCuentas.TIPOCORPORATIVA);
    			
    			//IREP
    			
    			if (cuitEmpresa==null) {throw new IllegalArgumentException("El CUIT de la empresa no puede ser vacío");
    			}
    			this.cuitEmpresa = cuitEmpresa;
    		}
 
    public static Cuenta crearCuentaCorporativa(String cvu, String alias, String dniUsuario, String cuitEmpresa) {
        return new CuentaCorporativa(cvu, alias, dniUsuario, cuitEmpresa);
    }

	
    public String obtenerCuitEmpresa() {
        return cuitEmpresa;
    }
    
    @Override
	public boolean validarMonto(double monto, double saldoActual) {
		
		return true;	
	}
    

    @Override
    public String toString() {
        
        return "CuentaCorporativa [cuitEmpresa=" + cuitEmpresa + ", " + super.toString() + "]";
    }

	

}

