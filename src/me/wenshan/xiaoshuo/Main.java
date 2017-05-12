package me.wenshan.xiaoshuo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class Main {
    public static void main(String[] args) {
        File file =new File ("d:/折天记.txt");
        PrintStream pr;
        if (!file.exists())
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream (file);
            pr = new PrintStream (fileOut);
            XiaoShuo xs = new XiaoShuo (pr);
            xs.fetchZheTianji();
            pr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
