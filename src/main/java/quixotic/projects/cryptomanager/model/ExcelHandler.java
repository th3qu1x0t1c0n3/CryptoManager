package quixotic.projects.cryptomanager.model;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelHandler {

    public List<Transaction> readExcel(String path) {
        List<Transaction> transactions = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(path + ".xlsx")) {
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0);

            // Skip the header row
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);

                Transaction transaction = new Transaction();
                transaction.setId((long) row.getCell(0).getNumericCellValue());
                // Set more properties as needed

                transactions.add(transaction);
            }

            workbook.close();
        } catch (IOException | EncryptedDocumentException ex) {
            ex.printStackTrace();
        }

        return transactions;
    }

    public void writeExcel(List<Transaction> transactions, String path) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Transactions");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Transaction ID");
            headerRow.createCell(1).setCellValue("To Coin Name");
            headerRow.createCell(2).setCellValue("To Coin Quantity");
            headerRow.createCell(3).setCellValue("To Coin Value");
            headerRow.createCell(4).setCellValue("To Coin Unit Value");
            headerRow.createCell(5).setCellValue("From Coin Name");
            headerRow.createCell(6).setCellValue("From Coin Quantity");
            headerRow.createCell(7).setCellValue("From Coin Value");
            headerRow.createCell(8).setCellValue("From Coin Unit Value");
            headerRow.createCell(9).setCellValue("Transaction Date");
            headerRow.createCell(10).setCellValue("Wallet");
            headerRow.createCell(11).setCellValue("Exchange");
            // Add more headers as needed

            // Write user transactions
//            List<Transaction> transactions = user.getTransactions();
            for (int i = 0; i < transactions.size(); i++) {
                Transaction transaction = transactions.get(i);
                Row row = sheet.createRow(i + 1); // +1 because header is at 0

                row.createCell(0).setCellValue(transaction.getId());
                row.createCell(1).setCellValue(transaction.getToCoinName());
                row.createCell(2).setCellValue(transaction.getToCoinQuantity());
                row.createCell(3).setCellValue(transaction.getToCoinValue());
                row.createCell(4).setCellValue(transaction.getToCoinUnitValue());
                row.createCell(5).setCellValue(transaction.getFromCoinName());
                row.createCell(6).setCellValue(transaction.getFromCoinQuantity());
                row.createCell(7).setCellValue(transaction.getFromCoinValue());
                row.createCell(8).setCellValue(transaction.getFromCoinUnitValue());
                row.createCell(9).setCellValue(transaction.getTransactionDate());
                row.createCell(10).setCellValue(transaction.getWallet());
                row.createCell(11).setCellValue(transaction.getExchange());
                // Add more cells as needed
            }

            // Write to file
            try (FileOutputStream fileOut = new FileOutputStream(path + ".xlsx")) {
                workbook.write(fileOut);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

