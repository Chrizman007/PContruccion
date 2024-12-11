/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemavinculacion.pojo;

/**
 *
 * @author chris
 */
public class Profesor {
    private Integer idProfesor;
    private String nombre;
    private String matricula;
    private String correo;
    private Integer idUsuario;
    private String usuario;
    private String contraseña;

    // Constructor vacío
    public Profesor() {
    }

    // Constructor con parámetros
    public Profesor(Integer idProfesor, String nombre, String matricula, String correo, Integer idUsuario, String usuario, String contraseña) {
        this.idProfesor = idProfesor;
        this.nombre = nombre;
        this.matricula = matricula;
        this.correo = correo;
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    // Getters y Setters
    public Integer getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(Integer idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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
