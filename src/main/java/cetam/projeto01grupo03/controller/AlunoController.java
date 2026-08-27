package cetam.projeto01grupo03.controller;

import cetam.projeto01grupo03.model.Aluno;
import cetam.projeto01grupo03.model.Autor;
import cetam.projeto01grupo03.service.AlunoService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;


    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping
    public String listar(@RequestParam(name = "nome", required = false) String nome, Model model) {
        model.addAttribute("alunos", alunoService.listarTodos(nome));
        model.addAttribute("nome", nome);
        return "alunos/listar";
    }


    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("aluno", new Aluno());
        return "alunos/formulario";
    }


    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Aluno aluno = alunoService.buscarPorId(id);
            model.addAttribute("aluno", aluno);
            return "alunos/formulario";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Aluno não encontrado.");
            return "redirect:/alunos";
        }
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("aluno") Aluno aluno, Model model, RedirectAttributes redirectAttributes) {
        try {
            alunoService.salvar(aluno);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Aluno salvo com sucesso!");
            return "redirect:/alunos";
        } catch (IllegalArgumentException e) {
            model.addAttribute("mensagemErro", e.getMessage());
            model.addAttribute("aluno", aluno);
            return "alunos/formulario";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("mensagemErro", "Erro de duplicidade: Já existe um aluno com esta matrícula ou e-mail.");
            model.addAttribute("aluno", aluno);
            return "alunos/formulario";
        } catch (Exception e) {
            model.addAttribute("mensagemErro", "Erro ao salvar aluno: " + e.getMessage());
            model.addAttribute("aluno", aluno);
            return "alunos/formulario";
        }
    }

    @GetMapping("/status/{id}")
    public String alternarStatus(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            Aluno aluno = alunoService.alternarStatus(id);
            String status = Boolean.TRUE.equals(aluno.getAtivo()) ? "ativado" : "inativado";
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Aluno " + status + " com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao alterar status do aluno: " + e.getMessage());
        }
        return "redirect:/alunos";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            alunoService.excluir(id);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Aluno excluído com sucesso!");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Não é possível excluir o aluno pois existem registros vinculados a ele.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao excluir o aluno: " + e.getMessage());
        }
        return "redirect:/alunos";
    }
}
