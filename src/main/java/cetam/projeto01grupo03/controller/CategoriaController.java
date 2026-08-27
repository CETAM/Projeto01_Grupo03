package cetam.projeto01grupo03.controller;

import cetam.projeto01grupo03.model.Categoria;
import cetam.projeto01grupo03.service.CategoriaService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(@RequestParam(name = "nome", required = false) String nome, Model model) {
        model.addAttribute("categorias", categoriaService.listarTodas(nome));
        model.addAttribute("nome", nome);
        return "categorias/listar";
    }

    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categorias/formulario";
    }

    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Categoria categoria = categoriaService.buscarPorId(id);
            model.addAttribute("categoria", categoria);
            return "categorias/formulario";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Categoria não encontrada.");
            return "redirect:/categorias";
        }
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("categoria") Categoria categoria,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        try {
            categoriaService.salvar(categoria);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Categoria salva com sucesso!");
            return "redirect:/categorias";
        } catch (IllegalArgumentException e) {
            model.addAttribute("mensagemErro", e.getMessage());
            model.addAttribute("categoria", categoria);
            return "categorias/formulario";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("mensagemErro", "Erro de integridade: Já existe uma categoria cadastrada com este nome.");
            model.addAttribute("categoria", categoria);
            return "categorias/formulario";
        } catch (Exception e) {
            model.addAttribute("mensagemErro", "Erro ao salvar categoria: " + e.getMessage());
            model.addAttribute("categoria", categoria);
            return "categorias/formulario";
        }
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            categoriaService.excluir(id);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Categoria excluída com sucesso!");
        } catch (DataIntegrityViolationException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage() != null ? e.getMessage() : "Não é possível excluir a categoria pois existem livros vinculados a ela.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao excluir a categoria: " + e.getMessage());
        }
        return "redirect:/categorias";
    }
}
