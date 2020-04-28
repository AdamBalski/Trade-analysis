Tool for trade analysis
============
It provides simple analysis of stock market, but you have to give the alpha vantage api key.
You can registrate yourself, so the app will remember you, so you won't have to tell the key everytime.
Also you can see live stock prices of many companies and to do that you don't have to own your api key.
------------
If you want to start the program then first you have to add in `resources` directory a file called `application.properties` and add to it some values.
Template for that file:
```
spring.datasource.url = YOUR_DATABASE_URL
spring.datasource.username = YOUR_DATABASE_USERNAME
spring.datasource.password = YOUR_DATABASE_PASSWORD_IF_YOU_HAVE_ONE
spring.datasource.hostname=YOUR_DATABASE_HOSTNAME
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQL10Dialect
spring.jpa.properties.hibernate.hbm2ddl.auto=validate
```