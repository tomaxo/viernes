package com.example.viernes.Repository;

import com.example.viernes.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
