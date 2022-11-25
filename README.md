# ReadingIsGood Book Api

# Documentation
Swagger is used for api documentation, to read documentation on local:

`http://localhost:8080/readingisgood-books/swagger-ui/`


# Database
I use h2 in memory database to persist data for my application.
Data and schema is recreating for each start up.

`spring.datasource.url=jdbc:h2:mem:book-db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;`

to open h2 ui-view please put below slug after context-path

`/h2-ui`

credentials:

`spring.datasource.username=test-user`

`spring.datasource.password=12345`


# How to run

if you want to run application in docker, start_in_docker script will build project, it creates container and runs it. it is enough to run below command:

`sh start_in_docker.sh`

Also you can start application via  below command on your local

`java -jar book-api-0.0.1-SNAPSHOT.jar -Dspring.profiles.active=test -Denvironment=test -Xms128m -Xmx512m -XX:+UseG1GC -XX:+UnlockExperimentalVMOptions -XX:MaxGCPauseMillis=200 -XX:+UseStringDeduplication -XX:ParallelGCThreads=4 -XX:InitiatingHeapOccupancyPercent=70 -noverify -XX:TieredStopAtLevel=1 -web -webAllowOthers -tcp -tcpAllowOthers -browser`

# Security
jwt token was used to get access for authorized end points. You have to provide token as below to get access for authorized endpoints;

`Authorization: Bearer [token]`

Application supports 2 role type; USER, ADMIN.
ADMIN user is automatically creating during the initialization of application.

`api.security.admin_user=admin@readingisgood.com`

`api.security.admin_password=admin12345`

A customer can be registered by ".../register" endpoint. By this way every user can only have USER role type.
Three different authority check were applied to endpoints in application:

`hasAuthority('ADMIN')`

`hasAuthority('USER')`

`@customerOwnerShipAccessChecker.check(#id)`

OwnerShipAccessChecker is used to allow updates to specific resources for only it's owners.


# Api Test
I have put sample postman requests in postman directory, you can import it and test requests.