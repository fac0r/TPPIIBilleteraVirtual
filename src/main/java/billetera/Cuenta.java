package billetera;

import java.util.HashMap;
import java.util.Map;

public abstract class Cuenta {
	



	private String cvu;
	 private String alias;
	 private String idUsuarioPropietario;
	 private String tipoDeCuenta;
	 private double saldoTotal;
	 private double saldoDisponible;
	 private double saldoInvertido;
	 private Map <String, Actividad> historialCuenta = new HashMap<>();
	 private int volumenTransacciones;
	
	 
	 
	 public Cuenta(String cvu, String alias, String idUsuarioPropietario, double saldoTotal, String tipoDeCuenta) {
			
			this.setSaldoTotal(saldoTotal);
			this.saldoDisponible= saldoTotal;
			this.saldoInvertido=ControlDeCuentas.INICIO;
			this.cvu = cvu;
			this.alias = alias;
			this.idUsuarioPropietario = idUsuarioPropietario;
			this.volumenTransacciones=0;
			this.tipoDeCuenta=tipoDeCuenta;
		
			
		}
	 
	
	 
	 private String getTipoDeCuenta() {
		return tipoDeCuenta;
	}

	 public String obtenerTipoDeCuenta() {
		 return getTipoDeCuenta();
	 }

	public void actualizarSaldoDisponible() {
		    saldoDisponible = saldoTotal - saldoInvertido;
		}
	 
	 private void reducirSaldoTotal(double monto) {
		 double saldoTotal= getSaldoTotal() - monto;
		 setSaldoTotal(saldoTotal);
		 
	 }
	 
	 private boolean  hayDisponibilidadParaRealizarOperacion (double monto) {
		 
		 return getSaldoDisponible() >= monto;
		 
	 }
	 
	 public void realizarInversion (double monto) {
		 
		 if (!hayDisponibilidadParaRealizarOperacion(monto)) {
			 
			 throw new IllegalArgumentException ("No hay suficiente saldo disponible");
			 	 
		 };
	 }
	 
	 public void emitirTransferencia (double monto) {
		 
		 if (!hayDisponibilidadParaRealizarOperacion(monto)) {
			 
			 throw new IllegalArgumentException ("No hay suficiente saldo disponible");
			 	 
		 }// Este metodo es para no entrar directo al Set
		 reducirSaldoTotal(monto);
		 actualizarSaldoDisponible();
		
	
	 }
	 
	 public void recibirTransferencia (double monto) {
		 if (!validarMonto(monto, getSaldoTotal())){
			 throw new IllegalStateException ("Para ingresar ese monto debe aumentar la categoria de su cuenta");
		}
		 setSaldoTotal(getSaldoTotal()+monto);
		 actualizarSaldoDisponible();
		 
	 }

	 

		
	

	public abstract boolean validarMonto(double monto, double saldoActual);

	public double getSaldoDisponible() {
		return saldoDisponible;
	}



	public void setSaldoDisponible(double saldoDisponible) {
		this.saldoDisponible = saldoTotal - saldoInvertido;
	}
	
	



	public double getSaldoInvertido() {   //Es public para que el metodo actualizar SaldoDisponible de la cuentaPremium los pueda usar 
		return saldoInvertido;
	}

	public double llamarGetSaldoInvertido() {
		return getSaldoInvertido();
	}


	private void setSaldoInvertido(double saldoInvertido) {
		this.saldoInvertido = saldoInvertido;
		actualizarSaldoDisponible();
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

	public String mostrarCvu() {
		return getCvu();
	}

	private String getAlias() {
		return alias;
	}

	public String mostrarAlias() {
		return getAlias();
	}


	private String getIdUsuarioPropietario() {
		return idUsuarioPropietario;
	}

	public double getSaldoTotal() {
		return saldoTotal;
	}

	public double llamarGetSaldoTotal() {
		return getSaldoTotal();
	}


	private void setSaldoTotal(double saldoTotal) {
		this.saldoTotal = saldoTotal;
	}


	private Map<String, Actividad> getHistorialCuenta() {
		return historialCuenta;
	}

    public Map<String, Actividad> accesoGetHistorialCuenta() {
		
    	Map<String, Actividad> historialCuenta= getHistorialCuenta() ;
    	
    	return historialCuenta;}
    
    
    private void actualizarSaldoInvertido(double saldo) {
    	setSaldoInvertido(getSaldoInvertido()+ saldo);
    	
    }
    
	public void agregarActividad(String id, Actividad actividad) {
		
 
		    this.historialCuenta.put(id, actividad);
		    if (actividad instanceof Inversion ) {
		    	double monto = Double.parseDouble(actividad.mostrarMonto());
		    	this.actualizarSaldoInvertido(monto);
		    }
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
