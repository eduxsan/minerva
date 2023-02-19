package com.moricion.qa.common.reporting;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class ReportEngine {

    public static final String REPORT_DIR = "target/test-report";
    private final String RESOURCES_DIR = "src/main/resources";
    private final String LOGO_IMG = "la-moricion.png";

    private String template;
    private String html;

    public ReportEngine(String template, String html) {
        this.template = template;
        this.html = html;
    }

    /**
     * Generates a test report using freemarker.
     * @param testReport
     * @throws Exception
     */
    public void generateReport(TestReport testReport)  {

        // sets, pass, fail, total, rates
        /* ------------------------------------------------------------------------ */
        /* You should do this ONLY ONCE in the whole application life-cycle:        */

        /* Create and adjust the configuration singleton */
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
        try {
            cfg.setDirectoryForTemplateLoading(new File("src/main/resources"));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to setup freemarker config", e);
        }

        /* ------------------------------------------------------------------------ */
        /* You usually do these for MULTIPLE TIMES in the application life-cycle:   */

        /* Create a data-model */
        Map root = new HashMap();

        root.put("testreport", testReport);

        /* Get the template (uses cache internally) */
        Template temp = null;
        try {
            FileUtils.copyFile(
                    new File(RESOURCES_DIR, LOGO_IMG),
                    new File(REPORT_DIR, LOGO_IMG));

            temp = cfg.getTemplate(template);
            Writer file = new FileWriter(new File(REPORT_DIR, html));
            temp.process(root, file);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to generate report", e);
        }
    }
}
