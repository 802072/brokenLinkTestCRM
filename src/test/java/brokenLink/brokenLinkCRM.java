package brokenLink;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class brokenLinkCRM {

	public static WebDriver driver;

	String myhomePage = "https://vnshealth-crm--fullsbx.sandbox.my.site.com/provider/login";

	String myurl = "";
	HttpURLConnection myhuc = null;
	int responseCode = 200;
	
	String excelPath = "C:\\Users\\802072\\git\\brokenLinkTestCRM\\resources\\testData\\testData.xlsx";  
	String sheetName = "loginInfo";
	String username = "";
	String password = "";
	
	@BeforeTest
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.get(myhomePage);

		//Read Excel file for username and password
		try {
			FileInputStream fileInputStream = new FileInputStream(new File(excelPath));
			Workbook workbook = new XSSFWorkbook(fileInputStream);
			Sheet sheet = workbook.getSheet(sheetName);

			Row row = sheet.getRow(1);
			org.apache.poi.ss.usermodel.Cell usernameCell = row.getCell(0);
			org.apache.poi.ss.usermodel.Cell passwordCell = row.getCell(1);

			username = usernameCell.getStringCellValue();
			password = passwordCell.getStringCellValue();
			System.out.println("The logged in user is:"+ username);
			
			workbook.close();
			fileInputStream.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test (description= "log into the homepage")
	public void login() {

		//enter username
		WebElement uname = driver.findElement(By.xpath("//input[@id='input-25']"));
		uname.sendKeys(username);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//enter password
		WebElement pwd = driver.findElement(By.xpath("//input[@id='input-26']"));
		pwd.sendKeys(password);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		//login
		WebElement signIn = driver.findElement(By.xpath("//button[contains(text(),'Log In')]"));
		signIn.click();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test (description= "Check for broken links")
	public void testBrokenLinks() {
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
	}

	@AfterTest
	public void tearUp() {
		driver.close();
	}


}


