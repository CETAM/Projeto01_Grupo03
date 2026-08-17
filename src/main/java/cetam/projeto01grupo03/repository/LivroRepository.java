package cetam.projeto01grupo03.repository;

import cetam.projeto01grupo03.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository <Livro, Long> {
}
