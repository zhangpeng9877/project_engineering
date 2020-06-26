package com.itheima.test.health;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 测试poi读写excel表格数据
 */
public class TestPioByExcel {

    /**
     * 读取excel表格的数据：方式一
     * 通过遍历工作表获得行，遍历行获得单元格，最终获取单元格中的值
     *
     * @throws Exception
     */
    @Test
    public void exportReadExcel() throws Exception {
        // 创建工作蒲对象
        XSSFWorkbook workbook = new XSSFWorkbook("E:/04_spring框架/传智健康项目资源/第4天/资料/read.xlsx");
        // 获取工作表，既可以根据工作表的顺序获取，也可以根据工作表的名称获取
        XSSFSheet sheetAt = workbook.getSheetAt(0);
        // 遍历工作表获取的行对象
        for (Row row : sheetAt) {
            // 遍历行对象获取单元格对象
            for (Cell cell : row) {
                // 获得单元格中的值
                String value = cell.getStringCellValue();
                System.out.println(value);
            }
        }
        // 关闭连接
        workbook.close();
    }

    /**
     * 读取excel表格的数据：方式二
     * 获取工作表最后一个行号，从而根据行号获得行对象，通过行获取最后一个单元格索引，从而根据单元格索引获取每行的一个单元格对象
     *
     * @throws Exception
     */
    @Test
    public void test01() throws Exception {
        // 创建工作蒲对象
        XSSFWorkbook workbook = new XSSFWorkbook("E:/04_spring框架/传智健康项目资源/第4天/资料/read.xlsx");
        // 获取工作表，既可以根据工作表的顺序获取，也可以根据工作表的名称获取
        XSSFSheet sheetAt = workbook.getSheetAt(0);
        // 获取当前工作表最后一行的行号，行号从0开始
        int lastRowNum = sheetAt.getLastRowNum();
        for (int i = 0; i < lastRowNum; i++) {
            // 根据行号获取行对象
            XSSFRow row = sheetAt.getRow(i);
            short lastCellNum = row.getLastCellNum();
            for (short j = 0; j < lastCellNum; j++) {
                String value = row.getCell(j).getStringCellValue();
                System.out.println(value);
            }
        }
        // 关闭连接
        workbook.close();
    }

    /**
     * 将数据写入进excel表格中
     * 使用POI可以在内存中创建一个Excel文件并将数据写入到这个文件，最后通过输出流将内存中的Excel文件下载到磁盘
     * @throws IOException
     */
    @Test
    public void importWriteExcel() throws IOException {
        //在内存中创建一个Excel文件
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建工作表，指定工作表名称
        XSSFSheet sheet = workbook.createSheet("用户表");

        //创建行，0表示第一行
        XSSFRow row = sheet.createRow(0);
        //创建单元格，0表示第一个单元格
        row.createCell(0).setCellValue("编号");
        row.createCell(1).setCellValue("姓名");
        row.createCell(2).setCellValue("年龄");

        XSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("1");
        row1.createCell(1).setCellValue("小明");
        row1.createCell(2).setCellValue("10");

        XSSFRow row2 = sheet.createRow(2);
        row2.createCell(0).setCellValue("2");
        row2.createCell(1).setCellValue("小王");
        row2.createCell(2).setCellValue("20");

        //通过输出流将workbook对象下载到磁盘
        FileOutputStream out = new FileOutputStream("E:/04_spring框架/传智健康项目资源/第4天/资料/user.xlsx");
        workbook.write(out);
        out.flush();//刷新
        out.close();//关闭
        workbook.close();
    }
}
