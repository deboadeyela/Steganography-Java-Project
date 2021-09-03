/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.compsec;

/**
 *
 * @author gbadebo
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CT437_Stegano1
{
    /**
     * Constructor for objects of class Stegano1
     */
    public CT437_Stegano1()
    {
    }

    public static void main(String[] args) {
        String arg1, arg2, arg3, arg4;
        Boolean err = false;
       
        if (args != null && args.length > 1) { // Check for minimum number of arguments
            arg1 = args[0];
            arg2 = args[1];
               
            if (arg2.equals("")) {
                err = true;
            }
            else if (arg1.equals("A") && (args.length > 2)){
                // Get other arguments
                arg3 = args[2];
                arg4 = args[3];
                if (arg3.equals("") || arg4.equals("") ) {
                    err = true;
                }
                else {
                    // Hide bitstring
                    hide(arg2, arg3, arg4);
                }
            }
            else if (arg1.equals("E") ){
                // Extract bitstring from text  
                retrieve(arg2);  
            }
            else {
                err = true;
                //System.out.println("err");
             
            }
        }
        else {
            err = true;
        }
       
        if (err == true) {
            System.out.println();
            System.out.println("    Use: CT437_Stegano1 <A:E><Input File><OutputFile><Bitstring>");
            System.out.println("Example: CT437_Stegano1 A inp.txt out.txt 0010101");
            System.out.println("Example: CT437_Stegano1 E inp.txt");
           
        }
    }
   
    static void hide(String inpFile, String outFile, String binString) {
        //
        BufferedReader reader;
        BufferedWriter writer;
        try {
            reader = new BufferedReader(new FileReader(inpFile));
            writer = new BufferedWriter(new FileWriter(outFile));
            String line = reader.readLine();
            int i = 0;//index variable
            while (line != null) {
                // Your code starts here
                //Increment index in loop for length of string 
                while (i < binString.length()) {
                    // IF loop proceeds if last element in string is  mod 2 is 0
                    if (i == binString.length() - 1 && i % 2 == 0) {
                        // if value of index is 0 add double space to output file
                        if (Integer.parseInt(String.valueOf(binString.charAt(i))) == 0) {
                            writer.write(line + "  ");
                            writer.newLine();//adds new line to file
                            i++;
                            line = reader.readLine();//The next line is read
                            //if value of index is 1 add line to output file and add dash and space
                        } else if (Integer.parseInt(String.valueOf(binString.charAt(i))) == 1) {
                            writer.write(line + ", ");
                            writer.newLine();//adds new line to file
                            i++;
                            line = reader.readLine();//The next line is read
                        }
                    } else {
                        //if value of index is 1 and value of the next index is 0 add line to output file and add double space to line
                        if (Integer.parseInt(String.valueOf(binString.charAt(i))) == 0
                                && Integer.parseInt(String.valueOf(binString.charAt(i + 1))) == 0) {
                            writer.write(line + "  ");
                            writer.newLine();//adds new line to file
                            i += 2;
                            line = reader.readLine();//The next line is read
                            //if value of index is 1 and value of the next index is 0 add line to output file and add a comma and space to line
                        } else if (Integer.parseInt(String.valueOf(binString.charAt(i))) == 1
                                && Integer.parseInt(String.valueOf(binString.charAt(i + 1))) == 0) {
                            writer.write(line + ", ");
                            writer.newLine();
                            i += 2;
                            line = reader.readLine();
                            //if value of index is 0 and value of the next index is 1 add line to output file and add sppace and comma to line to end of line
                        } else if (Integer.parseInt(String.valueOf(binString.charAt(i))) == 0
                                && Integer.parseInt(String.valueOf(binString.charAt(i + 1))) == 1) {
                            writer.write(line + " ,");
                            writer.newLine();
                            i += 2;
                            line = reader.readLine();
                            //if value of index is 1 and value of the next index is 1 add line to output file and add double comma to line to end of line
                        } else if (Integer.parseInt(String.valueOf(binString.charAt(i))) == 1
                                && Integer.parseInt(String.valueOf(binString.charAt(i + 1))) == 1) {
                            writer.write(line + ",,");
                            writer.newLine();
                            i += 2;
                            line = reader.readLine();

                        }
                    }

                }
                //break loop for null line
                if (line == null) {
                    break;
                } else {
                    // Additional line is added to file and read
                    writer.write(line);
                    writer.newLine();

                    line = reader.readLine();
                }
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
    static void retrieve(String inpFile) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(inpFile));
            String line = reader.readLine();
            String message = "";//used to store bitvector

            while (line != null) {
                // Your code starts here
                //Reads next line if blank 
                if (line.length() <= 0) {
                    line = reader.readLine();
                } else {
                       //Adds 00 and read next line when last character is space and previous character is a space 
                    if (String.valueOf(line.charAt(line.length() - 1)).equals(" ")
                            && String.valueOf(line.charAt(line.length() - 2)).equals(" ") && line.length() >= 2) {
                        message += "00";
                        
                        line = reader.readLine();
                        //if last character is comma and previous character is a space 01 is added to the message
                    } else if (String.valueOf(line.charAt(line.length() - 1)).equals(",")
                            && String.valueOf(line.charAt(line.length() - 2)).equals(" ") && line.length() >= 2) {
                        message += "01";
                        line = reader.readLine();
                        //if last character is comma and previous character is a space 01 is added to the message
                    } else if (String.valueOf(line.charAt(line.length() - 1)).equals(" ")
                            && String.valueOf(line.charAt(line.length() - 2)).equals(",") && line.length() >= 2) {
                        message += "10";
                        line = reader.readLine();
                         //if last character is comma and previous character are commas 11 is added to the message 
                    } else if (String.valueOf(line.charAt(line.length() - 1)).equals(",")
                            && String.valueOf(line.charAt(line.length() - 2)).equals(",") && line.length() >= 2) {
                        message += "11";
                        line = reader.readLine();
                    } else {
                        //Otherwise read next line
                        line = reader.readLine();
                    }
                }

            }
            reader.close();
            if (message.equals("")) {//print no message received if blank
                System.out.println("no message recieved");
            }//only print first 12 bits 
            else if (message.length() > 12) {
                System.out.println(message.substring(0, 12));
            } else {
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

