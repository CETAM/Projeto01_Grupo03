package cetam.projeto01grupo03.repository;

import cetam.projeto01grupo03.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByNomeContainingIgnoreCase(String nome);
}


