package quixotic.projects.cryptomanager.service;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import quixotic.projects.cryptomanager.model.CoinTransaction;
import quixotic.projects.cryptomanager.model.Transaction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExcelHandler {

    private static final String PATH = "./src/main/resources/excel/";

    public List<Transaction> readTransactionsFromExcel(String name) {
        List<Transaction> transactions = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(PATH + name + ".xlsx")) {
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0);

            // Skip the header row
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);

                showRow(row);

                if (row == null || row.getCell(0) == null) {
                    System.out.println("Row is null");
                    break;
                }

                Transaction transaction = new Transaction();
                transaction.setId((long) row.getCell(0).getNumericCellValue());
                transaction.setToCoin(CoinTransaction.builder()
                        .name(row.getCell(1).getStringCellValue())
                        .quantity(row.getCell(2).getNumericCellValue())
                        .value(row.getCell(3).getNumericCellValue())
                        .unitValue(row.getCell(4).getNumericCellValue())
                        .build()
                );
                transaction.setFromCoin(CoinTransaction.builder()
                        .name(row.getCell(5).getStringCellValue())
                        .quantity(row.getCell(6).getNumericCellValue())
                        .value(row.getCell(7).getNumericCellValue())
                        .unitValue(row.getCell(8).getNumericCellValue())
                        .build()
                );
                transaction.setTransactionDate(LocalDate.parse(row.getCell(9).getStringCellValue()));
                transaction.setWallet(row.getCell(10).getStringCellValue());
                transaction.setExchange(row.getCell(11).getStringCellValue());

                transactions.add(transaction);
            }

            workbook.close();
        } catch (IOException | EncryptedDocumentException ex) {
            ex.printStackTrace();
        }

        return transactions;
    }

    public void writeTransactionsToExcel(List<Transaction> transactions, String name) {
        Workbook workbook;
        Sheet sheet;
        int rowCount;

        File file = new File(PATH + name + ".xlsx");

        if (file.exists()) {
            // If file exists, read the existing file
            try (FileInputStream fis = new FileInputStream(file)) {
                workbook = WorkbookFactory.create(fis);
                sheet = workbook.getSheetAt(0);
                rowCount = sheet.getPhysicalNumberOfRows();
            } catch (IOException | EncryptedDocumentException ex) {
                ex.printStackTrace();
                return;
            }
        } else {
            // If file does not exist, create a new workbook and sheet
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("Transactions");

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

            rowCount = 1;
        }

        // Write transactions
        for (Transaction transaction : transactions) {

            boolean exists = false;
            for (int i = 1; i < rowCount; i++) {
                Row row = sheet.getRow(i);

                if (row == null || row.getCell(0) == null) {
                    System.out.println("Row is null");
                    break;
                }

                if (row.getCell(0).getNumericCellValue() == transaction.getId()) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                Row row = sheet.createRow(rowCount++);

                row.createCell(0).setCellValue(transaction.getId());
                row.createCell(1).setCellValue(transaction.getToCoinName());
                row.createCell(2).setCellValue(transaction.getToCoinQuantity());
                row.createCell(3).setCellValue(transaction.getToCoinValue());
                row.createCell(4).setCellValue(transaction.getToCoinUnitValue());
                row.createCell(5).setCellValue(transaction.getFromCoinName());
                row.createCell(6).setCellValue(transaction.getFromCoinQuantity());
                row.createCell(7).setCellValue(transaction.getFromCoinValue());
                row.createCell(8).setCellValue(transaction.getFromCoinUnitValue());
                row.createCell(9).setCellValue(transaction.getTransactionDate().toString());
                row.createCell(10).setCellValue(transaction.getWallet());
                row.createCell(11).setCellValue(transaction.getExchange());
            }
        }

        // Write to file
        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            workbook.write(fileOut);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showRow(Row row) {
        if (row == null || row.getCell(0) == null) {
            System.out.println("Row is null or cell is null");
            return;
        }
        System.out.println("Row: " + row.getRowNum());
        System.out.println(row.getCell(0).getNumericCellValue());
        System.out.println(row.getCell(1).getStringCellValue());
        System.out.println(row.getCell(2).getNumericCellValue());
        System.out.println(row.getCell(3).getNumericCellValue());
        System.out.println(row.getCell(4).getNumericCellValue());
        System.out.println(row.getCell(5).getStringCellValue());
        System.out.println(row.getCell(6).getNumericCellValue());
        System.out.println(row.getCell(7).getNumericCellValue());
        System.out.println(row.getCell(8).getNumericCellValue());
        System.out.println(row.getCell(9).getStringCellValue());
        System.out.println(row.getCell(10).getStringCellValue());
        System.out.println(row.getCell(11).getStringCellValue());
    }
}

