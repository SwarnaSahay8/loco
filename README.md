# Loco Transaction Assignment

## APIs
### 1. PUT /transactionservice/transaction/:transaction_id : This api will upsert transaction details to db
```bash
curl --location --request PUT 'http://localhost:4000/transactionservice/transaction/5' \
--header 'Content-Type: application/json' \
--data-raw '{
  "amount": 30,
  "type": "bus",
  "parent_id": 2
}'
```

### 2. GET /transactionservice/types/:type: This api gives the list of transaction_ids belonging to given type
```bash
curl --location --request GET 'http://localhost:4000/transactionservice/types/car'
```

### 3. GET /transactionservice/sum/:transaction_id: This api gives total sum of a transaction
```bash
curl --location --request GET 'http://localhost:4000/transactionservice/sum/2'
```

## Tech dependencies
* Java 11.0.5+
* [Spark-java](http://sparkjava.com/) framework
* Postgres 11.2
* gradle

### Java11 (for macOS)
```bash
brew cask install java11
```
This project is build with java 11.

### Gradle (for macOS)
```bash
brew install gradle
```
It will directly install latest version of gradle. This project is build with gradle 7.1 . You can verify the versions [here](https://gradle.org/install/).

### Postgres11
Download the postgres 11 from [here](https://postgresapp.com/).

## Test instructions
```bash
./gradlew clean test
```

## Build instructions
```bash
./gradlew clean build
```

## Run instruction
**For running server:**
```bash
docker-compose up -d
./gradlew clean build run
```
Note: For above commands make sure you have docker installed. You can download docker from [here](https://hub.docker.com/editions/community/docker-ce-desktop-mac) 

### Author
Swarna
