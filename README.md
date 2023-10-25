# Tool for trade analysis in web app

#### Description
It provides simple analysis of the stock market pulling data from the alpha vantage key.

---
#### Prerequisites to run
If you want to start the program then first you have to add a file `./env.properties`.
Template for that file:
```
POSTGRES_JDBC_URL='jdbc:postgresql://db:5432/app'
POSTGRES_PASSWORD='dbpassword'
POSTGRES_USERNAME='app'
POSTGRES_USER='app'
POSTGRES_HOSTNAME='postgresql'
POSTGRES_DBNAME='app'

E_MAIL_HOST=smtp.server.com
E_MAIL_PORT=587
E_MAIL_PASSWORD=emailpassword
E_MAIL_ADDRESS=name@server.domain
```
Now compile with `mvn clean compile package`.
## Docker
```
docker compose up
```

## Manual
You have to add a `logger` directory and put a file called `logfile` in it.
After that, run create-all-tables.sql script in your postgres db.

---
#### Done with
* backend - maven dependencies:
    * check 'pom.xml'
* frontend:
    * bootstrap
    * popper.js
    * jQuery
    * google charts
* postgresql
* docker
