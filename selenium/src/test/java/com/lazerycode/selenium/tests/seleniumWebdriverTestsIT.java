package com.lazerycode.selenium.tests;

import org.openqa.selenium.By;
import com.lazerycode.selenium.DriverBase;
import com.lazerycode.selenium.page_objects.GoogleHomePage;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import org.testng.Assert;

import java.io.File;
import org.openqa.selenium.OutputType;
import org.apache.commons.io.FileUtils;

public class seleniumWebdriverTestsIT extends DriverBase {

    private ExpectedCondition<Boolean> pageTitleStartsWith(final String searchString) {
        return driver -> driver.getTitle().toLowerCase().startsWith(searchString.toLowerCase());
    }

    @Test
    public void googleWordMinecraft() throws Exception {
        WebDriver driver = getDriver();
        driver.get("http://www.google.com");

        GoogleHomePage googleHomePage = new GoogleHomePage();
        System.out.println("\n\n\nTest google search");
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
        System.out.println("\n\n\nTest button functions");
        System.out.println("\nPage title is: " + driver.getTitle());
        System.out.println("\nCurrent url is: " + driver.getCurrentUrl());
        WebDriverWait wait = new WebDriverWait(driver, 5, 100);

        String tagName = driver.findElement(By.linkText("Add/Remove Elements")).getTagName();
        System.out.println("\nLooking for Add/Remove Elements");
        System.out.println("\nFound tag: " + tagName + "\n\n" + "clicking...");
        Assert.assertEquals("a", tagName);

        driver.findElement(By.linkText("Add/Remove Elements")).click();

        wait = new WebDriverWait(driver, 5, 100);

        System.out.println("\nCurrent url is: " + driver.getCurrentUrl());
        Assert.assertEquals("http://the-internet.herokuapp.com/add_remove_elements/", driver.getCurrentUrl());

        System.out.println("\nClicking on add button...");

        driver.findElement(By.tagName("button")).click();

        wait = new WebDriverWait(driver, 5, 100);

        if(driver.findElement(By.className("added-manually")).isDisplayed()){
            System.out.println("\nCreated the element delete button with text: " + driver.findElement(By.className("added-manually")).getText());
            System.out.println("\nClicking on delete button...");
            driver.findElement(By.className("added-manually")).click();

            try{
                driver.findElement(By.className("added-manually")).isDisplayed();
                System.out.println("\nError while deleting :(");
            }catch(Exception e){
                System.out.println("\nDeleting button was a success");
            }
        }
    }

    @Test
    public void loginTest() throws Exception{
        WebDriver driver = getDriver();
        driver.get("http://the-internet.herokuapp.com/");
        System.out.println("\n\n\nTest login");
        System.out.println("\nPage title is: " + driver.getTitle());
        System.out.println("\nCurrent url is: " + driver.getCurrentUrl());
        WebDriverWait wait = new WebDriverWait(driver, 5, 100);
        String tagName = driver.findElement(By.linkText("Form Authentication")).getTagName();
        System.out.println("\nLooking for Form Authentication");
        System.out.println("\nFound tag: " + tagName + "\n\n" + "clicking...");
        Assert.assertEquals("a", tagName);
        driver.findElement(By.linkText("Form Authentication")).click();
        wait = new WebDriverWait(driver, 5, 100);
        System.out.println("\nCurrent url is: " + driver.getCurrentUrl());
        Assert.assertEquals("http://the-internet.herokuapp.com/login", driver.getCurrentUrl());


        System.out.println("\nTyping username");
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        System.out.println("\nTyping password");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        System.out.println("\nClicking login");
        driver.findElement(By.className("radius")).click();


        try{
            driver.findElement(By.className("success"));
            System.out.println("\nLoging in have been succesful.");

            System.out.println("\nTaking screenshot...");
            TakesScreenshot scrShot =((TakesScreenshot)driver);
            File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
            File DestFile=new File("/home/PJWSTK/s15806/Documents/zajeciaTAU/selenium/LogIn.png");
            FileUtils.copyFile(SrcFile, DestFile);
            System.out.println("\nTook screenshot succesfuly.");

            System.out.println("\nClicking logout");
            driver.findElement(By.className("radius")).click();
            try{
                driver.findElement(By.id("username"));
                System.out.println("\nLoging out have been succesful.");
            }catch(Exception e){
                System.out.println("\nEncountered an error while logging out");
            }

        }catch(Exception e){
            System.out.println("\nEncountered an error while logging in");
        }
    }

    @Test
    public void validationTest() throws Exception{
        WebDriver driver = getDriver();
        driver.get("http://the-internet.herokuapp.com/");
        System.out.println("\n\n\nValidation test");
        System.out.println("\nPage title is: " + driver.getTitle());
        System.out.println("\nCurrent url is: " + driver.getCurrentUrl());
        WebDriverWait wait = new WebDriverWait(driver, 5, 100);
        String tagName = driver.findElement(By.linkText("Form Authentication")).getTagName();
        System.out.println("\nLooking for Form Authentication");
        System.out.println("\nFound tag: " + tagName + "\n\n" + "clicking...");
        Assert.assertEquals("a", tagName);
        driver.findElement(By.linkText("Form Authentication")).click();
        wait = new WebDriverWait(driver, 5, 100);
        System.out.println("\nCurrent url is: " + driver.getCurrentUrl());
        Assert.assertEquals("http://the-internet.herokuapp.com/login", driver.getCurrentUrl());


        System.out.println("\nTyping username");
        driver.findElement(By.id("username")).sendKeys("tomsmith@");
        WebElement inputField = driver.findElement(By.id("username"));
        String text = inputField.getAttribute("value");
        System.out.println("\nInput value is: " + text);

        if((text).contains("@")){
            System.out.println("Found special haracter. Input text is invalid.");
            System.out.println("\n\n\n");
        }
        else{
            System.out.println("Input text is valid");
            System.out.println("\n\n\n");
        }
    }
}