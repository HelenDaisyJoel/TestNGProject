package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import io.cucumber.java.Before;
import pageObjects.VideoPlatformDolbyBookingPage;

public class BaseClass {
	
	public static WebDriver driver;
	public Logger logger;
	public static Properties p;
	VideoPlatformDolbyBookingPage page;
//	@BeforeClass(groups= {"Sanity","Regression","Master"})
//	@Before(@)
	@Before
	@Parameters({"os", "browser"})
	public void setup(String os, String chrome) throws IOException
	{
//		FileReader=file=new FileReader(./src//test//resources//config.properties);
		
		
//		C:\SampleWorkspace\1st-prism-web-integration-test\TestNGProject\src\main\resources\globalData.properties
//		FileReader file=new FileReader(("user.dir")+"//src//test//resources//config.properties";
		FileReader file=new FileReader("./src//main//resources//globalData.properties");
		p= new Properties();
		p.load(file);
		
		try {
			logger= LogManager.getLogger(this.getClass());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		if(p.getProperty("execution_env").equalsIgnoreCase("remote")) {
//			DesiredCapabilities capabilities=new DesiredCapabilities();
//			//os
//			if(os.equalsIgnoreCase("windows"))
//			{
//				capabilities.setPlatform(Platform.WIN11);
//			}
//			else if (os.equalsIgnoreCase("mac"))
//			{
//				capabilities.setPlatform(Platform.MAC);
//			}
//			else
//			{
//				System.out.println("No matching os");
//				return;
//			}
//		}
//		switch(br.toLowerCase())
//		{
//		case "chrome": driver=new ChromeDriver(); break;
//		case "edge": driver=new EdgeDriver(); break;
//		case "firefox": driver=new FirefoxDriver(); break;
//		default: System.out.println("Invalid Browser Name");return;		
//		}
		
		String br = "chrome";
		if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities capabilities=new DesiredCapabilities();
			
			//os
			if(os.equalsIgnoreCase("windows"))
			{
				capabilities.setPlatform(Platform.WIN11);
			}
			else if (os.equalsIgnoreCase("mac"))
			{
				capabilities.setPlatform(Platform.MAC);
			}
			else
			{
				System.out.println("No matching os");
				return;
			}
			
			//browser
			switch(br.toLowerCase())
			{
			case "chrome": capabilities.setBrowserName("chrome"); break;
			case "edge": capabilities.setBrowserName("MicrosoftEdge"); break;
			default: System.out.println("No matching browser"); return;
			}
			
			driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
		}
		
				
		if(p.getProperty("execution_env").equalsIgnoreCase("local"))
		{

			switch(br.toLowerCase())
			{
			case "chrome" : driver=new ChromeDriver(); break;
			case "edge" : driver=new EdgeDriver(); break;
			case "firefox": driver=new FirefoxDriver(); break;
			default : System.out.println("Invalid browser name.."); return;
			}
		}
		
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(p.getProperty("appURL"));
		driver.manage().window().maximize();
		
		// Initialize the page object
	    page = new VideoPlatformDolbyBookingPage(driver);
	}
	
	@AfterClass(groups= {"Sanity","Regression","Master"})
	public void teardown() {
		 if (driver != null) {
	            driver.quit();
	        }
	}

	
	public String RandomString() {
		String generatedString= RandomStringUtils.randomAlphabetic(5);
		return generatedString;
		
	}
	
	public String RandomNumber() {
		String generatedNumber= RandomStringUtils.randomNumeric(10);
		return generatedNumber;
		
	}
	public String RandomAlphaNumeric() {
		String str=RandomStringUtils.randomAlphabetic(3);
		String num=RandomStringUtils.randomNumeric(3);
		
		return (str+"@gmail.com");
		
	}
	
	public String captureScreen(String tname) throws IOException {

	    String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

	    TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
	    File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

	    String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";
	    File targetFile = new File(targetFilePath);

	    // Rename the source file to the target file path
	    sourceFile.renameTo(targetFile);

	    // Maintain only 10 screenshots in the folder
	    File screenshotsFolder = new File(System.getProperty("user.dir") + "\\screenshots\\");
	    File[] files = screenshotsFolder.listFiles();

	    if (files != null && files.length > 10) {
	        // Sort the files by last modified date in ascending order
	        Arrays.sort(files, Comparator.comparingLong(File::lastModified));

	        // Delete the oldest files until only 10 remain
	        for (int i = 0; i < files.length - 10; i++) {
	            files[i].delete();
	        }
	    }

	    return targetFilePath;
	}
}
