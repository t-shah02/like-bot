import java.util.ArrayList;
import java.util.Scanner;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LikeBot {
	
	private WebDriver driver;
	private ArrayList<String> likedProfiles;
	private boolean validProfile;
	private int totalPostsLiked;
	
	public LikeBot() {
		
	}
	
	public LikeBot(WebDriver driver) {
		System.setProperty("webdriver.chrome.driver",".\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		this.driver = driver;
	}
	
	public void login() throws InterruptedException {
		Secrets s = new Secrets();
		driver.get("https://www.instagram.com/accounts/login/?hl=en");
		Thread.sleep(2000);
		WebElement user = driver.findElement(By.xpath("/html/body/div[1]/section/main/div/article/div/div[1]/div/form/div[2]/div/label/input"));
		user.click();
		user.sendKeys(s.username);
		WebElement pass = driver.findElement(By.xpath("/html/body/div[1]/section/main/div/article/div/div[1]/div/form/div[3]/div/label/input"));
		pass.click();
		pass.sendKeys(s.password);
		pass.sendKeys(Keys.ENTER);
		Thread.sleep(5000);
		
		// handle notifications 
		
		WebElement notNow = driver.findElement(By.xpath("/html/body/div[4]/div/div/div[3]/button[2]"));
		notNow.click();
		
	}
	
	public void redirect(String profileName) throws InterruptedException {
		driver.get("https://www.instagram.com/"+profileName+"/?hl=en");
		Thread.sleep(2000);
	}
	
	public void goHome() {
		driver.get("https://www.instagram.com/?hl=en");
		
	}
	public boolean isPrivate() {
		try {
			String privateProfile = driver.findElement(By.xpath("/html/body/div[1]/section/main/div/div/article/div[1]/div/h2")).getText();
			return true;
		}
		catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public void likePosts() throws InterruptedException {
		validProfile = false;
		String posted = driver.findElement(By.xpath("/html/body/div[1]/section/main/div/header/section/ul/li[1]/span/span")).getText();
		int totalPosts = Integer.parseInt(posted.replaceAll(",", ""));
		boolean privateProfile = isPrivate();
		
		if (totalPosts==0) {
			System.out.println("This profile has no posts! Failed");
			validProfile = false;
			return;
		}
		
		if (privateProfile == true) {
			System.out.println("This profile is private! Failed");
			validProfile = false;
			return;
		}
			
			
			for (int i = 1; i <=totalPosts; i++) {
				if (i==1) {
					WebElement postOne = driver.findElement(By.className("eLAPa"));
					postOne.click();
					Thread.sleep(2000);
					WebElement likeOne = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/article/div[2]/section[1]/span[1]/button"));
					likeOne.click();
					WebElement nextOne = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/div/a"));
					nextOne.click();
					Thread.sleep(5000);
				}
				
				else if (i==totalPosts) {
					WebElement like = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/article/div[2]/section[1]/span[1]/button"));
					like.click();
					
				}
				else {
					WebElement like = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/article/div[2]/section[1]/span[1]/button"));
					like.click();
					WebElement nextOne = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/div/a[2]"));
					nextOne.click();
					Thread.sleep(5000);
					
				}
			}
			
			validProfile = true;
			return;
		}
		
		
	public boolean validProfile() {
		return validProfile;
	}
	
	public void addProfile(String profile) {
		likedProfiles.add(profile);
	}
	
	public void setupProfiles() {
		Serializer s = new Serializer();
		likedProfiles = s.deserialize("likedprofiles.ser");
		
		if (likedProfiles==null) {
			likedProfiles = new ArrayList<String>();
		}
	}
	
	public void saveProfiles() {
		Serializer s = new Serializer();
		s.serialize(likedProfiles, "likedprofiles.ser");
	}
	
	
	public boolean matches(String target) {
		boolean matched = false;
		for (int i = 0; i < likedProfiles.size(); i++) {
			if (likedProfiles.get(i).equals(target)) {
				matched = true;
				break;
			}
		}
		return matched;
	}
	
	
	public void quit() {
		driver.quit();
	}
	
	
	
	public void runBot() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver",".\\drivers\\chromedriver.exe");
		
		
		driver = new ChromeDriver();
		Scanner sc = new Scanner(System.in);
		String yourProfile = "";
		
		boolean isLiking = true;
		boolean quit = false;
		
		
		login();
		setupProfiles();
		
		System.out.println("-- LIKE BOT FOR PUBLIC INSTAGRAM PROFILES, VERSION 1.0 created by Tanish Shah -------");
		
		do {
			if (likedProfiles.size()==0) {
				System.out.println("No profiles liked yet!");
			}
		
			System.out.println("Profiles that have been liked on previous launches of the program. Do not input them again, that is unless you want to dislike their posts haha!");
			System.out.println();
			
			for (int i = 0; i < likedProfiles.size(); i++) {
				System.out.println(i + ". " + likedProfiles.get(i));
			}
			
			System.out.print("Enter the exact profile name: QUIT TO EXIT ");
			yourProfile = sc.nextLine();
			
			if (yourProfile.equals("QUIT")) {
				isLiking = false;
				quit = true;
			}
			
			else {
				redirect(yourProfile);
				likePosts();
				
				if (validProfile()) {
					addProfile(yourProfile);
					saveProfiles();
				}
			}
			
			
		} while (isLiking == true);
		
		
		
		
		if (quit == true) {
			saveProfiles();
			quit();
			System.out.println("Like bot execution is now finished!");
		}
		
		
		
	}
	
	
	
	
	
	

}
