import requests
import json

ARBISCAN_API_KEY = 'VGT8MHGKBMVG4XX6M5NGR5QDZNIXF2RFN3'
TX_HASH = '0xdfb4ed11d62b1ffd95b7e388fdc56520b19859cb3fa4614d7eb5befe53c1d2b1'
WALLET_ADDRESS = '0x387bbee0ba094Ee6dC74d6F9dFDdbE66A3C2D422'

# Step 1: Fetch Transaction Receipt Logs
receipt_url = f"https://api.arbiscan.io/api?module=proxy&action=eth_getTransactionReceipt&txhash={TX_HASH}&apikey={ARBISCAN_API_KEY}"
receipt_response = requests.get(receipt_url)
receipt_data = receipt_response.json()

receipt_response = requests.get(receipt_url)
receipt_data = receipt_response.json()

# Step 2: Parse Logs to Determine Start and End Tokens
logs = receipt_data.get('result', {}).get('logs', [])

transfers = []

for log in logs:
    # Check if the log is a Transfer event (ERC-20)
    if log['topics'][0] == '0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef':
        from_address = '0x' + log['topics'][1][26:]
        to_address = '0x' + log['topics'][2][26:]
        value = int(log['data'], 16)

        transfer = {
            'token_address': log['address'],
            'from': from_address,
            'to': to_address,
            'value': value
        }

        transfers.append(transfer)
        print(transfer, log)

# Filter out the transfers involving the wallet address
start_token_transfers = [t for t in transfers if t['from'].lower() == WALLET_ADDRESS.lower()]
end_token_transfers = [t for t in transfers if t['to'].lower() == WALLET_ADDRESS.lower()]

if len(start_token_transfers) == 0:
    # Step 1: Create a list of token_address values from end_token_transfers
    end_token_addresses = [t['token_address'] for t in end_token_transfers]

    # Step 2: Filter start_token_transfers to only include transfers where the token_address is not in the list from step 1
    filtered_start_token_transfers = [t for t in transfers if t['token_address'] not in end_token_addresses]

    # Step 3: Find the transfer with the highest value from the filtered list
    highest_value_transfer = max(filtered_start_token_transfers, key=lambda t: t['value'], default=None)
    start_token_transfers.append(highest_value_transfer)

    if highest_value_transfer is not None:
        print(f"The highest value transfer for a token_address not in end_token_transfers is: {highest_value_transfer}")
    else:
        print("No transfers found for a token_address not in end_token_transfers.")

# Aggregate the values for start token and end token
start_tokens = {}
end_tokens = {}

for transfer in start_token_transfers:
    token_address = transfer['token_address']
    value = transfer['value']
    start_tokens[token_address] = start_tokens.get(token_address, 0) + value

for transfer in end_token_transfers:
    token_address = transfer['token_address']
    value = transfer['value']
    end_tokens[token_address] = end_tokens.get(token_address, 0) + value

# Print Start Tokens and End Tokens
print("Start Tokens (Tokens Used to Buy):")
print(json.dumps(start_tokens, indent=2))

print("End Tokens (Tokens Bought):")
print(json.dumps(end_tokens, indent=2))
