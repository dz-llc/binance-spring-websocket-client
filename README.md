# Binance WebSocket Client

> This project is a simple Binance WebSocket Client that can be used to subscribe to Binance WebSocket streams. Included in this project is a simple example of how to use the client. As the application streams from Binance, it will log the data to the console as it calculates the median value of each symbol.

> As of now, the Binance stream is not available in the US. If you are in the US, you will need to use a VPN to access the stream. 

## Installation

```shell
$ git clone https://github.com/dz-llc/binance-spring-websocket-client.git
$ cd binance-spring-websocket-client
$ ./mvnw clean install
```

## Usage

```shell
$ ./mvwn spring-boot:run
```