package cetam.projeto01grupo03.repository;

import cetam.projeto01grupo03.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository <Aluno, Long> {
    List<Aluno> findByNomeContainingIgnoreCase(String nome);

    Optional<Aluno> findByMatricula(String matricula);

    List<Aluno> findByAtivoTrue();

    boolean existsByMatricula(String matricula);

    boolean existsByMatriculaAndIdNot(String matricula, Long id);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
}
