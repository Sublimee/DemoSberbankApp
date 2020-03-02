package ru.sberbank.demo.app.service.report;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import javax.print.PrintService;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReportGenerator {
    private String JRXML_PATH = "C:\\Projects\\DemoSberbankApp\\src\\main\\resources\\Blank_A4.jrxml";
    private String REPORT_pdf = "C:\\Projects\\DemoSberbankApp\\src\\main\\resources\\report\\template\\Blank_A4.pdf";

    private File reportPattern;
    private Map<String, Object> parameters;

    private JasperDesign jasperDesign;
    private JasperReport jasperReport;
    private JasperPrint jasperPrint;

    public void generateReport() throws JRException {
//        InputStream is = PrintService.class.getClassLoader().getResourceAsStream("employeeList.jrxml");
//        JasperReport jasperReport = JasperCompileManager.compileReport(is);
                jasperDesign = JRXmlLoader.load(new File(JRXML_PATH));
        jasperReport = JasperCompileManager.compileReport(jasperDesign);
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("DATE", new Date());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
                new JREmptyDataSource());
//        File outDir = new File("D:/myJasperReports");
//        outDir.mkdirs();
        JasperExportManager.exportReportToPdfFile(jasperPrint, REPORT_pdf);
        System.out.println("PDF report done!");


//
//        parameters = new HashMap<String, Object>();
//
//        jasperDesign = JRXmlLoader.load(new File(JRXML_PATH));
//        jasperReport = JasperCompileManager.compileReport(jasperDesign);
//        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
//        JasperExportManager.exportReportToPdfFile(jasperPrint, REPORT_pdf);
    }

}
