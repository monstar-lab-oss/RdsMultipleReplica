# Read Me First

Please make sure Docker nad Maven is provided in your system.

- Execute unit tests only 
```
mvn clean -DskipITs=true verify
```

- Execute Integration Tests (IT) only
```
mvn clean -Dskip.unit.tests=true verify
```

- Execute both unit and integrations test
```
mvn clean install
```

or

- Run the application with PostgreSQL 
```
docker run -d --name template_postgres -e POSTGRES_USER=templateUser -e POSTGRES_PASSWORD=templatePassword -e POSTGRES_DB=template -p 5435:5432  --restart=always postgres
```
- Start the application with 
```
mvn spring-boot:run
```