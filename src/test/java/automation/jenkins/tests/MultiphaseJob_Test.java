package automation.jenkins.tests;

import automation.jenkins.keyword.BuildMulitphaseJobEmail;
import automation.jenkins.report.ResultsIT;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.mail.MessagingException;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;

public class MultiphaseJob_Test extends BuildMulitphaseJobEmail {

    @Test
    public void createEmailableReportForMultiphaseJob() {
        buildMulitphaseJobList();
//        addAnalyticsCharts();
        ResultsIT results = new ResultsIT();
        emailBody = prettyPrintHTML(emailBody);
        System.out.println(emailBody);
        try {
            results.sendResultsMail(emailBody, multiphaseJobName + "  Results");
        } catch (MessagingException | IOException e) {
        }
    }

//     @Test
    public void createAnalyticsReport() {
        addAnalyticsCharts();
    }

    public String prettyPrintHTML(String rawHTML) {
        Tidy tidy = new Tidy();
        tidy.setXHTML(true);
        tidy.setIndentContent(true);
        tidy.setPrintBodyOnly(true);
        tidy.setTidyMark(false);
        tidy.setShowWarnings(false);

        // HTML to DOM
        Document htmlDOM = tidy.parseDOM(new ByteArrayInputStream(rawHTML.getBytes()), null);

        // Pretty Print
        OutputStream out = new ByteArrayOutputStream();
        tidy.pprint(htmlDOM, out);

        return out.toString();
    }
}
