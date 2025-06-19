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
    List<RegistroImposto> findByMesAno(@Param("mes") int mes, @Param("ano") int ano);
    
    @Query("SELECT COALESCE(SUM(r.valorVendido), 0) FROM RegistroImposto r WHERE YEAR(r.dataEfetivacao) = :ano AND MONTH(r.dataEfetivacao) = :mes")
    Double sumValorVendidoByMesAno(@Param("mes") int mes, @Param("ano") int ano);
    
    @Query("SELECT COALESCE(SUM(r.valorImposto), 0) FROM RegistroImposto r WHERE YEAR(r.dataEfetivacao) = :ano AND MONTH(r.dataEfetivacao) = :mes")
    Double sumValorImpostoByMesAno(@Param("mes") int mes, @Param("ano") int ano);
    
    @Query("SELECT COUNT(r) FROM RegistroImposto r WHERE YEAR(r.dataEfetivacao) = :ano AND MONTH(r.dataEfetivacao) = :mes")
    Long countByMesAno(@Param("mes") int mes, @Param("ano") int ano);
    
    List<RegistroImposto> findByDataEfetivacaoBetween(LocalDateTime inicio, LocalDateTime fim);
}

