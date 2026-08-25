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
            redirectAttributes.addFlashAttribute("mensagemErro", "Aluno não encontrando. ");
            return "redirect:/alunos";
        }
    }


    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("aluno") Aluno aluno, Model model, RedirectAttributes redirectAttributes) {
        if (aluno.getNome() == null || aluno.getNome().trim().isEmpty()) {
            model.addAttribute("mensagemErro", "o nome do aluno é obrigatório. ");
            return "alunos/formulario";
        }

        if (aluno.getMatricula() == null || aluno.getMatricula().trim().isEmpty()) {
            model.addAttribute("mensagemErro", "A matrícula do aluno é obrigatória.");
            return "alunos/formulario";
        }

        alunoService.salvar(aluno);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Aluno salvo com sucesso!");
        return "redirect:/alunos";
    }

    @GetMapping("/status/{id}")
    public String alternarStatus(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            Aluno aluno = alunoService.alternarStatus(id);
            String status = Boolean.TRUE.equals(aluno.getAtivo()) ? "ativado" : "inativado";
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Aluno" + status + "com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao alterar status do aluno: " + e.getMessage());
        }
        return "redirect:/alunos";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            alunoService.excluir(id);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Aluno excluido com sucesso!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Não é possivel excluir o aluno pois tem livros vinculaods a ele.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao excluir o aluno: " + e.getMessage());
        }
        return  "redirect:/alunos";
    }
}
