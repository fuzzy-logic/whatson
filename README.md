# whatson

Simple web application to retreive events from api.eventful.com


This application was structured using Domain Driven Design Principles with the following layers:

* UI (User interface & presentation)
* Application (Apllication Logic and Services as entry point)
* Domain (Business logic)
* Infrastruture (typically utilities and IO)



### Build and run whatson with maven:

```
mvn package && java -jar target/whatson-0.0.1.jar
```

Now open a browser and go to address:

```
http://loclahost:8080
```




### Quick build and run skipping tests:

```
mvn package  -Dmaven.test.skip=true && java -jar target/whatson-0.0.1.jar
```


