package automation.jenkins.keyword;

import automation.jenkins.responses.JSONEntityResponseValues;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import automation.jenkins.utils.HTMLWriter;
import automation.jenkins.utils.ConvertTimeFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONException;

public class Build_MulitJob_Phase_Email {

    public String emailBody = null;
    public String dateTable = "";
    public String greenBuildColorCode = "#2EFE2E"; 
    public String unstableBuildColorCode = "#FFFF00";
    public String failedBuildColorCode = "#F21010";
    public String abortedBuildColorCode = "D3D3D3";
    public String analyticsChartsScript = "";
    public String multiJobName = System.getProperty("multiJobName", "MultiJob_Trust_Staging_Test");
    public String projectName = System.getProperty("projectName", "TRUST");
    
    String jenkinsURL = System.getProperty("jenkinsURL", "Url of your Jenkins");
    String multiphaseJobAPIURL = jenkinsURL + "job/" + multiJobName + "/api/json";
    String replyto = System.getProperty("replyTo", "Email id to whom you want reply to");
    String analyticsFlag = System.getProperty("buildAnalytics", "no");
   
    JSONEntityResponseValues jsonObject = new JSONEntityResponseValues();
    ConvertTimeFormat timeConvert;

    public void buildMulitphaseJobList() {
        jsonObject.initiliazeEntity(multiphaseJobAPIURL);
        try {
            System.out.println("Jenkins API Final URL :- " + multiphaseJobAPIURL);
            System.out.println("Now fetching JSON for all Jobs");
            @SuppressWarnings("unused")
			JSONObject entityValue = JSONEntityResponseValues.getJSONResponseForNode("lastBuild");
            emailBody = "<html>";
            emailBody += "\n<head><style>body {background-color: white;}"
                    + "p,td,tr,table {"
                    + "color: black;"
                    + "font-family: calibri;"
                    + "font-size: 100%;"
                    + "}"
                    + "</style><title>" + multiJobName + " Execution Report</title>";

            emailBody += "</head><body>";
            emailBody += "\n<p>Hello All,<br />";
            emailBody += "\n<br />";
            emailBody += "\n Greetings of the day!<br />";
            emailBody += "\n<br />";
            emailBody += "\nPlease find below the execution results of <i><b>" + multiJobName + "</b></i> Jenkins job:-" + "</p>";
            
//            if (JSONEntityResponseValues.getJSONResponseOnlyForKey("color").contains("yellow")) {
//                emailBody += "<br><table border=\"1\"><tr><td align=\"center\"><font color = Black>" + multiJobName + "</font> </b></td><td bgcolor=\"" + unstableBuildColorCode + " align=\"center\"><font color = Black>FAIL</font></b></td></tr>";
//            } else if (JSONEntityResponseValues.getJSONResponseOnlyForKey("color").contains("green") || JSONEntityResponseValues.getJSONResponseOnlyForKey("color").contains("blue")) {
//                emailBody += "<br><table border=\"2\"><tr><td align=\"center\"><font color = Black> <font color = Black>" + multiJobName + "</font></b></td><td bgcolor=\"" + greenBuildColorCode + " align=\"center\"><font color = Black>PASS</font></b></td></tr>";
//            }
//            emailBody += "<table border=\"2\">";
//            emailBody += "<tr><td bgcolor=\"#F6CECE\" align=\"center\"><font color = Black>Build Number</a></font></b></td>";
//            emailBody += "<td bgcolor=\"#E3CEF6\" align=\"center\"><font color = Black>" + entityValue.getString("number") + "</a></font></b></td></tr>";
//            emailBody += "<tr><td bgcolor=\"#F6CECE\" align=\"center\"><font color = Black>Last Build URL</a></font></b></td>";
//            emailBody += "<td bgcolor=\"#E3CEF6\" align=\"center\"><font color = Black><a href= " + entityValue.getString("url") + ">" + (entityValue.getString("url"))
//                    + "</a></font></b></td></tr></table><br><br>";
            
            JSONArray entityValues = JSONEntityResponseValues.getJSONResponse("downstreamProjects");
            emailBody += "<table border=\"1\">";
            emailBody += "<tr><td align=\"center\"><font color = Black><b>Job Name</b></font></td><td><b><font color = Black>Build Health</b></font></td><td><td align=\"center\"><b><font color = Black>Comments</b></font></td></tr>";
            for (int i = 0; i < entityValues.length(); i++) {
                String buildURL = entityValues.getJSONObject(i).getString("url");
                String screenshotURL=null,consoleURL=null;
                String buildColor = entityValues.getJSONObject(i).getString("color");
                if (entityValues.getJSONObject(i).getString("name").toUpperCase().contains("REPORT")) {
                    continue;
                }
                if (buildColor.contains("yellow") || buildColor.contains("red")) {
                    emailBody += "<tr><td align=\"center\"><font color = Black>" + entityValues.getJSONObject(i).getString("name").toUpperCase() + "</font></b></td>"
                            + "<td bgcolor=\"" + unstableBuildColorCode + " align=\"center\"><b><font color = Black>FAIL</font></b></td></tr>";
                } else if (buildColor.contains("green") || buildColor.contains("blue")) {
                    emailBody += "<tr><td align=\"center\"><font color = Black>" + entityValues.getJSONObject(i).getString("name").toUpperCase() + "</font></b></td><td bgcolor=\"" + greenBuildColorCode + " align=\"center\"><b><font color = Black>PASS</font></b></td></tr>";
                } else if (buildColor.contains("aborted")) {
                    emailBody += "<tr><td align=\"center\"><font color = Black>" + entityValues.getJSONObject(i).getString("name").toUpperCase() + "</font></b></td>"
                            + "<td bgcolor=\"" + abortedBuildColorCode + " align=\"center\"><b><font color = Black>ABORTED</font></b></td></tr>";
                }
                
//              emailBody += "<table border=\"2\">";
//              emailBody += "<tr><td bgcolor=\"#F6CECE\" align=\"center\"><font color = Black>Last Build URL</a></font></b></td>";
                
                if (buildColor.contains("yellow") || buildColor.contains("red")) {
                    screenshotURL = buildURL+"ws/target/";
                    emailBody += "<td colspan=\"2\" bgcolor=\"#E3CEF6\" align=\"center\"><font color = Black><a href=" + screenshotURL + ">Refer to Console</a></font></b></td></tr>";
                } else if(buildColor.contains("aborted")){
                    consoleURL=buildURL+getLastBuildNumber(buildURL+"api/json")+"/console";
                    emailBody += "<td colspan=\"2\" bgcolor=\"#E3CEC6\" align=\"center\"><font color = Black><a href=" + consoleURL + ">CONSOLE OUTPUT</a></font></b></td></tr>";
                }
// 				buildFailedJobEmail(buildColor, buildURL);
//				<a href="analytics.html">Click here to view Analytics Report</a>
            }
            emailBody += "</table>";
            addSalutationLines();
            
            emailBody += "</font></body></html>";
        } catch (Exception e) {

        }
    }

    public void addAnalyticsCharts() {
        if (analyticsFlag.equalsIgnoreCase("yes")) {
            jsonObject.initiliazeEntity(multiphaseJobAPIURL);
            int pass = getPassCount(multiphaseJobAPIURL);
            int fail = getTotalCount(multiphaseJobAPIURL) - pass;
            System.out.println("pass:-" + pass + " & fail:-" + fail);
            JSONArray entityValues = JSONEntityResponseValues.getJSONResponse("downstreamProjects");
            try {
                analyticsChartsScript += "<html><head><script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\n"
                        + "    <script type=\"text/javascript\">\n"
                        + "      google.charts.load('current', {'packages':['bar','calendar']});\n"
                        + "      google.charts.setOnLoadCallback(drawChart);\n"
                        + "      function drawChart() {\n"
                        + "        var data = google.visualization.arrayToDataTable([\n"
                        + "          ['Job Name', 'Passed', 'Failed'],\n"
                        + "          ['" + JSONEntityResponseValues.getJSONResponseOnlyForKey("name") + "'," + pass + "," + fail + "]\n";

                for (int i = 0; i < entityValues.length(); i++) {
                    String name = entityValues.getJSONObject(i).getString("name");
                    if (name.toUpperCase().contains("REPORT")) {
                        continue;
                    }
                    String buildURL = entityValues.getJSONObject(i).getString("url") + "api/json";
                    pass = getPassCount(buildURL);
                    System.out.println(name + "|passed:-" + pass + " & Failed:-" + (getTotalCount(buildURL) - pass));
                    analyticsChartsScript += ",['" + name + "'," + pass + "," + (getTotalCount(buildURL) - pass) + "]\n";
                }
                analyticsChartsScript += " ]);\n"
                        + "\n";
                analyticsChartsScript += createDateTable(multiphaseJobAPIURL)
                        + " \n       var options = {\n"
                        + "          chart: {\n"
                        + "            title: '" + JSONEntityResponseValues.getJSONResponseOnlyForKey("name") + " analytics report" + "',\n"
                        + "            subtitle: 'MTQ  Analytics',\n"
                        + "          },\n"
                        + "          bars: 'vertical' // Required for Material Bar Charts.\n"
                        + "        };\n"
                        + " var options2 = {\n"
                        + "         title: '" + JSONEntityResponseValues.getJSONResponseOnlyForKey("name") + "_Execution_Calendar'" + ",\n"
                        + "         height: 350,\n"
                        + "		 \n"
                        + "		   noDataPattern: {\n"
                        + "           backgroundColor: '#76a7fa',\n"
                        + "           color: '#ffffff'\n"
                        + "         },\n"
                        + "		 calendar: {\n"
                        + "			cellSize: 15 ,\n"
                        + "      monthLabel: {\n"
                        + "        fontName: 'Times-Roman',\n"
                        + "        fontSize: 12,\n"
                        + "        color: '#00008B',\n"
                        + "        bold: true,\n"
                        + "        italic: true\n"
                        + "      },\n"
                        + "	  \n"
                        + "	  \n"
                        + "      monthOutlineColor: {\n"
                        + "        stroke: '#981b48',\n"
                        + "        strokeOpacity: 0.8,\n"
                        + "        strokeWidth: 2\n"
                        + "      },\n"
                        + "      unusedMonthOutlineColor: {\n"
                        + "        stroke: '#bc5679',\n"
                        + "        strokeOpacity: 0.8,\n"
                        + "        strokeWidth: 1\n"
                        + "		},\n"
                        + "      underMonthSpace: 16,\n"
                        + "    }"
                        + "};"
                        + "\n"
                        + "        var chart = new google.charts.Bar(document.getElementById('barchart_material'));\n"
                        + "var chart2 = new google.visualization.Calendar(document.getElementById('calendar_Analytics'));\n"
                        + "\n"
                        + "        chart.draw(data, options);\n"
                        + "        chart2.draw(dataTable,options2);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "</head>\n"
                        + "<body>"
                        + " <div id=\"calendar_Analytics\" style=\"width: 1000px; height: 350px; margin:0 auto;\"></div>\n"
                        + "<div id=\"barchart_material\" style=\"width: 900px; height: 500px; margin:0 auto;\"></div>\n"
                        + "</body>"
                        + "</html>";
                HTMLWriter htmlWriter = new HTMLWriter();
                htmlWriter.writeHTMLFile(analyticsChartsScript);
            } catch (Exception e) {

            }
        }
    }

    protected String getLastBuildNumber(String URI) {
        String number = null;
        try {
            String localEntity = JSONEntityResponseValues.locallyIntialiseEntity(URI);
            JSONObject entityValue = JSONEntityResponseValues.getJSONResponseForNode(localEntity, "lastBuild");
            number = entityValue.getString("number");
        } catch (JSONException ex) {
            Logger.getLogger(Build_MulitJob_Phase_Email.class.getName()).log(Level.SEVERE, null, ex);
        }
        return number;
    }

    protected int getPassCount(String URI) {
        String localEntity = JSONEntityResponseValues.locallyIntialiseEntity(URI);
        int passCounter = 0;
        try {
            JSONArray buildURL = JSONEntityResponseValues.getJSONResponse(localEntity, "builds"); //fetching from global entity
            for (int i = 0; i < buildURL.length(); i++) {
                String currentConsoleOutput = buildURL.getJSONObject(i).getString("url") + "api/json"; //Everything is stored locally. 

                String local = JSONEntityResponseValues.locallyIntialiseEntity(currentConsoleOutput);    //local HTTP entity  
                if (JSONEntityResponseValues.getJSONResponseOnlyForKey(local, "result").contains("SUCCESS")) {
                    passCounter++;
                }
            }
        } catch (Exception e) {

        }
        return passCounter;
    }

    public String createDateTable(String URI) {
        dateTable = "\n var dataTable = new google.visualization.DataTable();\n"
                + "	   var pass=1,fail=-1,abort=0;\n"
                + "	   \n"
                + "       dataTable.addColumn({ type: 'date', id: 'Date' });\n"
                + "       dataTable.addColumn({ type: 'number', id: 'Pass/Fail' }); \n"
                + "       dataTable.addRows([\n";

        String localEntity = JSONEntityResponseValues.locallyIntialiseEntity(URI);
        timeConvert = new ConvertTimeFormat();
        try {

            JSONArray buildURL = JSONEntityResponseValues.getJSONResponse(localEntity, "builds"); //fetching from global entity
            for (int j = 0; j < buildURL.length(); j++) {
                String currentConsoleOutput = buildURL.getJSONObject(j).getString("url") + "api/json"; //Everything is stored locally. 
                String local = JSONEntityResponseValues.locallyIntialiseEntity(currentConsoleOutput);    //local HTTP entity  
                timeConvert.initialiseTime(JSONEntityResponseValues.getJSONResponseOnlyForKey(local, "timestamp"));
                if (JSONEntityResponseValues.getJSONResponseOnlyForKey(local, "result").contains("SUCCESS")) { //UNSTABLE
                    dateTable += "[ new Date(" + timeConvert.returnYear() + "," + timeConvert.returnMonth() + "," + timeConvert.returnDate() + " ),pass ],\n";
                } else if (JSONEntityResponseValues.getJSONResponseOnlyForKey(local, "result").contains("UNSTABLE")) {
                    dateTable += "[ new Date(" + timeConvert.returnYear() + "," + timeConvert.returnMonth() + "," + timeConvert.returnDate() + " ),fail ],\n";
                } else {
                    dateTable += "[ new Date(" + timeConvert.returnYear() + "," + timeConvert.returnMonth() + "," + timeConvert.returnDate() + " ),abort ],\n";
                }
            }
            dateTable += " ]);";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateTable;
    }

    public int getTotalCount(String URI) {
        String localEntity = JSONEntityResponseValues.locallyIntialiseEntity(URI);
        try {
            JSONArray buildURL = JSONEntityResponseValues.getJSONResponse(localEntity, "builds");
            return buildURL.length() - 1;

        } catch (Exception e) {
            System.out.println("Error in retrieving builds count");
            return 0;
        }
    }
    
    public void buildFailedJobEmail(String status, String buildURL) {
        try {
            JSONArray entityValues = null;
            if (status.contains("yellow") || status.contains("red")) { //if build fails get into that build and pickup information
                multiphaseJobAPIURL = buildURL + "lastBuild/testReport/api/json";
                System.out.println("Failed job URL is " + multiphaseJobAPIURL);
                jsonObject.initiliazeEntity(multiphaseJobAPIURL);
                emailBody += "<tr><td bgcolor=\"#F6CECE\" align=\"center\"><font color = Black>Failed/Total Test Count</a></font></b></td>";
                emailBody += "<td colspan=\"2\" bgcolor=\"#E3CEF6\" align=\"center\"><font color = Black>" + JSONEntityResponseValues.getJSONResponseOnlyForKey("failCount") + "/" + JSONEntityResponseValues.getJSONResponseOnlyForKey("totalCount") + "</a></font></b></td></tr>";
                entityValues = JSONEntityResponseValues.getJSONResponse("childReports");
                JSONArray suiteStatus = entityValues.getJSONObject(0).getJSONObject("result").getJSONArray("suites").getJSONObject(0).getJSONArray("cases");
                for (int j = 0; j < suiteStatus.length(); j++) {
                    String buildStatus = suiteStatus.getJSONObject(j).getString("status");
                    if (buildStatus.contains("FAILED")) {
                        emailBody += "<tr><td  bgcolor=\"#F6CECE\" align=\"center\"><font color = Black>" + buildStatus + " Test Case Name</a></font></b></td>";
                        emailBody += "<td align=\"center\"><font color = Black>" + suiteStatus.getJSONObject(j).getString("name") + "</a></font></b></td>";
                        if (Integer.valueOf(suiteStatus.getJSONObject(j).getString("age")) > 1) {
                            emailBody += "<td align=\"center\"><font color = Black> This test cases failing since <b>" + suiteStatus.getJSONObject(j).getString("age") + "</b> builds</a></font></b></td></tr>";
                        } else {
                            emailBody += "<td bgcolor=\"#F2F5A9\"align=\"center\"><font color = Black> This test cases has failed for 1st time</a></font></b></td></tr>";
                        }
                    }
                }
            }
        } catch (Exception e) {
        	
        }
    }

    public void addSalutationLines() {
        if (analyticsFlag.equalsIgnoreCase("yes")) {
            emailBody += "<h5>Download the attached analytics.html for viewing this test analytics.</h5><br><br>";
        }
        emailBody = emailBody + "-- <br>Best Regards";
        emailBody = emailBody + "<br><b><i>" + projectName + " Automation Team </i></b></br>";

        emailBody = emailBody + "<hr>" + "<i>Note: This is a system generated mail. Please do not reply." + " ";
        emailBody = emailBody + "If you have any queries mail to <a href=mailto:" + replyto
                + "?subject=Reply-of-" + multiJobName + "Automation-Status>" + projectName + " Test Automation Team</a></i>";
    }

}
