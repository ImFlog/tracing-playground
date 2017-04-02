# Tracing playground
A distributed tracing support for the devoxx conference.

To get started:
- run a zipkin server :`java -jar zipkin.jar` or `docker container run --name zipkin -p 9411:9411 openzipkin/zipkin` for docker
- launch dude `node dude/main.js`
- launch barman and shaker (java applications)
- launch clerk : `go run clerk/main.go`

- Go to [the dude UI](http://localhost:8080) and ask for some white russian
- See results on [Zipkin](http://localhost:9411/)

For more information check out the [slides](https://github.com/ImFlog/tracing-playground/blob/master/slides/slides.pdf).