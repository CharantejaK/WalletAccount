# WalletAccount

1) Data.sql is loaded on start of the application and few records records are inserted into the tables whith Players and Account Information.

After the players and accounts are loaded inot the Database you can access the below link to save transactions 

2) http://localhost:8080/saveTransaction (POST METHOD)

####SAMPLE 1
{
  "playerId":"1366",
  "transactionCode":"TRAN132",
  "transactionType":"debit",
  "transactionAmount":"10"
  
}

####SAMPLE 2
{
  "playerId":"1364",
  "transactionCode":"TRAN136",
  "transactionType":"debit",
  "transactionAmount":"10"
  
}

2) http://localhost:8080/getBalance?playerId=1364 (GET) 

3) http://localhost:8080//transactionHistory?playerId=1364 (GET) 

4) http://localhost:8080/swagger-ui.html#/transaction-controller (SWAGGER CONTRACT for the SERVCIES)