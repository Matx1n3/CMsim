import java.io.*;
public class Word {
    private int[] word;
    private int wordSize;

    //Constructor----------------------------------
    public Word(int i){
        //Size of the words 4 or 8 (bytes)
        if (i == 4 || i == 8){
            word = new int[i];
            wordSize = i;
        }
    }
    //---------------------------------------------

    //getters--------------------------------------
    public int getWordSize() {
        return wordSize;
    }
    //---------------------------------------------

    //setEverythingTo0-----------------------------
    public void setEverythingTo0(){
        for (int i = 0; i < word.length; i++){
            word[i] = 0;
        }
    }
    //----------------------------------------------

    //toString--------------------------------------
    public String toString(){
        String returnS = "";
        for (int i = 0; i < word.length; i++){
            //System.out.println("In word, byte: " + i);
            returnS = returnS + " " + word[i];
        }
        return returnS;
    }
    //----------------------------------------------

    //setByte---------------------------------------
    public void setByte(int index, int val){
        word[index] = val;
    }
    //----------------------------------------------

    //getByte--------------------------------------
    public int getByte(int index){
        return word[index];
    }
    //---------------------------------------------
}
