package stepDefinitions;


import com.cucumber.listener.Reporter;

import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.lexer.Th;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import pageObjects.Constant;
import pageObjects.PageObjects;
import utility.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Steps {

    WebDriver driver;


    @Before
    public void testSetup(){
        System.setProperty("webdriver.chrome.driver",Constant.driverpath);    // Replace path with local path in Constant.java
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
//        options.addArguments("--headless");
//        options.addArguments("window-size=1024x768");
        options.addArguments("--no-sandbox");
//        options.addArguments("--proxy-server="+proxy);
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--allow-running-insecure-content");

        //Create a new ChromeDriver
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Given("^user browse to pepperfry live website and click on profile$")
    public void user_browse_to_pepperfry_live_website_and_click_on_profile() throws Throwable {

        PageObjects pageObjects = new PageObjects(driver);
        driver.get(Constant.url);              // Navigate to Website
        String actual= driver.getTitle();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        Thread.sleep(5000);
        pageObjects.closePopup();
        Assert.assertEquals(Constant.main_page_title,actual);
        Reporter.addStepLog("Main page launched successfully");
        Thread.sleep(2000);
        Reporter.addScreenCaptureFromPath(Utility.getScreenshot(driver));

    }

    @When("^user login to site using email and password$")
    public void userLoginToSiteUsingEmailAndPassword() throws IOException, InterruptedException {
        PageObjects pageObjects = new PageObjects(driver);
        pageObjects.login();
        pageObjects.closePopup();
        By profileClick = By.xpath("//*[@id=\"headerUserArea\"]/div[2]/div/div[2]/div[4]/a/span[1]");
        driver.findElement(profileClick).click();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        Reporter.addStepLog("User logged in successfully");
        Thread.sleep(2000);
        Reporter.addScreenCaptureFromPath(Utility.getScreenshot(driver));
    }

    @And("^user search ‘sofa’ which redirects to product list$")
    public void userSearchSofaWhichRedirectsToProductList() throws IOException, InterruptedException {
        PageObjects pageObjects = new PageObjects(driver);
        By search_bar = By.xpath("//input[@id=\"search\"]");
        driver.findElement(search_bar).click();
        driver.findElement(search_bar).sendKeys("sofa");
        driver.findElement(By.xpath("//input[@id=\"searchButton\"]")).click();
        Reporter.addStepLog("Sofa search successful");
        Thread.sleep(2000);
        Reporter.addScreenCaptureFromPath(Utility.getScreenshot(driver));
    }

    @And("^user clicks very first product from the list and product detail page gets open in new tab$")
    public void userClicksVeryFirstProductFromTheListAndProductDetailPageGetsOpenInNewTab() throws IOException, InterruptedException {
        By first_product = By.xpath("//a[@title=\"Eiichi 3 Seater Sofa in Light Grey Colour\"]");
        String clicklnk = Keys.chord(Keys.CONTROL,Keys.ENTER);
        driver.findElement(first_product).sendKeys(clicklnk);
        ArrayList<String> newTb = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(newTb.get(1));
        Reporter.addStepLog("Sofa details are getting displayed in New tab");
        Thread.sleep(2000);
        Reporter.addScreenCaptureFromPath(Utility.getScreenshot(driver));

    }


    @And("^user selects (\\d+) in the quantity dropdown and clicks on ‘Buy Now’ button$")
    public void userSelectsInTheQuantityDropdownAndClicksOnBuyNowButton(int count) throws IOException, InterruptedException {
        By quantity_select = By.xpath("//select[@id=\"quantity\"]");
        Select s = new Select(driver.findElement(quantity_select));
        s.selectByVisibleText(Integer.toString(count));
        By buynow = By.xpath("//a[@id=\"vipBuyNowButton\"]");
        driver.findElement(buynow).click();
        Reporter.addStepLog("Product added to cart");
        Thread.sleep(2000);
        Reporter.addScreenCaptureFromPath(Utility.getScreenshot(driver));
    }

    @And("^on cart page apply coupon ‘Republic’ and store the ‘You Pay’ values$")
    public void onCartPageApplyCouponRepublicAndStoreTheYouPayValues() throws IOException, InterruptedException {
        By coupon_text = By.xpath("//input[@id=\"coupon_code\"]");
        driver.findElement(coupon_text).click();
        driver.findElement(coupon_text).sendKeys("Republic");
        By apply_btn = By.xpath("//input[@id=\"cpn_check_btn\"]");
        driver.findElement(apply_btn).click();

        By pay_value_text = By.xpath("//span[@id=\"total_pay_coupon\"]");
        String pay_value = driver.findElement(pay_value_text).getText();
        Constant.payValueAtCart= pay_value;
        Reporter.addStepLog("Coupon applied and pay value is now stored");
        Thread.sleep(2000);
        Reporter.addScreenCaptureFromPath(Utility.getScreenshot(driver));
    }

    @And("^user click on Place Order button, it will redirect to address page$")
    public void userClickOnPlaceOrderButtonItWillRedirectToAddressPage() throws IOException, InterruptedException {
        By place_order = By.xpath("//a[@class=\"btn_green_big  \"]");
        driver.findElement(place_order).click();
        Reporter.addStepLog("Redirected to address page");
        Thread.sleep(2000);
        Reporter.addScreenCaptureFromPath(Utility.getScreenshot(driver));
    }


    @Then("^store the You Pay value on address$")
    public void storeTheYouPayValueOnAddress() throws IOException, InterruptedException {
        By payValue_address = By.xpath("//span[@id=\"total_pay_coupon\"]");
        String pay_val_at_adress =  driver.findElement(payValue_address).getText();
        Constant.payValueAtAddress = pay_val_at_adress;
        Reporter.addStepLog("Pay value at address page stored");
        Thread.sleep(2000);
        Reporter.addScreenCaptureFromPath(Utility.getScreenshot(driver));
    }

    @And("^compare those with earlier stored You Pay value of cart$")
    public void compareThoseWithEarlierStoredYouPayValueOfCart() throws IOException, InterruptedException {
        if(!Constant.payValueAtCart.equals(Constant.payValueAtAddress)){
            Reporter.addStepLog("Pay values do not match");
            Thread.sleep(2000);
            Reporter.addScreenCaptureFromPath(Utility.getScreenshot(driver));
        }
        Reporter.addStepLog("Pay values match at both pages");
        Thread.sleep(2000);
        Reporter.addScreenCaptureFromPath(Utility.getScreenshot(driver));
        driver.quit();
    }

    @And("^hover on profile and click on ‘My Account’$")
    public void hoverOnProfileAndClickOnMyAccount() throws InterruptedException, IOException {
        By my_account = By.xpath("//*[@id=\"headerUserArea\"]/div[2]/div/div[2]/div[4]/div/div[1]/div/div[2]/a");
        driver.findElement(my_account).click();
        Reporter.addStepLog("Account dashboard page opened");
        Thread.sleep(2000);
        Reporter.addScreenCaptureFromPath(Utility.getScreenshot(driver));
    }

    @Then("^click on ‘Add New Address’ and pop will be displayed$")
    public void clickOnAddNewAddressAndPopWillBeDisplayed() throws InterruptedException, IOException {
        By addressbook = By.xpath("//li[@id=\"address\"]");
        driver.findElement(addressbook).click();

        By new_address = By.xpath("//a[@class=\"add-new-address\"]");
        driver.findElement(new_address).click();

        Reporter.addStepLog("Address pop-up launched");
        Thread.sleep(2000);
        Reporter.addScreenCaptureFromPath(Utility.getScreenshot(driver));
    }

    @And("^enter all the valid details and save the address$")
    public void enterAllTheValidDetailsAndSaveTheAddress() throws InterruptedException, IOException {
        By postcode = By.xpath("//input[@id=\"postcode\"]");
        driver.findElement(postcode).sendKeys("209622");
        By fname = By.xpath("//input[@id=\"name\"]");
        driver.findElement(fname).sendKeys("Shashikant");
        By lname = By.xpath("//input[@id=\"name2\"]");
        driver.findElement(lname).sendKeys("Awasthi");
        By mobile = By.xpath("//input[@id=\"mobile\"]");
        driver.findElement(mobile).sendKeys("9794155598");
        By street = By.xpath("//input[@id=\"street\"]");
        driver.findElement(street).sendKeys("Awas Vikas Coloney");


        By saveAddress = By.xpath("//a[@id=\"saveAddress\"]");
        driver.findElement(saveAddress).click();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        Reporter.addStepLog("Address saved");
        Thread.sleep(2000);
        Reporter.addScreenCaptureFromPath(Utility.getScreenshot(driver));
        driver.quit();
    }


    @And("^hover on ‘Furniture’ and click on three Seater Sofa$")
    public void hoverOnFurnitureAndClickOnThreeSeaterSofa() throws InterruptedException, IOException {
        By furniture_hover = By.xpath("//a[@rel=\"meta-furniture\"]");
        driver.findElement(furniture_hover).click();
        By _3seater_sofa = By.xpath("//*[@id=\"meta-furniture\"]/div/div[1]/div[2]/div[4]/a");
        driver.findElement(_3seater_sofa).click();
        Reporter.addStepLog("All 3 seater sofa page launched");
        Thread.sleep(2000);
        Reporter.addScreenCaptureFromPath(Utility.getScreenshot(driver));
    }


    @And("^on redirected product listing page, click on first product$")
    public void onRedirectedProductListingPageClickOnFirstProduct() throws InterruptedException, IOException {
        By first_product = By.xpath("/html/body/div[2]/div[2]/div/div[4]/div[3]/div/div[2]/div/div/div/div[1]/div/div[3]/div/h2/a");
        String clicklnk = Keys.chord(Keys.CONTROL,Keys.ENTER);
        driver.findElement(first_product).sendKeys(clicklnk);
        ArrayList<String> newTb = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(newTb.get(1));
        Reporter.addStepLog("1st product details displayed");
        Thread.sleep(2000);
        Reporter.addScreenCaptureFromPath(Utility.getScreenshot(driver));
    }

    @And("^it will open product in new tab, click on ‘Add to wishlist’ and store the product name$")
    public void itWillOpenProductInNewTabClickOnAddToWishlistAndStoreTheProductName() throws InterruptedException, IOException {
        By wishlist = By.xpath("/html/body/div[2]/div[2]/div/div[2]/div/div[2]/div/div/div[1]/div/div[3]/div[3]/div");
        driver.findElement(wishlist).click();
        By sofa_name = By.xpath("//h1[@itemprop=\"name\"]");
        String detailsPageName1 = driver.findElement(sofa_name).getText();
        Constant.three_seater_sofa.add(detailsPageName1);
        Reporter.addStepLog("product added to wishlist and name stored");
        Thread.sleep(2000);
        Reporter.addScreenCaptureFromPath(Utility.getScreenshot(driver));

    }


    @And("^click on previous tab, click on second product from the list$")
    public void clickOnPreviousTabClickOnSecondProductFromTheList() throws InterruptedException, IOException {
        ArrayList<String> newTb = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(newTb.get(0));
        Reporter.addStepLog("Back to previous tab");
        Thread.sleep(2000);
        Reporter.addScreenCaptureFromPath(Utility.getScreenshot(driver));
        By second_product = By.xpath("/html/body/div[2]/div[2]/div/div[4]/div[3]/div/div[2]/div/div/div/div[2]/div/div[3]/div/h2/a");
        String clicklnk = Keys.chord(Keys.CONTROL,Keys.ENTER);
        driver.findElement(second_product).sendKeys(clicklnk);
        ArrayList<String> newTb1 = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(newTb1.get(2));
        Reporter.addStepLog("2nd product details displayed");
        Thread.sleep(2000);
        Reporter.addScreenCaptureFromPath(Utility.getScreenshot(driver));
    }

    @Then("^click on Wishlist on the top section$")
    public void clickOnWishlistOnTheTopSection() throws InterruptedException, IOException {
        By wishlist_link = By.xpath("/html/body/div[2]/header/div[3]/div/div/div[1]/div/div/div[2]/div/div[2]/div[2]/a/span[2]");
        driver.findElement(wishlist_link).click();
        Reporter.addStepLog("Wishlist is dispalyed");
        Thread.sleep(2000);
        Reporter.addScreenCaptureFromPath(Utility.getScreenshot(driver));
    }


    @And("^Validate the products in the side panel$")
    public void validateTheProductsInTheSidePanel() throws InterruptedException, IOException {
        By sofa1 = By.xpath("/html/body/div[2]/div[3]/div[5]/div/div[2]/div/div[1]/div[2]/div/div/div[2]/p[1]/a");
        String name1_wishlist = driver.findElement(sofa1).getText();
        By sofa2 = By.xpath("/html/body/div[2]/div[3]/div[5]/div/div[2]/div/div[1]/div[1]/div/div/div[2]/p[1]/a");
        String name2_wishlist = driver.findElement(sofa2).getText();
        Assert.assertEquals(Constant.three_seater_sofa.get(0).substring(0,15),name1_wishlist.substring(0,15));
        Assert.assertEquals(Constant.three_seater_sofa.get(1).substring(0,15),name2_wishlist.substring(0,15));
        Reporter.addStepLog("Products in wishlist verified");
        Thread.sleep(2000);
        Reporter.addScreenCaptureFromPath(Utility.getScreenshot(driver));
    }

    @And("^Validate the count of the Wishlist and the number of products listed in the side panell$")
    public void validateTheCountOfTheWishlistAndTheNumberOfProductsListedInTheSidePanell() throws InterruptedException, IOException {
        By wishlist_count = By.xpath("//span[@id=\"wishlist_mini_cart\"]");
        String count = driver.findElement(wishlist_count).getText();
        int actual_count = Integer.parseInt(count);
        Assert.assertEquals(Constant.three_seater_sofa.size(),actual_count);
        Reporter.addStepLog("Wishlist count verified");
        Thread.sleep(2000);
        Reporter.addScreenCaptureFromPath(Utility.getScreenshot(driver));
        driver.quit();
    }
}
