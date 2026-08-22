package cetam.projeto01grupo03.controller;

import cetam.projeto01grupo03.dto.DashboardDTO;
import cetam.projeto01grupo03.service.RelatorioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    private final RelatorioService relatorioService;

    public HomeController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/")
    public String index(Model model) {
        DashboardDTO dashboard = relatorioService.obterDadosDashboard();
        model.addAttribute("dashboard", dashboard);
        return "index";
    }

    @GetMapping("/painel")
    public String painel(Model model) {
        return index(model);
    }

    @GetMapping("/api/dashboard")
    @ResponseBody
    public DashboardDTO apiDashboard() {
        return relatorioService.obterDadosDashboard();
    }
}
