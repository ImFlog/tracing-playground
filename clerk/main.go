package main

import (
	"fmt"
	"log"
	"net/http"
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
		serverSpan := opentracing.StartSpan("clerk", ext.RPCServerOption(wireContext))
		defer serverSpan.Finish()

		getMilk(serverSpan.Context())
		getIce(serverSpan.Context())
		fmt.Fprint(w, "Good milk and ice for you !")
	})

	log.Fatal(http.ListenAndServe(":8082", nil))
}

func getMilk(parentContext opentracing.SpanContext) {
	// Create a new span (using the parent span context)
	milkSpan := opentracing.StartSpan(
		"milk",
		opentracing.ChildOf(parentContext))
	// Always close spans
	defer milkSpan.Finish()

	time.Sleep(100 * time.Millisecond)
}

func getIce(parentContext opentracing.SpanContext) {
	// Create a new span (use the parent span context)
	iceSpan := opentracing.StartSpan(
		"ice",
		opentracing.ChildOf(parentContext))
	// Always close spans
	defer iceSpan.Finish()

	time.Sleep(150 * time.Millisecond)
}
