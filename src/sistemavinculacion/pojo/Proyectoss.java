/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemavinculacion.pojo;

/**
 *
 * @author chris
 */
public class Proyectoss {
    private int idProyecto;
    private String nombre;
    private String descripcion;
    private int cupos;
    private String requisitos;
    private String fechaInicio;
    private String fechaFin;
    private String nombreOrganizacion;
    private String ubicacion;
    private int ofertado;
    private int idResponsable;
    private byte[] archivo;

    public Proyectoss(int idProyecto, String nombre, String descripcion, int cupos, String requisitos, String fechaInicio, String fechaFin, String nombreOrganizacion, String ubicacion, int ofertado, int idResponsable, byte[] archivo) {
        this.idProyecto = idProyecto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cupos = cupos;
        this.requisitos = requisitos;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.nombreOrganizacion = nombreOrganizacion;
        this.ubicacion = ubicacion;
        this.ofertado = ofertado;
        this.idResponsable = idResponsable;
        this.archivo = archivo;
    }

    
    
    public Proyectoss() {}

    
    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCupos() {
        return cupos;
    }

    public void setCupos(int cupos) {
        this.cupos = cupos;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getNombreOrganizacion() {
        return nombreOrganizacion;
    }

    public void setNombreOrganizacion(String nombreOrganizacion) {
        this.nombreOrganizacion = nombreOrganizacion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int isOfertado() {
        return ofertado;
    }

    public void setOfertado(int ofertado) {
        this.ofertado = ofertado;
    }

    public int getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(int idResponsable) {
        this.idResponsable = idResponsable;
    }
    
    public byte[] getArchivo() {
        return archivo;
    }
    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }
}