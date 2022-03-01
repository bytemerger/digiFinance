
# digicore



## Indices

* [Ungrouped](#ungrouped)

  * [Create Account](#1-create-account)
  * [Deposit Cash](#2-deposit-cash)
  * [Get Account](#3-get-account)
  * [Get Statement](#4-get-statement)
  * [Login](#5-login)
  * [Withdraw Cash](#6-withdraw-cash)


--------


## Ungrouped



### 1. Create Account


create a new account


***Endpoint:***

```bash
Method: POST
Type: RAW
URL: localhost:8080/api/v1/create_account
```



***Body:***

```js        
{
    "initialDeposit":800,
    "accountName":"Adamu Cheta",
    "accountPassword":"test1234"
}
```



***More example Requests/Responses:***


##### I. Example Request: Create Account Successfully



***Body:***

```js        
{
    "initialDeposit":800,
    "accountName":"Adamu Cheta",
    "accountPassword":"test1234"
}
```



##### I. Example Response: Create Account Successfully
```js
{
    "responseCode": 200,
    "success": true,
    "message": "successfully created",
    "data": {
        "accountNumber": "0150254002"
    }
}
```


***Status Code:*** 200

<br>



### 2. Deposit Cash


This route deposits cash


***Endpoint:***

```bash
Method: POST
Type: RAW
URL: localhost:8080/api/v1/deposit
```



***Body:***

```js        
{
    "accountNumber":"6513138112",
    "amount":100.00
}
```



### 3. Get Account


Get a particular account


***Endpoint:***

```bash
Method: GET
Type: RAW
URL: localhost:8080/api/v1/account_info/:accountNumber
```



***URL variables:***

| Key | Value | Description |
| --- | ------|-------------|
| accountNumber | 28387876445 |  |



***Body:***

```js        
{}
```



### 4. Get Statement


Get the statement of an account
This is the list of all the transactions by the account


***Endpoint:***

```bash
Method: GET
Type: RAW
URL: localhost:8080/api/v1/account_statement/:accountNumber
```



***URL variables:***

| Key | Value | Description |
| --- | ------|-------------|
| accountNumber | 6513138112 |  |



***Body:***

```js        
{}
```



***More example Requests/Responses:***


##### I. Example Request: UnAuthoried Request



***Query:***

| Key | Value | Description |
| --- | ------|-------------|
| accountNumber | 6513138112 |  |



***Body:***

```js        
{}
```



##### I. Example Response: UnAuthoried Request
```js
{
    "responseCode": 401,
    "success": false,
    "message": "401 UNAUTHORIZED \"JWT strings must contain exactly 2 period characters. Found: 0 Invalid JWT token, try logging in\""
}
```


***Status Code:*** 401

<br>



### 5. Login


Login in to access protected routes


***Endpoint:***

```bash
Method: POST
Type: RAW
URL: localhost:8080/api/v1/login
```



***Body:***

```js        
{
    "accountNumber":"0150254002",
    "accountPassword":"test1234"
}
```



***More example Requests/Responses:***


##### I. Example Request: Login Successful



***Body:***

```js        
{
    "accountNumber":"0150254002",
    "accountPassword":"test1234"
}
```



##### I. Example Response: Login Successful
```js
{
    "success": true,
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwMTUwMjU0MDAyIiwiYXV0aCI6eyJhY2NvdW50TmFtZSI6IkFkYW11IENoZXRhIiwiZW5hYmxlZCI6InRydWUifSwiZXhwIjoxNjQ2MTIyMjI3fQ.neYY2tKyYtFWnU-kd1qZoaTKk8gStF2fKIOmbb0POFM"
}
```


***Status Code:*** 200

<br>



### 6. Withdraw Cash


This route Withdraws cash


***Endpoint:***

```bash
Method: POST
Type: RAW
URL: localhost:8080/api/v1/withdrawal
```



***Body:***

```js        
{
    "accountNumber":"6513138112",
    "amount":100.00,
    "accountPassword":"test1234"
}
```



---
[Back to top](#digicore)
> Made with &#9829; by [thedevsaddam](https://github.com/thedevsaddam) | Generated at: 2022-03-01 08:53:22 by [docgen](https://github.com/thedevsaddam/docgen)
