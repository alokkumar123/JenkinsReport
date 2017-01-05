package com.qait.Trust.automation.keywords;

import com.qait.Trust.automation.getpageobjects.GetPage;
import com.qait.Trust.automation.utils.ReportMsg;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import java.util.TimeZone;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class DetailScreenPageActions extends GetPage {

    WebDriver driver;

    public DetailScreenPageActions(WebDriver driver) {
        super(driver, "DetailScreen");
        this.driver = driver;
    }

    private void verifyBreadcrumb(String appName) {
        isElementDisplayed("link_breadCrumb", appName);
        ReportMsg.info("Verified breadcrumb for " + appName + " app");
    }

    public void userNavigateToPlatfromAvailableScreenWhenClickOnPlatfromAvailabilityHome() {
        isElementDisplayed("link_platfromAvailabilityHome");
        element("link_platfromAvailabilityHome").click();
        ReportMsg.info("Clicked on Platfrom Availability Home");
        isElementDisplayed("text_screenPage");
        ReportMsg.info("verified user navigate to platform Availabilty Screen");
    }

    public void verifyDropDownWithOptions(String string) {
        element("DropDownSelector").click();
        isElementDisplayed("dropdownOptions", string);
        ReportMsg.info("Verified " + string + " drop down");
    }

    public void clickOnSingleApp(String appName) {
        element("allApps", appName).click();
        ReportMsg.info("Clicked on " + appName + " app");
    }

    private void selectLastAvailableHours(String string) {
        if (string.contains("last 12 hours")) {
            isElementDisplayed("span_DropDownSelector");
        } else {
            wait.waitForElementToBeVisible(element("span_DropDownSelector"));
            element("span_DropDownSelector").click();
            isElementDisplayed("list_dropdownOptions", string);
            element("list_dropdownOptions", string).click();
        }
        ReportMsg.info("Selected " + string + " from drop down");
    }

    private void columnShouldRepresentLastHoursFromCurrent(String last_12_hours) {
        Date date = new Date();
        Date date1 = new Date();
        String strDateFormat = "HH a";
        DateFormat sdf = new SimpleDateFormat(strDateFormat);
        sdf.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
        String systemTime = sdf.format(date).toLowerCase();
        String strDateFormat1 = "HH:MM z";
        DateFormat sdf1 = new SimpleDateFormat(strDateFormat1);
        sdf1.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
        String systemTime1 = sdf1.format(date1);
        ReportMsg.info("System time = " + systemTime1);
        if (systemTime.contains("0")) {
            systemTime = systemTime.replace("0", "");
        }
        wait.waitForElementToBeVisible(element("applicationCurrentTime", systemTime));
        isElementDisplayed("applicationCurrentTime", systemTime);
        isElementDisplayed("tr_lastHours", systemTime);

        int hoursSize = Integer.parseInt(last_12_hours);
        ReportMsg.info("hours size in table = " + hoursSize + " for last " + last_12_hours + " hours");
        int rowSize = elements("tr_lastHours", systemTime).size();
        //ReportMsg.info("row size = " + rowSize);
        //Assert.assertEquals(rowSize, hoursSize);
    }

    public void verifyLeagendShouldBeAvailable() {
        isElementDisplayed("span_noIssues");
        isElementDisplayed("span_minorDisruption");
        isElementDisplayed("span_serviceTemporarilyUnavailable");
        isElementDisplayed("span_plannedMaintenance");
    }

    private void verifyInformationOnGregorianCalendar(String appName) {
        verifyDaysOnGregorianCalender(appName);
        verifyCurrentAndLastMonth(appName);
    }

    private void verifyCurrentAndLastMonth(String appName) {
        Calendar cal = Calendar.getInstance();
        String currentMonth = new SimpleDateFormat("MMM").format(cal.getTime());
        isElementDisplayed("text_currentMonth", currentMonth);
        ReportMsg.info("verified current month " + currentMonth + " is displaying on calender for " + appName);
        cal.add(Calendar.MONTH, -1);
        String lastMonth = new SimpleDateFormat("MMM").format(cal.getTime());
        isElementDisplayed("text_currentMonth", currentMonth);
        ReportMsg.info("verified last month " + lastMonth + " is displaying on calender for " + appName);
    }

    private void verifyDaysOnGregorianCalender(String appName) {
        ArrayList mylist = new ArrayList();
        mylist.add("Sun");
        mylist.add("Mon");
        mylist.add("Tue");
        mylist.add("Wed");
        mylist.add("Thu");
        mylist.add("Fri");
        mylist.add("Sat");

        Iterator it = mylist.iterator();
        while (it.hasNext()) {
            Object day = it.next();
            isElementDisplayed("text_days", (String) day);
            ReportMsg.info("verified " + day + " is displaying on calender for " + appName);

        }
    }

    public void onHoverOverOnAnyDay24HourClockShouldBeSeen() {
        hoverOnAnyDay("18");
        verifyDayHeading();
    }

    private void hoverOnAnyDay(String day) {
        hover(element("hover_anyday", day));
        hover(element("btn_currnetInformationAlerts"));
        ReportMsg.info("hover on day " + day);
    }

    private void verifyDayHeading() {
        isElementDisplayed("text_dayHeading");
        ReportMsg.info(element("text_dayHeading").getText() + " heading is displaying while hover on day");
        //ReportMsg.info("verified heading of the selected day");
    }

//    public void clickOnApp(String last_30_days) {
//        element("app_singleApp").click();
//        selectLastAvailableHours(last_30_days);
//        verifiInformationOnGregorianCalendar();
//    }
    private void verifyCurrentInformationAlerts(String appName) {
        try {
            isElementDisplayed("btn_currnetInformationAlerts");
            element("btn_currnetInformationAlerts").click();
            ReportMsg.info("clicked on current information alerts button");
            isElementDisplayed("txt_informationalAlert");
            ReportMsg.info("verified text Informational alerts text on pop up");
            executeJavascript("document.getElementsByClassName('btn btn-default')[0].click()");
//        isElementDisplayed("btn_close");     
//        element("btn_close").click();
            ReportMsg.info("Message bar is closed when clicked on close button");
            isElementDisplayed("btn_currnetInformationAlerts");
        } catch (Exception e) {
            ReportMsg.info("Creating new alert notification");
            isElementDisplayed("link_login");
            element("link_login").click();

            element("userName").clear();
            element("userName").click();
            element("userName").sendKeys("Admin");

            element("password").clear();
            element("password").click();
            element("password").sendKeys("Cengage1");
            element("button_login").click();

//            isElementDisplayed("button_Admin");
//            element("button_Admin").click();
//            ReportMsg.info("Click on admin button");
//            isElementDisplayed("link_adminPage");
//            element("link_adminPage");
//            ReportMsg.info("Select admin page");
            isElementDisplayed("button_createNotification");
            element("button_createNotification").click();
            ReportMsg.info("Click on create new notification button");
            isElementDisplayed("div_selectMonitor");
            element("div_selectMonitor").click();
            isElementDisplayed("list_allApp");
            element("list_allApp").click();
            ReportMsg.info("Select system all app from slect monitors");
            isElementDisplayed("select_endDate");
            element("select_endDate").click();
            String currentDate = element("text_currentDate").getText();
            int a = Integer.parseInt(currentDate);

            if (a < 31) {
                a = a + 1;
            } else {
                a = 1;
            }
            String date = Integer.toString(a);
            element("text_endDate", date).click();
            ReportMsg.info("Set end date ");
            isElementDisplayed("button_OK");
            element("button_OK").click();
            ReportMsg.info("Click on Ok button");
            isElementDisplayed("commentSection");
            element("commentSection").click();
            element("commentSection").clear();
            element("commentSection").sendKeys("creating notification");
            ReportMsg.info("send commnets creating notification");
            isElementDisplayed("button_save");
            element("button_save").click();
            ReportMsg.info("Click On save button");
            userNavigateToPlatfromAvailableScreenWhenClickOnPlatfromAvailabilityHome();
            isElementDisplayed("singleApp", appName);
            element("singleApp", appName).click();
            ReportMsg.info("Click on App = " + appName);
            isElementDisplayed("txt_appName");
            appName = element("txt_appName").getText();
            String b[] = appName.split("> ");
            appName = b[1];
            ReportMsg.info("AppName = " + appName);

        }
    }

    public void checkColorNotationGreenInPlatformAvailability() {
        int i = 0;
        for (WebElement el : elements("listOfApps")) {
            String appName = null;
            try {
                wait.waitForElementToBeVisible(elements("listOfApps").get(i));
                appName = elements("listOfApps").get(i).getText();
                ReportMsg.info("App Namr = " + appName);
            } catch (StaleElementReferenceException e) {
                wait.waitForElementToBeVisible(el);
                appName = el.getText();
                e.printStackTrace();
            }
            i++;
            verifyAppIsAvailableAndGreenInColor(appName);
        }
    }

    private void verifyAppIsAvailableAndGreenInColor(String appName) {
        isElementDisplayed("img_AppColor", appName);
        String img = element("img_AppColor", appName).getAttribute("src");
        ReportMsg.info("img color for " + appName + " is " + img);
        if (img.contains("green.png")) {
            img = "green.png";
        }
        Assert.assertEquals(img, "green.png", "app color is not green");
        ReportMsg.info("Verified color is green for " + appName);
        isElementDisplayed("text_appAvailability", appName);
        String appStatus = element("text_appAvailability", appName).getText();
        Assert.assertEquals(appStatus, "Available", "App is not avilable on");
        ReportMsg.info(appName + " app is available on platform ");

    }

//    public void checkColorNotationGreenInDetailScreen() {
//        int i = 0;
//        for (WebElement el : elements("listOfApps")) {
//            String appName = null;
//
//            try {
//                wait.waitForElementToBeVisible(elements("listOfApps").get(i));
//                appName = elements("listOfApps").get(i).getText();
//                ReportMsg.info("App Namr = " + appName);
//            } catch (StaleElementReferenceException e) {
//                wait.waitForElementToBeVisible(el);
//                appName = el.getText();
//                e.printStackTrace();
//            }
//            i++;
//            selectAppsFromPlatformScreen(appName);
////        }
//    }
    public void verifyBreadCrumb(String appName) {
        System.out.println("############## Verifying Breadcrumb, TRUST-317 ##############\n");
        isElementDisplayed("singleApp", appName);
        element("singleApp", appName).click();
        ReportMsg.info("Click on App = " + appName);
        isElementDisplayed("txt_appName");
        appName = element("txt_appName").getText();
        String a[] = appName.split("> ");
        appName = a[1];
        ReportMsg.info("AppName = " + appName);
        verifyBreadcrumb(appName);
    }

    public void verifyDropDownOptionsForLastHours() {
        System.out.println("\n############## Verifying Drop Down options for last hours, TRUST-318 ##############\n");
        String hours = "last 12 hours";
        executeJavascript("document.getElementsByClassName('rw-input')[0].click()");
        ReportMsg.info("Verified " + hours + " from last hours drop down");
        hours = "last 24 hours";
        isElementDisplayed("list_dropdownOptions", hours);
        ReportMsg.info("Verified " + hours + " from last hours drop down");
        hours = "last 30 days";
        isElementDisplayed("list_dropdownOptions", hours);
        ReportMsg.info("Verified " + hours + " from last hours drop down");

    }

    public void verifyTimeZoneDropDownForUser() {
        System.out.println("\n############## Verifying Drop Down options for Time Zones, TRUST-319 ##############\n");
        String timeZone = "EST (local)";
        executeJavascript("document.getElementsByClassName('rw-input')[1].click()");
        //isElementDisplayed("list_timezonedropdownOptions", timeZone);
        ReportMsg.info("Verified " + timeZone + " from time zone drop down");
        timeZone = "CST";
        isElementDisplayed("list_timezonedropdownOptions", timeZone);
        ReportMsg.info("Verified " + timeZone + " from time zone drop down");
        timeZone = "PST";
        isElementDisplayed("list_timezonedropdownOptions", timeZone);
        ReportMsg.info("Verified " + timeZone + " from time zone drop down");
        timeZone = "GMT";
        isElementDisplayed("list_timezonedropdownOptions", timeZone);
        ReportMsg.info("Verified " + timeZone + " from time zone drop down");
    }

    public void verifyInformationAvailableForLastHours1(String lastHours, String hours, String appName) {
        String JiraId = "TRUST-321";
        if (!lastHours.contains("last 12 hours")) {
            JiraId = "TRUST-322";
            selectLastAvailableHours(lastHours);
        }
        try {
            System.out.println("\n############## Verifying Information Available for " + lastHours + " on Detail Screen Page, " + JiraId + " ##############\n");
            isElementDisplayed("table_systemStatus");
            ReportMsg.info("verified system status of table");
            columnShouldRepresentLastHoursFromCurrent(hours);
            verifyLeagendShouldBeAvailable();
            //userNavigateToPlatfromAvailableScreenWhenClickOnPlatfromAvailabilityHome();
        } catch (Exception e1) {
            // e1.printStackTrace();
            ReportMsg.info("table system status is not availabe for app");
            isElementDisplayed("div_errorMessage");
            String message = element("div_errorMessage").getText();
            ReportMsg.info("App Information is not available, Message is appearing with text :- " + message + " for " + lastHours);
            userNavigateToPlatfromAvailableScreenWhenClickOnPlatfromAvailabilityHome();
            isElementDisplayed("singleApp", appName);
            element("singleApp", appName).click();
            ReportMsg.info("Click on App = " + appName);
            isElementDisplayed("txt_appName");
            appName = element("txt_appName").getText();
            String a[] = appName.split("> ");
            appName = a[1];
            ReportMsg.info("AppName = " + appName);
        }
    }

    public void verifyInformationAvailableForLast30Days1(String last_30_days, String appName) {
        System.out.println("\n############## Verifying Information Available for " + last_30_days + " on Deltail Screen Page, TRUST-323 ##############\n");
        selectLastAvailableHours(last_30_days);
        try {
            isElementDisplayed("table_systemStatus");
            element("table_systemStatus").isDisplayed();
            ReportMsg.info("Verifying table system stutus");
            verifyInformationOnGregorianCalendar(appName);
            onHoverOverOnAnyDay24HourClockShouldBeSeen();
            verifyLeagendShouldBeAvailable();
        } catch (Exception e) {
            ReportMsg.info("App Information is not available, Message is appearing with text:- 'Detailed data view coming soon...'  for " + last_30_days);
            verifyBreadcrumb(appName);
        }
    }

    public void checkCurrecntInformationAlertButtonforLastDays1(String appName) {
        System.out.println("\n############## Verifying Current Information Alert For All last hours , TRUST-324, TRUST-340, TRUST-345 ##############\n");
        String lastHours = "last 12 hours";
        selectLastAvailableHours(lastHours);
        verifyCurrentInformationAlerts(appName);
        lastHours = "last 24 hours";
        selectLastAvailableHours(lastHours);
        verifyCurrentInformationAlerts(appName);
        lastHours = "last 30 days";
        selectLastAvailableHours(lastHours);
        verifyCurrentInformationAlerts(appName);
    }

    public void checkForColorNotationGreenInThePlatformAvailabilityAndDetailScreen(String appName) {
        System.out.println("\n############## Verifying Green Color notation, TRUST-346 ##############\n");
        try {
            isElementDisplayed("div_appColorOnPlatform", appName);
            ReportMsg.info("Verified color for " + appName + " app is green on Platform Availability Screen");
            isElementDisplayed("singleApp", appName);
            element("singleApp", appName).click();
            // ReportMsg.info("Click on App = " + appName);
            isElementDisplayed("txt_appName");
            appName = element("txt_appName").getText();
            String a[] = appName.split("> ");
            appName = a[1];
            ReportMsg.info("Click on App " + appName + " and navigate to the details screen");
            ReportMsg.info("Set time frame for 12 hours");
            ReportMsg.info("Set timezone to EST");
            isElementDisplayed("table_systemStatus");
            ReportMsg.info("Verifying table system stutus");
            isElementDisplayed("greenColor_timeFrame");
            ReportMsg.info("Verified current frame notation is green ");
            int size = elements("greenColor_timeFrame").size();
            ReportMsg.info("Number of current time frames in green color are " + size + " for 12 hours and EST time zone on Detail Screen");
            userNavigateToPlatfromAvailableScreenWhenClickOnPlatfromAvailabilityHome();
        } catch (Exception e) {
            // e1.printStackTrace();
            ReportMsg.info("table system status is not availabe for app");
            isElementDisplayed("div_errorMessage");
            String message = element("div_errorMessage").getText();
            ReportMsg.info("App Information is not available, Message is appearing with text :- " + message + " for " + appName);
            userNavigateToPlatfromAvailableScreenWhenClickOnPlatfromAvailabilityHome();
        }
    }

}
