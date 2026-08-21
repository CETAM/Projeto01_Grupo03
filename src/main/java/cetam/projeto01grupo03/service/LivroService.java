package cetam.projeto01grupo03.service;

import cetam.projeto01grupo03.model.Livro;
import cetam.projeto01grupo03.model.StatusEmprestimo;
import cetam.projeto01grupo03.repository.EmprestimoRepository;
import cetam.projeto01grupo03.repository.LivroRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final EmprestimoRepository emprestimoRepository;

    public LivroService(LivroRepository livroRepository, EmprestimoRepository emprestimoRepository) {
        this.livroRepository = livroRepository;
        this.emprestimoRepository = emprestimoRepository;
    }

    public List<Livro> listarTodos(String titulo) {
        if (titulo != null && !titulo.isBlank()) {
            return livroRepository.findByTituloContainingIgnoreCase(titulo.trim());
        }
        return livroRepository.findAll();
    }

    public List<Livro> listarDisponiveis() {
        return livroRepository.findByDisponivelTrue();
    }

    public Livro buscarPorId(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado com o ID: " + id));
    }

    @Transactional
    public Livro salvar(Livro livro) {
        if (livro.getQuantidadeExemplares() == null || livro.getQuantidadeExemplares() < 0) {
            livro.setQuantidadeExemplares(1);
        }
        if (livro.getQuantidadeExemplares() > 0 && livro.getDisponivel() == null) {
            livro.setDisponivel(true);
        }
        return livroRepository.save(livro);
    }

    @Transactional
    public Livro alternarDisponibilidade(Long id) {
        Livro livro = buscarPorId(id);
        livro.setDisponivel(livro.getDisponivel() == null || !livro.getDisponivel());
        return livroRepository.save(livro);
    }

    @Transactional
    public void excluir(Long id) {
        Livro livro = buscarPorId(id);

        boolean possuiEmprestimoEmAberto = emprestimoRepository.findByLivroId(id).stream()
                .anyMatch(e -> e.getStatus() == StatusEmprestimo.ATIVO || e.getStatus() == StatusEmprestimo.ATRASADO);

        if (possuiEmprestimoEmAberto) {
            throw new IllegalStateException("Não é possível excluir o livro '" + livro.getTitulo() + "' pois existem exemplares emprestados ou em atraso no momento.");
        }

        boolean possuiHistorico = !emprestimoRepository.findByLivroId(id).isEmpty();
        if (possuiHistorico) {
            throw new IllegalStateException("Não é possível excluir o livro '" + livro.getTitulo() + "' porque ele possui histórico de empréstimos registrado no sistema.");
        }

        livroRepository.delete(livro);
    }
}

