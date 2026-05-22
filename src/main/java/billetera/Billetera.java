package billetera;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Billetera implements IBilletera {
	
	private Map<String, Usuario> usuarios = new HashMap<>();
	private Map<String, Inversion> inversiones= new HashMap<>();
	private Map<String, Transferencia> transferencias = new HashMap<>();
	private   Map<String, Actividad> historialGlobal= new HashMap<>();
	private   Map<String, Empresa> empresas= new HashMap<>();
	private Map<String, Cuenta> cuentasPorCvu = new HashMap<>();
	private Map<String, Cuenta> cuentasPorAlias = new HashMap<>();
	
	
	

	public Billetera() {
		
	}
	
	
	
	

	@Override
	public void registrarEmpresa(String cuit, String nombreFantasia, String telefono, String email,
			String nombreContacto) {
		
		if (empresas.containsKey(cuit)) {throw new IllegalArgumentException("La empresa ya está registrada");}
		
		Empresa empresa= new Empresa (cuit, nombreFantasia, telefono, email, nombreContacto);
		empresas.put(cuit, empresa);
		

	}
	
	

	@Override
	public void agregarPersonaAutorizada(String cuitEmpresa, String dniAutorizado) {
		
		if (!empresas.containsKey(cuitEmpresa)) {throw new IllegalArgumentException("La empresa no existe");}
		Empresa empresa = empresas.get(cuitEmpresa);
		empresa.agregarPersonaAutorizada(dniAutorizado);

	}

	@Override
	public void registrarUsuario(String dni, String nombre, String telefono, String email) {
	 if  (usuarios.containsKey(dni)) {throw new IllegalArgumentException("El dni ya está siendo utilizado por un usuario");}
	 Usuario usuario= new Usuario (dni, nombre, telefono, email);
	 usuarios.put(dni, usuario);
	 
	}

	
	private void controlNuevaCuenta (String dniUsuario, String alias) {
		
		
		if (!usuarios.containsKey(dniUsuario)) {throw new IllegalArgumentException ("El usuario no existe");
		}
		if (cuentasPorAlias.containsKey(alias)) {throw new IllegalArgumentException ("El alias ya es utilizado");}
		
	
	}
	
	@Override
	public String crearCuentaRegular(String dniUsuario, String alias) {
		
			controlNuevaCuenta(dniUsuario,alias);
			
			String cvu = Utilitarios.generarSiguienteCvu();
			
			Cuenta cuenta = CuentaRegular.crearCuentaRegular(cvu, alias, dniUsuario);
			
			cuentasPorCvu.put(cvu, cuenta);
			cuentasPorAlias.put(alias, cuenta);
			Usuario usuario = usuarios.get(dniUsuario);
			usuario.agregarCuenta(cvu, cuenta);
			
			
			return cvu;
		}
	
	


	@Override
	public String crearCuentaPremium(String dniUsuario, String alias, double depositoInicial) {
		controlNuevaCuenta(dniUsuario,alias);
		if (depositoInicial < ControlDeCuentas.MINIMOCUENTAPREMIUM) {  
	        throw new IllegalArgumentException("El depósito inicial no cumple el mínimo requerido");
	    } //Esto va a aca para que no se cree el cvu sin que se pueda crear la cuenta
		String cvu = Utilitarios.generarSiguienteCvu();
		Cuenta cuenta = CuentaPremium.crearCuentaPremium (cvu, alias, dniUsuario,depositoInicial);
		cuentasPorCvu.put(cvu, cuenta);
		cuentasPorAlias.put(alias, cuenta);
		Usuario usuario= usuarios.get(dniUsuario);
		usuario.agregarCuenta(cvu, cuenta);
		
		return cvu;
	}
	
	
	private void controlEmpresaExiste (String cuit) {
		
		if (!empresas.containsKey(cuit)) {throw new IllegalArgumentException ("La empresa no existe");}
	}
	
	
	@Override
	public String crearCuentaCorporativa(String dniUsuario, String alias, String cuitEmpresa) {
		controlNuevaCuenta(dniUsuario,alias);
		controlEmpresaExiste(cuitEmpresa);
		Empresa e= empresas.get(cuitEmpresa);
		e.usuarioAutorizado(dniUsuario);
		
		
		
		String cvu = Utilitarios.generarSiguienteCvu();
		Cuenta cuenta= CuentaCorporativa.crearCuentaCorporativa(cvu, alias, dniUsuario, cuitEmpresa);
		cuentasPorCvu.put(cvu, cuenta);
		cuentasPorAlias.put(alias,cuenta);
		Usuario usuario= usuarios.get(dniUsuario);
		usuario.agregarCuenta(cvu, cuenta);
		
		return cvu;
		
		
	}

	@Override
	public List<String> obtenerCuentas(String dniUsuario) {
		Usuario u = usuarios.get(dniUsuario);
		List<String> cuentasDelUsuario= u.obtenerMisCuentas();

		
		return cuentasDelUsuario;
	}

	@Override
	public double obtenerSaldoDisponible(String cvu) {
		
		Cuenta c= cuentasPorCvu.get(cvu);
		double saldoDisponible= c.obtenerSaldoDisponible();
		
		return saldoDisponible;
	}

	@Override
	public void realizarTransferencia(String cvuOrigen, String cvuDestino, double monto) {
		// TODO Auto-generated method stub

	}

	@Override
	public int realizarInversionRentaFija(String dni, String cvu, double monto, int plazoDias) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int realizarInversionDivisa(String dni, String cvu, double monto, int plazoDias, String divisa,
			double tasa) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int realizarInversionLiquidez(String dni, String cvu, double monto, int plazoDias) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void precancelarInversion(String dni, String cvu, int idInversion) {
		// TODO Auto-generated method stub

	}

	@Override
	public String consultarCvu(String alias) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> consultarHistorialGlobal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> consultarHistorialCuenta(String cvu) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> consultarHistorialUsuario(String dniUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double obtenerTotalInvertido(String dniUsuario) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<String> cuentasConMayorVolumen(int cantidadTop) {
		// TODO Auto-generated method stub
		return null;
	}

}
