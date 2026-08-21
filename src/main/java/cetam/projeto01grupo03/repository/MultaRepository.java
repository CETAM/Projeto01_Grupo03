package cetam.projeto01grupo03.repository;

import cetam.projeto01grupo03.model.Multa;
import cetam.projeto01grupo03.model.StatusMulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface MultaRepository extends JpaRepository <Multa, Long> {
    List<Multa> findByStatus(StatusMulta status);

    List<Multa> findByEmprestimoAlunoId(Long alunoId);

    List<Multa> findByEmprestimoAlunoNomeContainingIgnoreCaseOrEmprestimoAlunoMatriculaContainingIgnoreCase(String nome, String matricula);

    List<Multa> findByDataGeracaoBetween(LocalDate inicio, LocalDate fim);

    List<Multa> findByDataGeracaoBetweenAndStatus(LocalDate inicio, LocalDate fim, StatusMulta status);

    long countByStatus(StatusMulta status);

    long countByStatusAndDataGeracaoBetween(StatusMulta status, LocalDate inicio, LocalDate fim);

    @Query("SELECT COALESCE(SUM(m.valor), 0) FROM Multa m WHERE m.status = :status")
    BigDecimal sumValorByStatus(@Param("status") StatusMulta status);

    @Query("SELECT COALESCE(SUM(m.valor), 0) FROM Multa m WHERE m.status = :status AND m.dataGeracao BETWEEN :inicio AND :fim")
    BigDecimal sumValorByStatusAndDataGeracaoBetween(@Param("status") StatusMulta status, @Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

    @Query("SELECT COALESCE(SUM(m.valor), 0) FROM Multa m")
    BigDecimal sumTotalValor();

    @Query("SELECT COALESCE(SUM(m.valor), 0) FROM Multa m WHERE m.dataGeracao BETWEEN :inicio AND :fim")
    BigDecimal sumTotalValorBetween(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);
}
