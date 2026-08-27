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

    @org.springframework.transaction.annotation.Transactional
    public Autor salvar(Autor autor) {
        if (autor.getNome() == null || autor.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do autor é obrigatório.");
        }
        autor.setNome(autor.getNome().trim());

        if (autor.getId() == null) {
            if (autorRepository.existsByNomeIgnoreCase(autor.getNome())) {
                throw new IllegalArgumentException("Já existe um autor cadastrado com o nome '" + autor.getNome() + "'.");
            }
        } else {
            if (autorRepository.existsByNomeIgnoreCaseAndIdNot(autor.getNome(), autor.getId())) {
                throw new IllegalArgumentException("Já existe outro autor cadastrado com o nome '" + autor.getNome() + "'.");
            }
        }

        return autorRepository.save(autor);
    }

    public void excluir(Long id) {
        autorRepository.deleteById(id);
    }
}


