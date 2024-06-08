from datetime import datetime

import openpyxl


def add_single_quote_to_dates(file_path, sheet_name, column_letter):
    # Load the workbook and the specified sheet
    workbook = openpyxl.load_workbook(file_path)
    sheet = workbook[sheet_name]

    # Iterate over the specified column
    for row in range(1, sheet.max_row + 1):
        cell = sheet[f"{column_letter}{row}"]
        if cell.value:
            if isinstance(cell.value, datetime):
                # Directly add a single quote before the date if it's a datetime object
                cell.value = "'" + cell.value.strftime("%Y-%m-%d")
                cell.number_format = '@'  # Set cell format to text

            elif isinstance(cell.value, str):
                # Check if the cell already starts with a single quote
                if not cell.value.startswith("'"):
                    try:
                        # Attempt to parse the cell value as a date in the expected format
                        date_value = datetime.strptime(cell.value, "%Y-%m-%d")
                        # Add a single quote before the date
                        cell.value = "'" + date_value.strftime("%Y-%m-%d")
                        cell.number_format = '@'  # Set cell format to text

                    except ValueError:
                        # Skip if it's not a date in the expected format
                        pass

    # Save the modified workbook
    workbook.save(file_path)


# Example usage
file_path = './excel/Crypto_Whale_Copy.xlsx'  # Replace with the path to your Excel file
sheet_name = 'Transactions'  # Replace with the name of your sheet
column_letter = 'J'  # Replace with the letter of the column containing the dates

add_single_quote_to_dates(file_path, sheet_name, column_letter)

print("DONE!")