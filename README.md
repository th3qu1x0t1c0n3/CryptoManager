# CryptoManager

# Lien / Link
[CryptoManager](https://cpm.quixotic.date/)

# Todo
1. Add Wallets
2. Find all transactions for each wallet
3. Find details of each transaction
4. Calculate Avg buy of holdings
5. Calculate Portfolio value
   1. Get entry price of each token?

# Pages 
- Allocations page. The allocations are listed here.

# Transactions
1. Wallet address to get transactions
2. Transaction hash to get the transaction details / Logs
3. Transaction logs to get the allocations
---
1. Get Set of contractAddresses
   - module=account&action=tokentx&address={{address}}&apikey={{api_Key}}
2. Get allocations using contract addresses


# Deployment
```
mvn clean package
java -jar target/CryptoManager-0.0.1-LTS.jar
```

Before massive change: `14b2c8b3b71e31f4b695debcee628e87bde14c82`
(Using onchain wallet)


