package ru.sberbank.demo.app.service.report;

import net.sf.jasperreports.engine.JRException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReportGeneratorTest {

    @Test
    void generateReport() throws JRException {
        ReportGenerator reportGenerator = new ReportGenerator();
        reportGenerator.generateReport();
    }

}