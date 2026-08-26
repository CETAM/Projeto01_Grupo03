package cetam.projeto01grupo03.controller;

import cetam.projeto01grupo03.model.Editora;
import cetam.projeto01grupo03.service.EditoraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/editoras")
public class EditoraRestController {

    private final EditoraService editoraService;

    public EditoraRestController(EditoraService editoraService) {
        this.editoraService = editoraService;
    }

    @GetMapping
    public ResponseEntity<List<Editora>> listarTodas() {
        return ResponseEntity.ok(editoraService.listarTodas(null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Editora editora = editoraService.buscarPorId(id);
            return ResponseEntity.ok(editora);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensagem", "Editora não encontrada."));
        }
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Editora editora) {
        if (editora.getNome() == null || editora.getNome().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("mensagem", "O nome da editora é obrigatório."));
        }
        try {
            Editora salva = editoraService.salvar(editora);
            return ResponseEntity.status(HttpStatus.CREATED).body(salva);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensagem", "Erro ao salvar editora: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Editora editoraAtualizada) {
        if (editoraAtualizada.getNome() == null || editoraAtualizada.getNome().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("mensagem", "O nome da editora é obrigatório."));
        }
        try {
            Editora editora = editoraService.buscarPorId(id);
            editora.setNome(editoraAtualizada.getNome());
            Editora salva = editoraService.salvar(editora);
            return ResponseEntity.ok(salva);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("mensagem", "Erro ao atualizar editora: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        try {
            editoraService.excluir(id);
            return ResponseEntity.ok(Map.of("mensagem", "Editora excluída com sucesso!"));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("mensagem", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("mensagem", "Erro ao excluir editora: " + e.getMessage()));
        }
    }
}
