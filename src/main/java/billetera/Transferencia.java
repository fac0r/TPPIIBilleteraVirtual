package billetera;

import java.util.Date;

public class Transferencia extends Actividad {
	
	private static int contadorTransferencias = 0;
	private  String cuentaOrigen;
	private  String cuentaDestino;
	private  String situacion; //Pensar que hacer con situacion para el constructor
	
	

	 public Transferencia(Date fecha, double monto, String detalle, 
             String cuentaOrigen, String cuentaDestino) {

		 	super(generarId(), fecha, TipoOperacion.TRANSFERENCIA, monto, detalle);
		 	
		 		this.cuentaOrigen = cuentaOrigen;
		 		this.cuentaDestino = cuentaDestino;
		 		this.situacion = comprobanteSituacion();
	 	}


	private static String generarId() {
		contadorTransferencias++;
		return "T" + (contadorTransferencias );
		
	}


	private String comprobanteSituacion() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String toString() {
		return "Transferencia [contadorTransferencias=" + contadorTransferencias + ", cuentaOrigen=" + cuentaOrigen
				+ ", cuentaDestino=" + cuentaDestino + ", situacion=" + situacion + "]";
	}
	
	
	

}
