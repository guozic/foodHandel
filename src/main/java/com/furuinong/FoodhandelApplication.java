package com.furuinong;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FoodhandelApplication {

    public static void simpleRead() {
        String fileName = "G:\\work\\excelHandle\\foodhandel\\src\\main\\resources\\0630.xlsx";
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(fileName, Food.class, new FoodDataListener()).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
    }

    public static void main(String[] args) {
        simpleRead();
    }
}
