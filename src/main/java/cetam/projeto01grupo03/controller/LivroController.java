package cetam.projeto01grupo03.controller;

import cetam.projeto01grupo03.model.Livro;
import cetam.projeto01grupo03.service.AutorService;
import cetam.projeto01grupo03.service.CategoriaService;
import cetam.projeto01grupo03.service.EditoraService;
import cetam.projeto01grupo03.service.LivroService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;
    private final AutorService autorService;
    private final EditoraService editoraService;
    private final CategoriaService categoriaService;

    public LivroController(LivroService livroService, AutorService autorService, EditoraService editoraService, CategoriaService categoriaService) {
        this.livroService = livroService;
        this.autorService = autorService;
        this.editoraService = editoraService;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(@RequestParam(name = "titulo", required = false) String titulo, Model model) {
        model.addAttribute("livros", livroService.listarTodos(titulo));
        model.addAttribute("titulo", titulo);
        model.addAttribute("autores", autorService.listarTodos(null));
        model.addAttribute("editoras", editoraService.listarTodas(null));
        model.addAttribute("categorias", categoriaService.listarTodas(null));
        return "livros/listar";
    }

    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("livro", new Livro());
        model.addAttribute("autores", autorService.listarTodos(null));
        model.addAttribute("editoras", editoraService.listarTodas(null));
        model.addAttribute("categorias", categoriaService.listarTodas(null));
        return "livros/formulario";
    }

    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Livro livro = livroService.buscarPorId(id);
            model.addAttribute("livro", livro);
            model.addAttribute("autores", autorService.listarTodos(null));
            model.addAttribute("editoras", editoraService.listarTodas(null));
            model.addAttribute("categorias", categoriaService.listarTodas(null));
            return "livros/formulario";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Livro não encontrado.");
            return "redirect:/livros";
        }
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("livro") Livro livro,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (livro.getTitulo() == null || livro.getTitulo().trim().isEmpty()) {
            model.addAttribute("mensagemErro", "O título do livro é obrigatório.");
            model.addAttribute("autores", autorService.listarTodos(null));
            model.addAttribute("editoras", editoraService.listarTodas(null));
            model.addAttribute("categorias", categoriaService.listarTodas(null));
            return "livros/formulario";
        }

        livroService.salvar(livro);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Livro salvo com sucesso!");
        return "redirect:/livros";
    }

    @GetMapping("/disponibilidade/{id}")
    public String alternarDisponibilidade(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            Livro livro = livroService.alternarDisponibilidade(id);
            String status = Boolean.TRUE.equals(livro.getDisponivel()) ? "disponível" : "indisponível";
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Livro marcado como " + status + "!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao alterar disponibilidade do livro: " + e.getMessage());
        }
        return "redirect:/livros";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            livroService.excluir(id);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Livro excluído com sucesso!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Não é possível excluir o livro pois existem empréstimos vinculados a ele.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao excluir o livro: " + e.getMessage());
        }
        return "redirect:/livros";
    }
}
