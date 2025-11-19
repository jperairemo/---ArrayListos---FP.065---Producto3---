//package com.uoc.alquilatusvehiculos.security;
//
//import com.uoc.alquilatusvehiculos.model.User;           // ajusta el paquete de tu entidad
//import com.uoc.alquilatusvehiculos.repository.UserRepository; // ajusta el paquete del repo
//import org.springframework.security.core.userdetails.*;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UserRepository repo;
//
//    public CustomUserDetailsService(UserRepository repo) {
//        this.repo = repo;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {
//        // Permite iniciar sesión con username **o** email
//        User u = repo.findByUsername(input)
//                .orElseGet(() -> repo.findByEmail(input)
//                        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + input)));
//
//        return org.springframework.security.core.userdetails.User
//                .withUsername(u.getUsername())     // o u.getEmail() si prefieres
//                .password(u.getPassword())         // DEBE estar codificada (BCrypt)
//                .authorities("ROLE_USER")      // p.ej. "ROLE_USER"
//                .build();
//    }
//}
