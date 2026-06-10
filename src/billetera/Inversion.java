package billetera;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public abstract class Inversion extends Actividad {
	
	private String dniAsociado;
	private String cvuAsociado;
	private boolean precancelable;
	private String estadoInversion;
	private double tasaInteres;
	private int plazoDias;
	private String tipoDeInversion;
	private LocalDate fechaDeVencimiento;


	public Inversion(String dniAsociado, String cvuAsociado,boolean precancelable,double tasaInteres,int plazoDias,  LocalDate fecha, double monto, String detalle,  String tipoDeInversion, LocalDate fechaDeVencimiento) {
		super(generarId(), fecha, TipoOperacion.INVERSION, monto, detalle);
		
		
		//IREP
		
		if (dniAsociado==null) 
		{throw new IllegalArgumentException("La inversion debe tener un dni asociado ");}
		if (cvuAsociado==null)
		{throw new IllegalArgumentException("La inversion debe tener una cuenta asociada ");}
		if (tasaInteres<=0)
		{throw new IllegalArgumentException("La tasa de interes no puede ser menor a cero");}
		if (plazoDias<0)
		{throw new IllegalArgumentException("La cantidad de dias de permanencia de la inversion debe ser mayor a cero");}
		if (tipoDeInversion==null) {
		throw new IllegalArgumentException ("EL tipo de inversion debe ser informado");}
		if (fechaDeVencimiento==null)
		{throw new IllegalArgumentException("La inversion debe tener una fecha de vencimiento prefijada");}
		if (ChronoUnit.DAYS.between(fecha, fechaDeVencimiento) < 0)
		{throw new IllegalArgumentException("La fecha final no puede ser anterior a la de inicio de la inversion");}
			
		this.dniAsociado=dniAsociado;
		this.cvuAsociado=cvuAsociado;
		this.precancelable=precancelable;
		this.estadoInversion=EstadoInversion.ACTIVA;
		this.tasaInteres=tasaInteres;
		this.plazoDias=plazoDias;
		this.tipoDeInversion=tipoDeInversion;
		this.fechaDeVencimiento=fechaDeVencimiento;
	
	}
	
	public int cantidadDeDiasTranscurridosEnInversion(LocalDate fechaInicio, LocalDate fechaDeCancelacion) {
		// ChronoUnit es la herramienta perfecta y exacta para calcular días en Java
		   return (int) ChronoUnit.DAYS.between(fechaInicio, fechaDeCancelacion);  //Esto el profe lo comento en una clase
	   }
	
	public static LocalDate calcularFechaDeVencimiento(int cantDias) {
		 
	    LocalDate fecha = Utilitarios.hoy().plusDays(cantDias);
	    return fecha;
	}
	
	public String obtenerEstadoInversion() {
		return estadoInversion;
	}

	public void cambiarEstadoDeInversion(String estadoInversion) {
		   this.estadoInversion = estadoInversion;
	}

	public String getIdActividad () {
			return  obtenerIdActividad();
	}

	public double obtenerTasaInteres() {
		return tasaInteres;
	}
	
	public boolean esPrecancelable() {
        return precancelable;
    }

	public LocalDate obtenerFechaDeVencimiento() {
		return fechaDeVencimiento;
	}

	public String obtenerCvuAsociado() {
		return cvuAsociado; 
	}

	public String obtenerDniAsociado() {
		return dniAsociado;
	}
	
	
	

    public String getEstadoInversion() {
		return estadoInversion;
	}

	public void setEstadoInversion(String estadoInversion) {
		this.estadoInversion = estadoInversion;
	}

	public double calcularRentabilidadDeInversion(LocalDate fechaDeCancelacion) {
        // formula: monto * (Tasa / 365) * dias
        int dias = cantidadDeDiasTranscurridosEnInversion(obtenerFecha(), fechaDeCancelacion);
        
        double rentabilidad = obtenerMonto() * (obtenerTasaInteres() / 365) * dias;
        return rentabilidad;
    }
	
	
    public double cancelar() {
       
        double rentabilidad = calcularRentabilidadDeInversion(obtenerFechaDeVencimiento());
        cambiarEstadoDeInversion(EstadoInversion.FINALIZADA);
        return rentabilidad;
    }
	
	
    public double precancelar() {
       
        if (!esPrecancelable()) {
            throw new RuntimeException("Esta inversión no admite precancelación.");
        }
       
        double rentabilidad = calcularRentabilidadDeInversion(Utilitarios.hoy());
        
        cambiarEstadoDeInversion(EstadoInversion.PRECANCELADA);
          
        double rentabilidadEnPrecancelacion = rentabilidad / 2;
        
        return rentabilidadEnPrecancelacion;
    }
	
    
    
    
	private int getPlazoDias() {
		return plazoDias;
	}
	
	public int obtenerPlazo() {
		return getPlazoDias();
	}

	private String getTipoDeInversion() {
		return tipoDeInversion;
	}
	
	public String obtenerTipoDeInversion()
	{return getTipoDeInversion();}	
	

	@Override
	public String toString() {
		return " Fecha: " + obtenerFecha() + " Origen: " + obtenerCvuAsociado() + " Descripcion: " + obtenerTipoDeInversion() 
		+ " monto: " + obtenerMonto() + " Plazo: " + obtenerPlazo() + obtenerEstadoInversion()  ;
	}

	
	



	
	
}
