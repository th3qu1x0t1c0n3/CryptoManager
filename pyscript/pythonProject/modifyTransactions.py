import pandas as pd

# Step 1: Read the CSV file
df = pd.read_csv('./excel/transactions.csv', quotechar='"')

# Step 2: Write the DataFrame to an Excel file
df.to_excel('./excel/new_file.xlsx', index=False)

# Step 3: Read the Excel file
df = pd.read_excel('./excel/new_file.xlsx')

# Step 4: Iterate over each cell in the DataFrame
for col in df.columns:
    # If a cell's value contains a comma, split the cell's value by the comma and expand the split values into new
    # columns
    df = df.join(df[col].str.split(',', expand=True).add_prefix(f'{col}_')).drop([col], axis=1)

# Step 5: Write the modified DataFrame back to the Excel file
df.to_excel('./excel/new_file.xlsx', index=False)