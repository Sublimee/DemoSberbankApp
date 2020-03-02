package ru.sberbank.demo.app;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.InputStream;
import java.util.*;

public class PrintService {
    List<Person> persons = Arrays.asList(new Person("Иван", "Иванов"), new Person("Пётр", "Петров"),
            new Person("Сергей", "Сергеев"));
    private String REPORT_pdf = "C:\\Projects\\DemoSberbankApp\\src\\main\\resources\\report\\template\\Blank_A4.pdf";

    public void createPdfReport() {
        try {
//            InputStream is = PrintService.class.getClassLoader().getResourceAsStream("employeeList.jrxml");
//            JasperReport jasperReport = JasperCompileManager.compileReport(is);

            JasperCompileManager.compileReportToFile(
                    "C:\\Projects\\DemoSberbankApp\\src\\main\\resources\\employeeList.jrxml", //Relative or absoulte path to the .jrxml file to compile
                    "C:\\Projects\\DemoSberbankApp\\src\\main\\resources\\employeeList.jasper"); //Relative or absolute path to the compiled file .jasper
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("DATE", new Date());
//            JasperReport jasperReport = new JasperReport()
            JasperPrint jasperPrint = JasperFillManager.fillReport("C:\\Projects\\DemoSberbankApp\\src\\main\\resources\\employeeList.jasper", parameters,
                    new JRBeanCollectionDataSource(persons));
//			File outDir = new File("D:/myJasperReports");
//			outDir.mkdirs();
            JasperExportManager.exportReportToPdfFile(jasperPrint, REPORT_pdf);

            System.out.println("PDF report done!");
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }

}
