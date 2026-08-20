package cetam.projeto01grupo03.repository;

import cetam.projeto01grupo03.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository <Livro, Long> {
    List<Livro> findByTituloContainingIgnoreCase(String nome);
    List<Livro> findByDisponivelTrue();
    List<Livro> findByAutorId(Long autorId);
    List<Livro> findByEditoraId(Long editoraId);
    Optional<Livro> findBysbn(String isbn);

}
