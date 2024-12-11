/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemavinculacion.pojo;

/**
 *
 * @author chris
 */
public class Estudiante {

    private String matricula;
    private String nombre;
    private String correo;
    private String telefono;
    private String carrera;
    private String seguroFacultativo;
    private Float promedioGeneral;
    private Integer creditos;
    private byte[] cartaLiberacion;
    private byte[] cartaAsignacion;
    private byte[] planActividades;
    private byte[] evaluacionOrganizacion;
    private Integer idUsuario;
    private String usuario;
    private String contraseña;

    public Estudiante() {
    }

    public Estudiante(String matricula, String nombre, String correo, String telefono, String carrera, String seguroFacultativo, Float promedioGeneral, Integer creditos, byte[] cartaLiberacion, byte[] cartaAsignacion, byte[] planActividades, byte[] evaluacionOrganizacion, Integer idUsuario, String usuario, String contraseña) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.carrera = carrera;
        this.seguroFacultativo = seguroFacultativo;
        this.promedioGeneral = promedioGeneral;
        this.creditos = creditos;
        this.cartaLiberacion = cartaLiberacion;
        this.cartaAsignacion = cartaAsignacion;
        this.planActividades = planActividades;
        this.evaluacionOrganizacion = evaluacionOrganizacion;
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getSeguroFacultativo() {
        return seguroFacultativo;
    }

    public void setSeguroFacultativo(String seguroFacultativo) {
        this.seguroFacultativo = seguroFacultativo;
    }

    public Float getPromedioGeneral() {
        return promedioGeneral;
    }

    public void setPromedioGeneral(Float promedioGeneral) {
        this.promedioGeneral = promedioGeneral;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

    public byte[] getCartaLiberacion() {
        return cartaLiberacion;
    }

    public void setCartaLiberacion(byte[] cartaLiberacion) {
        this.cartaLiberacion = cartaLiberacion;
    }

    public byte[] getCartaAsignacion() {
        return cartaAsignacion;
    }

    public void setCartaAsignacion(byte[] cartaAsignacion) {
        this.cartaAsignacion = cartaAsignacion;
    }

    public byte[] getPlanActividades() {
        return planActividades;
    }

    public void setPlanActividades(byte[] planActividades) {
        this.planActividades = planActividades;
    }

    public byte[] getEvaluacionOrganizacion() {
        return evaluacionOrganizacion;
    }

    public void setEvaluacionOrganizacion(byte[] evaluacionOrganizacion) {
        this.evaluacionOrganizacion = evaluacionOrganizacion;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}