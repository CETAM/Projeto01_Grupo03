package cetam.projeto01grupo03.service;

import cetam.projeto01grupo03.model.ConfigSistema;
import cetam.projeto01grupo03.repository.ConfigSistemaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class ConfigSistemaService {

    private final ConfigSistemaRepository configSistemaRepository;

    public ConfigSistemaService(ConfigSistemaRepository configSistemaRepository) {
        this.configSistemaRepository = configSistemaRepository;
    }

    @Transactional
    public ConfigSistema obterConfiguracoes() {
        return configSistemaRepository.findById(1L)
                .orElseGet(() -> {
                    ConfigSistema defaultConfig = new ConfigSistema();
                    defaultConfig.setId(1L);
                    return configSistemaRepository.save(defaultConfig);
                });
    }

    @Transactional
    public ConfigSistema salvarConfiguracoes(ConfigSistema novasConfigs) {
        ConfigSistema atual = obterConfiguracoes();

        if (novasConfigs.getDiasPrazoEmprestimo() != null && novasConfigs.getDiasPrazoEmprestimo() > 0) {
            atual.setDiasPrazoEmprestimo(novasConfigs.getDiasPrazoEmprestimo());
        }
        if (novasConfigs.getDiasPrazoRenovacao() != null && novasConfigs.getDiasPrazoRenovacao() > 0) {
            atual.setDiasPrazoRenovacao(novasConfigs.getDiasPrazoRenovacao());
        }
        if (novasConfigs.getLimiteLivrosSimultaneos() != null && novasConfigs.getLimiteLivrosSimultaneos() > 0) {
            atual.setLimiteLivrosSimultaneos(novasConfigs.getLimiteLivrosSimultaneos());
        }
        if (novasConfigs.getMaximoRenovacoesPermitidas() != null && novasConfigs.getMaximoRenovacoesPermitidas() >= 0) {
            atual.setMaximoRenovacoesPermitidas(novasConfigs.getMaximoRenovacoesPermitidas());
        }
        if (novasConfigs.getValorMultaPorDia() != null && novasConfigs.getValorMultaPorDia().compareTo(BigDecimal.ZERO) >= 0) {
            atual.setValorMultaPorDia(novasConfigs.getValorMultaPorDia());
        }
        if (novasConfigs.getDiasToleranciaAtraso() != null && novasConfigs.getDiasToleranciaAtraso() >= 0) {
            atual.setDiasToleranciaAtraso(novasConfigs.getDiasToleranciaAtraso());
        }
        if (novasConfigs.getBloquearEmprestimoComMultaPendente() != null) {
            atual.setBloquearEmprestimoComMultaPendente(novasConfigs.getBloquearEmprestimoComMultaPendente());
        }
        if (novasConfigs.getNomeInstituicao() != null && !novasConfigs.getNomeInstituicao().isBlank()) {
            atual.setNomeInstituicao(novasConfigs.getNomeInstituicao().trim());
        }
        if (novasConfigs.getTextoRodapeRelatorio() != null && !novasConfigs.getTextoRodapeRelatorio().isBlank()) {
            atual.setTextoRodapeRelatorio(novasConfigs.getTextoRodapeRelatorio().trim());
        }

        atual.setId(1L);
        return configSistemaRepository.save(atual);
    }

    @Transactional
    public ConfigSistema restaurarPadroes() {
        ConfigSistema padrao = new ConfigSistema();
        padrao.setId(1L);
        return configSistemaRepository.save(padrao);
    }
}
