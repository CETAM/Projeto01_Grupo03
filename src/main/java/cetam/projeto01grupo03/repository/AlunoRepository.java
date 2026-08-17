package cetam.projeto01grupo03.repository;

import cetam.projeto01grupo03.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
