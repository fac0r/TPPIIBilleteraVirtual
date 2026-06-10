package billetera;

import java.util.HashMap;
import java.util.Map;

public abstract class Cuenta {

	 private String cvu;
	 private String alias;
	 private String idUsuarioPropietario;
	 private String tipoDeCuenta;
	 private double saldoTotal;
	 protected double saldoDisponible;
	 private double saldoInvertido;
	 private Map <String, Actividad> historialCuenta = new HashMap<>();
	 private int volumenTransacciones;
	
	    public Cuenta(String cvu, String alias, String idUsuarioPropietario, double saldoTotal, String tipoDeCuenta) {
	    	
	    	//IREP
	    	
	    	if (cvu==null) {
	    	throw new IllegalArgumentException("El CVU no puede ser vacío");}
	    	if (alias==null) {throw new IllegalArgumentException("El Alias no puede ser vacío");}
	    	if (idUsuarioPropietario ==null) {throw new IllegalArgumentException("La cuenta debe tener un usuario asociado");}
	    	
	        this.cvu = cvu;
	        this.alias = alias;
	        this.idUsuarioPropietario = idUsuarioPropietario;
	        this.tipoDeCuenta = tipoDeCuenta;
	        this.saldoTotal = saldoTotal;
	        this.saldoInvertido = ControlDeCuentas.INICIO; // Asumo que es 0.0
	        this.saldoDisponible = saldoTotal;
	        this.volumenTransacciones = 0;
	    }

	    public abstract boolean validarMonto(double monto, double saldoActual);


	    public void emitirTransferencia(double monto) {
	        if (!hayDisponibilidadParaRealizarOperacion(monto)) {
	            throw new IllegalArgumentException("no hay suficiente saldo disponible para transferir");
	        }
	        reducirSaldoTotal(monto);
	        actualizarVolumenTransacciones();
	    }
		 
	    public void recibirTransferencia(double monto) {
	      
	        if (!validarMonto(monto, saldoTotal)) {
	            throw new IllegalStateException("para ingresar ese monto debe aumentar la categoría de su cuenta");
	        }
	        aumentarSaldoTotal(monto);
	        actualizarVolumenTransacciones();
	    }

	    public void realizarInversion(double monto) {
	        if (!hayDisponibilidadParaRealizarOperacion(monto)) {
	            throw new IllegalArgumentException("no hay suficiente saldo disponible para invertir");
	        }
	        actualizarVolumenTransacciones();
	        // el saldo invertido se actualiza cuando se agrega la actividad al historial
	    }

	    public void agregarActividad(String id, Actividad actividad) {
	        this.historialCuenta.put(id, actividad);
	    
	        if (actividad instanceof Inversion) {
	            double monto = actividad.obtenerMonto();
	            aumentarSaldoInvertido(monto);
	        }
	    }
	    
	    private boolean hayDisponibilidadParaRealizarOperacion(double monto) {
	        return saldoDisponible >= monto;
	    }
	    
	    private void actualizarSaldoDisponible() {
	        this.saldoDisponible = this.saldoTotal - this.saldoInvertido;
	    }
	    
	    private void reducirSaldoTotal(double monto) {
	        this.saldoTotal = this.saldoTotal - monto;
	        actualizarSaldoDisponible();
	    }
	    
	    public void aumentarSaldoTotal(double monto) {
	        this.saldoTotal = this.saldoTotal + monto;
	        actualizarSaldoDisponible();
	    }
	    
	    public void aumentarSaldoInvertido(double monto) {
	        this.saldoInvertido = this.saldoInvertido + monto;
	        actualizarSaldoDisponible();
	    }
   
	    public void restarSaldoInvertido(double monto) {
	        this.saldoInvertido = this.saldoInvertido - monto;
	        actualizarSaldoDisponible();
	    }
	    
	    private void actualizarVolumenTransacciones() {
	        this.volumenTransacciones++; // Forma correcta de sumar 1 en Java
	    }

	    public String obtenerCvu() { 
	    	return cvu; 
	    }

	    public String obtenerAlias() { 
	    	return alias; 
	    }

	    public String obtenerTipoDeCuenta() { 
	    	return tipoDeCuenta; 
	    }

	    public String obtenerIdUsuarioPropietario() { 
	    	return idUsuarioPropietario; 
	    }

	    public double obtenerSaldoTotal() { 
	    	return saldoTotal; 
	    }

	    public double obtenerSaldoDisponible() { 
	    	return saldoDisponible; 
	    }
  
	    public double obtenerSaldoInvertido() { 
	    	return saldoInvertido; 
	    }
	
		public int obtenerVolumenTransacciones() { 
			return volumenTransacciones; 
		}

		public Map<String, Actividad> obtenerHistorialCuenta() { 
			return historialCuenta; 
		}
	
		@Override
		public String toString() {
			return "Cuenta [cvu=" + cvu + ", alias=" + alias + ", tipo=" + tipoDeCuenta + ", idUsuarioPropietario=" + idUsuarioPropietario
				+ ", saldoDisponible=" + saldoDisponible + ", saldoInvertido=" + saldoInvertido + ", historialCuenta="
				+ historialCuenta + ", volumenTransacciones=" + volumenTransacciones + "]";
	
		}
		
		

}
