package com.uoc.alquilatusvehiculos.api;

import com.uoc.alquilatusvehiculos.model.Alquiler;
import com.uoc.alquilatusvehiculos.model.Cliente;
import com.uoc.alquilatusvehiculos.model.Extra;
import com.uoc.alquilatusvehiculos.model.Vehiculo;
import com.uoc.alquilatusvehiculos.repository.AlquilerRepository;
import com.uoc.alquilatusvehiculos.repository.ClienteRepository;
import com.uoc.alquilatusvehiculos.repository.ExtraRepository;
import com.uoc.alquilatusvehiculos.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/alquileres")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AlquilerRestController {

    private final AlquilerRepository alquilerRepo;
    private final ClienteRepository clienteRepo;
    private final VehiculoRepository vehiculoRepo;
    private final ExtraRepository extraRepo;

    // GET /api/alquileres
    @GetMapping
    public List<Alquiler> getAll() {
        return alquilerRepo.findAll();
    }

    // GET /api/alquileres/cliente/{id}
    @GetMapping("/cliente/{clienteId}")
    public List<Alquiler> getByCliente(@PathVariable Long clienteId) {
        return alquilerRepo.findByClienteId(clienteId);
    }

    // POST /api/alquileres
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Alquiler nuevo) {

        if (nuevo.getCliente() == null || nuevo.getVehiculo() == null) {
            return ResponseEntity.badRequest().body("cliente y vehiculo son obligatorios");
        }

        Cliente cliente = clienteRepo.findById(nuevo.getCliente().getId())
                .orElse(null);
        Vehiculo vehiculo = vehiculoRepo.findById(nuevo.getVehiculo().getId())
                .orElse(null);

        if (cliente == null || vehiculo == null) {
            return ResponseEntity.badRequest().body("cliente o vehiculo no válido");
        }

        // extraer los ids de extras si vienen
        var extraIds = nuevo.getExtras().stream().map(Extra::getId).toList();
        var extras = extraRepo.findAllById(extraIds);

        nuevo.setCliente(cliente);
        nuevo.setVehiculo(vehiculo);
        nuevo.setExtras(Set.copyOf(extras));

        nuevo.calcularPrecioTotal();

        Alquiler guardado = alquilerRepo.save(nuevo);

        return ResponseEntity.created(
                URI.create("/api/alquileres/" + guardado.getId())
        ).body(guardado);
    }

    // GET /api/alquileres/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Alquiler> getById(@PathVariable Long id) {
        return alquilerRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/alquileres/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!alquilerRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        alquilerRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
