### step to optimize springboot support 1Mrs

1. React programing
2. Query optimization
2. Connection pool tuning
3. Implemented strategy cache
4. Serialization optimization
5. Thread pool and connection pooling
6. Horizontal scaling with kubernetes

## Protect system
1. resilience4J (Rate Limiter, Retry, TimeLimiter, Cache)

## vegata test
## grafana board JVM (4701)
echo "GET http://localhost:6677/ticket/1/detail/1" | vegeta attack -name=2000qps -duration=10s -rate=100 | tee benchmark/results_2000qps.bin | vegeta report
## node exporter (1860)

## mysqld (7362)

## redis-exporter (763)

## test performance wrk -t12 -c2000 -d2m http://localhost:6677/ticket/1/detail/1

## Using Lombok Library With JDK 23

<properties>
	...
	<java.version>23</java.version>
	<maven-compiler-plugin.version>3.13.0</maven-compiler-plugin.version>
	<maven.compiler.proc>full</maven.compiler.proc>
</properties>
<build>
	<plugins>
		...
		<plugin>
			<groupId>org.apache.maven.plugins</groupId>
			<artifactId>maven-compiler-plugin</artifactId>
			<version>${maven-compiler-plugin.version}</version>
			<configuration>
				<source>${java.version}</source>
				<target>${java.version}</target>
			</configuration>
		</plugin>
	</plugins>
</build>

