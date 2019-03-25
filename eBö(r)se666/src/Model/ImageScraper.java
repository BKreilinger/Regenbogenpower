package Model;

import java.io.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
 
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.URL;

public class ImageScraper {
    private static final String webSiteURL = "http://www.supercars.net/gallery/119513/2841/5.html";
    private static final String folderPath = "<FOLDER PATH>";
 
    public static void main(String[] args) {
 
        try {
            Document doc = Jsoup.connect(webSiteURL).get();
            Elements img = doc.getElementsByTag("img");
 
            for (Element el : img) {
                String src = el.absUrl("src");
                System.out.println("Image Found!");
                System.out.println("src attribute is : "+src);
                getImages(src);
            }
 
        } catch (IOException ex) {
            System.err.println("There was an error");
            Logger.getLogger(ImageScraper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    private static void getImages(String src) throws IOException {
 
        String folder = null;
        int indexname = src.lastIndexOf("/");
        if (indexname == src.length()) {
            src = src.substring(1, indexname);
        }
        indexname = src.lastIndexOf("/");
        String name = src.substring(indexname, src.length());
 
        System.out.println(name);
        URL url = new URL(src);
        InputStream in = url.openStream();
 
        OutputStream out = new BufferedOutputStream(new FileOutputStream(folderPath+ name));
 
        for (int b; (b = in.read()) != -1;) {
            out.write(b);
        }
        out.close();
        in.close();
 
    }
}
