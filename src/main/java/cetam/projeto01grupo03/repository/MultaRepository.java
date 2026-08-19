package cetam.projeto01grupo03.repository;

import cetam.projeto01grupo03.model.Multa;
import cetam.projeto01grupo03.model.StatusMulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MultaRepository extends JpaRepository <Multa, Long> {
    List<Multa> findByStatus(StatusMulta status);

    List<Multa> findByEmprestimoAlunoId(Long alunoId);

    List<Multa> findByEmprestimoAlunoNomeContainingIgnoreCaseOrEmprestimoAlunoMatriculaContainingIgnoreCase(String nome, String matricula);
}
