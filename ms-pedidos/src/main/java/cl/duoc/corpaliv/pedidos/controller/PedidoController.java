package cl.duoc.corpaliv.pedidos.controller;

import cl.duoc.corpaliv.pedidos.model.Pedido;
import cl.duoc.corpaliv.pedidos.repository.PedidoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    private final PedidoRepository repository;

    public PedidoController(PedidoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pedido> crear(@Valid @RequestBody Pedido pedido) {
        if (pedido.getTotal() == null && pedido.getProductoPrecio() != null && pedido.getCantidad() != null) {
            pedido.setTotal(pedido.getProductoPrecio() * pedido.getCantidad());
        }
        if (pedido.getFechaRegistro() == null || pedido.getFechaRegistro().isBlank()) {
            pedido.setFechaRegistro(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        }
        if (pedido.getEstado() == null || pedido.getEstado().isBlank()) {
            pedido.setEstado("PENDIENTE");
        }
        Pedido guardado = repository.save(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizar(@PathVariable Long id, @Valid @RequestBody Pedido datos) {
        return repository.findById(id)
                .map(existente -> {
                    existente.setProductoNombre(datos.getProductoNombre());
                    existente.setProductoPrecio(datos.getProductoPrecio());
                    existente.setCantidad(datos.getCantidad());
                    existente.setTotal(datos.getProductoPrecio() * datos.getCantidad());
                    existente.setNombreCliente(datos.getNombreCliente());
                    existente.setCorreoCliente(datos.getCorreoCliente());
                    existente.setTelefonoCliente(datos.getTelefonoCliente());
                    if (datos.getEstado() != null && !datos.getEstado().isBlank()) {
                        existente.setEstado(datos.getEstado());
                    }
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
