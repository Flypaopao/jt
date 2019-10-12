package jt.com;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.*;

public class TestExcel {
    static String filePath = "D:\\java\\work.txt";

    @Test
    public void test01() throws IOException, InvalidFormatException {
        //Wo workbook = new XSSFWorkbook(new FileInputStream(new File(filePath)));
        Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(filePath)));
        // 获取第一个表单
        Sheet first = workbook.getSheetAt(0);
        for (int i=0;i<10000;i++){
            Row row = first.getRow(i);
            for (int j=0;j<11;j++){
                //首行
                if (i==0){
                    row.createCell(j).setCellValue("coulumn"+j);
                }else {
                    //数据
                    if (j ==0 ){
                        CellUtil.createCell(row , j, String.valueOf(i));
                    }else {
                        CellUtil.createCell(row , j, String.valueOf(Math.random()));
                    }
                }
            }
        }
        //写入文件
        FileOutputStream out = new FileOutputStream("workbook.xlsx");
        workbook.write(out);
        out.close();
    }
}
