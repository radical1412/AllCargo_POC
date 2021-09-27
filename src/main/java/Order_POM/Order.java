package Order_POM;

import CommonUtil.Common;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.IOException;

public class Order {

    WebDriver driver;
    WebDriverWait wait;
    Common prop;
    String amount;

    public Order(WebDriver driver) {
        this.driver = driver;
        //Even with 100 seconds as loader time the loaders are not going away
        wait = new WebDriverWait(driver, 150, 150);
        prop = new Common();
    }

    By loader = By.xpath("//mat-spinner[@class]");

    //Login Page
    By userTB = By.id("email");
    By passTB = By.id("passwordLogin");
    By loginBtn = By.xpath("//button[contains(@class, 'landingpage')]");

    //Dashboard
    By instantQuoteBtn = By.xpath("//*[contains (text(), 'Instant Quote')]");
    By logoutBtn = By.xpath("//button[@aria-label = 'Logout']");

    //Quote
    By fromTB = By.id("from");
    By toTB = By.id("to");
    By sourceDD = By.xpath("//span[text() = ' INNSA , NHAVA SHEVA , INDIA ']");
    By destinationDD = By.xpath("//span[text() = ' BEANR , ANTWERP , BELGIUM ']");
    By weightTB = By.id("totalWeight");
    By volumeTB = By.id("totalVolume");
    By dimensionDD = By.id("isDimensionProvided");
    By getQuoteBtn = By.xpath("//button[@type = 'submit']");
    By clickRandom = By.xpath("//label[@class = 'mr20']");

    //Quote (Dimension Fields)
    By dOptionYes = By.xpath("//*[@class = 'mat-option-text' and text() = ' Yes ']");
    By dOptionNo = By.xpath("//*[@class = 'mat-option-text' and text() = ' No ']");
    By dLengthTB = By.id("length");
    By dWidthTB = By.id("width");
    By dHeightTB = By.id("height");
    By dPiecesTB = By.id("quantity");
    By dWeightTB = By.id("weight");

    //Quote (Error Messages)
    By erFromTB = By.xpath("//input[@id = 'from']//ancestor::mat-form-field//mat-error[@role = 'alert']");
    By erToTB = By.xpath("//input[@id = 'to']//ancestor::mat-form-field//mat-error[@role = 'alert']");
    By erWeightTB = By.xpath("//input[@id = 'totalWeight']//ancestor::mat-form-field//mat-error[@role = 'alert']");
    By erVolumeTB = By.xpath("//input[@id = 'totalVolume']//ancestor::mat-form-field//mat-error[@role = 'alert']");
    By erdLengthTB = By.xpath("//input[@id = 'length']//ancestor::mat-form-field//mat-error[@role = 'alert']");
    By erdWidthTB = By.xpath("//input[@id = 'width']//ancestor::mat-form-field//mat-error[@role = 'alert']");
    By erdHeightTB = By.xpath("//input[@id = 'height']//ancestor::mat-form-field//mat-error[@role = 'alert']");
    By erdPiecesTB = By.xpath("//input[@id = 'quantity']//ancestor::mat-form-field//mat-error[@role = 'alert']");
    By erdWeightTB = By.xpath("//input[@id = 'weight']//ancestor::mat-form-field//mat-error[@role = 'alert']");
    By erWarningMessage = By.xpath("//simple-snack-bar[@class = 'mat-simple-snackbar ng-star-inserted']");

    //Quotation List
    By amountList = By.xpath("//div[@class = 'flex1 pricetext']//span");
    By saveEmailBtn = By.xpath("//button[span[text() = 'Save & Email']]");
    By viewDetailsBtn = By.xpath("//button[span[text() = 'View Details']]");

    //Quotation Details
    By amountDetails = By.xpath("//p[span[contains(text(), 'USD')]]//span");
    By originCheck = By.xpath("//span[text() = ' ORIGIN | INCLUDED ']");
    By freightCheck = By.xpath("//span[text() = ' ORIGIN | INCLUDED ']");
    By additionalCheck = By.xpath("//span[text() = ' ORIGIN | INCLUDED ']");
    By destinationCheck = By.xpath("//li[text() = ' Prepaid - Destination Charges not included ']");
    By bookNowBtn = By.xpath("//button/span[text() = ' Book Now ']");

    //Tip Messages
    By rateTip = By.xpath("//span[text() = 'Rates offered might no longer be valid at time of advised cargo " +
            "ready date and are to be considered as an indication.']");
    By quoteTip = By.xpath("//span[text() = ' The selected Quote is no longer valid and will not be attached " +
            "to your booking. Rates might no longer be valid']");
    By saveTip = By.xpath("//span[text() = 'Quotation Saved Successfully']");

    public void enterUser(String email) {
        driver.findElement(userTB).sendKeys(email);
    }

    public void enterPass(String password) {
        driver.findElement(passTB).sendKeys(password);
    }

    public void login() {
        driver.findElement(loginBtn).click();
        waitLoader();
        waitLoader();
    }

    public void logout() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(quoteTip));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(quoteTip));
        driver.findElement(logoutBtn).click();
    }

    public void selectInstant() {
        waitClickable(instantQuoteBtn).click();
        waitLoader();
    }


    public void verifyMandatory() throws IOException, InterruptedException {
        //Quote error verification
        driver.findElement(fromTB).click();
        driver.findElement(toTB).click();
        driver.findElement(weightTB).click();
        driver.findElement(volumeTB).sendKeys(Keys.RETURN);
        waitClickable(erVolumeTB);
        Assert.assertEquals(driver.findElement(erFromTB).getText(), prop.readProp().getProperty("erFrom"),
                "Error message is not valid for Source TB");
        Assert.assertEquals(driver.findElement(erToTB).getText(), prop.readProp().getProperty("erTo"),
                "Error message is not valid for Destination TB");
        Assert.assertEquals(driver.findElement(erWeightTB).getText(), prop.readProp().getProperty("erWeight"),
                "Error message is not valid for Weight TB");
        Assert.assertEquals(driver.findElement(erVolumeTB).getText(), prop.readProp().getProperty("erVolume"),
                "Error message is not valid for Volume TB");
        Assert.assertTrue(driver.findElement(erWarningMessage).isDisplayed());

        //Dimensions error verification
        driver.findElement(dimensionDD).click();
        driver.findElement(dOptionYes).click();
        Thread.sleep(500);
        //Sometimes these fields don't appear at all
        waitClickable(dLengthTB).click();
        driver.findElement(dWidthTB).click();
        driver.findElement(dHeightTB).click();
        driver.findElement(dPiecesTB).click();
        driver.findElement(dWeightTB).sendKeys(Keys.RETURN, Keys.TAB);
        waitClickable(erdWeightTB);
        Assert.assertEquals(driver.findElement(erdLengthTB).getText(), prop.readProp().getProperty("erdLength"),
                "Error message is not valid for Source TB");
        Assert.assertEquals(driver.findElement(erdWidthTB).getText(), prop.readProp().getProperty("erdWidth"),
                "Error message is not valid for Destination TB");
        Assert.assertEquals(driver.findElement(erdHeightTB).getText(), prop.readProp().getProperty("erdHeight"),
                "Error message is not valid for Weight TB");
        Assert.assertEquals(driver.findElement(erdPiecesTB).getText(), prop.readProp().getProperty("erdPieces"),
                "Error message is not valid for Volume TB");
        Assert.assertEquals(driver.findElement(erdWeightTB).getText(), prop.readProp().getProperty("erdWeight"),
                "Error message is not valid for Volume TB");
        Assert.assertTrue(driver.findElement(erWarningMessage).isDisplayed());
    }

    public void setSource(String source) {
        int counter = 0;
        waitClickable(fromTB).sendKeys(source);
        waitLoader();
        /*The POC document specifically asked for the below values to be selected.
         * In practice, I would have selected whatever the first option and verified if that is the correct auto-suggestion.
         */
        while (!waitPresent(sourceDD))
        {
            waitClickable(fromTB).click();
            counter++;
            if (counter>2){
                Assert.fail("Auto Suggestion is not loading up properly.");
                break;
            }
        }
        driver.findElement(clickRandom).click();
        waitLoader();
    }

    public void setDestination(String destination) {
        int counter = 0;
        waitClickable(toTB).sendKeys(destination);
        waitLoader();
        /*The POC document specifically asked for the below values to be selected.
         * In practice, I would have selected whatever the first option and verified if that is the correct auto-suggestion.
         */
        while (!waitPresent(destinationDD))
        {
            waitClickable(toTB).click();
            counter++;
            if (counter>2){
                Assert.fail("Auto Suggestion is not loading up properly.");
                break;
            }
        }
        driver.findElement(clickRandom).click();
        waitLoader();
    }

    public void setWeightVolume(double weight, double volume) {
        driver.findElement(weightTB).sendKeys(weight + "");
        driver.findElement(volumeTB).sendKeys(volume + "");
    }

    public void setDimensions(double length, double width, double height, double pieces, double weight) {
        driver.findElement(dimensionDD).click();
        driver.findElement(dOptionYes).click();
        //Sometimes these fields don't appear at all
        driver.findElement(dLengthTB).sendKeys(length + "");
        driver.findElement(dWidthTB).sendKeys(width + "");
        driver.findElement(dHeightTB).sendKeys(height + "");
        driver.findElement(dPiecesTB).sendKeys(pieces + "");
        driver.findElement(dWeightTB).sendKeys(weight + "");
    }

    public void getQuote() {
        waitClickable(getQuoteBtn).click();
        waitLoader();
        //Application is not going ahead with single click oin Get Quote
        waitClickable(getQuoteBtn).click();
        waitLoader();
        amount = driver.findElement(amountList).getText();
    }

    public void saveQuote() {
        driver.findElement(saveEmailBtn).click();
        waitLoader();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(saveTip));
    }

    public void viewDetails() {
        driver.findElement(viewDetailsBtn).click();
        waitLoader();
        Assert.assertEquals(driver.findElement(amountDetails).getText(), amount);
    }

    public void verifyDetailsPage() {
        waitClickable(rateTip);
        Assert.assertTrue(driver.findElement(originCheck).isDisplayed());
        Assert.assertTrue(driver.findElement(freightCheck).isDisplayed());
        Assert.assertTrue(driver.findElement(additionalCheck).isDisplayed());
        Assert.assertTrue(driver.findElement(destinationCheck).isDisplayed());
    }

    public void bookNow() {
        driver.findElement(bookNowBtn).click();
        waitLoader();
    }

    public void waitLoader() {
            wait.until(ExpectedConditions.visibilityOfElementLocated(loader));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
    }

    public WebElement waitClickable(By element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        return driver.findElement(element);
    }

    public Boolean waitPresent(By element) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(element));
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
