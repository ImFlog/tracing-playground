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
		zipkintracer.NewRecorder(collector, false, ":8082", "clerk"),
	)
	if err != nil {
		log.Fatal(err)
	}
	// Init the global tracer
	opentracing.InitGlobalTracer(tracer)
	log.Println("Starting HTTP service at 8082")

	http.HandleFunc("/fetchIngredients", func(w http.ResponseWriter, r *http.Request) {
		// Wire the request context to a span
		wireContext, err := opentracing.GlobalTracer().Extract(
			opentracing.HTTPHeaders,
			opentracing.HTTPHeadersCarrier(r.Header))
		if err != nil {
			log.Print(err)
		}
		getMilk(wireContext)
		getIce(wireContext)
		fmt.Fprint(w, "Good milk and ice for you !")
	})

	log.Fatal(http.ListenAndServe(":8082", nil))
}

func getMilk(context opentracing.SpanContext) {
	var serverSpan opentracing.Span
	appSpecificOperationName := "milk"
	// Create a new span (use the wired context)
	serverSpan = opentracing.StartSpan(
		appSpecificOperationName,
		ext.RPCServerOption(context))

	time.Sleep(100 * time.Millisecond)
	// Always close spans
	serverSpan.Finish()
}

func getIce(context opentracing.SpanContext) {
	var serverSpan opentracing.Span
	appSpecificOperationName := "ice"
	// Create a new span (use the wired context)
	serverSpan = opentracing.StartSpan(
		appSpecificOperationName,
		ext.RPCServerOption(context))

	time.Sleep(100 * time.Millisecond)
	// Always close spans
	serverSpan.Finish()
}