package cetam.projeto01grupo03.controller;

import cetam.projeto01grupo03.model.StatusEmprestimo;
import cetam.projeto01grupo03.service.AlunoService;
import cetam.projeto01grupo03.service.EmprestimoService;
import cetam.projeto01grupo03.service.LivroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;
    private final AlunoService alunoService;
    private final LivroService livroService;

    public EmprestimoController(
            EmprestimoService emprestimoService,
            AlunoService alunoService,
            LivroService livroService) {

        this.emprestimoService = emprestimoService;
        this.alunoService = alunoService;
        this.livroService = livroService;
    }

    @GetMapping
    public String listar(
            @RequestParam(name = "status", required = false) StatusEmprestimo status,
            @RequestParam(name = "nomeAluno", required = false) String nomeAluno,
            Model model) {

        if (nomeAluno != null && !nomeAluno.isBlank()) {
            model.addAttribute(
                    "emprestimos",
                    emprestimoService.buscarPorNomeAluno(nomeAluno)
            );
        } else {
            model.addAttribute(
                    "emprestimos",
                    emprestimoService.listarTodos(status)
            );
        }

        model.addAttribute("statusSelecionado", status);
        model.addAttribute("nomeAluno", nomeAluno);
        model.addAttribute("statusList", StatusEmprestimo.values());
        model.addAttribute("alunos", alunoService.listarAtivos());
        model.addAttribute("livros", livroService.listarDisponiveis());
        model.addAttribute("totalAtivos", emprestimoService.contarAtivos());
        model.addAttribute(
                "totalDevolucoesHoje",
                emprestimoService.contarDevolucoesHoje()
        );
        model.addAttribute(
                "totalAtrasados",
                emprestimoService.contarAtrasados()
        );

        return "emprestimos/listar";
    }

    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model model) {

        model.addAttribute("alunos", alunoService.listarAtivos());
        model.addAttribute("livros", livroService.listarDisponiveis());

        return "emprestimos/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(
            @RequestParam("alunoId") Long alunoId,
            @RequestParam("livroId") Long livroId,
            RedirectAttributes redirectAttributes) {

        try {
            emprestimoService.realizarEmprestimo(alunoId, livroId);

            redirectAttributes.addFlashAttribute(
                    "mensagemSucesso",
                    "Empréstimo realizado com sucesso!"
            );

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "mensagemErro",
                    "Erro ao realizar empréstimo: " + e.getMessage()
            );
        }

        return "redirect:/emprestimos";
    }

    @GetMapping("/renovar/{id}")
    public String renovar(
            @PathVariable("id") Long id,
            RedirectAttributes redirectAttributes) {

        try {
            emprestimoService.renovarEmprestimo(id);

            redirectAttributes.addFlashAttribute(
                    "mensagemSucesso",
                    "Empréstimo renovado por mais 14 dias com sucesso!"
            );

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "mensagemErro",
                    "Erro ao renovar empréstimo: " + e.getMessage()
            );
        }

        return "redirect:/emprestimos";
    }

    @GetMapping("/devolver/{id}")
    public String devolver(
            @PathVariable("id") Long id,
            RedirectAttributes redirectAttributes) {

        try {
            emprestimoService.devolverLivro(id);

            redirectAttributes.addFlashAttribute(
                    "mensagemSucesso",
                    "Livro devolvido com sucesso!"
            );

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "mensagemErro",
                    "Erro ao devolver livro: " + e.getMessage()
            );
        }

        return "redirect:/emprestimos";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(
            @PathVariable("id") Long id,
            RedirectAttributes redirectAttributes) {

        try {
            emprestimoService.excluir(id);

            redirectAttributes.addFlashAttribute(
                    "mensagemSucesso",
                    "Empréstimo excluído com sucesso!"
            );

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "mensagemErro",
                    "Erro ao excluir empréstimo: " + e.getMessage()
            );
        }

        return "redirect:/emprestimos";
    }
}