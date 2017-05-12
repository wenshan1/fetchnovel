package me.wenshan.xiaoshuo;

import java.io.IOException;
import java.io.PrintStream;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class XiaoShuo {
    private PrintStream pr;
    private String  preUrl = "http://www.mpzw.com/html/69/69667/";
    XiaoShuo (PrintStream pr){
        this.pr = pr;
    }
    private void getZhangJie (String url){
        Connection con = Jsoup.connect(url);
        con.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0");
        con.timeout(300000);
        Document doc;
        try {
            doc = con.get();
        } catch (IOException e) {
            e.printStackTrace();
            return ;
        }
        
        //章节标题
        
        Elements eles = doc.getElementsByTag("title");
        pr.println(eles.get(0).text());
        pr.println ();
        pr.println ();
        
        Element ele = doc.getElementById("clickeye_content");
        String str = ele.html();
        
       StringBuilder sbd = new StringBuilder (str);
       int nstart = 0;
       int nIndex ; 
       
       String retu = "\n";
       while ((nIndex = sbd.indexOf(retu, nstart)) != -1 ){
           
           sbd.replace(nIndex, nIndex + retu.length(), "");
           nstart = nIndex;
       }
       
       /* 去除 <br /> */
       
       nstart = 0;
       
       String br = "<br />";
       while ((nIndex = sbd.indexOf(br, nstart)) != -1 ){
           nstart += 3;
           if (nIndex == nstart)
               sbd.replace(nIndex, nIndex + br.length(), "");
           else
               sbd.replace(nIndex, nIndex + br.length(), "\r\n");
           nstart = nIndex;
       }
        
       nIndex = 0; 
       String space = "&nbsp;&nbsp;&nbsp;&nbsp;";
       while ((nIndex = sbd.indexOf(space, nIndex)) != -1 ){
           sbd.replace(nIndex, nIndex + space.length(), "\r\n");
       }
       
       pr.print(sbd.toString());
       pr.println ();
       pr.println ();
       pr.flush();
    }
    
    public void fetchZheTianji (){
        Connection con = Jsoup.connect(preUrl + "index.html");
        con.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0");
        con.timeout(300000);
        Document doc;
        try {
            doc = con.get();
        } catch (IOException e) {
            e.printStackTrace();
            return ;
        }
        
        org.jsoup.select.Elements ccssEles = doc.getElementsByClass("ccss");
        for (int i = 0; i < ccssEles.size(); i ++){
            Element ele = ccssEles.get(i);
            Elements childeles = ele.children();
            for (int ii = 0; ii < childeles.size(); ii ++){
                Element childele = childeles.get(ii);
                String ahre = childele.attr("href");
                System.out.println (preUrl + ahre);
                getZhangJie (preUrl + ahre);
            }
        }
       
    }
}
