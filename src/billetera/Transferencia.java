package billetera;

import java.time.LocalDate;

public class Transferencia extends Actividad {
	
	private  String cuentaOrigen;
	private  String cuentaDestino;
	private  String situacion; 
	
	public Transferencia(LocalDate fecha, double monto, String detalle, 
             String cuentaOrigen, String cuentaDestino,boolean comprobante) {

		 super(generarId(), fecha, TipoOperacion.TRANSFERENCIA, monto, detalle);
		 	
		 	//IREP
		 
		 	if(cuentaOrigen ==null)
		 	{throw new IllegalArgumentException("Se debe informar la cuenta que realiza la transferencia");}
		 	if(cuentaDestino==null)
		 	{throw new IllegalArgumentException("Se debe informar la cuenta que recibirá la transferencia");}
		 	if(cuentaOrigen.equals(cuentaDestino))
		 	{throw new IllegalArgumentException("No se puede realizar una transferencia hacia la propia cuenta"); }
		 
		 
		 	this.cuentaOrigen = cuentaOrigen;
		 	this.cuentaDestino = cuentaDestino;
		 	this.situacion = comprobanteSituacion(comprobante);
	 }

	public static Transferencia crearTransferencia(String cvuOrigen, String cvuDestino, double monto, boolean comprobante) {
		   LocalDate fecha = Utilitarios.hoy();
		   return new Transferencia(fecha, monto, "DESCRIPCION DE TRANSFERENCIA", cvuOrigen, cvuDestino, comprobante);
	}
	 
	private String comprobanteSituacion(boolean comprobante) {
		 if (comprobante) {
			 return ComprobanteSituacion.APROBADA;
		 }
		 
		 else return ComprobanteSituacion.NOAPROBADA;
		
	}

	@Override
	public String toString() {
	    return "fecha: " + obtenerFecha() + "\n" +
	           "origen: " + cuentaOrigen + "\n" +
	           "destino: " + cuentaDestino + "\n" +
	           "monto: " + obtenerMonto() + "\n" +
	           situacion;
	}
	
}
