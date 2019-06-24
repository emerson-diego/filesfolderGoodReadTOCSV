
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

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * Author: davecahill
 *
 * Adapted from user Sqeezer's StackOverflow post at
 * http://stackoverflow.com/questions/15194182/examples-for-oauth1-using-google-api-java-oauth
 * to work with Goodreads' oAuth API.
 *
 * Get a key / secret by registering at https://www.goodreads.com/api/keys and
 * replace YOUR_KEY_HERE / YOUR_SECRET_HERE in the code below.
 */
public class GoodReads {

    // public static final String BASE_GOODREADS_URL = "https://www.goodreads.com";
    // public static final String TOKEN_SERVER_URL = BASE_GOODREADS_URL +
    // "/oauth/request_token";
    // public static final String AUTHENTICATE_URL = BASE_GOODREADS_URL +
    // "/oauth/authorize";
    // public static final String ACCESS_TOKEN_URL = BASE_GOODREADS_URL +
    // "/oauth/access_token";

    // public static final String GOODREADS_KEY = "x0F6Rd2cVbxEhZFm6Wsn3w";
    // public static final String GOODREADS_SECRET =
    // "1Gg27o4bT48ZIkQ13ytDsS3ZZQ6XAAjPD3PmKVI4JE";

    /*
     * public static void main(String[] args) throws IOException { String[] data =
     * null; BufferedReader csvReader = null; PrintWriter writer = null;
     * StringBuilder sb = new StringBuilder();
     * 
     * try { csvReader = new BufferedReader(new FileReader("test.csv")); writer =
     * new PrintWriter(new File("test2.csv"));
     * 
     * String row = ""; int cont = 1;
     * 
     * row = csvReader.readLine();
     * 
     * while (row != null) {
     * 
     * data = row != null ? row.split(",") : new String[0];
     * 
     * // do something with the data
     * 
     * if (data.length > 1) { HttpRequestFactory requestFactory = new
     * ApacheHttpTransport().createRequestFactory(); String url =
     * "https://www.goodreads.com/book/title.xml?key=x0F6Rd2cVbxEhZFm6Wsn3w&title="
     * + data[1]; System.out.println(url); GenericUrl genericUrl = new
     * GenericUrl(url); HttpResponse resp = null;
     * 
     * try { resp = requestFactory.buildGetRequest(genericUrl).execute();
     * 
     * // System.out.println(resp.parseAsString());
     * 
     * XPathFactory xpathFactory = XPathFactory.newInstance(); XPath xpath =
     * xpathFactory.newXPath();
     * 
     * // System.out.println(resp.parseAsString()); InputSource source = new
     * InputSource(new StringReader(resp.parseAsString())); Node root = (Node)
     * xpath.evaluate("/", source, XPathConstants.NODE); String status; String
     * reviews; try { status =
     * xpath.evaluate("/GoodreadsResponse/book/average_rating", root); reviews =
     * xpath.evaluate("/GoodreadsResponse/book/work/reviews_count", root);
     * System.out.println("satus=" + status); if (status.startsWith("0")) { status =
     * status.substring(1, status.length()); } if (status != null &&
     * !status.equals("") && reviews != null && Integer.parseInt(reviews) > 100) {
     * sb.append(row + "," + status + "\n"); } else { sb.append(row +
     * ", 0 XXXXXXXXXXXXXXXXXXXXXXXXXX não tem status  \n"); } } catch (Exception e)
     * { // TODO Auto-generated catch block e.printStackTrace(); sb.append(row +
     * ", 0 XXXXXXXXXXXXXXXXXXXXXXXXXX " + e.getMessage() + " \n"); } } catch
     * (Exception e) { sb.append(row + ", 0 XXXXXXXXXXXXXXXXXXXXXXXXXX " +
     * "NOT FOUND" + " \n"); }
     * 
     * } else { sb.append(row + ", 0 XXXXXXXXXXXXXXXXXXXXXXXXXX não tem \n"); }
     * 
     * // writer.write(sb.toString()); // sb = new StringBuilder(); row =
     * csvReader.readLine(); } } finally { writer.write(sb.toString());
     * writer.close(); csvReader.close(); }
     * 
     * }
     */
}