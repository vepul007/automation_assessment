package pageObjects;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;


public class PageObjects {

    WebDriver driver;

    public PageObjects(WebDriver driver){
        this.driver = driver;
    }

    By pop_up = By.xpath("/html/body/div/div/div[1]/div[1]/span");
    By popup_close = By.xpath("//*[@id=\"regPopUp\"]/a");
    By profile = By.xpath("//*[@id=\"headerUserArea\"]/div[2]/div/div[2]/div[4]");
    By regbtn = By.xpath("//*[@id=\"registerPopupLink\"]");
    By existing_user_btn = By.xpath("//*[@id=\"regPopUp\"]/div/div[2]/div[2]/div[1]/a");
    By username = By.xpath("//*[@id=\"signIn-form-username\"]/div[2]/div/input");
    By password = By.xpath("//*[@id=\"password\"]");
    By loginBtn = By.xpath("//*[@id=\"signIn-form-username\"]/div[5]/div");



    public void login(){
        driver.findElement(existing_user_btn).click();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        driver.findElement(username).click();
        driver.findElement(username).sendKeys(Constant.username);
        driver.findElement(password).click();
        driver.findElement(password).sendKeys(Constant.password);
        driver.findElement(loginBtn).click();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);

    }

    public void closePopup(){
        driver.switchTo().frame("webklipper-publisher-widget-container-notification-frame");
        driver.findElement(pop_up).click();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        driver.switchTo().defaultContent();

    }
}
