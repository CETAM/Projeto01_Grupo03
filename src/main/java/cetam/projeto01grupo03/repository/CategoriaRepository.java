package cetam.projeto01grupo03.repository;

import cetam.projeto01grupo03.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByNomeContainingIgnoreCase(String nome);
    boolean existsByNomeIgnoreCase(String nome);
}
