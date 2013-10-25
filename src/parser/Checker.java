package parser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Checker {
    public List<String> check(List<String> IP_list, File input, boolean toFile){
        if (IP_list == null){
            System.out.println("Nothing to check.");
            return null;
        }
        System.out.println("Begin checking " + IP_list.size() + " addresses");
        URLConnection connection;
        URL url;
        StringBuilder url_builder;

        List<String> checked = new ArrayList<String>();
        for (String ip : IP_list){
            url_builder = new StringBuilder();
            url_builder.append("http://");
            url_builder.append(ip);
            url_builder.append("/");
            try {
                url = new URL(url_builder.toString());
                connection = url.openConnection();
                //InputStream is_content = connection.getInputStream();
                BufferedReader content_reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder content_builder = new StringBuilder();
                String s;
                while ((s = content_reader.readLine()) != null){
                    content_builder.append(s);
                }
                String content = content_builder.toString();
                if (content.toLowerCase().contains("dir")){
                    checked.add(ip);
                }
                if(checked.size() > 0 && toFile == true){
                    File outputFile = new File(input.getAbsolutePath() + ".checked");
                    FileWriter outputWriter = new FileWriter(outputFile);
                    for (String checked_ip : checked){
                        outputWriter.write(checked_ip + "\n");
                    }
                    outputWriter.close();
                }
            } catch (MalformedURLException e) {
                //todo add console output about malformed URL
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IOException e) {
                //todo add console output about unavaliable opening url
            }
        }
        return null;
    }
}
