package billetera;

import java.util.HashMap;
import java.util.Map;

public abstract class Cuenta {
	



	private String cvu;
	 private String alias;
	 private String idUsuarioPropietario;
	 private double saldoTotal;
	 private double saldoDisponible;
	 private double saldoInvertido;
	 private Map <String, Actividad> historialCuenta = new HashMap<>();
	 private int volumenTransacciones;
	
	 
	 
	 public Cuenta(String cvu, String alias, String idUsuarioPropietario, double saldoTotal) {
			
			this.setSaldoTotal(saldoTotal);
			this.saldoDisponible= saldoTotal;
			this.saldoInvertido=ControlDeCuentas.INICIO;
			this.cvu = cvu;
			this.alias = alias;
			this.idUsuarioPropietario = idUsuarioPropietario;
			this.volumenTransacciones=0;
		
			
		}
	 

	 
	 private double getSaldoDisponible() {
		return saldoDisponible;
	}



	private void setSaldoDisponible(double saldoDisponible) {
		this.saldoDisponible = saldoDisponible;
	}
	
	public void actualizarSaldoDisponible(double saldoDisponible) {
		// Este metodo es para no entrar directo al Set
	}



	private double getSaldoInvertido() {
		return saldoInvertido;
	}



	private void setSaldoInvertido(double saldoInvertido) {
		this.saldoInvertido = saldoInvertido;
	}
	
	public void setearSaldoInvertido (double saldoInvertido) {}



	private int getVolumenTransacciones() {
		return volumenTransacciones;
	}



	private void setVolumenTransacciones(int volumenTransacciones) {
		this.volumenTransacciones = volumenTransacciones;
	}
	
	public void actualizarVolumenTransacciones (int volumenTransacciones) {}



	private String getCvu() {
		return cvu;
	}



	private String getAlias() {
		return alias;
	}



	private String getIdUsuarioPropietario() {
		return idUsuarioPropietario;
	}

	private double getSaldoTotal() {
		return saldoTotal;
	}



	private void setSaldoTotal(double saldoTotal) {
		this.saldoTotal = saldoTotal;
	}


	private Map<String, Actividad> getHistorialCuenta() {
		return historialCuenta;
	}



	public void agregarActividad(String id, Actividad actividad) {
		    this.historialCuenta.put(id, actividad);
		}



	@Override
	public String toString() {
		return "Cuenta [cvu=" + cvu + ", alias=" + alias + ", idUsuarioPropietario=" + idUsuarioPropietario
				+ ", saldoDisponible=" + saldoDisponible + ", saldoInvertido=" + saldoInvertido + ", historialCuenta="
				+ historialCuenta + ", volumenTransacciones=" + volumenTransacciones + "]";
	}



	public double  obtenerSaldoDisponible() {
		
		double saldoDispoible= saldoTotal - saldoInvertido;
		
		return saldoDispoible;
		
	}




	
}
