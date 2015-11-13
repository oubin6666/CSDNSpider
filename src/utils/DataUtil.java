package utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administraor on 2015/10/31.
 */
public class DataUtil {

    public String doGet(String urlStr) throws CommonException {
        StringBuffer buffer = new StringBuffer();
        HttpURLConnection connection = null;

        try {
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            if (connection.getResponseCode() == 200) {
                InputStream is = connection.getInputStream();
                int len = 0;
                byte[] buf = new byte[1024];

                while ((len = is.read(buf)) != -1) {
                    buffer.append(new String(buf, 0, len, "UTF-8"));
                }

                is.close();
            } else {

                throw new CommonException("∑√Œ Õ¯¬Á ß∞‹£°");
            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException("∑√Œ Õ¯¬Á“Ï≥£");

        }


      //  System.out.print(buffer.toString());
        return buffer.toString();
    }


    public String getUrl(String sUrl){
        String url=sUrl.substring(1,sUrl.length()-1);
        return url;
    }



}
