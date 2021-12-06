package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(id = "submit-logout-button")
    WebElement logoutSubmitButton;

    @FindBy(id = "nav-notes-tab")
    WebElement navNotesTabLink;

    @FindBy(id = "nav-credentials-tab")
    WebElement navCredentialsTabLink;

    @FindBy(id = "new-note-modal-button")
    WebElement newNoteModalButton;

    @FindBy(id = "note-title")
    WebElement inputNoteTitle;

    @FindBy(id = "note-description")
    WebElement inputNoteDescription;

    @FindBy(id = "noteSubmitModal")
    WebElement noteSubmitModalButton;

    @FindBy(id ="note-displayed-edit")
    WebElement noteDisplayedEdit;

    @FindBy(id ="note-displayed-delete")
    WebElement noteDisplayedDelete;

    @FindBy(id = "note-displayed-title")
    WebElement noteDisplayedTitle;

    @FindBy(id = "note-displayed-description")
    WebElement noteDisplayedDescription;

    @FindBy(id= "newCredentialModalButton")
    WebElement newCredentialModalButton;

    @FindBy(id = "credential-url")
    WebElement inputCredentialURL;

    @FindBy(id = "credential-username")
    WebElement inputCredentialUsername;

    @FindBy(id = "credential-password")
    WebElement inputCredentialPassword;

   @FindBy(id = "credentialModalButtonSubmit")
   WebElement credentialModalButtonSubmit;

   @FindBy(id = "credential-displayed-url")
   WebElement credentialDisplayedUrl;

   @FindBy(id = "credential-displayed-username")
   WebElement credentialDisplayedUsarname;

   @FindBy(id = "credential-displayed-password")
   WebElement credentialDisplayedPassword;

   @FindBy(id = "credential-displayed-edit")
   WebElement credentialDisplayedEdit;

   @FindBy(id = "credential-displayed-delete")
   WebElement credentialDisplayedDelete;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void  logout(){
        logoutSubmitButton.click();
    }

    public void createNote(String title, String description) throws InterruptedException {
        this.navNotesTabLink.click();
        Thread.sleep(1000);
        this.newNoteModalButton.click();
        Thread.sleep(1000);
        this.inputNoteTitle.sendKeys(title);
        this.inputNoteDescription.sendKeys(description);
        this.noteSubmitModalButton.click();
        Thread.sleep(1000);
    }

    public void editNote(String title, String description) throws InterruptedException {
        this.navNotesTabLink.click();
        Thread.sleep(1000);
        this.noteDisplayedEdit.click();
        Thread.sleep(1000);
        this.inputNoteTitle.clear();
        this.inputNoteTitle.sendKeys(title);
        this.inputNoteDescription.clear();
        this.inputNoteDescription.sendKeys(description);
        this.noteSubmitModalButton.click();
        Thread.sleep(1000);
    }

    public void deleteNote() throws InterruptedException {
        this.navNotesTabLink.click();
        Thread.sleep(1000);
        this.noteDisplayedDelete.click();
        Thread.sleep(1000);
    }

    public WebElement getDisplayedNoteTitle(){
        return this.noteDisplayedTitle;
    }

    public WebElement getDisplayedNoteDescription(){
        return this.noteDisplayedDescription;
    }

    public void createCredential(String url, String username, String password) throws InterruptedException {
        this.navCredentialsTabLink.click();
        Thread.sleep(1000);
        this.newCredentialModalButton.click();
        Thread.sleep(1000);
        this.inputCredentialURL.sendKeys(url);
        this.inputCredentialUsername.sendKeys(username);
        this.inputCredentialPassword.sendKeys(password);
        this.credentialModalButtonSubmit.click();
        Thread.sleep(1000);
    }

    public void editCredential(String url, String username, String password) throws InterruptedException {
        //this.navCredentialsTabLink.click();
        //Thread.sleep(1000);
       // this.credentialDisplayedEdit.click();
        //Thread.sleep(1000);

        this.inputCredentialURL.clear();
        this.inputCredentialUsername.clear();
        this.inputCredentialPassword.clear();

        this.inputCredentialURL.sendKeys(url);
        this.inputCredentialUsername.sendKeys(username);
        this.inputCredentialPassword.sendKeys(password);

        this.credentialModalButtonSubmit.click();
        Thread.sleep(1000);
    }

    public void showUnencryptedPassword() throws InterruptedException{
        this.navCredentialsTabLink.click();
        this.credentialDisplayedEdit.click();
        Thread.sleep(1000);
    }

    public void deleteCredential() throws InterruptedException {
        this.navCredentialsTabLink.click();
        Thread.sleep(1000);
        this.credentialDisplayedDelete.click();
        Thread.sleep(1000);
    }

    public WebElement getDisplayedCredentialURL(){
        return this.credentialDisplayedUrl;
    }

    public WebElement getDisplayedCredentialUsername(){
        return this.credentialDisplayedUsarname;
    }

    public WebElement getDisplayedCredentialPassword(){
        return this.credentialDisplayedPassword;
    }

    public  String getInputCredentialPassword(){
        return this.inputCredentialPassword.getAttribute("value");
    }
}
