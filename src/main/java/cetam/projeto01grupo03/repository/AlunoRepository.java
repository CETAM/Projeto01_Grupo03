package cetam.projeto01grupo03.repository;

import cetam.projeto01grupo03.model.Aluno;
import cetam.projeto01grupo03.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlunoRepository extends JpaRepository <Aluno, Long> {
    List<Aluno> findByNomeContainingIgnoreCase(String nome);
}
