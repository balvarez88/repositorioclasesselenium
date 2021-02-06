import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class dataProviderTest {

    private static final String BOOKING_URL = "https://www.booking.com/";

    private WebDriver getDriver(String url) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\balvarez\\Documents\\brisly-study\\repositorioclasesselenium\\.idea\\Drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
        return driver;
    }

    @DataProvider
    public Object[] dataProviderEmails() throws InterruptedException {
        return new Object[]{"email1.brisly","email2@brian","email3.@arian"};
    }

    @Test(dataProvider = "dataProviderEmails")
    public void loginUsuarioIncorrectoTestWithDataProvider(String email) throws InterruptedException {
        WebDriver driver = getDriver(BOOKING_URL);

        driver.findElement(By.xpath("//span[contains(text(),'Inicia sesión')]")).click();
        driver.findElement(By.cssSelector("input[name='username']")).sendKeys(email);
        driver.findElement(By.xpath("//span[contains(text(),'Continuar con e-mail')]")).click();

        WebElement element = driver.findElement(By.cssSelector("div[id='username-description']"));
        Assert.assertTrue(element.getText().equals("Comprueba si el e-mail que has introducido es correcto"));
        driver.quit();
    }
}
