package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;
	private WebDriver driver;
	private SignupPage signupPage;
	private LoginPage loginPage;
	private HomePage homePage;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	/**
	 * Write a test that verifies that an unauthorized user can only access the login and signup pages.
	 */
	@Test
	public void verifiesUnauthorizedAccess() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * Write a test that signs up a new user, logs in, verifies that the home page is accessible, logs out, and verifies that the home page is no longer accessible.
	 */
	@Test
	public void verifiedAuthorizaedAccess(){
		driver.get("http://localhost:" + port + "/signup");
		signupPage = new SignupPage(driver);

		//ingreso datos para registro de nuevo usuario
		String username = "gabus";
		String password = "gabus";
		String firstname = "gabus";
		String lastname = "leon";

		signupPage.registerNewUser(firstname, lastname, username, password);

		//inicio sesion con el nuevo usuario creado
		driver.get("http://localhost:" + port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		//verifica que tiene acceso a la pagina /home
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Home", driver.getTitle());
		homePage = new HomePage(driver);

		//cierra sesion
		homePage.logout();
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());

	}

	/**
	 * Write a test that creates a note, and verifies it is displayed.
	 */
	@Test
	public void createNote() throws InterruptedException {
		driver.get("http://localhost:" + port + "/signup");
		signupPage = new SignupPage(driver);

		//ingreso datos para registro de nuevo usuario
		String username = "gabus";
		String password = "gabus";
		String firstname = "gabus";
		String lastname = "leon";

		signupPage.registerNewUser(firstname, lastname, username, password);

		//inicio sesion con el nuevo usuario creado
		driver.get("http://localhost:" + port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		//inserta una nueva nota
		String title = "Nueva Nota";
		String description = "Esta es una prueba de creacion de notas";
		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.createNote(title, description);

		//verifica que la nota ha sido creada y se muestra en la pagina home
		Assertions.assertTrue(homePage.getDisplayedNoteTitle().getText().contains(title));
		Assertions.assertTrue(homePage.getDisplayedNoteDescription().getText().contains(description));
	}

	/**
	 * 	Write a test that edits an existing note and verifies that the changes are displayed.
	 */
	@Test
	public void editNote() throws InterruptedException {
		driver.get("http://localhost:" + port + "/signup");
		signupPage = new SignupPage(driver);

		//ingreso datos para registro de nuevo usuario
		String username = "gabus";
		String password = "gabus";
		String firstname = "gabus";
		String lastname = "leon";

		signupPage.registerNewUser(firstname, lastname, username, password);

		//inicio sesion con el nuevo usuario creado
		driver.get("http://localhost:" + port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		//inserta una nueva nota
		String title = "Nueva Nota";
		String description = "Esta es una prueba de creacion de notas";
		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.createNote(title, description);

		//edito la nota existente
		String newTitle = "Nota Editada";
		String newDescription = "Esta es una prueba de edicion de notas";
		homePage.editNote(newTitle, newDescription);

		//verifica que la nota ha sido modificada y se muestra en la pagina home
		Assertions.assertTrue(homePage.getDisplayedNoteTitle().getText().contains(newTitle));
		Assertions.assertTrue(homePage.getDisplayedNoteDescription().getText().contains(newDescription));
	}

	/**
	 * Write a test that deletes a note and verifies that the note is no longer displayed.
	 */
	@Test
	public void deleteNote() throws InterruptedException {
		driver.get("http://localhost:" + port + "/signup");
		signupPage = new SignupPage(driver);

		//ingreso datos para registro de nuevo usuario
		String username = "gabus";
		String password = "gabus";
		String firstname = "gabus";
		String lastname = "leon";

		signupPage.registerNewUser(firstname, lastname, username, password);

		//inicio sesion con el nuevo usuario creado
		driver.get("http://localhost:" + port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		//inserta una nueva nota
		String title = "Nueva Nota";
		String description = "Esta es una prueba de creacion de notas";
		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.createNote(title, description);

		//elimina la nota existente
		homePage.deleteNote();

		//verifica que la nota ha sido eliminada y no se muestra en la pagina home
		Assertions.assertFalse(homePage.getDisplayedNoteTitle() == null);
		Assertions.assertFalse(homePage.getDisplayedNoteDescription() == null);
	}

	/**
	 * Write a test that creates a set of credentials, verifies that they are displayed, and verifies that the displayed password is encrypted.
	 */
	@Test
	public void createCredential() throws InterruptedException {
		driver.get("http://localhost:" + port + "/signup");
		signupPage = new SignupPage(driver);

		//ingreso datos para registro de nuevo usuario
		String username = "gabus";
		String password = "gabus";
		String firstname = "gabus";
		String lastname = "leon";

		signupPage.registerNewUser(firstname, lastname, username, password);

		//inicio sesion con el nuevo usuario creado
		driver.get("http://localhost:" + port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		//inserta una nueva credencial
		String urlCredential = "http://www.ups.edu.ec";
		String usernameCredential = "gleon";
		String passwordCredential = "gleon.123";

		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.createCredential(urlCredential, usernameCredential, passwordCredential);

		//verifica que la credencial ha sido creada y se muestra en la pagina home. Además, que la contraseña mostrada es la encriptada
		Assertions.assertTrue(homePage.getDisplayedCredentialURL().getText().contains(urlCredential));
		Assertions.assertTrue(homePage.getDisplayedCredentialUsername().getText().contains(usernameCredential));
		Assertions.assertNotEquals(homePage.getDisplayedCredentialPassword().getText(), passwordCredential);
	}

	/**
	 * Write a test that views an existing set of credentials, verifies that the viewable password is unencrypted, edits the credentials, and verifies that the changes are displayed.
	 *
	 * @throws InterruptedException
	 */
	@Test
	public void editCredential() throws InterruptedException {
		driver.get("http://localhost:" + port + "/signup");
		signupPage = new SignupPage(driver);

		//ingreso datos para registro de nuevo usuario
		String username = "gabus";
		String password = "gabus";
		String firstname = "gabus";
		String lastname = "leon";

		signupPage.registerNewUser(firstname, lastname, username, password);

		//inicio sesion con el nuevo usuario creado
		driver.get("http://localhost:" + port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		//inserta una nueva credencial
		String urlCredential = "http://www.ups.edu.ec";
		String usernameCredential = "gleon";
		String passwordCredential = "gleon.123";

		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.createCredential(urlCredential, usernameCredential, passwordCredential);

		//verifica que la contraseña este desencriptada
		homePage.showUnencryptedPassword();
		String unencryptedPassword = homePage.getInputCredentialPassword();
		Assertions.assertTrue(unencryptedPassword.equals(passwordCredential));
		Thread.sleep(1000);

		//edita la credencial existente
		String newUrlCredential = "http://www.wissen.edu.ec";
		String newUsernameCredential = "gabriel.leon";
		String newPasswordCredential = "leon.123";
		homePage.editCredential(newUrlCredential, newUsernameCredential, newPasswordCredential);

		//verifica que la credencial ha sido creada y se muestra en la pagina home. Además, que la contraseña mostrada es la encriptada
		Assertions.assertTrue(homePage.getDisplayedCredentialURL().getText().contains(newUrlCredential));
		Assertions.assertTrue(homePage.getDisplayedCredentialUsername().getText().contains(newUsernameCredential));
		Assertions.assertNotEquals(homePage.getDisplayedCredentialPassword().getText(), newPasswordCredential);
	}

	/**
	 * Write a test that deletes an existing set of credentials and verifies that the credentials are no longer displayed.
	 */
	@Test
	public void deleteCredential() throws InterruptedException {
		driver.get("http://localhost:" + port + "/signup");
		signupPage = new SignupPage(driver);

		//ingreso datos para registro de nuevo usuario
		String username = "gabus";
		String password = "gabus";
		String firstname = "gabus";
		String lastname = "leon";

		signupPage.registerNewUser(firstname, lastname, username, password);

		//inicio sesion con el nuevo usuario creado
		driver.get("http://localhost:" + port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		//inserta una nueva credencial
		String urlCredential = "http://www.ups.edu.ec";
		String usernameCredential = "gleon";
		String passwordCredential = "gleon.123";

		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.createCredential(urlCredential, usernameCredential, passwordCredential);

		//elimina la credencial existente
		homePage.deleteCredential();

		//verifica que la nota ha sido eliminada y no se muestra en la pagina home
		Assertions.assertFalse(homePage.getDisplayedCredentialURL() == null);
		Assertions.assertFalse(homePage.getDisplayedCredentialUsername() == null);
		Assertions.assertFalse(homePage.getDisplayedCredentialPassword() == null);
	}
}
