package com.example.viernes.Repository;

import com.example.viernes.Model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefonoRepository extends JpaRepository<Phone,Long> {
}
