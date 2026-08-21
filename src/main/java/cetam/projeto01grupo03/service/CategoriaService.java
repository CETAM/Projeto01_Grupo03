package cetam.projeto01grupo03.service;

import cetam.projeto01grupo03.model.Categoria;
import cetam.projeto01grupo03.repository.CategoriaRepository;
import cetam.projeto01grupo03.repository.LivroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final LivroRepository livroRepository;

    public CategoriaService(CategoriaRepository categoriaRepository, LivroRepository livroRepository) {
        this.categoriaRepository = categoriaRepository;
        this.livroRepository = livroRepository;
    }

    public List<Categoria> listarTodas(String nome) {
        if (nome != null && !nome.isBlank()) {
            return categoriaRepository.findByNomeContainingIgnoreCase(nome.trim());
        }
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada com o ID: " + id));
    }

    @Transactional
    public Categoria salvar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public void excluir(Long id) {
        Categoria categoria = buscarPorId(id);

        boolean possuiLivros = !livroRepository.findByCategoriaId(id).isEmpty();
        if (possuiLivros) {
            throw new IllegalStateException("Não é possível excluir a categoria '" + categoria.getNome() + "' porque existem livros vinculados a ela no acervo.");
        }

        categoriaRepository.delete(categoria);
    }
}
