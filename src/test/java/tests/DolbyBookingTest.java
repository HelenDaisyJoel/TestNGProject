package tests;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import pageObjects.VideoPlatformDolbyBookingPage;
import testBase.BaseClass;

public class DolbyBookingTest{

    public static WebDriver driver;
    protected VideoPlatformDolbyBookingPage page;
    ExtentReports extent;
    ExtentTest test;
    public static String updatedEncoder;
    public static String encoderName; // Ensure this is an instance variable
    String updatedSource;
    String bookingSourceName;
    String bookingChannelName;
    
    String userName = "admin@1st.com";
    String password = "admin";
    String orgName = "Test_Org";
    String unitName = "Unit-01";
    String randomDisplayName = UUID.randomUUID().toString();
    
    String VendorName = "Dolby";
    
//    @Before
    public void setUp() throws InterruptedException, IOException {
        // Initialize WebDriver
    	
    	Properties p=new Properties();
    	
    	// Retrieve the values for 'br' and 'os'
        String browser = p.getProperty("br");
        String os = p.getProperty("os");
        BaseClass baseClass = new BaseClass();
		baseClass.setup(browser, os);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://prism-web.dev.1stbet.com/");
        driver.manage().window().maximize();
        
        // Initialize Page Object
        page = new VideoPlatformDolbyBookingPage(driver);
    }

    @Test(priority = 1)
    public void testEncoder(String encoderName, String encoderID) throws InterruptedException {
        page.Select_Encoder();
        page.createEncoder(encoderName, encoderID);
        Assert.assertEquals(page.getMessage(), "Video Encoder Successfully Added");

        page.verifyToolTip("Add Video Encoder", VideoPlatformDolbyBookingPage.TOOLTIP_ADD_ENCODER);
        page.verifyToolTip("Export", VideoPlatformDolbyBookingPage.TOOLTIP_EXPORT);
        page.verifyToolTip("Column Settings", VideoPlatformDolbyBookingPage.TOOLTIP_COLUMN_SETTINGS);

        page.displayInactive();
        String EncoderSearchKeyword = encoderName;
        page.search(EncoderSearchKeyword);
        updatedEncoder = "Update" + encoderName;
        this.encoderName = encoderName; // Set the instance variable
        String updatedID = "UpdatedID" + encoderName;

        page.updateEncoder(updatedEncoder, updatedID);
        Assert.assertEquals(page.getMessage(), "Video Encoder Successfully Updated");
    }

    @Test(priority = 2)
    public void testDecoder(String DolbyDisplayName, String DecoderID) throws InterruptedException {
        page.Select_Decoder();
        page.verifyToolTip("Add Video Decoder", VideoPlatformDolbyBookingPage.TOOLTIP_ADD_DECODER);
        page.verifyToolTip("Export", VideoPlatformDolbyBookingPage.TOOLTIP_EXPORT);
        page.verifyToolTip("Column Settings", VideoPlatformDolbyBookingPage.TOOLTIP_COLUMN_SETTINGS);

        page.addDolbyDecoder(DolbyDisplayName, DecoderID);
        Assert.assertEquals(page.getMessage(), "Video Decoder Successfully Added");

        page.displayInactive();
        String decoderSearchKeyword = DolbyDisplayName;
        page.search(decoderSearchKeyword);

        page.disableEntity();
        Assert.assertEquals(page.getMessage(), "Video Decoder Successfully Disabled");
        String updatedName = "Update" + DolbyDisplayName;
        String updatedID = "Update" + DolbyDisplayName;
        page.updateDecoder(updatedName, updatedID);
        Assert.assertEquals(page.getMessage(), "Video Decoder Successfully Updated");

        page.deleteEntity();
        Assert.assertEquals(page.getMessage(), "Video Decoder Successfully Deleted");
    }

    @Test(priority = 3)
    public void testSource(String sourceName, String sourceAngle, String ForeignIDType, String ForeignIDValue, String AutomatedStartMargin, String AutomatedEndMargin, boolean automateBooking) throws InterruptedException {
        page.Select_Source();
        page.createSource(sourceName, sourceAngle, updatedEncoder);
        Assert.assertEquals(page.getMessage(), "Video Source Successfully Added");
        
        page.verifyToolTip("Add Video Source", VideoPlatformDolbyBookingPage.TOOLTIP_ADD_SOURCE);
        page.verifyToolTip("Export", VideoPlatformDolbyBookingPage.TOOLTIP_EXPORT);
        page.verifyToolTip("Column Settings", VideoPlatformDolbyBookingPage.TOOLTIP_COLUMN_SETTINGS);
        page.displayInactive();
        String sourceSearchKeyword = sourceName;
        page.search(sourceSearchKeyword);
        updatedSource = "UpdatedSourceTest-" + randomDisplayName.substring(0, 4);
        page.updateSource(updatedSource);
        Assert.assertEquals(page.getMessage(), "Video Source Successfully Updated");
        bookingSourceName = updatedSource + "_" + sourceAngle;
        System.out.println("Combined Source Name: " + bookingSourceName);
    }

    @Test(priority = 4)
    public void testChannel(String channelName, String autoAngle, int connectorID, String clusterName, String height, String frameRate, String description, String foreignIDType, String foreignIDValue, String createReplay) throws InterruptedException {
        page.Select_Channel();
        page.CreateDolby_Channel(channelName, VendorName, autoAngle, connectorID, clusterName, description);
        Assert.assertEquals(page.getMessage(), "Video Channel Successfully Added");

        page.verifyToolTip("Add Video Channel", VideoPlatformDolbyBookingPage.TOOLTIP_ADD_CHANNEL);
        page.verifyToolTip("Export", VideoPlatformDolbyBookingPage.TOOLTIP_EXPORT);
        page.verifyToolTip("Column Settings", VideoPlatformDolbyBookingPage.TOOLTIP_COLUMN_SETTINGS);
        String ChannelSearchKeyword = channelName;
        page.displayInactive();
        page.search(ChannelSearchKeyword);

//        page.disableEntity();
//        Assert.assertEquals(page.getMessage(), "Video Channel Successfully Disabled");

        page.updateDolbyChannel();
        Assert.assertEquals(page.getMessage(), "Video Channel Successfully Updated");

        bookingChannelName = channelName + " (Dolby)";
        System.out.println("Combined Channel Name: " + bookingChannelName);
    }

    @Test(priority = 5)
    public void testBooking( String bookingSourceName, String bookingChannelName, String ForeignIDType, String ForeignIDValue) throws InterruptedException {
        page.Select_Booking();
        System.out.println("Source name is" + bookingSourceName );
        System.out.println("Channel name is" + bookingChannelName);
      
        page.createBooking(bookingSourceName, bookingChannelName);
        Assert.assertEquals(page.getMessage(), "Video Booking Successfully Added");

        page.disableEntity();
        Assert.assertEquals(page.getMessage(), "Video Booking Successfully Disabled");

        page.deleteEntity();
        Assert.assertEquals(page.getMessage(), "Video Booking Successfully Deleted");
    }
   

    @AfterClass
    public void tearDown() throws InterruptedException {
        // Close the browser and end the session
        driver.quit();

        // Write all test information to the report
//        extent.flush();
    }

	public WebDriver getDriver() {
		// TODO Auto-generated method stub
		return driver;
	}
}
