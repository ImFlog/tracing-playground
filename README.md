# Distributed tracing for dummies
A distributed tracing support for the snowcamp.io conference.

To get started:
- run a docker image for zipkin : `docker container run --name zipkin -p 9411:9411 openzipkin/zipkin`
- Then launch the four applications (winemaker, wine, spice, warm)
- Go to [the winemaker UI](http://localhost:8080) and ask for some wine
- See results on [Zipkin](http://localhost:9411/)

For more information check out the slides in `support` folder.