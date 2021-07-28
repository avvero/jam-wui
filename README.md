# Web UI for JAM - https://github.com/avvero/jam

Gradle composite approach is been using and jam project is required to be at `../jam`. 
But it could be changed in `settings.gradle`.

## Launching

```properties
docker run avvero/jam-wui:latest
```

## Properties

```properties
app.jira.host = http://localhost:8081
app.jira.username = admin
app.jira.password = admin
```

## Application endpoints

`/dependencies?checkout=?` - Checkouts issue by key and returns schema representation

`/dependencies?issueCode=?` - Returns graphviz schema with dependencies