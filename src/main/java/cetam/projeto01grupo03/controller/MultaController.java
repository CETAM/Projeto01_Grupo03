package cetam.projeto01grupo03.controller;

import cetam.projeto01grupo03.service.MultaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/multas")
public class MultaController {

    private final MultaService multaService;;

    public MultaController(MultaService multaService) {
        this.multaService = multaService;
    }

    @GetMapping
    public String listar(@RequestParam(name = "termo", required = false) String termo,
                         @RequestParam(name = "alunoId", required = false) Long alunoId,
                         Model model) {
        if (alunoId != null) {
            model.addAttribute("multas", multaService.listarPorAluno(alunoId));
        } else {
            model.addAttribute("multas", multaService.listarTodas(termo));
        }
        model.addAttribute("termo", termo);
        model.addAttribute("totalPendente", multaService.calcularTotalPendente());
        model.addAttribute("totalAlunosEmAtraso", multaService.contarAlunosEmAtraso());
        return "multas/listar";
    }

    @GetMapping("/pagar/{id}")
    public String pagar(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            multaService.pagarMulta(id);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Pagamento de multa registrado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao registrar pagamento: " + e.getMessage());
        }
        return "redirect:/multas";
    }

}
