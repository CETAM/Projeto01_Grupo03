package cetam.projeto01grupo03.controller;

import cetam.projeto01grupo03.model.ConfigSistema;
import cetam.projeto01grupo03.service.ConfigSistemaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/configuracoes")
public class ConfiguracaoController {

    private final ConfigSistemaService configSistemaService;

    public ConfiguracaoController(ConfigSistemaService configSistemaService) {
        this.configSistemaService = configSistemaService;
    }

    @GetMapping
    public String index(Model model) {
        ConfigSistema config = configSistemaService.obterConfiguracoes();
        model.addAttribute("config", config);
        return "configuracoes/index";
    }

    @PostMapping
    public String salvar(@ModelAttribute("config") ConfigSistema config, RedirectAttributes redirectAttributes) {
        try {
            configSistemaService.salvarConfiguracoes(config);
            redirectAttributes.addFlashAttribute("sucesso", "Configurações do sistema atualizadas com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar configurações: " + e.getMessage());
        }
        return "redirect:/configuracoes";
    }

    @PostMapping("/restaurar")
    public String restaurarPadroes(RedirectAttributes redirectAttributes) {
        try {
            configSistemaService.restaurarPadroes();
            redirectAttributes.addFlashAttribute("sucesso", "Configurações restauradas para os padrões de fábrica com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao restaurar configurações: " + e.getMessage());
        }
        return "redirect:/configuracoes";
    }
}
