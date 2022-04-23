package hust.cs2007.qpphust;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * ClassName: project
 * PackageName:hust.cs2007.qpphust
 * Description:表格形式转变，支持多行多列
 * date: 2022/4/23 15:10
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
public class MainProject {
    /***
     * @author qpphust
     * @description 读取文件，将其分词
     * @createTime  2022/4/23 18:01
     * @return java.util.List<java.lang.String[]>
     **/
    public static List<String[]> readFile(String filename) {
        List<String[]> data = new ArrayList<>();
        String term = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            while((term = reader.readLine()) != null){
                data.add(term.split(","));
            }
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
      /***
       * @author qpphust
       * @description 将更改后的内容输出到文件中
       * @createTime  2022/4/23 18:02
       * @return void
       **/
    public static void writeFile(List<String[]> data,String filename){
        int dataNum = data.size() - 1;
        String[] firstLine = data.remove(0);
        int years = firstLine.length;
        //自动释放资源
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("country,year,cases\n");
            for (int i = 0; i < years-1; i++) {
                for (int j = 0;j<dataNum;j++){
                    writer.write(data.get(j)[0]+","+firstLine[i+1]+","+data.get(j)[i+1]+"\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * @author qpphust
     * @description 修改表并打印到控制台
     * @createTime  2022/4/23 18:02
     * @return void
     **/
    public static void changeAndPrint(List<String[]> data){
        int dataNum = data.size() - 1;
        String[] firstLine = data.remove(0);
        int years = firstLine.length;
        System.out.println("country,year,cases");
        for (int i = 0; i < years-1; i++) {
            for (int j = 0;j<dataNum;j++){
                System.out.println(data.get(j)[0]+","+firstLine[i+1]+","+data.get(j)[i+1]);
            }
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        List<String[]> list = new ArrayList<>();
        String item = "";
        System.out.println("选择模式:\n" +
                "[1]--------文件\n" +
                "[2]--------控制台");
        String s = in.nextLine();
        switch (s){
            case "1":{
                System.out.println("输入您的文件的全路径名或直接输入data.csv");
                String filename = in.nextLine();
                System.out.println();
                List<String[]> data = readFile(filename);
                String parent = new File(filename).getParent();
                if(parent != null) {
                    writeFile(data, parent + "\\output.csv");
                }else{
                    writeFile(data,"output.csv");
                }
                System.out.println("更改后的结果保存在了源文件同级目录下的output.csv");
                break;
            } case "2":{
                System.out.println("按行输入，逗号分隔，以_OK_结束");
                while (!((item = in.nextLine()).equals("_OK_"))){
                    list.add(item.split(","));
                }
                changeAndPrint(list);
                break;
            } case "3":{
                System.out.println("goodbye");
                System.exit(0);
            }
        }
    }

}
