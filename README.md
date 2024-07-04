# CryptoManager

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

Wallet: `0x387bbee0ba094Ee6dC74d6F9dFDdbE66A3C2D422`

