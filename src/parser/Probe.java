package parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Pattern;

public class Probe {

    public static void main(String[] args) throws IOException {
        URL url = new URL("http://192.168.1.2/");
        URLConnection connection = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String s;
        while((s = reader.readLine()) != null) {
            System.out.print(s);
        }
        reader.close();
    }
}
