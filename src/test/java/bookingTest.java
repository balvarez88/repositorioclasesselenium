import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class bookingTest {

    private static final String BOOKING_URL = "https://www.booking.com/";

    private WebDriver getDriver(String url) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\balvarez\\Documents\\brisly-study\\repositorioclasesselenium\\.idea\\Drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
        return driver;
    }

    @Test
    public void validarTituloTest() throws InterruptedException {
        WebDriver driver = getDriver(BOOKING_URL);
        String ActualTitle = driver.getTitle();
        String ExpectedTitle = "Booking.com | Web oficial | Los mejores hoteles y alojamientos";
        Assert.assertEquals(ActualTitle, ExpectedTitle, "Los títulos diferentes");
        System.out.println("Títulos OK");
        driver.quit();
    }

    @Test
    public void mostrarLinksTest() throws InterruptedException {
        WebDriver driver = getDriver(BOOKING_URL);

        List<WebElement> allLinks = driver.findElements(By.tagName("a"));
        for (WebElement link : allLinks) {
            String textLink = link.getText();
            if (!textLink.trim().isEmpty()) {
                System.out.println("Link: " + textLink);
            }
        }
        //Ir a un Link especifico dentro de la web
        /*driver.findElement(By.linkText("Cantabria")).click();
        System.out.println("Title of page is: " + driver.getTitle());*/
        driver.quit();
    }

    @Test
    public void mostrarH1sTest() throws InterruptedException {
        WebDriver driver = getDriver(BOOKING_URL);

        List<WebElement> allH1 = driver.findElements(By.tagName("h2"));
        for (WebElement h1 : allH1) {
            System.out.println("H1s: " + h1.getText());
        }
        driver.quit();
    }

    @Test
    public void buscarGenteViajeraTest() throws InterruptedException {
        WebDriver driver = getDriver(BOOKING_URL);

        WebElement element = driver.findElement(By.xpath("//h2[contains(text(),'Conecta con gente viajera')]"));
        Assert.assertTrue(element.getText().equals("Conecta con gente viajera"));
        System.out.println("Los textos son iguales!");
        driver.quit();
    }

    @Test
    public void loginUsuarioIncorrectoTest() throws InterruptedException {
        WebDriver driver = getDriver(BOOKING_URL);

        driver.findElement(By.xpath("//span[contains(text(),'Inicia sesión')]")).click();

        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-GB"), new RandomService());
        //driver.findElement(By.cssSelector("input[name='username']")).sendKeys("brisly02@gmail.com");
        String email = fakeValuesService.bothify("?????###yahoo.es");
        driver.findElement(By.cssSelector("input[name='username']")).sendKeys(email);
        driver.findElement(By.xpath("//span[contains(text(),'Continuar con e-mail')]")).click();

        WebElement element = driver.findElement(By.cssSelector("div[id='username-description']"));
        Assert.assertTrue(element.getText().equals("Comprueba si el e-mail que has introducido es correcto"));
        driver.quit();
    }

    @Test
    public void registroUsuarioTest() throws InterruptedException {
        WebDriver driver = getDriver(BOOKING_URL);

        driver.findElement(By.xpath("//span[contains(text(),'Hazte una cuenta')]")).click();

        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-GB"), new RandomService());
        String email = fakeValuesService.bothify("?????###@yahoo.es");
        driver.findElement(By.cssSelector("input[name='username']")).sendKeys(email);
        driver.findElement(By.xpath("//span[contains(text(),'Continuar con e-mail')]")).click();

        driver.findElement(By.cssSelector("input[name='new_password']")).sendKeys("012345Abcd.");
        driver.findElement(By.cssSelector("input[name='confirmed_password']")).sendKeys("12345ABCD.");
        driver.findElement(By.xpath("//span[contains(text(),'Crear una cuenta')]")).click();

        WebElement element = driver.findElement(By.cssSelector("div[id='confirmed_password-description']"));
        Assert.assertTrue(element.getText().equals("Las contraseñas no coinciden. Inténtalo de nuevo."));
        driver.quit();
    }

    @Test
    public void crearCuentaTest() throws InterruptedException {
        WebDriver driver = getDriver(BOOKING_URL);

        driver.findElement(By.xpath("//span[contains(text(),'Hazte una cuenta')]")).click();

        driver.findElement(By.cssSelector("input[name='username']")).sendKeys("brisly02@gmail.com");
        driver.findElement(By.xpath("//span[contains(text(),'Continuar con e-mail')]")).click();

        WebElement element = driver.findElement(By.cssSelector("p[class='nw-step-description']"));
        Assert.assertTrue(element.getText().equals("Introduce tu contraseña de Booking.com para brisly02@gmail.com."));
        driver.quit();
    }

}
