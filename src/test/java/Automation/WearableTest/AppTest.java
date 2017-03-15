package Automation.WearableTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void testApp() throws IOException, InterruptedException {
		String line;
		// Below path should change according the adb directory
		Process p = Runtime.getRuntime().exec(
				"/Users/naseralmasri/Library/Android/sdk/platform-tools/adb forward tcp:4445 localabstract:/adb-hub");
		Thread.sleep(2000);
		Process pp = Runtime.getRuntime()
				.exec("/Users/naseralmasri/Library/Android/sdk/platform-tools/adb connect localhost:4445");
		Thread.sleep(2000);

		Process ppp = Runtime.getRuntime().exec("/Users/naseralmasri/Library/Android/sdk/platform-tools/adb devices");

		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		while ((line = in.readLine()) != null) {
			System.out.println(line);
		}
		in.close();

		BufferedReader in2 = new BufferedReader(new InputStreamReader(pp.getInputStream()));
		while ((line = in2.readLine()) != null) {
			System.out.println(line);
		}
		in2.close();

		BufferedReader in3 = new BufferedReader(new InputStreamReader(ppp.getInputStream()));
		while ((line = in3.readLine()) != null) {
			System.out.println(line);
		}
		in3.close();
		Runtime.getRuntime().exec("killall node");
		AppiumDriverLocalService Service;
		Service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder().withIPAddress("127.0.0.1")
				.usingPort(5555).withArgument(GeneralServerFlag.LOG_LEVEL, "error")
				.withArgument(GeneralServerFlag.SESSION_OVERRIDE));
		Service.start();

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("appium-version", "1.0");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("platformVersion", "6.0.1");
		capabilities.setCapability("deviceName", "localhost:4445");
		capabilities.setCapability("app",
				"/Users/naseralmasri/Documents/workspace/WearableTest/src/App/NasserWear.apk");
		AppiumDriver MDriver = (AppiumDriver) new AndroidDriver(new URL("http://127.0.0.1:5555/wd/hub"), capabilities);
		((AndroidDriver) MDriver).pressKeyCode(AndroidKeyCode.KEYCODE_WAKEUP);
		WebDriverWait wait = new WebDriverWait(MDriver, 43);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.Button")));
		((AndroidDriver) MDriver).pressKeyCode(AndroidKeyCode.KEYCODE_WAKEUP);
		MDriver.findElement(By.xpath("//android.widget.Button")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//android.widget.TextView[contains(@text,'Clicked!')]")));
		MDriver.quit();
		assertTrue(true);
	}
}
