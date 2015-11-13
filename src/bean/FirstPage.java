package bean;

import utils.CommonException;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administraor on 2015/10/31.
 * ������ҳ���࣬��������п��Ի�ȡ����ҳ�����ŵı��⣬�������ӣ��ͼ��
 */
public class FirstPage {

    private int page;
//    private int pageSum = 5;
//    private int newsSum = 30;  //��������
//    private String brief;
    private List<String> titleList = new ArrayList<>();    //�����б�
    private List<String> titleUrlList = new ArrayList<>();   //���������б�
    private List<String> briefList=new ArrayList<>();

    public FirstPage(int page) {

            this.page=page;

    }

    public void dealPage(String pageHtml) {

            Pattern titlePattern = Pattern.compile("<a name=\"\\d+\" href=\".*\" target=\"_blank\">.*</a>");
            Pattern briefPattern=Pattern.compile("<dd>[^<]*</dd>");
            Matcher tMatcher = titlePattern.matcher(pageHtml);
            Matcher bMatcher=briefPattern.matcher(pageHtml);
            while (tMatcher.find()) {
                String data = tMatcher.group();
                int tIndex = data.indexOf("\"_blank\">");
                int tLastIndex = data.indexOf("</a>");
                String title = data.substring(tIndex + 9, tLastIndex);
                titleList.add(title);
                int uIndex = data.indexOf("href=");
                int uLastIndex = data.lastIndexOf("target");
                String titleUrl = data.substring(uIndex + 5, uLastIndex-1);
                titleUrlList.add(titleUrl);

            }

            while (bMatcher.find()){
                String data=bMatcher.group();
                int bIndex=data.indexOf("<dd>");
                int bLastIndex=data.lastIndexOf("</dd>");
                String brief=data.substring(bIndex+4,bLastIndex);
                briefList.add(brief);
            }


    }








    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<String> getTitleList() {
        return titleList;
    }


    public List<String> getTitleUrlList() {
        return titleUrlList;
    }

    public List<String> getBriefList() {
        return briefList;
    }

    public void setBriefList(List<String> briefList) {
        this.briefList = briefList;
    }
}
