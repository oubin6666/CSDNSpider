package bean;



import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administraor on 2015/10/31.
 */
public class Assay {

    private String data;
    private String title;
    private String content;
    private String filePath;
    private List<String> imgList = new ArrayList<>();


    //获取标题
    public void dealTitle(String assayHtml) {
        Pattern titlePattern = Pattern.compile("<title>[^<]*</title>");
        Matcher titleMatcher = titlePattern.matcher(assayHtml);
        while (titleMatcher.find()) {
            String data = titleMatcher.group();
            int tIndex = data.indexOf("<title>");
            int tLastIndex = data.indexOf("</title>");
            String title = data.substring(tIndex + 7, tLastIndex);
            this.title = title;

        }


    }


    //然后下载图片
    public void dealAssay(String assayHtml) {
        dealTitle(assayHtml);
        String content1 = this.cutAssay(assayHtml);      //将文章页面的HTML数据进行初步截取，除去前面和后面的无用内容
        dealImg(content1);
        String content2 = this.replaceAssay(content1);    //将
        this.content = content2;

        downloadAssay();
        dealImg(content);
        downloadImg();

    }


    public void downloadImg(){
        for (int i=0;i<imgList.size();i++){
            try {

                URL url = new URL(imgList.get(i));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36");
                conn.setConnectTimeout(8000);
                conn.setReadTimeout(10000);
                File img = new File(filePath + "/" +i+".jpg");
                FileOutputStream outputStream = new FileOutputStream(img);
                InputStream is = conn.getInputStream();
                int r = -1;
                while((r=is.read())!=-1){
                    outputStream.write(r);
                }
                outputStream.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public String cutAssay(String assayHtml) {
        int startIndex = 0;
        int lastIndex = 0;
        startIndex = assayHtml.indexOf("<div id=\"article_content\" class=\"article_content\">");
        lastIndex = assayHtml.indexOf("<!-- Baidu Button BEGIN -->");
        String content1 = assayHtml.substring(startIndex + 50, lastIndex - 1);

        return content1;
    }


    public String replaceAssay(String content1) {
        content1 = content1.replaceAll("<div[^<]*>|<p[^<]*>|</p>|<span[^<]*>|</span>|</li>|</div>|<pre[^<]*>|</pre>|<a[^<]*>|</a>|</h1>|</h2>|<ul>|</ul>|</blockquote>|<code[^<]*>|</code>|<strong>|</strong>|<s>", "");
        content1 = content1.replaceAll("<br />|<br>", "\n");

        content1 = content1.replace("&nbsp", " ");
        content1 = content1.replaceAll("&quot;", "\"");
        content1 = content1.replaceAll("&gt;", ">");
        content1 = content1.replaceAll("&lt;", "<");


        content1 = content1.replaceAll("<h1[^<]*>|</h1>", "#");
        content1 = content1.replaceAll("<h2[^<]*>|</h2>", "##");
        content1 = content1.replaceAll("<h3[^<]*>|</h3>", "###");
        content1 = content1.replaceAll("<h4[^<]*>|</h4>", "####");
        content1 = content1.replaceAll("<h5[^<]*>|</h5>", "#####");
        content1 = content1.replaceAll("<h6[^<]*>|</h6>", "######");
        content1 = content1.replaceAll("<blockquote[^<]*>", ">");
        content1 = content1.replaceAll("<li>", "* ");
        content1 = content1.replaceAll("<hr>", "_____");


        return content1;

    }


    public void downloadAssay() {
        File file = new File(filePath);
        if(!file.exists()){
            file.mkdirs();
        }
        String sFile = "文章.md";
        File fileName = new File(filePath+"/"+sFile);
        if(!fileName.exists()) {
            try {
                fileName.createNewFile();

            } catch (Exception e) {
                e.printStackTrace();
            }


            try{
                FileOutputStream outputStream=new FileOutputStream(fileName);
                OutputStreamWriter writer=new OutputStreamWriter(outputStream,"utf-8");
                writer.write(content);
                writer.close();
            }catch (IOException e){
                e.printStackTrace();
            }



        }

    }


    public void dealImg(String content1) {
        Pattern imgPattern = Pattern.compile("<img src=\\\"[^<>\\\"]*\\\"[^<>]*>");
        Matcher imgMatcher = imgPattern.matcher(content1);

        while (imgMatcher.find()) {
            String data = imgMatcher.group();
            String imgLink;
            if (data.contains("alt=")) {
                int index = data.indexOf("alt=");
                imgLink = data.substring(10, index - 2);
            } else {
                imgLink = data.substring(10, imgMatcher.group().length() - 1);
            }

            this.imgList.add(imgLink);

        }




    }


    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


}
