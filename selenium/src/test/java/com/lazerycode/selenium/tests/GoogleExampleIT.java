package com.lazerycode.selenium.tests;

import org.openqa.selenium.By;
import com.lazerycode.selenium.DriverBase;
import com.lazerycode.selenium.page_objects.GoogleHomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import org.testng.Assert;

public class GoogleExampleIT extends DriverBase {

    private ExpectedCondition<Boolean> pageTitleStartsWith(final String searchString) {
        return driver -> driver.getTitle().toLowerCase().startsWith(searchString.toLowerCase());
    }

    @Test
    public void googleWordMonecraft() throws Exception {
        WebDriver driver = getDriver();
        driver.get("http://www.google.com");

        GoogleHomePage googleHomePage = new GoogleHomePage();

        System.out.println("\n\nPage title is: " + driver.getTitle());

        googleHomePage.enterSearchTerm("Minecraft").submitSearch();

        WebDriverWait wait = new WebDriverWait(driver, 10, 100);
        wait.until(pageTitleStartsWith("Minecraft"));

        System.out.println("Page title is: " + driver.getTitle() + "\n");
        Assert.assertEquals("Minecraft - Szukaj w Google", driver.getTitle());
    }

    @Test
    public void checkLink() throws Exception {
        WebDriver driver = getDriver();
        driver.get("http://the-internet.herokuapp.com/");

        System.out.println("\n\nPage title is: " + driver.getTitle());

        WebDriverWait wait = new WebDriverWait(driver, 5, 100);

        String tagName = driver.findElement(By.linkText("Add/Remove Elements")).getTagName();
        System.out.println("tag is: " + tagName + "\n" + "clicking...");
        Assert.assertEquals("a", tagName);

        driver.findElement(By.linkText("Add/Remove Elements")).click();

        wait = new WebDriverWait(driver, 5, 100);

        System.out.println("Current url is: " + driver.getCurrentUrl() + "\n");
        Assert.assertEquals("http://the-internet.herokuapp.com/add_remove_elements/", driver.getCurrentUrl());

        System.out.println("\nClicking on add button...");

        driver.findElement(By.tagName("button")).click();

        wait = new WebDriverWait(driver, 5, 100);

        if(driver.findElement(By.className("added-manually")).isDisplayed()){
            System.out.println("Created the element delete button with text: " + driver.findElement(By.className("added-manually")).getText() + "\n");
            System.out.println("\nClicking on delete button...");
            driver.findElement(By.className("added-manually")).click();

            try{
                driver.findElement(By.className("added-manually")).isDisplayed();
                System.out.println("\n Error while deleting :(");
            }catch(Exception e){
                System.out.println("\n Deleting button was a success");
            }

        }

    }
}