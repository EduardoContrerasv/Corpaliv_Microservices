package cl.duoc.corpaliv.voluntarios.controller;

import cl.duoc.corpaliv.voluntarios.model.Voluntario;
import cl.duoc.corpaliv.voluntarios.repository.VoluntarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/voluntarios")
@CrossOrigin(origins = "*")
public class VoluntarioController {

    private final VoluntarioRepository repository;

    public VoluntarioController(VoluntarioRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Voluntario>> listarTodos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Voluntario> obtenerPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Voluntario> crear(@Valid @RequestBody Voluntario voluntario) {
        Voluntario guardado = repository.save(voluntario);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Voluntario> actualizar(@PathVariable Long id, @Valid @RequestBody Voluntario datos) {
        return repository.findById(id)
                .map(existente -> {
                    existente.setNombreCompleto(datos.getNombreCompleto());
                    existente.setCorreo(datos.getCorreo());
                    existente.setTelefono(datos.getTelefono());
                    existente.setFechaContacto(datos.getFechaContacto());
                    existente.setRecibirCorreo(datos.getRecibirCorreo());
                    existente.setVoluntarioPrimera(datos.getVoluntarioPrimera());
                    return ResponseEntity.ok(repository.save(existente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
