package stepDefinition;

import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pageObjects.VideoPlatformDolbyBookingPage;


import tests.DolbyBookingTest;

public class stepDefinition {

    private DolbyBookingTest dolbyBookingTest;
    private VideoPlatformDolbyBookingPage page;
    private String updatedEncoder;
    private WebDriver driver;
    
    

    public stepDefinition() {
        dolbyBookingTest = new DolbyBookingTest();
        try {
            dolbyBookingTest.setUp(); // Ensure WebDriver is initialized
            this.driver = new DolbyBookingTest().getDriver();// Get the initialized WebDriver
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("Landed in 1ST Application")
    public void landed_in_1st_application() throws InterruptedException {
        page = new VideoPlatformDolbyBookingPage(driver);
        page.login("admin@1st.com", "admin");
        page.navigateToVideoPlatform("Test_Org", "Unit-01");
    }

    @Given("Create Encoder with DisplayName {string} and ID {string}")
    public void create_encoder_with_display_name_and_id(String displayName, String encoderID) throws InterruptedException {
        dolbyBookingTest.testEncoder(displayName, encoderID);
        System.out.println("Encoder Name: " + DolbyBookingTest.encoderName);
        updatedEncoder = "Update" + DolbyBookingTest.encoderName;
        System.out.println("Updated Encoder: " + updatedEncoder);
    }

    @Given("Create Decoder with DisplayName {string} and ID {string}")
    public void create_decoder_with_display_name_and_id(String displayName, String decoderID) throws InterruptedException {
        dolbyBookingTest.testDecoder(displayName, decoderID);
    }

    @Given("Create Source with {string} {string} {string} {string} {string} {string} {string}")
    public void create_source_with(String sourceName, String sourceAngle, String foreignIDType, String foreignIDValue, String automatedStartMargin, String automatedEndMargin, String automateBookingString) throws InterruptedException {
        boolean automateBooking = Boolean.parseBoolean(automateBookingString);
        dolbyBookingTest.testSource(sourceName, sourceAngle, foreignIDType, foreignIDValue, automatedStartMargin, automatedEndMargin, automateBooking);
        System.out.println("Encoder Name: " + DolbyBookingTest.encoderName);
        updatedEncoder = "Update" + DolbyBookingTest.encoderName;
        System.out.println("Updated Encoder: " + updatedEncoder);
    }

    @Given("Create Channel with {string} {string} {string} {string} {string} {string} {string} {string} {string} {string}")
    public void create_channel_with(String displayName, String autoAngle, String connectorID, String clusterName, String height, String frameRate, String description, String foreignIDType, String foreignIDValue, String createReplay) throws InterruptedException {
        int connectorIDInt = Integer.parseInt(connectorID);
        dolbyBookingTest.testChannel(displayName, autoAngle, connectorIDInt, clusterName, height, frameRate, description, foreignIDType, foreignIDValue, createReplay);
    }

    @Given("Create Booking with {string} {string} {string} {string}")
    public void create_booking_with(String startTime, String endTime, String foreignIDType, String foreignIDValue) throws InterruptedException {
        dolbyBookingTest.testBooking(startTime, endTime, foreignIDType, foreignIDValue);
    }

    @Then("close the application")
    public void close_the_application() throws InterruptedException {
        dolbyBookingTest.tearDown();
    }
}
