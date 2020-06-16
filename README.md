Example of a scalable service returning unique integer numbers.

####Build project
- Run `mvn clean package` from the project root
- Find two artifacts `mediator/target/mediator.jar`, `scalable-node/target/scalable-node.jar`

####Service Mediator
- Service with a single endpoint `GET range` responsible for providing ranges of integer (64 bit) numbers.
- All ranges returned are guaranteed to not intersect each other.
 
 NOTE: current implementation does not persist state between runs, 
       so the contract can be fulfilled only within a context of a single running instance.
 
####Service ScalableNode
- Service with a single endpoint `GET id` responsible for providing unique of integer (64 bit) numbers.
- Relies on `mediator` service for getting not consumed ranges of numbers to provide values from.

####Example of two scalable nodes setup on a single machine
- Prepare jar files (see `Build project` section)
- Inspect `application.properties` to find property names and use them for tweaking the nodes and mediator.
- Run `mediator`: `java -jar mediator.jar --server.port=12345`
- Run `scalable node 1`: `java -jar scalable-node.jar  --server.port=13001  --ranges.source.location=localhost:12345/range`
- Run `scalable node 2`: `java -jar scalable-node.jar  --server.port=13002  --ranges.source.location=localhost:12345/range`
- First and second nodes are available on `localhost:13001`, `localhost:13002` accodingly.
- Try `GET id` endpoint on each node: i.e. `GET http://localhost:13002/id`
- Set up a gateway (i.e. load balancer) to apply nodes usage strategy. *! not included in this implementation*
- It is guaranteed that numbers returned from `GET id` are unique across all nodes.

See sample run for windows in `example/run_two_nodes.bat` file


####Performance estimation
0. assume `ranges.size.basic` is 5_000_000
1. instance of `mediator` has expected response time 10-20ms serving 10 requests per second.
2. it is supposed to have low contention (1 request per second or less) on the `mediator`
3. to reduce contention try to increase `ranges.size.basic`
4. a single `scalable-node` instance on a sample machine (4 cores x 2GHz) can provide 1000 RPS (with median response time below 300 ms)

####Tuning recommendations
It is recommended to increase `ranges.size.basic` as much as possible, so each node could work autonomously 
for a longer period of time.

For instance, say the gateway receives 1_000_000 RPS and equally distributes it among the 25 instances of `scalable-node`.
Then each node serves 40_000 RPS, i.e. utilizes 40_000 unique numbers per second.
If `ranges.size.basic` is 800_000, then approximately every 20 seconds a node will ask `mediator` for a new range.
So `mediator` will be supposed to serve between 1 and 2 requests per second in average.

In case of when a node is predominantly long living, it is better to increase `ranges.size.basic`,
so each `scalable-node` could work autonomously most of the time without bothering `mediator`.

In case of when a node is predominantly short living (unstable environment with crashing hardware),
find a suitable value for `ranges.size.basic` to avoid wasting large amounts of long numbers for nothing.
Mind that there are 2^63 of positive integers available, so even with throughput 1_000_000_000 RPS,
this service will be able to fulfill its contract for 10^10 seconds (~ 300 years)
