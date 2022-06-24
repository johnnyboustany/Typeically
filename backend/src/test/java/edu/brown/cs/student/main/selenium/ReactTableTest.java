package edu.brown.cs.student.main.selenium;

import edu.brown.cs.student.main.Main;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import spark.Spark;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Tests that dynamic changes work as expected on the frontend.
 * Make sure you do the following before running these tests (using mvn package):
 * - cd backend
 * - cd frontend
 * - cd table
 * - cd react-table
 * - npm start
 */

public class ReactTableTest {
  WebDriver driver;
  /**
   * Setup chrome driver.
   */

  @Before
  public void setupClass() throws InterruptedException {
    WebDriverManager.chromedriver().setup();
    //ChromeOptions object
    ChromeOptions options = new ChromeOptions();
    //set path of .crx file of extension
    options.addExtensions(new File("config/CORS Unblock 0.3.4.0.crx"));
    options.setCapability("capability_name", "capability_value");

    driver = new ChromeDriver(options);
    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
    Main.runSparkServer(4567);
    Thread.sleep(2000);
  }

  /**
   * Make sure you activate the extension in the browser.
   * Refer to README.
   */
  @Test
  public void searchForSong() throws InterruptedException {
    driver.get("http://www.localhost:3000/");

    Thread.sleep(10000);
    WebElement censorToggle = driver.findElement(By.id("censorToggle"));
    WebElement shortToggle = driver.findElement(By.id("shortToggle"));
    censorToggle.click();
    shortToggle.click();
    WebElement textbox = driver.findElement(By.id("search"));
    textbox.sendKeys("very short song");
    WebElement searchButton = driver.findElement(By.id("searchButton"));
    searchButton.click();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    WebElement resultsButton = driver.findElement(By.id("0"));
    List<WebElement> results = new ArrayList<>();
    for (int i = 0; i < 10; i++){
      results.add(driver.findElement(By.id(Integer.toString(i))));
    }
    assertEquals(results.size(),10);
    resultsButton.click();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
    WebElement lyrics = driver.findElement(By.id("lyrics"));
    assertNotNull(lyrics);
    WebElement title = driver.findElement(By.id("titleOfSong"));
    String titleOfSong = title.getAttribute("innerHTML");
    String[] arr = titleOfSong.split(" ");
    assertEquals(arr[0],"A");
    assertEquals(arr[1],"Very");
    assertEquals(arr[2],"Short");
    assertEquals(arr[3],"Song");
    Thread.sleep(10000);
  }

  /**
   * Make sure you activate the extension in the browser.
   * Refer to README.
   */
  @Test
  public void chooseTopSong() throws InterruptedException {
    driver.get("http://www.localhost:3000/");
    Thread.sleep(10000);
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
    List<WebElement> newSongs = driver.findElements(By.id("newSongs"));
    newSongs.get(0).click();
    String songTitleSelected =  newSongs.get(0).getAttribute("innerHTML");
    String[] selectedTitleArr = songTitleSelected.split(" ");

    assertFalse(newSongs.isEmpty());
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    WebElement lyrics = driver.findElement(By.id("lyrics"));
    assertNotNull(lyrics);
    WebElement title = driver.findElement(By.id("titleOfSong"));

    String titleOfSong = title.getAttribute("innerHTML");
    String[] shownTitleArr = titleOfSong.split(" ");
    assertEquals(shownTitleArr[0],selectedTitleArr[1]);
    assertEquals(shownTitleArr[1],selectedTitleArr[2]);
    Thread.sleep(10000);
  }

  /**
   * Remove references created by a test, reset to old state, etc., connection is terminated so website
   * is no longer interactive.
   **/
  @After
  public void tearDown() {
    Spark.stop();
    driver.quit();
  }
}
