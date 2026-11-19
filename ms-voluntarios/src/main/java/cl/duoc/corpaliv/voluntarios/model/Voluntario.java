package cl.duoc.corpaliv.voluntarios.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "voluntarios")
public class Voluntario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre completo es obligatorio")
    private String nombreCompleto;

    @NotBlank(message = "El correo es obligatorio")
    private String correo;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @NotBlank(message = "La fecha de contacto es obligatoria")
    private String fechaContacto;

    private Boolean recibirCorreo = false;

    private Boolean voluntarioPrimera = true;

    public Voluntario() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getFechaContacto() { return fechaContacto; }
    public void setFechaContacto(String fechaContacto) { this.fechaContacto = fechaContacto; }

    public Boolean getRecibirCorreo() { return recibirCorreo; }
    public void setRecibirCorreo(Boolean recibirCorreo) { this.recibirCorreo = recibirCorreo; }

    public Boolean getVoluntarioPrimera() { return voluntarioPrimera; }
    public void setVoluntarioPrimera(Boolean voluntarioPrimera) { this.voluntarioPrimera = voluntarioPrimera; }
}
