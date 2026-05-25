package billetera;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

	
	private Transferencia crearActividadTransferencia (String cuentaOrigen,String cuentaDestino, double monto, boolean comprobante) 
	{
		return Transferencia.crearTransferencia(cuentaOrigen,cuentaDestino, monto, comprobante);
		
	}
	
	
	private void registrarTransferenciaRechazada(String cvuOrigen, String cvuDestino, double monto) {
		
		  Cuenta cuentaOrigen = cuentasPorCvu.get(cvuOrigen);
		  Cuenta cuentaDestino = cuentasPorCvu.get(cvuDestino);
		  
		  
		
		Transferencia transferencia =crearActividadTransferencia(cvuOrigen, cvuDestino, monto, false);	
		   agregarTransferenciaAlHistorial(transferencia);
		   
		   cuentaOrigen.agregarActividad(transferencia.getIdActividad(), transferencia);
		    cuentaDestino.agregarActividad(transferencia.getIdActividad(), transferencia);
		   
		    agregarActividadAHistorialGlobal(transferencia);

		
	}
	
	@Override
	public void realizarTransferencia(String cvuOrigen, String cvuDestino, double monto) {
		
		  Cuenta cuentaOrigen = cuentasPorCvu.get(cvuOrigen);
		  Cuenta cuentaDestino = cuentasPorCvu.get(cvuDestino);

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
		   agregarTransferenciaAlHistorial(transferencia);
		   
		   cuentaOrigen.agregarActividad(transferencia.getIdActividad(), transferencia);
		    cuentaDestino.agregarActividad(transferencia.getIdActividad(), transferencia);
		   
		    agregarActividadAHistorialGlobal(transferencia);

	}
	
	private void agregarActividadAHistorialGlobal(Actividad actividad) {
		
		historialGlobal.put(actividad.mostrarIdActividad(), actividad);
		
	}
	
	private void agregarTransferenciaAlHistorial(Transferencia transferencia) {
		transferencias.put(transferencia.mostrarIdActividad(),transferencia);
		
	}
	
	private void agregarInversionAlHistorial (Inversion inversion) { 
		inversiones.put(inversion.mostrarIdActividad(), inversion);
	}
	
	//Este metodo cambia de String a int el id de la actividad
	private int formatearIdDeInversionesYTransferencias(String idDeLaActividad) {
	    int id =  Integer.parseInt(idDeLaActividad);
	    return id;
	}
	@Override
	public int realizarInversionRentaFija(String dni, String cvu, double monto, int plazoDias) {
		
		
		
	
		
		RentaFija inversion=  RentaFija.crearInversion(dni, cvu, monto, plazoDias); 
		agregarInversionAlHistorial (inversion);
		
		String idDeLaInversion = inversion.mostrarIdActividad();
		System.out.println("ASI SE IMPRIME EL ID ACTIVIDAD");
		System.out.println(idDeLaInversion);
		int id= formatearIdDeInversionesYTransferencias (idDeLaInversion);
		System.out.println("ASI SE IMPRIME EL ID LUEGO DEL FORMATEO");
		System.out.println(id);
		
		Cuenta c= cuentasPorCvu.get(cvu);
		c.realizarInversion(monto);
		
		c.agregarActividad(inversion.getIdActividad(), inversion);
		System.out.println("AHORA IMPRIMO LA ACTIVIDAD INVERSION RENTA FIJA");
		System.out.println(inversion.toString());
		
		
		
		return id;
	}
;
	@Override
	public int realizarInversionDivisa(String dni, String cvu, double monto, int plazoDias, String divisa,
			double tasa) {
		Divisa inversion = Divisa.crearInversion(dni, cvu, monto, plazoDias,divisa,tasa); 
		agregarInversionAlHistorial (inversion);
		String idDeLaInversion = inversion.mostrarIdActividad();
		System.out.println("ASI SE IMPRIME EL ID ACTIVIDAD");
		System.out.println(idDeLaInversion);
		int id= formatearIdDeInversionesYTransferencias (idDeLaInversion);
		System.out.println("ASI SE IMPRIME EL ID LUEGO DEL FORMATEO");
		System.out.println(id);
		Cuenta c= cuentasPorCvu.get(cvu);
		c.realizarInversion(monto);
		c.agregarActividad(inversion.getIdActividad(), inversion);
		System.out.println("AHORA IMPRIMO UNA ACTIVIDAD INVERSION DIVISA");
		System.out.println(inversion.toString());
		
		return id;
	}

	@Override
	public int realizarInversionLiquidez(String dni, String cvu, double monto, int plazoDias) {
		
		Cuenta c= cuentasPorCvu.get(cvu);
		
		if(c.obtenerTipoDeCuenta()!=ControlDeCuentas.TIPOCORPORATIVA) {
			throw new IllegalArgumentException ("Solo se puede invertir desde cuentas corporativas");
		}
		
		if (monto< FondoLiquidez.MONTO_MINIMO) {
			throw new IllegalArgumentException ("El monto minimo para inversiones de Fondo de Liquidez es 20 millones");
		}
		FondoLiquidez inversion = FondoLiquidez.crearInversion(dni, cvu, monto, plazoDias); 
		agregarInversionAlHistorial (inversion);
		String idDeLaInversion = inversion.mostrarIdActividad();
		System.out.println("ASI SE IMPRIME EL ID ACTIVIDAD");
		System.out.println(idDeLaInversion);
		int id= formatearIdDeInversionesYTransferencias (idDeLaInversion);
		System.out.println("ASI SE IMPRIME EL ID LUEGO DEL FORMATEO");
		System.out.println(id);
	
		c.realizarInversion(monto);
		c.agregarActividad(inversion.getIdActividad(), inversion);
		System.out.println("AHORA IMPRIMO UNA ACTIVIDAD INVERSION FONDO DE LIQUIDEZ");
		System.out.println(inversion.toString());
		
		return id;
	}
	
	private void inversionExiste(Cuenta c, int idInversion) {
		String id = String.valueOf(idInversion);
		
		Map <String, Actividad> actividadesDeLaCuenta=c.accesoGetHistorialCuenta();
		
		if (!actividadesDeLaCuenta.containsKey(id)) {
			throw new IllegalArgumentException("El id  de la inversion No existe");
		}
	}

	@Override
	public void precancelarInversion(String dni, String cvu, int idInversion) {
		
		
		
		Cuenta c = cuentasPorCvu.get(cvu);
		
		inversionExiste(c, idInversion);
		
		String id = String.valueOf(idInversion);
		Map <String, Actividad> actividadesDeLaCuenta=c.accesoGetHistorialCuenta();
		Inversion inversion = (Inversion)actividadesDeLaCuenta.get(id);
		
	
		double rendimiento = inversion.precancelar();
		
		
		
		c.restarSaldoInvertido(inversion.mostrarMontoDouble());
		c.aumentarSaldoTotal(rendimiento);
		
		inversiones.remove(id);
		

	}
	

	public void cancelarInversion(String dni, String cvu, int idInversion) {
		
		
		
		Cuenta c = cuentasPorCvu.get(cvu);
		
		inversionExiste(c, idInversion);
		
		String id = String.valueOf(idInversion);
		Map <String, Actividad> actividadesDeLaCuenta=c.accesoGetHistorialCuenta();
		Inversion inversion = (Inversion)actividadesDeLaCuenta.get(id);
		
	
		double rendimiento = inversion.cancelar();
		

		
		c.restarSaldoInvertido(inversion.mostrarMontoDouble());
		c.aumentarSaldoTotal(rendimiento);
		
	}

	@Override
	public String consultarCvu(String alias) {
		
	if (!cuentasPorAlias.containsKey(alias)) {
		throw new IllegalArgumentException("El alias no existe");
	}
	 Cuenta c= cuentasPorAlias.get(alias);
	 
	 String cvu = c.mostrarCvu();
		
	 return cvu;
	}

	@Override
	public List<String> consultarHistorialGlobal() {
		
		List<String> historial= new ArrayList<>();
		
		for(Actividad actividad : historialGlobal.values()) {
		 historial.add(actividad.mostrarIdActividad());
		}

		return historial;
	
	}

	@Override
	public List<String> consultarHistorialCuenta(String cvu) {
		
		if (!cuentasPorCvu.containsKey(cvu))
	        throw new IllegalArgumentException("La cuenta no existe");
	    
	    Cuenta cuenta = cuentasPorCvu.get(cvu);
	    List<String> historial = new ArrayList<>();
	    
	    for (Actividad actividad : cuenta.accesoGetHistorialCuenta().values()) {
	        historial.add(actividad.toString());
	    }
	    
	    return historial;
	}

	@Override
	public List<String> consultarHistorialUsuario(String dniUsuario) {
		
		List<String> historialDeCuentasDelUsuario= new ArrayList<>();
		
		Usuario u= usuarios.get(dniUsuario);
		
		Map<String, Cuenta> cuentasDelUsuario= u.devolverGetCuentas();

		
		for (Cuenta c : cuentasDelUsuario.values()) {
			 Map <String, Actividad> historialCuentas= c.accesoGetHistorialCuenta(); 
			 for(Actividad act: historialCuentas.values()) {
				 historialDeCuentasDelUsuario.add(act.mostrarIdActividad());
			 }
			 
			}
		
		
		return historialDeCuentasDelUsuario;
	}

	@Override
	public double obtenerTotalInvertido(String dniUsuario) {

		Usuario u= usuarios.get(dniUsuario);
		double total = u.obtenerTotalInvertido();

		return total;
	}

	private List<Cuenta> ordenarCuentasPorVolumen ()  {
		
		List<Cuenta> cuentas = new ArrayList<>(cuentasPorCvu.values());

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
				cuentasConMayorVolumen.add(c.mostrarCvu());
				cont++;
			}
			
		}
		return cuentasConMayorVolumen;
		}



	@Override
	public void procesarInversionesQueVencenHoy() {
		
	
		Iterator <Inversion> iterador= inversiones.values().iterator();
		
		while(iterador.hasNext()) {
			Inversion inversion= iterador.next();
		
			
			if (inversion.obtenerFechaDeVencimiento().equals(Utilitarios.hoy())) {
				String dniUsuario= inversion.obtenerDniAsociado();
				String cvuCuenta= inversion.obtenerCvuAsociado();
				int  idInversion= formatearIdDeInversionesYTransferencias(inversion.mostrarIdActividad());
				cancelarInversion(dniUsuario, cvuCuenta, idInversion);
				
				System.out.println("La inversion existe");
				System.out.println(inversionActiva(idInversion));
				
				iterador.remove();
				
				System.out.println("La inversion tiene que dejar de existir");
				System.out.println(inversionActiva(idInversion));
				
			}
		}
		
	}
		
	public boolean inversionActiva(int idInversion) {
	    return inversiones.containsKey(String.valueOf(idInversion));
	}

}
