# whatson

Sample web application to retrieve events from http://api.eventful.com

It's not pretty, it's not perfect, but it is just about passable :-)


This application was structured using Domain Driven Design Principles with the following layers:

* UI (User interface & presentation)
* Application (Apllication Logic and Services as entry point)
* Domain (Business logic)
* Infrastruture (typically utilities and IO)


## Features & Scope

The /events.html will present a dropdown list of categories from which to see events occuring ove the next 7 days.


### Build and run Whatson with maven:

```
mvn package && java -jar target/whatson-0.0.1.jar
```

## Take a look

Open a browser and go to address:

```
http://localhost:8080
```




# Misc

### Quick build and run skipping tests:

```
mvn package  -Dmaven.test.skip=true && java -jar target/whatson-0.0.1.jar
```


