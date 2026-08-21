package cetam.projeto01grupo03.controller;

import cetam.projeto01grupo03.model.Categoria;
import cetam.projeto01grupo03.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaRestController {

    private final CategoriaService categoriaService;

    public CategoriaRestController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> listarTodas() {
        return ResponseEntity.ok(categoriaService.listarTodas(null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Categoria categoria = categoriaService.buscarPorId(id);
            return ResponseEntity.ok(categoria);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensagem", "Categoria não encontrada."));
        }
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Categoria categoria) {
        if (categoria.getNome() == null || categoria.getNome().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("mensagem", "O nome da categoria é obrigatório."));
        }
        try {
            Categoria salva = categoriaService.salvar(categoria);
            return ResponseEntity.status(HttpStatus.CREATED).body(salva);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensagem", "Erro ao salvar categoria: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Categoria categoriaAtualizada) {
        if (categoriaAtualizada.getNome() == null || categoriaAtualizada.getNome().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("mensagem", "O nome da categoria é obrigatório."));
        }
        try {
            Categoria categoria = categoriaService.buscarPorId(id);
            categoria.setNome(categoriaAtualizada.getNome());
            categoria.setDescricao(categoriaAtualizada.getDescricao());
            Categoria salva = categoriaService.salvar(categoria);
            return ResponseEntity.ok(salva);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("mensagem", "Erro ao atualizar categoria: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        try {
            categoriaService.excluir(id);
            return ResponseEntity.ok(Map.of("mensagem", "Categoria excluída com sucesso!"));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("mensagem", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("mensagem", "Erro ao excluir categoria: " + e.getMessage()));
        }
    }
}

