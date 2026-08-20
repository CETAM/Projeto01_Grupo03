package cetam.projeto01grupo03.controller;

import cetam.projeto01grupo03.model.Livro;
import cetam.projeto01grupo03.service.LivroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/livros")
public class LivroRestController {

    private final LivroService livroService;

    public LivroRestController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<Map<String, Object>>> listarDisponiveis(@RequestParam(name = "termo", required = false) String termo) {
        List<Livro> todosDisponiveis = livroService.listarDisponiveis();

        String termoBusca = (termo != null) ? termo.trim().toLowerCase() : "";

        List<Map<String, Object>> resultados = todosDisponiveis.stream()
                .filter(l -> {
                    if (l.getQuantidadeExemplares() == null || l.getQuantidadeExemplares() <= 0) {
                        return false;
                    }
                    if (termoBusca.isEmpty()) {
                        return true;
                    }
                    boolean matchTitulo = l.getTitulo() != null && l.getTitulo().toLowerCase().contains(termoBusca);
                    boolean matchIsbn = l.getIsbn() != null && l.getIsbn().toLowerCase().contains(termoBusca);
                    boolean matchAutor = l.getAutor() != null && l.getAutor().getNome() != null && l.getAutor().getNome().toLowerCase().contains(termoBusca);
                    boolean matchCategoria = l.getCategoria() != null && l.getCategoria().getNome() != null && l.getCategoria().getNome().toLowerCase().contains(termoBusca);
                    return matchTitulo || matchIsbn || matchAutor || matchCategoria;
                })
                .map(l -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", l.getId());
                    map.put("titulo", l.getTitulo());
                    map.put("isbn", l.getIsbn());
                    map.put("ano", l.getAno());
                    map.put("quantidadeExemplares", l.getQuantidadeExemplares());
                    map.put("autorNome", l.getAutor() != null ? l.getAutor().getNome() : "Autor não informado");
                    map.put("editoraNome", l.getEditora() != null ? l.getEditora().getNome() : "Editora não informada");
                    map.put("categoriaNome", l.getCategoria() != null ? l.getCategoria().getNome() : "Geral");
                    return map;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultados);
    }
}
