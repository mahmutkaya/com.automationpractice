# com.automationpractice

- WEB & API Automation Testing Using Java, cucumber and restAssured

- Test results monitoring tool [Calliope.pro](https://app.calliope.pro)

- Environment and specs: [automationpractice](http://automationpractice.com/index.php)

### dependencies:
- [selenium-java](https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java)
- [cucumber-java](https://mvnrepository.com/artifact/io.cucumber/cucumber-java)
- [cucumber-junit](https://mvnrepository.com/artifact/io.cucumber/cucumber-junit)
- [webdrivermanager](https://mvnrepository.com/artifact/io.github.bonigarcia/webdrivermanager)
- [jackson-databind](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind)
- [rest-assured](https://mvnrepository.com/artifact/io.rest-assured/rest-assured)
- [hamcrest](https://mvnrepository.com/artifact/org.hamcrest/hamcrest)

### Setting Up
These instructions will get you a copy of the project up and running on your local machine.

- *clone the repo:*
```shell
git clone https://github.com/mahmutkaya/com.automationpractice.git
```
- *go to ```configuration.properties``` file - at project level*
- *and add the text below into it with replacing your own values from [automationpractice](http://automationpractice.com/index.php)*
```properties
token = <your_token>
cookie = <your_cookie>
```
- *set project sdk as 1.8*

Running tests from terminal:
```shell
mvn -B verify --file pom.xml
```
Running tests in CI/CD pipeline:
- In this project I used github actions but for any other CI/CD tools,
  checkout [calliope](https://docs.calliope.pro/import/manual-import/) documentation.
  
- If you want to use same pipeline setup just add following variables defined inside the workflows/maven.yml
to your github repository as secret variables.
  
```properties  
 API_KEY: Required to import tests to Calliope.pro. Can be found on your Calliope.pro account page
 PROFILE_ID: Tests will be imported in one of your profiles on Calliope.pro. 
```  

### About scenarios:
Automated scenarios are: 
- Create account (api and ui) 
- Login (ui)
- Add to cart (api)
- Delete from cart (api)

These are the most critical features for an ecommerce website.
If website visitors can not add items to the cart and remove it if they did not like it, 
can not sign up and sign in to check and track their orders, then they have no reasons to stay.

For the next step: Automate **Make payment** scenarios to complete smoke suit.