/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.io.File;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author tantal
 */
public class Launcher {
    public static void main(String[] args){
        
        while (true){
            System.out.print("Enter filename to parse: ");
            Scanner commandLinePrompt = new Scanner(System.in);
            String prompt = commandLinePrompt.nextLine();
            File inputFile = new File(prompt);
            //System.out.println("+++ " + inputFile.toString() + " +++");
            if  (inputFile.isFile()){

                //TODO add diagnostic messages
                Parser p = new Parser(inputFile);
                List<String> ip_list = p.parse(false);
                Checker chk = new Checker();
                chk.check(ip_list, inputFile, true);
                continue;
            } else if (prompt.trim().equalsIgnoreCase("EXIT")) {
                System.out.println("Bye-bye!");
                break;
            } else if(inputFile.isDirectory()){
                System.out.println("Specified file is directory! Try again.");
                continue;
            } else if(!inputFile.isDirectory() && !inputFile.isFile()){
                System.out.println("Can't read or find specified file: " + prompt + "\nTry enter filename again!");
                continue;
            }
        }
    }
}
