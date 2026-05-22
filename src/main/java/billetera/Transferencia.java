package billetera;

import java.time.LocalDate;

public class Transferencia extends Actividad {
	
	private static int contadorTransferencias = 0;
	private  String cuentaOrigen;
	private  String cuentaDestino;
	private  String situacion; //Pensar que hacer con situacion para el constructor
	
	

	 public Transferencia(LocalDate fecha, double monto, String detalle, 
             String cuentaOrigen, String cuentaDestino,boolean comprobante) {

		 	super(generarId(), fecha, TipoOperacion.TRANSFERENCIA, monto, detalle);
		 	
		 		this.cuentaOrigen = cuentaOrigen;
		 		this.cuentaDestino = cuentaDestino;
		 		this.situacion = comprobanteSituacion(comprobante);
	 	}


	 
	 public static Transferencia crearTransferencia(String cvuOrigen, String cvuDestino, double monto, boolean comprobante) {
		    LocalDate fecha = Utilitarios.hoy();
		    return new Transferencia(fecha, monto, "CREAR DESCRIPCION DE TRANSFERENCIA", cvuOrigen, cvuDestino, comprobante);
		}
	 
	private static String generarId() {
		contadorTransferencias++;
		return "T" + (contadorTransferencias );
		
	}


	private String comprobanteSituacion(boolean comprobante) {
		 if (comprobante) {
			 return ComprobanteSituacion.APROBADA;
		 }
		 
		 else return ComprobanteSituacion.NOAPROBADA;
		
	}

    
	public String getIdActividad () {
		
		return  mostrarIdActividad();
	}
	
	
	
	@Override
	public String toString() {
	    return "fecha: " + mostrarFecha() + "\n" +
	           "origen: " + cuentaOrigen + "\n" +
	           "destino: " + cuentaDestino + "\n" +
	           "monto: " + mostrarMonto() + "\n" +
	           situacion;
	}
	
	
	

}
