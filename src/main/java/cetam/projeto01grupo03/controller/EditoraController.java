package cetam.projeto01grupo03.controller;

import cetam.projeto01grupo03.model.Editora;
import cetam.projeto01grupo03.service.EditoraService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/editoras")
public class EditoraController {

    private final EditoraService editoraService;

    public EditoraController(EditoraService editoraService) {
        this.editoraService = editoraService;
    }

    @GetMapping
    public String listar(@RequestParam(name = "nomes", required = false) String nome, Model model){
        model.addAttribute("editoras", editoraService.listarTodas(nome));
        model.addAttribute("nome", nome);
        return "editoras/listar";
    }

    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model model){
        model.addAttribute("editora", new Editora());
        return "editoras/formulario";
    }

    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes){
        try {
            Editora editora = editoraService.buscarPorId(id);
            model.addAttribute("editora", editora);
            return "editoras/formulario";
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("mensagemErro", "Editora não encontrada.");
            return "redirect:/editoras";
        }
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("editora") Editora editora,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (editora.getNome() == null || editora.getNome().trim().isEmpty()){
            model.addAttribute("mensagemErro", "O nome da editora é obrigatório.");
            return "editoras/formulario";
        }

        editoraService.salvar(editora);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Editora salva com sucesso!");
        return "redirect: /editoras";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        try {
            editoraService.excluir(id);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Editoda excluída com sucesso!");
        } catch (DataIntegrityViolationException e){
            redirectAttributes.addFlashAttribute("mensagemErro", "Não é possível excluir a editora pois existem livros vinculados a ela.");
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao excluir editora: " + e.getMessage());
        }
        return "redirect:/editoras";
    }
}
