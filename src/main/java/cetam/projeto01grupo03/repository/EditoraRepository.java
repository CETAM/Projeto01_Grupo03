package cetam.projeto01grupo03.repository;

import cetam.projeto01grupo03.model.Editora;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EditoraRepository extends JpaRepository<Editora, Long> {
    List<Editora> findByNomeContainingIgnoreCase(String nome);
}
