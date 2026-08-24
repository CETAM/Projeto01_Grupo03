package cetam.projeto01grupo03.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class PdfReportService {

    private final TemplateEngine templateEngine;

    public PdfReportService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] gerarPdf(String templatePath, Map<String, Object> dados) {
        Context context = new Context();
        if (dados != null && !dados.isEmpty()) {
            dados.forEach(context::setVariable);
        }

        String htmlContent = templateEngine.process(templatePath, context);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(htmlContent, null);
            builder.toStream(outputStream);
            builder.run();
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar stream na geração do relatório PDF: " + templatePath, e);
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao gerar PDF com OpenHTMLtoPDF a partir do template: " + templatePath, e);
        }
    }
}
