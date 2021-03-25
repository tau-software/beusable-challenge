Room Occupancy Manager
======================

###### Coding challenge project for BeUsable

## Usage

### Test
```shell
./gradlew test
```

### Build
```shell
./gradlew build
```

### Run
```shell
./gradlew bootRun
```
Runs embedded Tomcat server listening on http://localhost:8080

### Query
```shell
curl -d "{\"rooms\": {\"premium\": 3, \"economy\": 3}, \"bids\": [23, 45, 155, 374, 22, 99, 100, 101, 115, 209]}" -H "Content-type:application/json" http://localhost:8080/bid 
```
should return:
```json
{
  "premium" : {
    "rooms" : 3,
    "money" : 738
  },
  "economy" : {
    "rooms" : 3,
    "money" : 167
  }
}
```

### List submitted queries
```json
curl http://localhost:8080/queries
```

### Health check
```json
curl http://localhost:8080/actuator/health
```

### Additional Links
1. Used [Spring Initializr params](https://start.spring.io/#!type=gradle-project&language=java&platformVersion=2.4.4.RELEASE&packaging=jar&jvmVersion=11&groupId=li.tau.beusable&artifactId=challenge&name=Coding%20Challenge&description=Coding%20Challenge%20for%20BeUsable&packageName=li.tau.beusable.challenge&dependencies=devtools,lombok,configuration-processor,web,kafka,validation,actuator,restdocs,data-jpa,h2)
