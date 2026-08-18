package cetam.projeto01grupo03.service;

import cetam.projeto01grupo03.model.Livro;
import cetam.projeto01grupo03.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public List<Livro> listarTodos(String nome) {
        if (nome != null && !nome.isBlank()){
            return livroRepository.findByNomeContainingIgnoreCase(nome.trim());
        }
        return livroRepository.findAll();
    }

    public Livro buscarPorId(Long id){
        return livroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livro nao encontrado com o ID: " + id));
    }

    public Livro salvar(Livro livro){
        return livroRepository.save(livro);
    }
    public void excluir(Long id) {
        livroRepository.deleteById(id);
    }
}
