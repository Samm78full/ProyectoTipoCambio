package com.example.tipocambio_backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoCambioRepository extends JpaRepository<TipoCambio, Long> {
    // Aquí puedes agregar métodos personalizados si los necesitas
}