package com.uoc.alquilatusvehiculos.service;

import com.uoc.alquilatusvehiculos.model.Cliente;
import com.uoc.alquilatusvehiculos.model.Role;
import com.uoc.alquilatusvehiculos.model.User;
import com.uoc.alquilatusvehiculos.repository.ClienteRepository;
import com.uoc.alquilatusvehiculos.repository.RoleRepository;
import com.uoc.alquilatusvehiculos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registra usuario nuevo, cifra contraseña, asigna rol y crea Cliente asociado
     */
    public User saveUser(User user) {

        // 1) cifrar contraseña
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 2) asignar rol USER por defecto (si no existe, lo crea automáticamente)
        Role roleUser = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> {
                    Role nuevoRol = new Role();
                    nuevoRol.setName("ROLE_USER");
                    return roleRepository.save(nuevoRol);
                });

        user.getRoles().add(roleUser);

        // 3) guardar usuario en base de datos
        User usuarioGuardado = userRepository.save(user);

        // 4) crear cliente vinculado al usuario (relación 1:1)
        Cliente cliente = Cliente.builder()
                .nombre(null) // se completará desde la vista de edición de perfil
                .apellidos(null)
                .dni(null)
                .telefono(null)
                .email(usuarioGuardado.getEmail())
                .user(usuarioGuardado)
                .build();

        clienteRepository.save(cliente);

        return usuarioGuardado;
    }
}
