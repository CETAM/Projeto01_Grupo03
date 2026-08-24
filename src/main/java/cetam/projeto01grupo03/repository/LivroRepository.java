package cetam.projeto01grupo03.repository;

import cetam.projeto01grupo03.dto.CategoriaDistribuicaoDTO;
import cetam.projeto01grupo03.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository <Livro, Long> {
    List<Livro> findByTituloContainingIgnoreCase(String titulo);

    List<Livro> findByDisponivelTrue();

    List<Livro> findByAutorId(Long autorId);

    List<Livro> findByEditoraId(Long editoraId);

    List<Livro> findByCategoriaId(Long categoriaId);

    Optional<Livro> findByIsbn(String isbn);

    @Query("SELECT COALESCE(SUM(l.quantidadeExemplares), 0) FROM Livro l")
    Long sumTotalExemplares();

    //Query que foi ajustada
    @Query("SELECT new cetam.projeto01grupo03.dto.CategoriaDistribuicaoDTO(l.categoria.nome, COUNT(l)) " +
            "FROM Livro l WHERE l.categoria IS NOT NULL " +
            "GROUP BY l.categoria.id, l.categoria.nome " +
            "ORDER BY COUNT(l) DESC")
    List<CategoriaDistribuicaoDTO> findDistribuicaoPorCategoria();

}
