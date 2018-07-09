package wyj.com.javaproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class XmlToXls {
    //txt文本路径
    private static String txtFilePath;
    //xls路径
    private static String xlsFilePath = "D:\\Android_String.xls";

    public static void main(String args[]) {
        txtFilePath = chooseStrings();
        if (txtFilePath != null) {
            String inputValue = JOptionPane.showInputDialog("请输入目标语言(英文,分隔)");
            if (inputValue != null) {
                String[] languages = inputValue.split(",");
                for (String language : languages) {
                    System.out.println(language);
                }
                getTxtInfos(languages);
            }
        }
    }

    private static void getTxtInfos(String[] languages) {
        WritableWorkbook book = null;
        BufferedReader bufferedReader = null;
        try {
            // 创建一个xls文件
            book = Workbook.createWorkbook(new File(xlsFilePath));
            // 生成名为'商品信息'的工作表，这里参数0表示第一页
            WritableSheet sheet = book.createSheet("Android", 0);
            // 这里注意指定文件的编码格式
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(txtFilePath), "UTF-8"));
            String element = null;
            int index = 0;
            Label labelKey = new Label(0, 0, "KEY");
            Label labelValue = new Label(1, 0, "VALUE");
            // 将定义好列名添加到工作表中
            sheet.addCell(labelKey);
            sheet.addCell(labelValue);

            for (int i = 0; i < languages.length; i++) {
                sheet.addCell(new Label(2 + i, 0, languages[i]));
            }


            while ((element = bufferedReader.readLine()) != null) {
                //如果是此行为空，则跳过
                if (element.trim().equals("")) {
                    continue;
                }
                // 第一行作为每列名称
                // <string name="shake_tip">点击右上角,连接设备</string>
                String[] str = element.trim().split("\">");
                if (str != null && str.length == 2) {
                    String s0 = str[0];//<string name="shake_tip
                    String s1 = str[1];//点击右上角,连接设备</string>
                    index++;
                    //这是第一列
                    String key = s0.substring(14);
                    Label label1 = new Label(0, index, key);
                    // 将定义好列名添加到工作表中
                    sheet.addCell(label1);


                    String value = s1.substring(0, s1.length() - 9);
                    System.out.println(index + "---" + value);
                    Label label2 = new Label(1, index, value);
                    sheet.addCell(label2);
                }
            }
            book.write();
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String chooseStrings() {
        int result = 0;
        String path = null;
        JFileChooser fileChooser = new JFileChooser();
        FileSystemView fsv = FileSystemView.getFileSystemView();  //注意了，这里重要的一句
        System.out.println(fsv.getHomeDirectory());                //得到桌面路径
        fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
        fileChooser.setDialogTitle("Please select the String file to be converted...");
        fileChooser.setApproveButtonText("OK");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        result = fileChooser.showOpenDialog(null);
        if (JFileChooser.APPROVE_OPTION == result) {
            path = fileChooser.getSelectedFile().getPath();
            System.out.println("path: " + path);
        }
        return path;
    }
}
