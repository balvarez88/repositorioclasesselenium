import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class prueba_netflix {

    private static final String NETFLIX_URL = "https://www.netflix.com/";
    private static final String ORIGINAL_TITLE = "Netflix Uruguay: Ve series online, ve películas online";

    private WebDriver getDriver(String url) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\balvarez\\Documents\\brisly-study\\repositorioclasesselenium\\.idea\\Drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return driver;
    }

    @Test
    public void validarTituloTest() throws InterruptedException {
        WebDriver driver = getDriver(NETFLIX_URL);
        String ActualTitle = driver.getTitle();
        Assert.assertEquals(ActualTitle, ORIGINAL_TITLE, "Los títulos son diferentes");
        System.out.println("El título actual y el esperado coinciden. OK");
        driver.quit();
    }

    @Test
    public void iniciarSesionPageTest() throws InterruptedException {
        WebDriver driver = getDriver(NETFLIX_URL);

        driver.findElement(By.xpath("//a[contains(text(),'Iniciar sesión')]")).click();

        String ActualTitle = driver.getTitle();
        Assert.assertFalse(ActualTitle.equals(ORIGINAL_TITLE), "El título no cambió");
        System.out.println("El título de la página cambió correctamente");

        WebElement element1 = driver.findElement(By.xpath("//h1[contains(text(),'Inicia sesión')]"));
        System.out.println("\"Inicia sesión\" se encuentra dentro de la etiqueta <h1></h1>");

        WebElement element2 = driver.findElement(By.xpath("//span[contains(text(),'Iniciar sesión con Facebook')]"));
        System.out.println("El texto \"Iniciar sesión con Facebook\" se encuentra dentro del sitio");

        driver.quit();
    }

    @Test
    public void loginToNetflixErrorTest() throws InterruptedException {
        WebDriver driver = getDriver(NETFLIX_URL);

        driver.findElement(By.xpath("//a[contains(text(),'Iniciar sesión')]")).click();

        driver.findElement(By.cssSelector("input[name='userLoginId']")).sendKeys("XXX");
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys("holamundo");
        driver.findElement(By.xpath("//span[contains(text(),'Recuérdame')]")).click();

        driver.findElement(By.xpath("//button[contains(text(),'Iniciar sesión')]")).click();

        WebElement element = driver.findElement(By.xpath("//div[contains(text(),'Escribe un email válido.')]"));
        Assert.assertTrue(element.getText().equals("Escribe un email válido."));

        WebElement element1 = driver.findElement(By.xpath("//span[contains(text(),'Recuérdame')]"));
        //WebElement element1 = driver.findElement(By.xpath("//span[@class='login-remember-me-label-text']"));
        System.out.println(element1.isSelected());

        driver.quit();
    }

    @Test
    public void fakeEmailTest() throws InterruptedException {
        WebDriver driver = getDriver(NETFLIX_URL);

        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-GB"), new RandomService());
        String email = fakeValuesService.bothify("????##@gmail.com");
        driver.findElement(By.cssSelector("input[name='email']")).sendKeys(email);

        driver.findElement(By.xpath("//span[contains(text(),'COMIENZA YA')]")).click();
        Thread.sleep(2000);

        //https://www.netflix.com/signup/registration?locale=es-UY
        String actualURL = driver.getCurrentUrl();
        Assert.assertTrue(actualURL.contains("signup"), "La palabra \"signup\" no está contenida en la URL");

        driver.quit();
    }

    @DataProvider
    public Object[] dataProviderEmails() throws InterruptedException {
        return new Object[]{"brisly20@gmail.com", "arianmtzcu@gmail.com", "briandi0817@gmail.com"};
    }

    @Test(dataProvider = "dataProviderEmails")
    public void dataProviderEmailTest(String email) throws InterruptedException {
        WebDriver driver = getDriver(NETFLIX_URL);

        driver.findElement(By.cssSelector("input[name='email']")).sendKeys(email);
        driver.findElement(By.xpath("//span[contains(text(),'COMIENZA YA')]")).click();
        Thread.sleep(2000);

        driver.quit();
    }

    @Test
    public void printTagsTest(@Optional("h1") String htmlTag) throws InterruptedException {
        WebDriver driver = getDriver(NETFLIX_URL);

        List<WebElement> allHtmlTag = driver.findElements(By.tagName(htmlTag));
        if (allHtmlTag.size() > 0) {
            for (WebElement tag : allHtmlTag) {
                System.out.println(htmlTag + ": " + tag.getText());
            }
        } else {
            System.out.println("No se encuentran elementos con esta etiqueta.");
        }
        driver.quit();
    }

}
