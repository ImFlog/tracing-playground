# Distributed tracing for dummies
A distributed tracing support for the devoxx conference.

To get started:
- run a docker image for zipkin : `docker container run --name zipkin -p 9411:9411 openzipkin/zipkin`
- launch winemaker and warm (java applications)
- launch spice : `go run main.go`
- launch wine (node js application)
- Go to [the winemaker UI](http://localhost:8080) and ask for some wine
- See results on [Zipkin](http://localhost:9411/)

For more information check out the [slides](https://github.com/ImFlog/DummyTracing/blob/master/slides/slides.pdf).