
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import com.google.api.client.auth.oauth.OAuthAuthorizeTemporaryTokenUrl;
import com.google.api.client.auth.oauth.OAuthCredentialsResponse;
import com.google.api.client.auth.oauth.OAuthGetAccessToken;
import com.google.api.client.auth.oauth.OAuthGetTemporaryToken;
import com.google.api.client.auth.oauth.OAuthHmacSigner;
import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * Hello world!
 *
 */
public class GetBooksTOcsv {
    static List<Book> books = new ArrayList<Book>();

    public static void main(String[] args) {
        final File folder = new File("C:\\Users\\edaraujo\\Google Drive\\livros");
        listBooksFromFolder(folder);
        registerBookswithRating();
    }

    public static void listBooksFromFolder(final File folder) {

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listBooksFromFolder(fileEntry);
            } else {

                Book book = new Book();
                book.setDescription(fileEntry.getName().replace(",", " ").replace(";", " ").replace(".pdf", "")
                        .replace("_", " ").replace("-", " "));
                book.setKeys(getLongestWordsFromBook(book.getDescription()));
                book.setRating(getRatingFromGoodReads(book.getKeys()));
                books.add(book);

            }
        }
    }

    public static String getLongestWordsFromBook(String book) {
        StringBuilder result = new StringBuilder();
        boolean firstOccur = true;

        String[] words = book.split(" ");
        for (String s : words) {
            if (s.length() > 3) {
                if (firstOccur) {
                    result.append(s);
                    firstOccur = false;
                } else {
                    result.append("+" + s);
                }
            }
        }

        return result.toString();
    }

    public static String getRatingFromGoodReads(String keys) {

        HttpRequestFactory requestFactory = new ApacheHttpTransport().createRequestFactory();
        String url = "https://www.goodreads.com/book/title.xml?key=x0F6Rd2cVbxEhZFm6Wsn3w&title=" + keys;

        System.out.println(url);
        GenericUrl genericUrl = new GenericUrl(url);
        HttpResponse resp = null;
        String rating = "";

        if (keys.length() > 1) {

            try {
                resp = requestFactory.buildGetRequest(genericUrl).execute();

                XPathFactory xpathFactory = XPathFactory.newInstance();
                XPath xpath = xpathFactory.newXPath();
                InputSource source = new InputSource(new StringReader(resp.parseAsString()));
                Node root = (Node) xpath.evaluate("/", source, XPathConstants.NODE);
                String status;
                String reviews;
                try {
                    status = xpath.evaluate("/GoodreadsResponse/book/average_rating", root);
                    reviews = xpath.evaluate("/GoodreadsResponse/book/work/reviews_count", root);
                    if (status.startsWith("0")) {
                        status = status.substring(1, status.length());
                    }
                    if (status != null && !status.equals("") && reviews != null && Integer.parseInt(reviews) > 100) {
                        rating = status;
                    } else {
                        rating = "0 XXX no there status";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    rating = "0 XXX " + e.getMessage();
                }
            } catch (Exception e) {
                rating = "0 XXX BOOK NOT FOUND";
            }

        } else {
            rating = "0 XXX no there";
        }

        return rating;

    }

    public static void registerBookswithRating() {
        PrintWriter writer = null;
        StringBuilder sb = new StringBuilder();
        books.sort((b1, b2) -> b2.getRating().compareTo(b1.getRating()));
        for (Book book : books) {
            sb.append(book.toString());
        }

        try {
            writer = new PrintWriter(new File("books.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            writer.write(sb.toString());
            writer.close();

        }

    }

}
