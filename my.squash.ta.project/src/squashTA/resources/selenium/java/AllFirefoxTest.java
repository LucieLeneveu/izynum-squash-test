//package example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AllFirefoxTest {

	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();
	private final static String[] fields = { "MDPH", "PES" };
	
	private final String baseUrl = "http://localhost/";
	private final String accueilUrl = "/scandoc76/#!/accueil";

	public String getBaseUrl() {
		return baseUrl;
	}

	public String getAccueilUrl() {
		return accueilUrl;
	}

	@Test
	public void createLot() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(getBaseUrl() + getAccueilUrl());
		driver.findElement(By.xpath("//input[@type='text']")).clear();
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys("svcNumerisation");
		driver.findElement(By.xpath("//input[@type='password']")).clear();
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Dept76@numerisation");
		driver.findElement(By.cssSelector("div.input-group-btn > button.btn.btn-default")).click();
		driver.findElement(By.cssSelector("span > button.btn.btn-primary")).click();
		new Select(driver.findElement(By.id("domain"))).selectByVisibleText("MDPH");
		driver.findElement(By.cssSelector("div.modal-footer > button.btn.btn-primary")).click();
		WebElement page = driver.findElement(By.cssSelector("div[class='panel rose']"));
		assertNotNull(page);
		WebElement header = page.findElement(By.cssSelector("div[class='panel-heading ng-binding']"));
		assertEquals(header.getText(), "Image : 1");
		driver.findElement(By.className("navbar-brand")).click();
		driver.findElement(By.cssSelector("span > button.btn.btn-primary")).click();
		new Select(driver.findElement(By.id("domain"))).selectByVisibleText("PES");
		driver.findElement(By.cssSelector("div.modal-footer > button.btn.btn-primary")).click();
		page = driver.findElement(By.cssSelector("div[class='panel rose']"));
		assertNotNull(page);
		header = page.findElement(By.cssSelector("div[class='panel-heading ng-binding']"));
		assertEquals(header.getText(), "Image : 1");
		driver.findElements(By.tagName("li")).get(7).click();
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	@Test
	public void navigationBar() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(getBaseUrl() + getAccueilUrl());
		driver.findElement(By.xpath("//input[@type='text']")).clear();
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys("svcNumerisation");
		driver.findElement(By.xpath("//input[@type='password']")).clear();
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Dept76@numerisation");
		driver.findElement(By.cssSelector("div.input-group-btn > button.btn.btn-default")).click();
		driver.findElement(By.cssSelector("a[class='navbar-brand']")).click();
		assertTrue(driver.getCurrentUrl().contains("reception"));
		List<WebElement> li = driver.findElements(By.tagName("li"));
		li.get(0).click();
		assertTrue(driver.getCurrentUrl().contains("reception"));
		li.get(1).click();
		assertTrue(driver.getCurrentUrl().contains("statistique"));
		li.get(2).click();
		assertTrue(driver.getCurrentUrl().contains("qrCode"));
		li.get(7).click();
		assertTrue(driver.getCurrentUrl().contains("accueil"));
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	@Test
	public void preparation() throws Exception {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(getBaseUrl() + getAccueilUrl());
		driver.findElement(By.xpath("//input[@type='text']")).clear();
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys("svcNumerisation");
		driver.findElement(By.xpath("//input[@type='password']")).clear();
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Dept76@numerisation");
		driver.findElement(By.cssSelector("div.input-group-btn > button.btn.btn-default")).click();
		List<WebElement> li = driver.findElements(By.tagName("li"));
		li.get(2).click();
		WebElement person = driver.findElement(By.cssSelector("div[class='input-group add-on']"))
				.findElements(By.tagName("input")).get(1);
		String name = driver.findElements(By.cssSelector("p[class='navbar-text ng-binding']")).get(0).getText();
		assertEquals(name, person.getAttribute("value"));
		assertEquals(driver.findElement(By.id("choixDomaine")).getAttribute("value"), "PES");
		assertEquals(driver.findElements(By.cssSelector("div[class='input-group add-on']")).get(1)
				.findElements(By.tagName("input")).get(1).getAttribute("placeholder"), "Rattachement");
		driver.findElements(By.cssSelector("div[class='input-group add-on']")).get(0)
				.findElement(By.cssSelector("div[class='input-group-btn']")).findElement(By.tagName("button")).click();
		Thread.sleep(2000);
		WebElement destinataire = driver.findElement(By.id("modalCompteAD"));
		assertEquals("modal fade in", destinataire.getAttribute("class"));
		assertEquals("block", destinataire.getCssValue("display"));
		destinataire.findElement(By.tagName("button")).click();
		Thread.sleep(2000);
		assertEquals("modal fade", destinataire.getAttribute("class"));
		assertEquals("none", destinataire.getCssValue("display"));
		driver.findElements(By.cssSelector("div[class='input-group add-on']")).get(1)
				.findElement(By.cssSelector("div[class='input-group-btn']")).findElement(By.tagName("button")).click();
		Thread.sleep(2000);
		WebElement reference = driver.findElement(By.id("modalReference"));
		assertEquals("modal fade in", reference.getAttribute("class"));
		assertEquals("block", reference.getCssValue("display"));
		reference.findElement(By.tagName("button")).click();
		Thread.sleep(2000);
		assertEquals("modal fade", reference.getAttribute("class"));
		assertEquals("none", reference.getCssValue("display"));
		driver.findElement(By.cssSelector("button[data-target='#modalPli']")).click();
		Thread.sleep(2000);
		WebElement pli = driver.findElement(By.id("modalPli"));
		assertEquals("modal fade ng-scope in", pli.getAttribute("class"));
		assertEquals("block", pli.getCssValue("display"));
		pli.findElement(By.tagName("button")).click();
		Thread.sleep(2000);
		assertEquals("modal fade ng-scope", pli.getAttribute("class"));
		assertEquals("none", pli.getCssValue("display"));
		driver.findElements(By.tagName("li")).get(7).click();
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	@Test
	public void reception() throws InterruptedException {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(getBaseUrl() + getAccueilUrl());
		driver.findElement(By.xpath("//input[@type='text']")).clear();
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys("svcNumerisation");
		driver.findElement(By.xpath("//input[@type='password']")).clear();
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Dept76@numerisation");
		driver.findElement(By.cssSelector("div.input-group-btn > button.btn.btn-default")).click();
		int nb = 15;
		List<WebElement> lines = driver.findElement(By.tagName("table")).findElement(By.tagName("tbody"))
				.findElements(By.tagName("tr"));
		assertTrue(lines.size() <= nb);
		driver.findElement(By.cssSelector("span > button.btn.btn-primary")).click();
		assertTrue(driver.findElement(By.id("modalCreationLotFromScratch")).isEnabled());
		driver.findElement(By.id("modalCreationLotFromScratch")).findElements(By.tagName("button")).get(0).click();
		Thread.sleep(2000);
		driver.findElements(By.tagName("li")).get(7).click();
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	@Test
	public void statistiques() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(getBaseUrl() + getAccueilUrl());
		driver.findElement(By.xpath("//input[@type='text']")).clear();
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys("svcNumerisation");
		driver.findElement(By.xpath("//input[@type='password']")).clear();
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Dept76@numerisation");
		driver.findElement(By.cssSelector("div.input-group-btn > button.btn.btn-default")).click();
		List<WebElement> li = driver.findElements(By.tagName("li"));
		li.get(1).click();
		Calendar calendar = Calendar.getInstance();
		List<WebElement> datepickers = driver.findElements(By.tagName("date-picker"));
		String startDate = datepickers.get(0).findElements(By.tagName("input")).get(0).getAttribute("value");
		String endDate = datepickers.get(1).findElements(By.tagName("input")).get(0).getAttribute("value");
		assertEquals(calendar.get(Calendar.DAY_OF_MONTH)
				+ "/" + (String.valueOf(calendar.get(Calendar.MONTH)).length() == 1
						? "0" + (calendar.get(Calendar.MONTH) + 1) : calendar.get(Calendar.MONTH))
				+ "/" + calendar.get(Calendar.YEAR), startDate);
		assertEquals(calendar.get(Calendar.DAY_OF_MONTH)
				+ "/" + (String.valueOf(calendar.get(Calendar.MONTH)).length() == 1
						? "0" + (calendar.get(Calendar.MONTH) + 1) : calendar.get(Calendar.MONTH))
				+ "/" + calendar.get(Calendar.YEAR), endDate);
		List<WebElement> selects = driver.findElements(By.tagName("select"));
		assertEquals("MDPH", fields[Integer.parseInt(selects.get(0).getAttribute("value")) - 1]);
		assertEquals("5", selects.get(1).getAttribute("value"));
		List<WebElement> lines = driver.findElement(By.tagName("table")).findElement(By.tagName("tbody"))
				.findElements(By.tagName("tr"));
		assertTrue(lines.size() <= Integer.parseInt(selects.get(1).getAttribute("value")));
		driver.findElements(By.tagName("li")).get(7).click();
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	@Test
	public void viewLot() throws InterruptedException {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(getBaseUrl() + getAccueilUrl());
		driver.findElement(By.xpath("//input[@type='text']")).clear();
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys("svcNumerisation");
		driver.findElement(By.xpath("//input[@type='password']")).clear();
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Dept76@numerisation");
		driver.findElement(By.cssSelector("div.input-group-btn > button.btn.btn-default")).click();
		WebElement line = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(0);
		String idLot = line.findElements(By.tagName("td")).get(2).getText().split(" ")[1];
		int numPage1 = Integer.parseInt(line.findElements(By.tagName("td")).get(5).getText());
		line.findElements(By.tagName("td")).get(0).click();
		assertTrue(driver.getCurrentUrl().contains(idLot));
		int numPage = driver.findElements(By.tagName("ng-repeat")).size();
		assertEquals(numPage1, numPage);
		List<WebElement> pinkPages = driver.findElements(By.cssSelector("div[class='panel rose']"));
		WebElement page = pinkPages.get(0);
		List<WebElement> icons = page.findElements(By.tagName("ul")).get(1).findElements(By.tagName("li"));
		WebElement img = page.findElement(By.tagName("img"));
		String css1 = img.getAttribute("class").split(" ")[img.getAttribute("class").split(" ").length - 1];
		int value1 = Integer.parseInt(css1.split("-")[css1.split("-").length - 1]);
		icons.get(1).click();
		String css2 = img.getAttribute("class").split(" ")[img.getAttribute("class").split(" ").length - 1];
		int value2 = Integer.parseInt(css2.split("-")[css2.split("-").length - 1]);
		assertEquals((value1 += 90) % 360, value2);

		css1 = css2;
		value1 = value2;
		icons.get(1).click();
		css2 = img.getAttribute("class").split(" ")[img.getAttribute("class").split(" ").length - 1];
		value2 = Integer.parseInt(css2.split("-")[css2.split("-").length - 1]);
		assertEquals((value1 += 90) % 360, value2);

		css1 = css2;
		value1 = value2;
		icons.get(1).click();
		css2 = img.getAttribute("class").split(" ")[img.getAttribute("class").split(" ").length - 1];
		value2 = Integer.parseInt(css2.split("-")[css2.split("-").length - 1]);
		assertEquals((value1 += 90) % 360, value2);

		css1 = css2;
		value1 = value2;
		icons.get(1).click();
		css2 = img.getAttribute("class").split(" ")[img.getAttribute("class").split(" ").length - 1];
		value2 = Integer.parseInt(css2.split("-")[css2.split("-").length - 1]);
		assertEquals((value1 += 90) % 360, value2);

		icons.get(0).click();
		List<WebElement> pages = driver.findElements(By.cssSelector("div[class='panel rose']"));
		assertEquals(pinkPages.size() + 1, pages.size());
		int numPage2 = Integer
				.parseInt(driver.findElement(By.cssSelector("div[class='ng-binding']")).getText().split("\\|")[1].split(
						" ")[driver.findElement(By.cssSelector("div[class='ng-binding']")).getText().split("\\|")[1]
								.split(" ").length - 1]);
		assertEquals(numPage + 1, numPage2);
		numPage++;
		WebElement header = pages.get(1).findElement(By.cssSelector("div[class='panel-heading ng-binding']"));
		assertEquals(header.getText(), "Image : 2");

		page.findElement(By.tagName("img")).click();
		assertTrue(driver.findElement(By.id("blueimp-gallery")).getAttribute("style").contains("block"));
		List<WebElement> slides = driver.findElements(By.cssSelector("div[class='slide']"));
		WebElement selectedSlide = null;
		for (WebElement slide : slides) {
			if (slide.getAttribute("style").contains("translate(0px, 0px)")) {
				selectedSlide = slide;
			}
		}
		assertEquals(selectedSlide.findElement(By.tagName("img")).getAttribute("title"), "Image : 1");
		WebElement next = driver.findElement(By.cssSelector("a[class='next']"));
		next.click();
		for (WebElement slide : slides) {
			if (slide.getAttribute("style").contains("translate(0px, 0px)")) {
				selectedSlide = slide;
			}
		}
		assertEquals(selectedSlide.findElement(By.tagName("img")).getAttribute("title"), "Image : 2");
		WebElement previous = driver.findElement(By.cssSelector("a[class='prev']"));
		previous.click();
		for (WebElement slide : slides) {
			if (slide.getAttribute("style").contains("translate(0px, 0px)")) {
				selectedSlide = slide;
			}
		}
		assertEquals(selectedSlide.findElement(By.tagName("img")).getAttribute("title"), "Image : 1");
		driver.findElement(By.id("blueimp-gallery")).findElements(By.tagName("a")).get(2).click();
		Thread.sleep(2000);
		driver.findElements(By.tagName("li")).get(7).click();
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	@Test
	public void viewTypeDoc() throws Exception {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(getBaseUrl() + getAccueilUrl());
		driver.findElement(By.xpath("//input[@type='text']")).clear();
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys("svcNumerisation");
		driver.findElement(By.xpath("//input[@type='password']")).clear();
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Dept76@numerisation");
		driver.findElement(By.cssSelector("div.input-group-btn > button.btn.btn-default")).click();
		Thread.sleep(2000);
		driver.navigate().to(getBaseUrl() + "/scandoc76/#!/typeDoc");
		driver.findElement(By.cssSelector("span[data-original-title='Ajouter un type de document']"))
				.findElements(By.tagName("button")).get(0).click();
		Thread.sleep(2000);
		WebElement addLine = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(0);
		driver.findElement(By.xpath("//input[@name='libelle']")).clear();
		driver.findElement(By.xpath("//input[@name='libelle']")).sendKeys("Aa");
		driver.findElement(By.xpath("//input[@name='typeEntite']")).clear();
		driver.findElement(By.xpath("//input[@name='typeEntite']")).sendKeys("Aa");
		addLine.findElements(By.tagName("td")).get(9).findElements(By.tagName("button")).get(0).click();
		addLine = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(0);
		Thread.sleep(2000);
		assertEquals("Aa", driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(0)
				.findElements(By.tagName("td")).get(0).getText());
		driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(0).findElements(By.tagName("td"))
				.get(9).findElements(By.tagName("button")).get(3).click();
		driver.findElements(By.tagName("li")).get(7).click();
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
