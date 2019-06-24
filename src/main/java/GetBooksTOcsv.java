
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
    public static void main(String[] args) {
        final File folder = new File("C:\\Users\\edaraujo\\Google Drive\\livros");
        listBooksFromFolder(folder);
    }

    public static void listBooksFromFolder(final File folder) {

        PrintWriter writer = null;

        try {
            writer = new PrintWriter(new File("books.csv"));
            StringBuilder sb = new StringBuilder();

            for (final File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    listBooksFromFolder(fileEntry);
                } else {

                    String book = fileEntry.getName().replace(",", " ").replace(";", " ").replace(".pdf", "")
                            .replace("_", " ").replace("-", " ");
                    sb.append(book);
                    sb.append(',');
                    sb.append(getLongestWordsFromBook(book));
                    sb.append('\n');

                }
            }

            writer.write(sb.toString());

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            writer.close();
        }

        try {
            getReviewFromGoodReads();
        } catch (IOException e) {
            System.out.println("Error to read the file.");
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

    public static void getReviewFromGoodReads() throws IOException {
        String[] data = null;
        BufferedReader csvReader = null;
        PrintWriter writer = null;
        List<String> listBooks = new ArrayList<String>();
        String register = "";

        try {
            csvReader = new BufferedReader(new FileReader("books.csv"));
            writer = new PrintWriter(new File("books_gr.csv"));

            String row = "";

            row = csvReader.readLine();

            while (row != null) {

                data = row != null ? row.split(",") : new String[0];

                if (data.length > 1) {
                    HttpRequestFactory requestFactory = new ApacheHttpTransport().createRequestFactory();
                    String url = "https://www.goodreads.com/book/title.xml?key=x0F6Rd2cVbxEhZFm6Wsn3w&title=" + data[1];

                    System.out.println(url);
                    GenericUrl genericUrl = new GenericUrl(url);
                    HttpResponse resp = null;

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
                            if (status != null && !status.equals("") && reviews != null
                                    && Integer.parseInt(reviews) > 100) {
                                register = row + "," + status + "\n";
                            } else {
                                register = row + ", 0 XXX no there status  \n";
                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            register = row + ", 0 XXX " + e.getMessage() + " \n";
                        }
                    } catch (Exception e) {
                        register = row + ", 0 XXX " + "BOOK NOT FOUND" + " \n";
                    }

                } else {
                    register = row + ", 0 XXX no there " + "\n";
                }

                listBooks.add(register);
                row = csvReader.readLine();
            }
        } finally {

            StringBuilder sb = new StringBuilder();
            for (String book : listBooks) {
                sb.append(book);
            }

            writer.write(sb.toString());
            writer.close();
            csvReader.close();
        }

    }

}
