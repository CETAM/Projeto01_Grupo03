package cetam.projeto01grupo03.repository;

import cetam.projeto01grupo03.model.Emprestimo;
import cetam.projeto01grupo03.model.StatusEmprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmprestimoRepository extends JpaRepository <Emprestimo, Long> {
    List<Emprestimo> findByStatus(StatusEmprestimo status);

    List<Emprestimo> findByAlunoNomeContainingIgnoreCase(String nomeAluno);

}
