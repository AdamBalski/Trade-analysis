# Tool for trade analysis

#### Description
It provides simple analysis of the stock market, but you have to give the alpha vantage api key.
You can register yourself, so the app will remember you, so you won't have to tell the key every time.
Also you can see live stock prices of many companies and to do that you don't have to own your api key.

---
#### Prerequisites to run
If you want to start the program then first you have to add in the `resources` directory a file called `application.properties` and add to it some values. E-mail sender is configured to work with gmail mail.
Template for that file:
```
# Databases
spring.datasource.url = YOUR_DATABASE_URL
spring.datasource.username = USERNAME_OF_YOUR_DATABASE'S_USER
spring.datasource.password = PASSWORD_OF_YOUR_DATABASE'S_USER_IF_YOUR_USER_HAS_ONE
spring.datasource.hostname = YOUR_DATABASE'S_HOSTNAME
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQL10Dialect
spring.jpa.properties.hibernate.hbm2ddl.auto = validate

# Logging (SLF4J)
logging.file.path=logger
logging.file.name=logger/logfile
logging.config=classpath:config/logback-spring.xml

# Sending e-mails
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=E-MAIL_ADDRESS
spring.mail.password=PASSWORD
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true%     
```
Also you have to add a `logger` directory and put a file called `logfile` in it.

---
#### Done with
* backend - maven dependencies:
    * check 'pom.xml'
    * postgres database
* frontend:
    * bootstrap
    * popper.js
    * jQuery
