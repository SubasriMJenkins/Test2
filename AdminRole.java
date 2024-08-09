package org.karkakasadara.tests.reportcard.reportcardview;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class AdminRole {
	static WebDriver driver;
	WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(10));

	@Test(priority=1)
	public void setUpDriver() {
		
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\Subasri\\Downloads\\geckodriver\\geckodriver.exe");
		driver= new FirefoxDriver();
		driver.navigate().to("https://uat.karkakasadara.in/");
		wait= new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.manage().window().maximize();
		
	}
	
	@Test(priority=2)
	public void loginAsAdmin() throws InterruptedException{
		
		WebElement email=wait.until(ExpectedConditions.elementToBeClickable(By.id("email")));
		email.click();
		
		email.sendKeys("demoadmin@velammal.edu.in");
		
		WebElement password= wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='password']")));
		password.click();
		
		password.sendKeys("adminuat");
		
		WebElement signIn=driver.findElement(By.xpath("//body/app-root[1]/layout[1]/empty-layout[1]/div[1]/div[1]/auth-sign-in[1]/div[1]/div[1]/div[1]/form[1]/button[1]"));
		signIn.click();
		
		Thread.sleep(1000);
		
		Reporter.log("Login Successful");
		
	}
	
	@Test(priority=3)
	public void checkReportCardModuleIsAvailable() {
		
		String pageSource= driver.getPageSource();
		String header="Your Approvals Awaited";
		
		if(!(pageSource.equals(header))) {
			
			driver.findElement(By.xpath("//span[contains(text(),'Report Card')]")).click();
		}
		else {
			System.out.println("Module not found");
		}	
		
	}

	@Test(priority=4)
	public void checkReportCardViewSubModueIsAvailable() {
		
		driver.findElement(By.xpath("//span[contains(text(),'Report Card View')]")).click();
	}
	@Test(priority=5)
	public void checkReportCardViewScreen() {
		
		WebElement actualHeadingPath= wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[contains(text(),'Report Card')]")));
		String actualHeadingText= actualHeadingPath.getText();
		System.out.println("Actual text is: " +actualHeadingText);
		String expectedHeading="Report Card";
		Assert.assertEquals(actualHeadingText, expectedHeading);

	}
	
	@Test(priority=6)
	public void fieldsToBeselected(){
		
		//Syllabus
		WebElement syllabus=driver.findElement(By.xpath("//span[contains(text(),'Select Syllabus')]"));
		syllabus.click();
		
		driver.findElement(By.xpath("//span[contains(text(),'NewGen')]")).click();
		
		//School
		WebElement school=driver.findElement(By.xpath("//span[contains(text(),'Select School')]"));
		school.click();
		
		driver.findElement(By.xpath("//span[contains(text(),'Velammal NewGen Medavakkam')]")).click();
		
		//Grade
		WebElement grade=driver.findElement(By.xpath("//span[contains(text(),'Select Grade')]"));
		grade.click();
		
		driver.findElement(By.xpath("//mat-option[@id='mat-option-34']")).click();
		
		//Section
		WebElement section=driver.findElement(By.xpath("//span[contains(text(),'Select Section')]"));
		section.click();
		
		driver.findElement(By.xpath("//mat-option[@id='mat-option-42']")).click();
	}
	
	@Test(priority=7)
	public void clickViewReportcardButton() throws IOException {
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(),'G6a')]")));
		Reporter.log("Student found and has view report card option");
		
		WebElement grid=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[contains(@class,'mat-table cdk-table mat-elevation-z8 p-6 ng-star-inserted')])")));
		List<WebElement> viewReportCardButton= driver.findElements(By.xpath("//span[contains(text(),'View Report Card')]"));
		
		if(grid.isDisplayed()) {
			for(int button=0;button<viewReportCardButton.size();button++) {
			
				driver.findElement(By.xpath("(//span[contains(text(),'View Report Card')])["+ (button+1) +"]"));
				
				TakesScreenshot screenShot =((TakesScreenshot)driver);
				//Call getScreenshotAs method to create image file
				File sourceFile=screenShot.getScreenshotAs(OutputType.FILE);
				//Move image file to new destination
				File destinationFile=new File("D://Screenshot/Admin/Reportcard.png");
				//Copy file at destination
				FileUtils.copyFile(sourceFile, destinationFile);
				
			}
		}
	}
}
