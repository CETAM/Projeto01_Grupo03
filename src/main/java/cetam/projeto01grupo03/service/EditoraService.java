package cetam.projeto01grupo03.service;

import cetam.projeto01grupo03.model.Editora;
import cetam.projeto01grupo03.repository.EditoraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditoraService {

    private final EditoraRepository editoraRepository;

    public EditoraService(EditoraRepository editoraRepository) {
        this.editoraRepository = editoraRepository;
    }


    public List<Editora> listarTodas(String nome){
        if (nome != null && !nome.isBlank()){
            return editoraRepository.findByNomeContainingIgnoreCase(nome.trim());
        }
        return editoraRepository.findAll();
    }

    public Editora buscarPorId(Long id){
        return editoraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Editora não encontrada com o ID: "+ id));
    }

    public Editora salvar(Editora editora){
        return editoraRepository.save(editora);
    }

    public void excluir(Long id){
        editoraRepository.deleteById(id);
    }
}
