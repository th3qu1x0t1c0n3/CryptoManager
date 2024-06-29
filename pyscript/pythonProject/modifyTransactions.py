# import pandas as pd
# import csv
# from openpyxl import Workbook
# from openpyxl import load_workbook
#
# path = './excel/transactions.csv'
#
# with open(path, 'r') as f:
#     # Create a CSV reader
#     reader = csv.reader(f, quotechar='"')
#
#     # Create a new Excel workbook
#     wb = Workbook()
#
#     # Create a new sheet in the workbook
#     ws = wb.create_sheet('New Sheet')
#
#     # For each row in the CSV file, write the row to a new row in the Excel sheet
#     for row in reader:
#         ws.append(row)
#
#     # Save the workbook
#     wb.save('./excel/new_file.xlsx')

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