/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qait.Trust.automation.tests;

import com.qait.Trust.automation.TestFundamentals;
import org.testng.annotations.Test;

public class ManagePermissionTest extends TestFundamentals {

    String index1 = "1";
    String index2 = "2";
    String name = "Name";
    String description = "Description";
    String newPermission = "New Permission";
    String descriptionTag = "Created By Automation Script";

    @Test
    public void TC001_Check_User_Manage_Pemissions() {
        test.createAndConfigPage.logAsAdmin();
        test.createAndConfigPage.breadcrumbShouldBeAvailableForNavigation("Admin");
        test.createAndConfigPage.navigateToAdminMenu("List permissions");
        test.createAndConfigPage.gridShouldBeAvailableFor("Name");
        test.createAndConfigPage.gridShouldBeAvailableFor("Description");
    }

    @Test
    public void TC002_Check_Search_Functionality_Positive_Cycle() {
        test.managRolePage.validateSearchFunctionalityForPossitiveCycleWithText("Ad");
        test.managRolePage.validateSearchFunctionalityForPossitiveCycleWithText("Admin");
        test.managRolePage.validateSearchFunctionalityForPossitiveCycleWithText("MonitorCreateAll");
        test.managRolePage.validateSearchFunctionalityForPossitiveCycleWithText("DeleteAll");
    }

    @Test
    public void TC003_Check_Search_Functionality_Negative_Cycle() {
        test.createAndConfigPage.validateSearchFunctionalityForNegativeCycle();
    }

    @Test
    public void TC004_Shorting_Of_Records() {
        test.createAndConfigPage.shortingOfRecordswith(name, index1);
        test.createAndConfigPage.shortingOfRecordswith(description, index2);
    }

    @Test
    public void TC005_Check_Add_Permission() {
        test.managRolePage.clickOnAdd("Add Permission");
        test.managRolePage.newFormShouldBeOpened("Add permission");
        test.managRolePage.enterValueInForm(name, newPermission);
        test.managRolePage.enterValueInForm(description, descriptionTag);
        test.managRolePage.addGroup("Group", "Groups");
        test.managRolePage.addGroup("Type", "Create");
        test.createAndConfigPage.clickOnSaveButton();
    }

    @Test
    public void TC006_Delete_User_Creayed_By_Automation_Script() {
        test.managRolePage.clickOnDeleteButton(newPermission);
        test.createAndConfigPage.popUpShouldBeAppears("Cancel");
        test.managRolePage.clickOnDeleteButton(newPermission);
        test.createAndConfigPage.popUpShouldBeAppears("Delete");
    }
}
