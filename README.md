# ParaBank
API Test Automation test cases for validating successful transactions in paraBank

## Prerequisites
 ### java installed
 ### maven installed
 ### dependencies in pom.xml isntalled 
 ### allure installed

## Installation 
 ### intall java    : from oracle --> openjdk 21.0.2 2024-01-16
 ### install maven  : from maven repo  --> version: "14.3.1"
 ### install allure : brew install allure --> version: "2.28.0"

## Configuration


## Running the Tests
### to run the tests: mvn test 
### to generate the resport: allure serve 

## Test Report
<img width="1676" alt="Screenshot 2024-04-18 at 10 24 30 PM" src="https://github.com/HagarAbdelwahab/ParaBank/assets/40496950/e24d0119-55e0-47e2-9dc9-bae5e2bd32ed">


## Test Structure
 - main
    - java
      - constants
      - services
      - utils
    - resources
 - test
    - java
       - moneyTransfer
       - Transfers
    - resources
      - testData
      - Allure
