package zhanglianxin;

import java.io.File;
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

/**
 * http://www.ibm.com/developerworks/cn/java/j-lo-jsouphtml/
 *
 * @author zhanglianxin
 *
 */
public class Test0 {

    public static void main(String[] args) {
        /* -- 文档输入 -- */
        // 直接从字符串中输入 HTML 文档
        String html = "<html><head><title>开源中国社区</title></head>"
                + "<body><p>这里是 jsoup 项目的相关文章</p></body></html>";
        Document doc = Jsoup.parse(html);

        // 从 URL 直接加载 HTML 文档
        String url = "https://www.oschina.net/";
        Connection con = Jsoup.connect(url);
        con.data("query", "Java");
        con.userAgent("I'm jsoup");
        con.cookie("auth", "token");
        con.timeout(3000);
        try {
            Document doc1 = con.post();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 从文件中加载 HTML 文档
        File input = new File("./src/zhanglianxin/test.html");
        try {
            Document doc2 = Jsoup.parse(input, "UTF-8",
                    "http://www.oschina.net/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        /* -- 解析并提取 HTML 元素 -- */
        File input1 = new File("./src/zhanglianxin/test.html");
        try {
            Document doc3 = Jsoup.parse(input1, "UTF-8",
                    "http://www.oschina.net/");
            Element content = doc3.getElementById("content");
            Elements links = content.getElementsByTag("a");
            for (Element link : links) {
                String linkHref = link.attr("href");
                String linkText = link.text();
//                System.out.println(linkHref + " " + linkText);
            }
            
//            // 具有 href 属性的链接
//            Elements links = doc3.select("a[href]");
//            // 所有引用 png 图片的元素
//            Elements pngs = doc3.select("img[src$=.png]");
//            // class = "masthead" 的第一个元素
//            Element masthead = doc3.select("div.masthead").first();
//            // h3 的直接子元素 a
//            Elements resultLinks = doc3.select("h3 > a");
            
            /* -- 修改数据 -- */
            // 为所有链接增加 rel = "nofollow" 属性
            doc3.select("div.comments a").attr("rel", "nofollow");
            // 为所有链接增加 class = "mylinkclass" 属性
            doc3.select("div.comments a").addClass("mylinkclass");
            // 清空所有文本输入框中的文本
            doc3.select("input[type=text]").val("");
//            System.out.println(doc3.select("input[type=text]"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        /* HTML 文档清理 */
        /*
         * Whitelist 类的常用方法
         *  方法名        简介
         *  none()  只允许包含文本信息
         *  basic() 允许的标签包括：a, b, blockquote, br, cite, code, dd, dl, dt, em,
         *          i, li, ol, p, pre, q, small, strike, strong, sub, sup, u, 
         *          ul, 以及合适的属性
         *  simpleText()    只允许 b, em, i, strong, u 这些标签
         *  basicWithImages()   在 basic() 的基础上增加了图片
         *  relaxed()   这个过滤器允许的标签最多，包括：a, b, blockquote, br, caption, 
         *              cite, code, col, colgroup, dd, dl, dt, em, h1, h2, h3, 
         *              h4, h5, h6, i, img, li, ol, p, pre, q, small, strike, 
         *              strong, sub, sup, table, tbody, td, tfoot, th, thead, 
         *              tr, u, ul
         */
        String unsafe = "<p><a href='http://www.oschina.net/' "
                + "onclick='stealCookies();'>开源中国社区</a></p>";
        String safe = Jsoup.clean(unsafe, Whitelist.basic());
//        System.out.println(safe);
     
        /* 选择器 */
        /*
         * 基本用法
         * tagname  使用标签名来定位，例如 a
         * ns|tag  使用命名空间的标签定位，例如 fb:name 来查找 <fb:name> 元素
         * #id 使用元素 id 定位，例如 #logo
         * .class  使用元素的 class 属性定位，例如 .head
         * [attribute] 使用元素的属性进行定位，例如 [href] 表示检索具有 href 属性的所有元素
         * [^attr] 使用元素的属性名前缀进行定位，例如 [^data-] 用来查找 HTML5 的 dataset 属性
         * [attr=value]    使用属性值进行定位，例如 [width=500] 定位所有 width 属性值为 500 的元素
         * [attr^=value], [attr$=value], [attr*=value] 这三个语法分别代表，属性以 value 开头、结尾以及包含
         * [attr~=regex]   使用正则表达式进行属性值的过滤，例如 img[src~=(?i)\.(png|jpe?g)]
         * *   定位所有元素
         * 
         * 组合用法
         * el#id    定位 id 值某个元素，例如 a#logo -> <a id=logo href= … >
         * el.class    定位 class 为指定值的元素，例如 div.head -> <div class=head>xxxx</div>
         * el[attr]    定位所有定义了某属性的元素，例如 a[href]
         * 以上三个任意组合    例如 a[href]#logo 、a[name].outerlink
         * ancestor child  这五种都是元素之间组合关系的选择器语法，其中包括父子关系、合并关系和层次关系。
         * parent > child
         * siblingA + siblingB
         * siblingA ~ siblingX
         * el, el, el
         * 
         * 表达式
         * :lt(n)  例如 td:lt(3) 表示 小于三列
         * :gt(n)  div p:gt(2) 表示 div 中包含 2 个以上的 p
         * :eq(n)  form input:eq(1) 表示只包含一个 input 的表单
         * :has(seletor)   div:has(p) 表示包含了 p 元素的 div
         * :not(selector)  div:not(.logo) 表示不包含 class=logo 元素的所有 div 列表
         * :contains(text) 包含某文本的元素，不区分大小写，例如 p:contains(oschina)
         * :containsOwn(text)  文本信息完全等于指定条件的过滤
         * :matches(regex) 使用正则表达式进行文本过滤：div:matches((?i)login)
         * :matchesOwn(regex)  使用正则表达式找到自身的文本
         */
    }
    
}
