package com.example.javafx;

import java.util.HashMap;
import java.io.*;

class Word {
    public String wordname;
    public  String mean;

    Word(String wordname , String mean){
        this.mean  =mean;
        this.wordname = wordname;
    }
}
public class Dictionary {
    public HashMap<String, Word> map = new HashMap<String , Word>();

    public  void loadRecords() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("L:\\javaFX\\src\\main\\java\\com\\example\\javafx\\dictionary.txt"));
        for(String line = br.readLine(); line != null ; line = br.readLine()){
            String[] parsedLine = line.split(",");
            String wordName = parsedLine[0];
            String meaning = parsedLine[1];
            Word words = new Word(wordName , meaning);
            this.map.put(wordName , words);
        }
        br.close();
    }

    public  String findWord(String word){
        for(String i : map.keySet()){
            if(i.equals(word)){
                Word newWord =map.get(i);
                return newWord.mean;
            }
        }
        return null;
    }

    public  void addWord(Word word){
        boolean isword = false;
        for(String i: map.keySet()){
            if(i.equals(word.wordname))
            {
                System.out.println("Đã có từ này!");
                isword = true;
                break;
            }
        }
        if(!isword){
            map.put(word.wordname , word);
            System.out.println("Thêm thành công!");
        }
    }

    public  void removeWord(Word word){
        for(String i: map.keySet()) {
            if (i.equals(word.wordname)) {
                map.remove(word.wordname);
                System.out.println("Xoa thanh cong!");
                break;
            }
        }
    }

    public  void saveRecords() throws Exception{
        BufferedWriter bw = new BufferedWriter(new FileWriter("L:\\javaFX\\src\\main\\java\\com\\example\\javafx\\dictionary.txt"));
        for(String i: map.keySet()){
            Word w = map.get(i);
            bw.write(w.wordname);
            bw.write(",");
            bw.write(w.mean);
            bw.write("\n");
        }
        bw.close();
    }

    public  static  void main(String[] args) throws Exception{
        Dictionary obj = new Dictionary();
        obj.loadRecords();
        System.out.println(obj.findWord("run"));
        System.out.println(obj.findWord("hello"));
        Word a = new Word("hu" , "hihi");
        obj.addWord(a);
        obj.saveRecords();

    }
}
