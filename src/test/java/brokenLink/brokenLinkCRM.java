package brokenLink;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.edge.EdgeDriver;
//import org.testng.annotations.AfterTest;
//import org.testng.annotations.BeforeTest;

import io.github.bonigarcia.wdm.WebDriverManager;

public class brokenLinkCRM {

	public static WebDriver driver;

	public static void main (String[] arg) {

		String myhomePage = "https://vnshealth-crm--fullsbx.sandbox.my.site.com/provider/login";

		String myurl = "";
		HttpURLConnection myhuc = null;
		int responseCode = 200;

		JavascriptExecutor js = (JavascriptExecutor) driver;

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.get(myhomePage);

		//username
		WebElement uname = driver.findElement(By.xpath("//input[@id='input-25']"));
		uname.sendKeys("testuser3@vns-fullsbx.com");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//password
		WebElement pwd = driver.findElement(By.xpath("//input[@id='input-26']"));
		pwd.sendKeys("QATester123");

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		//login
		WebElement signIn = driver.findElement(By.xpath("//button[contains(text(),'Log In')]"));
		signIn.click();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


		List<WebElement> mylinks = driver.findElements(By.xpath("//a"));
		
		Iterator<WebElement> myit = mylinks.iterator();
		while (myit.hasNext()) {

			myurl = myit.next().getAttribute("href");
			System.out.println("The link is :"+myurl);
			System.out.println(myurl);
			if (myurl == null || myurl.isEmpty()) {
				System.out.println("Empty URL or an Unconfigured URL");
				continue;
			}

			if (!myurl.contains("https://vnshealth")) {
				System.out.println("This URL is from another domain");
				continue;
			}

			try {
				myhuc = (HttpURLConnection) (new URL(myurl).openConnection());
				myhuc.setRequestMethod("HEAD");
				myhuc.connect();
				responseCode = myhuc.getResponseCode();
				System.out.println("The response code is:"+responseCode);
				if (responseCode >= 400) {
					System.out.println(myurl + " This link is broken");
				} else {
					System.out.println(myurl + " This link is valid");
				}

			} catch (MalformedURLException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			}


		}

		driver.close();
	}


}