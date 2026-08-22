package cetam.projeto01grupo03.controller;

import cetam.projeto01grupo03.dto.LivroMaisEmprestadoDTO;
import cetam.projeto01grupo03.dto.RelatorioGeralDTO;
import cetam.projeto01grupo03.dto.ResumoFinanceiroDTO;
import cetam.projeto01grupo03.model.Emprestimo;
import cetam.projeto01grupo03.model.Multa;
import cetam.projeto01grupo03.model.StatusEmprestimo;
import cetam.projeto01grupo03.model.StatusMulta;
import cetam.projeto01grupo03.service.RelatorioService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import  org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {
    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping
    public String index(Model model) {
        RelatorioGeralDTO resumo = relatorioService.obterResumoGeral();
        ResumoFinanceiroDTO financeiro = relatorioService.obterResumoFinanceiro(null, null);
        List<LivroMaisEmprestadoDTO> topLivros = relatorioService.obterLivrosMaisEmprestados(5);

        model.addAttribute("resumo", resumo);
        model.addAttribute("financeiro", financeiro);
        model.addAttribute("topLivros", topLivros);
        return "relatorios/index";
    }

    @GetMapping("/emprestimos")
    public String relatorioEmprestimos(
            @RequestParam(name = "dataInicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(name = "dataFim", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(name = "status", required = false) StatusEmprestimo status,
            Model model) {

        List<Emprestimo> emprestimos = relatorioService.filtrarEmprestimos(dataInicio, dataFim, status);

        model.addAttribute("emprestimos", emprestimos);
        model.addAttribute("dataInicio", dataInicio);
        model.addAttribute("dataFim", dataFim);
        model.addAttribute("statusSelecionado", status);
        model.addAttribute("statusList", StatusEmprestimo.values());
        model.addAttribute("totalRegistros", emprestimos.size());
        return "relatorios/emprestimos";
    }

    @GetMapping("/atrasados")
    public String relatorioAtrasados(Model model) {
        List<Emprestimo> atrasados = relatorioService.obterEmprestimosAtrasados();
        model.addAttribute("atrasados", atrasados);
        model.addAttribute("totalAtrasados", atrasados.size());
        return "relatorios/atrasados";
    }
    @GetMapping("/livros-populares")
    public String relatorioLivrosPopulares(
            @RequestParam(name = "limite", defaultValue = "10") int limite,
            Model model) {

        List<LivroMaisEmprestadoDTO> livrosPopulares = relatorioService.obterLivrosMaisEmprestados(limite);
        model.addAttribute("livrosPopulares", livrosPopulares);
        model.addAttribute("limite", limite);
        return "relatorios/livros-populares";
    }
     @GetMapping("/financeiro")
    public String relatorioFinanceiro(
            @RequestParam(name = "dataInicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(name = "dataFim", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(name = "status", required = false) StatusMulta status,
            Model model) {

        ResumoFinanceiroDTO resumoFinanceiro =  relatorioService.obterResumoFinanceiro(dataInicio, dataFim);
        List<Multa> multas = relatorioService.filtrarMultas(dataInicio, dataFim, status);

         model.addAttribute("financeiro", resumoFinanceiro);
         model.addAttribute("multas", multas);
         model.addAttribute("dataInicio", dataInicio);
         model.addAttribute("dataFim", dataFim);
         model.addAttribute("statusSelecionado", status);
         model.addAttribute("statusList", StatusMulta.values());
         return "relatorios/financeiro";
     }

    @GetMapping("/api/resumo")
    @ResponseBody
    public RelatorioGeralDTO apiResumo() {
        return relatorioService.obterResumoGeral();
    }

    @GetMapping("/api/livros-populares")
    @ResponseBody
    public List<LivroMaisEmprestadoDTO> apiLivrosPopulares(@RequestParam(name = "limite", defaultValue = "10") int limite) {
        return relatorioService.obterLivrosMaisEmprestados(limite);
    }

    @GetMapping("/api/financeiro")
    @ResponseBody
    public ResumoFinanceiroDTO apiFinanceiro(
            @RequestParam(name = "dataInicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(name = "dataFim", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return relatorioService.obterResumoFinanceiro(dataInicio, dataFim);
    }
}
