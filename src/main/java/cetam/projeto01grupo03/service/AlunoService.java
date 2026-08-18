package cetam.projeto01grupo03.service;

import cetam.projeto01grupo03.model.Aluno;
import cetam.projeto01grupo03.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {this.alunoRepository = alunoRepository; }

    public List<Aluno> listarTodos(String nome) {
        if (nome != null && !nome.isBlank()) {
            return alunoRepository.findByNomeContainingIgnoreCase(nome.trim());
        }
        return alunoRepository.findAll();
    }

    public Aluno buscarPorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado com o ID: " +id));
    }

    public Aluno salvar(Aluno aluno) { return alunoRepository.save(aluno); }

    public void excluir(Long id) { alunoRepository.deleteById(id); }
}
