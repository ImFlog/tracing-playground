package main

import (
	"net/http"
	"log"
	"fmt"
	"time"
	"github.com/opentracing/opentracing-go"
	"github.com/opentracing/opentracing-go/ext"
	"github.com/openzipkin/zipkin-go-opentracing"
)

func main() {
	// Initialize zipkin http collector
	collector, err := zipkintracer.NewHTTPCollector("http://localhost:9411/api/v1/spans")
	// Create the tracer
	tracer, err := zipkintracer.NewTracer(
		zipkintracer.NewRecorder(collector, false, "localhost:8082", "spice"),
	)
	if err != nil {
		log.Fatal(err)
	}
	// Init the global tracer
	opentracing.InitGlobalTracer(tracer)
	log.Println("Starting HTTP service at 8082")

	// Endpoint which returns html graph
	http.HandleFunc("/addSpice", func(w http.ResponseWriter, r *http.Request) {
		var serverSpan opentracing.Span
		appSpecificOperationName := "spicing"
		// Wire the request context to a span
		wireContext, err := opentracing.GlobalTracer().Extract(
			opentracing.HTTPHeaders,
			opentracing.HTTPHeadersCarrier(r.Header))
		if err != nil {
			log.Print(err)
		}
		// Create a new span (use the wired context)
		serverSpan = opentracing.StartSpan(
			appSpecificOperationName,
			ext.RPCServerOption(wireContext))

		// Always close spans
		defer serverSpan.Finish()

		time.Sleep(100 * time.Millisecond)
		fmt.Fprint(w, "Some spice !")
	})

	log.Fatal(http.ListenAndServe(":8082", nil))
}