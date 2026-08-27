package cetam.projeto01grupo03.controller;

import cetam.projeto01grupo03.model.Autor;
import cetam.projeto01grupo03.service.AutorService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    // Lista autores e permite filtro por nome
    @GetMapping
    public String listar(
            @RequestParam(name = "nome", required = false) String nome,
            Model model) {

        model.addAttribute("autores", autorService.listarTodos(nome));
        model.addAttribute("nome", nome);

        return "autores/listar";
    }

    // Exibe o formulário para cadastrar um novo autor
    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("autor", new Autor());
        return "autores/formulario";
    }

    // Exibe o formulário para editar um autor
    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(
            @PathVariable("id") Long id,
            Model model,
            RedirectAttributes redirectAttributes) {

        try {
            Autor autor = autorService.buscarPorId(id);
            model.addAttribute("autor", autor);

            return "autores/formulario";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "mensagemErro",
                    "Autor não encontrado."
            );

            return "redirect:/autores";
        }
    }

    // Salva ou atualiza um autor
    @PostMapping("/salvar")
    public String salvar(
            @ModelAttribute("autor") Autor autor,
            Model model,
            RedirectAttributes redirectAttributes) {

        try {
            autorService.salvar(autor);
            redirectAttributes.addFlashAttribute(
                    "mensagemSucesso",
                    "Autor salvo com sucesso!"
            );
            return "redirect:/autores";
        } catch (IllegalArgumentException e) {
            model.addAttribute("mensagemErro", e.getMessage());
            model.addAttribute("autor", autor);
            return "autores/formulario";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("mensagemErro", "Erro de integridade: Já existe um autor cadastrado com este nome.");
            model.addAttribute("autor", autor);
            return "autores/formulario";
        } catch (Exception e) {
            model.addAttribute("mensagemErro", "Erro ao salvar autor: " + e.getMessage());
            model.addAttribute("autor", autor);
            return "autores/formulario";
        }
    }

    // Exclui um autor
    @GetMapping("/excluir/{id}")
    public String excluir(
            @PathVariable("id") Long id,
            RedirectAttributes redirectAttributes) {

        try {
            autorService.excluir(id);

            redirectAttributes.addFlashAttribute(
                    "mensagemSucesso",
                    "Autor excluído com sucesso!"
            );

        } catch (DataIntegrityViolationException e) {

            redirectAttributes.addFlashAttribute(
                    "mensagemErro",
                    "Não é possível excluir o autor pois existem livros vinculados a ele."
            );

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "mensagemErro",
                    "Erro ao excluir o autor: " + e.getMessage()
            );
        }

        return "redirect:/autores";
    }
}