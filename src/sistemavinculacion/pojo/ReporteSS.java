/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemavinculacion.pojo;

/**
 *
 * @author chris
 */
public class ReporteSS {
    private int idReporteSS;
    private int idProyectoSS;
    private String fechaSubida;
    private byte[] archivoReporte;
    private String comentarios;
    private int matricula;
    private String tipo;
    
    public ReporteSS() {}
    
    public int getIdReporteSS() {
        return idReporteSS;
    }
    public void setIdReporteSS(int idReporteSS) {
        this.idReporteSS = idReporteSS;
    }
    public int getIdProyectoSS() {
        return idProyectoSS;
    }
    public void setIdProyectoSS(int idProyectoSS) {
        this.idProyectoSS = idProyectoSS;
    }
    public String getFechaSubida() {
        return fechaSubida;
    }
    public void setFechaSubida(String fechaSubida) {
        this.fechaSubida = fechaSubida;
    }
    public byte[] getArchivoReporte() {
        return archivoReporte;
    }
    public void setArchivoReporte(byte[] archivoReporte) {
        this.archivoReporte = archivoReporte;
    }
    public String getComentarios() {
        return comentarios;
    }
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    public int getMatricula() {
        return matricula;
    }
    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
