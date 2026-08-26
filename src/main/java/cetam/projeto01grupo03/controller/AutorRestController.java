package cetam.projeto01grupo03.controller;

import cetam.projeto01grupo03.model.Autor;
import cetam.projeto01grupo03.service.AutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/autores")
public class AutorRestController {

    private final AutorService autorService;

    public AutorRestController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public ResponseEntity<List<Autor>> listarTodos() {
        return ResponseEntity.ok(autorService.listarTodos(null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Autor autor = autorService.buscarPorId(id);
            return ResponseEntity.ok(autor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensagem", "Autor não encontrado."));
        }
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Autor autor) {
        if (autor.getNome() == null || autor.getNome().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("mensagem", "O nome do autor é obrigatório."));
        }
        try {
            Autor salvo = autorService.salvar(autor);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensagem", "Erro ao salvar autor: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Autor autorAtualizado) {
        if (autorAtualizado.getNome() == null || autorAtualizado.getNome().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("mensagem", "O nome do autor é obrigatório."));
        }
        try {
            Autor autor = autorService.buscarPorId(id);
            autor.setNome(autorAtualizado.getNome());
            Autor salvo = autorService.salvar(autor);
            return ResponseEntity.ok(salvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("mensagem", "Erro ao atualizar autor: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        try {
            autorService.excluir(id);
            return ResponseEntity.ok(Map.of("mensagem", "Autor excluído com sucesso!"));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("mensagem", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("mensagem", "Erro ao excluir autor: " + e.getMessage()));
        }
    }
}
