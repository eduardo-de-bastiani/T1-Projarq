package com.microregistro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RegistroImpostoRepository extends JpaRepository<RegistroImposto, Long> {
    
    @Query("SELECT r FROM RegistroImposto r WHERE YEAR(r.dataEfetivacao) = :ano AND MONTH(r.dataEfetivacao) = :mes")
    List<RegistroImposto> findByAnoAndMes(@Param("ano") int ano, @Param("mes") int mes);
    
    @Query("SELECT SUM(r.valorVendido) FROM RegistroImposto r WHERE YEAR(r.dataEfetivacao) = :ano AND MONTH(r.dataEfetivacao) = :mes")
    Double sumValorVendidoByAnoAndMes(@Param("ano") int ano, @Param("mes") int mes);
    
    @Query("SELECT SUM(r.valorImposto) FROM RegistroImposto r WHERE YEAR(r.dataEfetivacao) = :ano AND MONTH(r.dataEfetivacao) = :mes")
    Double sumValorImpostoByAnoAndMes(@Param("ano") int ano, @Param("mes") int mes);
}

