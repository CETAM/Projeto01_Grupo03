package cetam.projeto01grupo03.controller;

import cetam.projeto01grupo03.model.Livro;
import cetam.projeto01grupo03.service.AutorService;
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

    public LivroController(LivroService livroService, AutorService autorService) {
        this.livroService = livroService;
        this.autorService = autorService;
    }

    @GetMapping
    public String listar(@RequestParam(name = "nome", required = false) String nome, Model model){
        model.addAttribute("livro", livroService.listarTodos(nome));
        model.addAttribute("nome", nome);
        return "livro/listar";
    }

    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model model){
        model.addAttribute("ivro", new Livro());
        return "livros/formulario";
    }

    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes){
        try{
            Livro livro = livroService.buscarPorId(id);
            model.addAttribute("livro, livro");
            return "livros/formulario";
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Livro não encontrado.");
            return "redirect:/livros";
        }
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("livro") Livro livro,
                         Model model,
                         RedirectAttributes redirectAttributes){
        if (livro.getTitulo() == null || livro.getTitulo().trim().isEmpty()){
            model.addAttribute("mensagemErro", "O titulo do livro é obrigatório");
            return "livros/formulario";
        }

        livroService.salvar(livro);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Livro salvo com sucesso!");
        return "redirect:/livros";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        try {
            autorService.excluir(id);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Livro excluído com sucesso!");
        }catch (DataIntegrityViolationException e){
            redirectAttributes.addFlashAttribute("mensagemErro", "Não é possível excluir o livro");
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao excluir o livro" + e.getMessage());
        }
        return "redirect:/autores";
    }
}
