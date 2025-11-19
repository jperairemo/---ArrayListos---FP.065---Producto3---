package com.uoc.alquilatusvehiculos.api;

import com.uoc.alquilatusvehiculos.model.Vehiculo;
import com.uoc.alquilatusvehiculos.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")  // para poder llamar desde cualquier front o Postman sin líos de CORS
public class VehiculoRestController {

    private final VehiculoRepository vehiculoRepository;

    // GET /api/vehiculos  -> lista todos los vehículos
    @GetMapping
    public List<Vehiculo> getAll() {
        return vehiculoRepository.findAll();
    }

    // GET /api/vehiculos/{id}  -> un vehículo concreto
    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> getById(@PathVariable Long id) {
        return vehiculoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/vehiculos  -> crear vehículo
    @PostMapping
    public ResponseEntity<Vehiculo> create(@RequestBody Vehiculo nuevo) {
        Vehiculo guardado = vehiculoRepository.save(nuevo);
        return ResponseEntity
                .created(URI.create("/api/vehiculos/" + guardado.getId()))
                .body(guardado);
    }

    // PUT /api/vehiculos/{id} -> actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> update(@PathVariable Long id, @RequestBody Vehiculo datos) {
        return vehiculoRepository.findById(id)
                .map(v -> {
                    v.setMatricula(datos.getMatricula());
                    v.setMarca(datos.getMarca());
                    v.setModelo(datos.getModelo());
                    v.setTipo(datos.getTipo());
                    v.setPrecioDia(datos.getPrecioDia());
                    v.setDisponible(datos.isDisponible());
                    Vehiculo actualizado = vehiculoRepository.save(v);
                    return ResponseEntity.ok(actualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/vehiculos/{id} -> borrar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!vehiculoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        vehiculoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
