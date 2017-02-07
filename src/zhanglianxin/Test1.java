package zhanglianxin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 尝试使用 jsoup 解析本地 HTML 文件
 *
 * @author zhanglianxin
 *
 */
public class Test1 {

    public static void main(String[] args) {
        File input = new File("./src/zhanglianxin/10489.html");
        try {
            Document doc = Jsoup.parse(input, "UTF-8",
                    "http://www.gaoqingkong.com/html/");
            int uid = Integer.parseInt(input.getName().replace(".html", ""));

            Element content = doc.select("div#content").first();
            String title = content.select("div#lovexin1>h1").first().text();
            String lastModified = content
                    .select("div#lovexin1>div.article_info>span.info_date")
                    .first().text().replace("最后修改时间：", "");
            String imgPath = content.select("div#lovexin1>div.context>"
                    + "div#post_content>p:eq(1)>img").attr("src");
            Elements linkElems = content.select("a[target]");
            List<String> links = new ArrayList<>();
            for (int i = 0; i < linkElems.size(); i++) {
                String href = linkElems.eq(i).attr("href");
                if (href.startsWith("magnet:?xt=urn:btih:")) {
                    links.add(href);
                }
            }
            MoviePost post = new MoviePost(uid, title, lastModified, imgPath,
                    links);
            System.out.println(post);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
