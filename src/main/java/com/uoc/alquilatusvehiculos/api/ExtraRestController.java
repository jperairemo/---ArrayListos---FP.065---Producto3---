package com.uoc.alquilatusvehiculos.api;

import com.uoc.alquilatusvehiculos.model.Extra;
import com.uoc.alquilatusvehiculos.repository.ExtraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/extras")
@RequiredArgsConstructor
public class ExtraRestController {

    private final ExtraRepository extraRepository;

    @GetMapping
    public List<Extra> getAll() {
        return extraRepository.findAll();
    }

    @GetMapping("/{id}")
    public Extra getById(@PathVariable Long id) {
        return extraRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Extra create(@RequestBody Extra extra) {
        return extraRepository.save(extra);
    }

    @PutMapping("/{id}")
    public Extra update(@PathVariable Long id, @RequestBody Extra extra) {
        extra.setId(id);
        return extraRepository.save(extra);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        extraRepository.deleteById(id);
    }
}
