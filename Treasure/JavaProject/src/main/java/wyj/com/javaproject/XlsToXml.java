package wyj.com.javaproject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class XlsToXml {
    public static void main(String[] args) {
        String filepath = "D:\\Android_String.xls";
        try {
            Workbook workbook = Workbook.getWorkbook(new File(filepath));
            Sheet sheet = workbook.getSheet(0);
            int rows = sheet.getRows();//行数
            int columns = sheet.getColumns();//列数
            StringBuilder sb = new StringBuilder();
            //第一行 第2列
            for (int i = 0; i < columns; i++) {//列
                //循环生成语言文件 从第一列开始
                Cell cell = sheet.getCell(i, 0);
                String contents = cell.getContents();
                File file = new File(contents + "_strings.xml");
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                //从第2行开始 过滤第一行的 KEY 和 VALUE
                for (int j = 1; j < rows; j++) {//行
                    // j为行数,getCell("列号","行号")
                    sb.delete(0, sb.toString().length());
                    //第1行时候 要加上 <resources>
                    if (j == 1) {
                        sb.append("<resources>\n");
                    }
                    sb.append("\t<string name=\"");
                    Cell c = sheet.getCell(i, j);
                    String s = c.getContents();
                    Cell cellValue = sheet.getCell(0, j);
                    String contValue = cellValue.getContents();
                    sb.append(contValue);
                    sb.append("\">");
                    sb.append(s);
                    sb.append("</string>");
                    if (j == rows - 1) {
                        sb.append("\n</resources>");
                    }
                    bw.write(sb.toString().trim());
                    bw.write("\n\t");
                    bw.flush();
                }
                bw.newLine();
                bw.flush();
            }
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
