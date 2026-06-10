package billetera;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Billetera implements IBilletera {
	
	private Map<String, Usuario> usuarios; 
	private List<Actividad> historialGlobal; 
	private Map<String, Empresa> empresas; 

	

	public Billetera() {
		
		 usuarios = new HashMap<>();
		
		 historialGlobal= new ArrayList<Actividad>();
		 empresas= new HashMap<>();
	
		
	}
	@Override
	public void registrarEmpresa(String cuit, String nombreFantasia, String telefono, String email,
			String nombreContacto) {
		
		if (empresas.containsKey(cuit)) {
			throw new IllegalArgumentException("La empresa ya esta registrada");
		}
		
		Empresa empresa= new Empresa (cuit, nombreFantasia, telefono, email, nombreContacto);
		empresas.put(cuit, empresa);
		

	}

	@Override
	public void agregarPersonaAutorizada(String cuitEmpresa, String dniAutorizado) {
		
		if (!empresas.containsKey(cuitEmpresa)) {
			throw new IllegalArgumentException("La empresa no existe");
		}
		
		Empresa empresa = empresas.get(cuitEmpresa);
		empresa.agregarPersonaAutorizada(dniAutorizado);

	}

	@Override
	public void registrarUsuario(String dni, String nombre, String telefono, String email) {
	 
			if  (usuarios.containsKey(dni)) {
				throw new IllegalArgumentException("El dni ya está siendo utilizado por un usuario");
			}
			
			Usuario usuario= new Usuario (dni, nombre, telefono, email);
			usuarios.put(dni, usuario);
	 
	}
	
	private void controlNuevaCuenta (String dniUsuario, String alias) {
		
		if (!usuarios.containsKey(dniUsuario)) {
			throw new IllegalArgumentException ("El usuario no existe");
		}
		Usuario u= usuarios.get(dniUsuario);
		
		for (Cuenta c : (u.devolverGetCuentas()).values() ) {
		if (c.obtenerAlias().equals(alias)) {
			throw new IllegalArgumentException ("El alias ya es utilizado");
		} }
		
	
	}
	
	@Override
	public String crearCuentaRegular(String dniUsuario, String alias) {
		
			controlNuevaCuenta(dniUsuario,alias);
			
			String cvu = Utilitarios.generarSiguienteCvu();
			Cuenta cuenta = CuentaRegular.crearCuentaRegular(cvu, alias, dniUsuario);
			
			
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
		
			
			Usuario usuario= usuarios.get(dniUsuario);
			usuario.agregarCuenta(cvu, cuenta);
		
			return cvu;
	}
		
	private void controlEmpresaExiste (String cuit) {
		
		if (!empresas.containsKey(cuit)) {
			throw new IllegalArgumentException ("La empresa no existe");
		}
	}
			
	@Override
	public String crearCuentaCorporativa(String dniUsuario, String alias, String cuitEmpresa) {
		controlNuevaCuenta(dniUsuario,alias);
		controlEmpresaExiste(cuitEmpresa);
		Empresa e= empresas.get(cuitEmpresa);
		e.validarUsuarioAutorizado(dniUsuario);
		
		
		
		String cvu = Utilitarios.generarSiguienteCvu();
		Cuenta cuenta= CuentaCorporativa.crearCuentaCorporativa(cvu, alias, dniUsuario, cuitEmpresa);
		
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
	
	private Cuenta obtenerCuentaPorCvu(String cvu) {
		
		for (Usuario u : usuarios.values()) {
			for (Cuenta c : u.devolverGetCuentas().values()) {
				if (c.obtenerCvu().equals(cvu) ) {return c;}
			}
			
		}
		
		throw new IllegalArgumentException("LA cuenta no existe");
		
	}
	
		private Cuenta obtenerCuentaPorAlias(String alias) {
		
		for (Usuario u : usuarios.values()) {
			for (Cuenta c : u.devolverGetCuentas().values()) {
				if (c.obtenerAlias().equals(alias) ) {return c;}
			}
			
		}
		
		throw new IllegalArgumentException("LA cuenta no existe");
		
	}
	

	@Override
	public double obtenerSaldoDisponible(String cvu) {
	
		Cuenta c= obtenerCuentaPorCvu(cvu);
		double saldoDisponible= c.obtenerSaldoDisponible();
		
		return saldoDisponible;
	}
	
	private Transferencia crearActividadTransferencia (String cuentaOrigen,String cuentaDestino, double monto, boolean comprobante) 
	{
		return Transferencia.crearTransferencia(cuentaOrigen,cuentaDestino, monto, comprobante);
		
	}
		
	private void registrarTransferenciaRechazada(String cvuOrigen, String cvuDestino, double monto) {
		
		  Cuenta cuentaOrigen = obtenerCuentaPorCvu(cvuOrigen);
		  Cuenta cuentaDestino = obtenerCuentaPorCvu(cvuDestino);
		  
		  
		
		Transferencia transferencia =crearActividadTransferencia(cvuOrigen, cvuDestino, monto, false);	
		 
		   
		   cuentaOrigen.agregarActividad(transferencia.obtenerIdActividad(), transferencia);
		    cuentaDestino.agregarActividad(transferencia.obtenerIdActividad(), transferencia);
		   
		    agregarActividadAHistorialGlobal(transferencia);

		
	}
	
	@Override
	public void realizarTransferencia(String cvuOrigen, String cvuDestino, double monto) {
		
		  Cuenta cuentaOrigen =  obtenerCuentaPorCvu(cvuOrigen);
		  Cuenta cuentaDestino =  obtenerCuentaPorCvu(cvuDestino);

		boolean comprobante = true;
		  
		    try {
		        cuentaOrigen.emitirTransferencia(monto);
		        try {
		            cuentaDestino.recibirTransferencia(monto);
		        } catch (IllegalStateException e) {
		            cuentaOrigen.aumentarSaldoTotal(monto); // revertir débito
		            registrarTransferenciaRechazada(cvuOrigen, cvuDestino, monto);
		            throw e; 
		        }
		    } catch (IllegalArgumentException e) {
		        registrarTransferenciaRechazada(cvuOrigen, cvuDestino, monto);
		        throw e;
		    }
		  
		   Transferencia transferencia =crearActividadTransferencia(cvuOrigen, cvuDestino, monto, comprobante);	
		 
		   
		   cuentaOrigen.agregarActividad(transferencia.obtenerIdActividad(), transferencia);
		    cuentaDestino.agregarActividad(transferencia.obtenerIdActividad(), transferencia);
		   
		    agregarActividadAHistorialGlobal(transferencia);

	}
	
	private void agregarActividadAHistorialGlobal(Actividad actividad) {
		historialGlobal.add(actividad);
		
	}
	

	private int formatearIdDeInversionesYTransferencias(String idDeLaActividad) {

	    int id =  Integer.parseInt(idDeLaActividad);
	    return id;
	}
	
	
	public void sumarInversionUsuario (Usuario u, double monto) {
		
		u.agregarMontoInvertido(monto);}
	
	public void restarInversionUsuario (Usuario u, double monto) {
		u.restarMontoInvertido(monto);}
	
	
	
	@Override
	public int realizarInversionRentaFija(String dni, String cvu, double monto, int plazoDias) {
		
	    Usuario u= usuarios.get(dni);

		
		RentaFija inversion=  RentaFija.crearInversion(dni, cvu, monto, plazoDias); 
		
	
		
		String idDeLaInversion = inversion.obtenerIdActividad();
	
		int id= formatearIdDeInversionesYTransferencias (idDeLaInversion);
		
		
		Cuenta c= obtenerCuentaPorCvu(cvu);
		c.realizarInversion(monto);
		
		c.agregarActividad(inversion.getIdActividad(), inversion);
		
		sumarInversionUsuario(u,monto);
		
		agregarActividadAHistorialGlobal(inversion);
		
		
		return id;
	}
;
	@Override
	public int realizarInversionDivisa(String dni, String cvu, double monto, int plazoDias, String divisa,
			double tasa) {
		Usuario u= usuarios.get(dni);
		Divisa inversion = Divisa.crearInversion(dni, cvu, monto, plazoDias,divisa,tasa); 
		
		String idDeLaInversion = inversion.obtenerIdActividad();
		
		int id= formatearIdDeInversionesYTransferencias (idDeLaInversion);
		
		Cuenta c= obtenerCuentaPorCvu(cvu);
		c.realizarInversion(monto);
		c.agregarActividad(inversion.getIdActividad(), inversion);
		
		sumarInversionUsuario(u,monto);
		
		agregarActividadAHistorialGlobal(inversion);
		
		return id;
	}

	@Override
	public int realizarInversionLiquidez(String dni, String cvu, double monto, int plazoDias) {
		
		Usuario u= usuarios.get(dni);
		
		
		Cuenta c= obtenerCuentaPorCvu(cvu);
		
		if(c.obtenerTipoDeCuenta()!=ControlDeCuentas.TIPOCORPORATIVA) {
			throw new IllegalArgumentException ("Solo se puede invertir desde cuentas corporativas");
		}
		
		if (monto< FondoLiquidez.MONTO_MINIMO) {
			throw new IllegalArgumentException ("El monto minimo para inversiones de Fondo de Liquidez es 20 millones");
		}
		FondoLiquidez inversion = FondoLiquidez.crearInversion(dni, cvu, monto, plazoDias); 
		
		String idDeLaInversion = inversion.obtenerIdActividad();
		
		int id= formatearIdDeInversionesYTransferencias (idDeLaInversion);
		
	
		c.realizarInversion(monto);
		c.agregarActividad(inversion.getIdActividad(), inversion);
		
		sumarInversionUsuario(u,monto);
		
		agregarActividadAHistorialGlobal(inversion);
		
		return id;
	}
	
	private void inversionExiste(Cuenta c, int idInversion) {
		String id = String.valueOf(idInversion);
		
		Map <String, Actividad> actividadesDeLaCuenta=c.obtenerHistorialCuenta();
		
		if (!actividadesDeLaCuenta.containsKey(id)) {
			throw new IllegalArgumentException("El id  de la inversion No existe");
		}
	}
	
	
	
	public void esPrecancelable(Inversion inversion) {
		
 		if (!inversion.esPrecancelable()) {throw new IllegalArgumentException ("El tipo de inversion  no es precancelable" );} 
	}
	
	
	private void switchEstadoInversion(Inversion inversion, String estado) {
		
	
		inversion.cambiarEstadoDeInversion(estado);
	}

	@Override
	public void precancelarInversion(String dni, String cvu, int idInversion) {
	
		Cuenta c = obtenerCuentaPorCvu(cvu);
		Usuario u= usuarios.get(dni);
		inversionExiste(c, idInversion);
		
		String id = String.valueOf(idInversion);
		Map <String, Actividad> actividadesDeLaCuenta=c.obtenerHistorialCuenta();
		Inversion inversion = (Inversion)actividadesDeLaCuenta.get(id);
		
		esPrecancelable (inversion);
	
		double rendimiento = inversion.precancelar();
		
		double monto = inversion.obtenerMonto();
		
		c.restarSaldoInvertido(monto);
		c.aumentarSaldoTotal(rendimiento);
		restarInversionUsuario(u, monto);
		
		switchEstadoInversion(inversion,EstadoInversion.PRECANCELADA);
		
 
	}
	
	public void cancelarInversion(String dni, String cvu, int idInversion) {
		
		
		Usuario u= usuarios.get(dni);
		Cuenta c = obtenerCuentaPorCvu(cvu);
		
		inversionExiste(c, idInversion);
		
		String id = String.valueOf(idInversion);
		Map <String, Actividad> actividadesDeLaCuenta=c.obtenerHistorialCuenta();
		Inversion inversion = (Inversion)actividadesDeLaCuenta.get(id);
		
	
		double rendimiento = inversion.cancelar();
		

		double monto = inversion.obtenerMonto();
		
		c.restarSaldoInvertido(monto);
		c.aumentarSaldoTotal(rendimiento);
		restarInversionUsuario(u, monto);
		
		
		
	}

	@Override
	public String consultarCvu(String alias) {
		
	
	 Cuenta c= obtenerCuentaPorAlias(alias);
	 
	 String cvu = c.obtenerCvu();
		
	 return cvu;
	}

	@Override
	public List<String> consultarHistorialGlobal() {
		
		List<String> historial= new ArrayList<>();
		
		for(Actividad actividad : historialGlobal) {
		 historial.add(actividad.obtenerIdActividad());
		}

		return historial; 
	
	}

	@Override
	public List<String> consultarHistorialCuenta(String cvu) {
		
	    Cuenta cuenta = obtenerCuentaPorCvu(cvu);
	    List<String> historial = new ArrayList<>();
	    
	    for (Actividad actividad : cuenta.obtenerHistorialCuenta().values()) {
	        historial.add(actividad.toString());
	    }
	    
	    return historial;
	}

	@Override
	public List<String> consultarHistorialUsuario(String dniUsuario) {
		
		
		
		Usuario u= usuarios.get(dniUsuario);
		
		
		
		return u.consultarHistorialUsuario();
	}

	@Override
	public double obtenerTotalInvertido(String dniUsuario) {

		Usuario u= usuarios.get(dniUsuario);
		double total = u.obtenerTotalInvertido();

		return total;
	}

	private List<Cuenta> ordenarCuentasPorVolumen ()  {
		
		
		List<Cuenta> cuentas = new ArrayList<>();
		
		for (Usuario u: usuarios.values()) {
			for (Cuenta c: (u.devolverGetCuentas()).values()) {
				cuentas.add(c);
			}
		}

	    for (int i = 0; i < cuentas.size(); i++) {
	        for (int j = i + 1; j < cuentas.size(); j++) {
	            if (cuentas.get(j).obtenerVolumenTransacciones() > cuentas.get(i).obtenerVolumenTransacciones()) {
	                Cuenta auxiliar = cuentas.get(i);
	                cuentas.set(i, cuentas.get(j));
	                cuentas.set(j, auxiliar);
	            }
	        }
	    }

	    return cuentas;
	}

	@Override
	public List<String> cuentasConMayorVolumen(int cantidadTop) {
		List <String> cuentasConMayorVolumen= new ArrayList<>();
		List <Cuenta> cuentasOrdenadas = ordenarCuentasPorVolumen();
		
		int cont=0;
		for(Cuenta c : cuentasOrdenadas) {
			if (cont<cantidadTop) {
				cuentasConMayorVolumen.add(c.obtenerCvu());
				cont++;
			}
			
		}
		return cuentasConMayorVolumen;
		}

	@Override
	public void procesarInversionesQueVencenHoy() {
		
		

		Iterator <Actividad> iterador= historialGlobal.iterator();
		
		while(iterador.hasNext()) {
			Actividad act= iterador.next();
		
			
			if (act instanceof Inversion) {
				
				Inversion inv = (Inversion) act;
			
			if (inv.obtenerFechaDeVencimiento().equals(Utilitarios.hoy())) {
				String dniUsuario= inv.obtenerDniAsociado();
				String cvuCuenta= inv.obtenerCvuAsociado();
				int  idInversion= formatearIdDeInversionesYTransferencias(act.obtenerIdActividad());
				cancelarInversion(dniUsuario, cvuCuenta, idInversion);
				
				
				
				switchEstadoInversion(inv,EstadoInversion.FINALIZADA);
				
				
				
			}
			}
		}
		
	}
		

	@Override
	public String toString() {
		return "Billetera [usuarios=" + usuarios + ", historialGlobal=" + historialGlobal + ", empresas=" + empresas
				+ "]";
	}
	
	
	

}
