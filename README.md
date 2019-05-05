# todoservice

#### Overview and Brief description
__________

this is a spring boot micros service based implementation to help user store the tasks. the users can 
also add/remote/update task. The tasks can also be timed using the expiry time by which the 
tasks should be accomplished. 

Technical Stack for this implementation includes.
1. Spring boot Web to host micro services.
2. Sring Data/Repositories with Mysql
3. H2 database used for Integration tests.
4. gradle is used as build tool
5. Swagger API to establish the contacts.
6. sonarqube for static scan.
7. Junits and Mockito for Unit testing.
8. Mock MVC for integration tests.
9. liquibase migrations to migrate schema definitions readily to another database.

#### Security Considerations.
__________
The todo services is statically scanned with sonarqube for the vulnerabilities from OWASP TOP 10
and SANS 25.

for reference OWASP top10
```
https://www.owasp.org/index.php/Category:OWASP_Top_Ten_Project
```
for reference SANS 25
```
https://www.sans.org/top25-software-errors
```
report inform of screenshot is present in workspace with name "ToDo_StaticScanReport.png"

#### Pre-requisites for the build.
__________
Running Docker Containers:
* SonarQube for scan reports.
* Mysql to store the task data.
```
docker-compose -f src/main/docker/mysql.yml up -d
docker-compose -f src/main/docker/sonar.yml up -d
```
#### Build & Test Instructions.
__________
* Build, Compile, Tests execution
```
./gradlew clean build
```

* Running the micro service
```
./gradlew
```

###### Testing
__________
```
./gradlew test
```
Note: Jacoco coverage reports can be found in the path below:
```
./build/reports/jacoco/test/html/index.html
```

###### Code quality
__________
Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

Then, run a Sonar analysis:

```
./gradlew -Pprod clean test sonarqube
```

###### Dockerize the application.
__________
```
docker-compose -f src/main/docker/app.yml up -d
```

####  Testing the services with Actual data.
__________
App facilitates the sign-up process, hence no default accounts are needed.
However, using below curl commands one can register accounts for testing purposes.

* sign-up user
```
curl -X POST \
  http://localhost:9080/todoservice/sign-up \
  -H 'Content-Type: application/json' \
  -d '{
	"userName": "admin1",
	"password" : "admin1"
}'
```

* login with user (JWT token is returned in the response headers, this should be exactracted and be used in 
future requests)
```
curl -X POST \
  http://localhost:9080/todoservice/login \
  -H 'Content-Type: application/json' \
  -d '{
	"userName": "admin1",
	"password" : "admin1"
}'
```

* create task for the user. (auth token from the request 2 needs to be used here.)
```
curl -X POST \
  http://localhost:9080/todoservice/api/task-details \
  -H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEiLCJleHAiOjE1NTc5MTQyMDd9.8zW6pWzrE5AfVmyNqmwXAi6rTZPJZtjf4c7DMQl5duyauw2cXhezJC3bcJ_Gn4QSzYVOU1lOe7FLI-bjMCdVYA' \
  -H 'Content-Type: application/json' \
  -d '{
    "userName": "admin1",
    "taskName": "groceries",
    "taskDescription": "buy all groceries this weekend",
    "estimatedTime": 100
}'
```

* update task for the user. (auth token from the request 2 needs to be used here.)
```
curl -X PUT \
  http://localhost:9080/todoservice/api/task-details \
  -H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGFyYW50ZWoiLCJleHAiOjE1NTc4MjQxMjh9.Uy_kEs6_g5fhiSHaWEt7pQ__i2OVlmeayrWzHs75hyRz4A0qlI59BHCi9ZwX49bzF6KI4ORSzvADq4vGnjvLqg' \
  -H 'Content-Type: application/json' \
  -d '{
	"taskId": 1,
	"taskName":"groceries11",
	"taskDescription":"buy all groceries this weekend11",
	"estimatedTime": 10
}'
```

* get the tasks for the user.
```
curl -X GET \
  'http://localhost:9080/todoservice/api/task-details?name=admin1' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGFyYW50ZWoiLCJleHAiOjE1NTc4MjQxMjh9.Uy_kEs6_g5fhiSHaWEt7pQ__i2OVlmeayrWzHs75hyRz4A0qlI59BHCi9ZwX49bzF6KI4ORSzvADq4vGnjvLqg'
```

* delete a task for the user.
```
curl -X DELETE \
  http://localhost:9080/todoservice/api/task-details/1 \
  -H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGFyYW50ZWoiLCJleHAiOjE1NTc4MjQxMjh9.Uy_kEs6_g5fhiSHaWEt7pQ__i2OVlmeayrWzHs75hyRz4A0qlI59BHCi9ZwX49bzF6KI4ORSzvADq4vGnjvLqg'
```


####  Futuristic process.
__________
1. deployments into AWS with pipeline integrated with kubernetes.
2. spring reactive streaming (as of present, non-blocking drivers not available with rdbms databases)


