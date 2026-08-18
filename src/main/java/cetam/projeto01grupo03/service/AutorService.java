package cetam.projeto01grupo03.service;

import cetam.projeto01grupo03.model.Autor;
import cetam.projeto01grupo03.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> listarTodos(String nome) {
        if (nome != null && !nome.isBlank()) {
            return autorRepository.findByNomeContainingIgnoreCase(nome.trim());
        }
        return autorRepository.findAll();
    }

    public Autor buscarPorId(Long id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Autor não encontrado com o ID: " + id));
    }

    public Autor salvar(Autor autor) {
        return autorRepository.save(autor);
    }

    public void excluir(Long id) {
        autorRepository.deleteById(id);
    }
}


