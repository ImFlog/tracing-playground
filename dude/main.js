var express = require('express')
var sleep = require('sleep');
var rest = require('rest');
var path = require('path');
// Zipkin part
var {Tracer, BatchRecorder} = require('zipkin');
var {HttpLogger} = require('zipkin-transport-http');
var zipkinMiddleware = require('zipkin-instrumentation-express').expressMiddleware;
var CLSContext = require('zipkin-context-cls');

var app = express();

var ctxImpl = new CLSContext('zipkin');

// Add a http transport to local zipkin instance
var recorder = new BatchRecorder({
  logger: new HttpLogger({
    endpoint: 'http://localhost:9411/api/v1/spans'
  })
});

var tracer = new Tracer({
    ctxImpl,
    recorder
});

// instrument the client
var {restInterceptor} = require('zipkin-instrumentation-cujojs-rest');
var zipkinRest = rest.wrap(restInterceptor, {tracer, serviceName: 'dude'});
// Allow cross-origin, traced requests. See http://enable-cors.org/server_expressjs.html
app.use((req, res, next) => {
  res.header('Access-Control-Allow-Origin', '*');
  res.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Accept, X-B3-TraceId, X-B3-ParentSpanId, X-B3-SpanId, X-B3-Sampled');
  next();
});

// Add the Zipkin middleware
app.use(zipkinMiddleware({
  tracer,
  serviceName: 'dude' // name of this application
}));

// Configure REST endpoints
app.use(express.static(path.join(__dirname, 'static')));
app.get('/', (req, res) => {
    res.render('../static/index.html')
});
app.get('/cocktail', (req,res) => {
    zipkinRest('http://localhost:8081/make')
        .then(
            (response) => res.send(response.entity),
            (response) => console.error("Error", response.status)
        )
});

// Run the server
app.listen(8080, "localhost", function(){
	console.log("Running on http://localhost:8080\n");
});