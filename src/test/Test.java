package test;


import bean.Assay;
import bean.Constant;
import bean.FirstPage;
import utils.DataUtil;

import java.util.Scanner;

/**
 * Created by Administraor on 2015/10/31.
 */
public class Test {


    public static void main(String[] args) {
        try {
            DataUtil dataUtil = new DataUtil();
            Scanner sc = new Scanner(System.in);
            System.out.println("������һ��һ���ĵ����֣�");
            int page = sc.nextInt();
            FirstPage firstPage = new FirstPage(page);

            String pageHtml = dataUtil.doGet(Constant.startUrl+page);
            firstPage.dealPage(pageHtml);
            int i;
            for (i = 0; i < firstPage.getTitleUrlList().size(); i++) {
                String urlPage = firstPage.getTitleUrlList().get(i);
                String urlLast=dataUtil.getUrl(urlPage);                          //ò���ڽ��д�ǰ��������ʱ����Լ���URLStr�ټ���һ��˫����
                System.out.println(urlLast);
                String assayHtml = dataUtil.doGet(urlLast);
                Assay assay = new Assay();
                assay.setFilePath("F:/CSDNSpider/page_" + page + "/" + "article_" + i + "/");
                assay.dealAssay(assayHtml);
            }


        } catch (Exception e) {
            System.out.println("��������������");
            e.printStackTrace();

        }
    }


}
