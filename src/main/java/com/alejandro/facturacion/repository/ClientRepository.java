package com.alejandro.facturacion.repository;

import com.alejandro.facturacion.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);

    Optional<Client> findByIdentificationNumber(String identificationNumber);

    boolean existsByEmail(String email);

    boolean existsByIdentificationNumber(String identificationNumber);
}

