/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemavinculacion.pojo;

/**
 *
 * @author chris
 */
public class ReportePP {
    private int idReportePP;
    private int idProyectoPP;
    private String fechaSubida;
    private byte[] archivoReporte;
    private String comentarios;
    private int matricula;
    private String tipo;

    public ReportePP(int idReportePP, int idProyectoPP, String fechaSubida, byte[] archivoReporte, String comentarios, int matricula, String tipo) {
        this.idReportePP = idReportePP;
        this.idProyectoPP = idProyectoPP;
        this.fechaSubida = fechaSubida;
        this.archivoReporte = archivoReporte;
        this.comentarios = comentarios;
        this.matricula = matricula;
        this.tipo = tipo;
    }
    
    public ReportePP() {}
    
    public int getIdReportePP() {
        return idReportePP;
    }
    public void setIdReportePP(int idReportePP) {
        this.idReportePP = idReportePP;
    }
    public int getIdProyectoPP() {
        return idProyectoPP;
    }
    public void setIdProyectoPP(int idProyectoPP) {
        this.idProyectoPP = idProyectoPP;
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
