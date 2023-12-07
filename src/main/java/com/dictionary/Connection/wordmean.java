package com.dictionary.Connection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class wordmean {
    String wordname;
    String phonetic;
    List<String> meaning;
    List<String> synonym;
    List<String> antonym;
    List<String> speech;

    String voice;

    public wordmean() {
        wordname = "";
        phonetic = "";
        voice ="";
        meaning = new ArrayList<>();
        speech = new ArrayList<>();
        synonym = new ArrayList<>();
        antonym = new ArrayList<>();
    }

    public  void setVoice(String voice){
        this.voice = voice;
    }

    public String getVoice(){
        return this.voice;
    }
    public  void setWordname(String wordname){
        this.wordname = wordname;
    }
    public String getWordname(){
        return this.wordname;
    }

    public void setPhonetic(String phonetic){
        this.phonetic = phonetic;
    }

    public String getPhonetic(){
        return this.phonetic;
    }

    public void setMeaning(String[] meanings) {
        this.meaning = Arrays.asList(meanings);
    }

    public List<String> getMeaning(){
        return this.meaning;
    }

    public void addMeaning(String definition) {
        meaning.add(definition);
    }

    public void setSpeech(String[] speeches) {
        this.speech = Arrays.asList(speeches);
    }

    public List<String> getSpeech(){
        return this.speech;
    }

    public void addSpeech(String speechs) {
        speech.add(speechs);
    }

    public void setSynonym(String[] synonym) {
        this.synonym = Arrays.asList(synonym);
    }

    public List<String> getSynonym(){
        return this.synonym;
    }

    public void addSynonym(String synonyms) {
        synonym.add(synonyms);
    }

    public void setAntonym(String[] antonym) {
        this.antonym = Arrays.asList(antonym);
    }

    public List<String> getAntonym(){
        return this.antonym;
    }

    public void addAntonym(String antonyms) {
        antonym.add(antonyms);
    }
}
