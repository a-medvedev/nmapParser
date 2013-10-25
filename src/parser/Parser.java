/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    
    private File inputFile = null;
    public Parser(File f){
        inputFile = f;
    }
    
    public List<String> parse(boolean toFile){
        if (inputFile.isFile()){
            System.out.println("Begin parsing file " + inputFile.toString());
            Scanner input;
            try {
                input = new Scanner (inputFile);
                StringBuilder block = new StringBuilder();
                List<String> blocks = new ArrayList<String>();
                while (input.hasNextLine()){
                    String tmpStr = input.nextLine();
                    if (tmpStr.trim().isEmpty() || tmpStr.startsWith("#")){
                        if (!block.toString().trim().isEmpty()){
                            blocks.add(block.toString());
                            block = new StringBuilder();
                        }
                    } else {
                        block.append(tmpStr);
                    }
                }
                List<String> IPmatches = new LinkedList<String>();
                Pattern ip_pattern = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
                for (String match : find(blocks, "open")){
                    Matcher ipMatcher = ip_pattern.matcher(match);
                    if (ipMatcher.find()){
                        //System.out.println(ipMatcher.group());
                        IPmatches.add(ipMatcher.group());
                    }
                }
                if (toFile == true && IPmatches.size() > 0){
                    File outputFile = new File(inputFile.getAbsolutePath() + ".parsed");
                    FileWriter outputWriter = new FileWriter(outputFile);
                    for (String ip : IPmatches){
                        outputWriter.write(ip + "\n");
                    }
                    outputWriter.close();
                }
                System.out.println("Parsing done!");
                return IPmatches;
            } catch (FileNotFoundException ex) {
                System.out.println("File not found.");
                return null;
            } catch (IOException ex) {
                System.out.println("Error opening file.");
                return null;
            }
        } else {
            return null;
        }
    }
    
    private List<String> find(List<String> entries, String expr){ //expr - what did you want to find
        List<String> matches = new ArrayList<String>();
        if (expr.isEmpty()){
            return null;
        }
        for (String record : entries){
            if (record.contains(expr)){
                matches.add(record);
            }
        }
        return matches;
    }
}
