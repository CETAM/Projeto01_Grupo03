package cetam.projeto01grupo03.repository;

import cetam.projeto01grupo03.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository <Autor, Long> {
}
