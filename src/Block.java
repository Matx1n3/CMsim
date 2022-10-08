import java.io.*;
import java.util.InputMismatchException;

public class Block {
    private Word[] Bloque;

    //Constructor----------------------------
    public Block(int sb, int sw) throws InputMismatchException {
        /**
         sb: size block; sw: size word
        Size of the block/line 32 or 64 (bytes)
         **/
        if((sb == 32 || sb == 64) && (sw == 4 || sw == 8)) {
            Bloque = new Word[sb/sw]; //F.E: 8 words of 4bytes each = 32bytes/block
            //System.out.println("Each block has " + Bloque.length + "words");
            for (int i = 0; i<Bloque.length; i++){
                Bloque[i] = new Word(sw);
            }
        }
        else {
            throw new InputMismatchException("sb: 32 or 64; sw: 4 or 8");
        }
    }
    //---------------------------------------

    //setEverythingTo0-----------------------
    public void setEverythingTo0(){
        for (int i = 0; i < Bloque.length; i++){
            Bloque[i].setEverythingTo0();
        }
    }
    //----------------------------------------

    //toString---------------------------------
    public String toString(){
        String returnS = "";
        for (int i = 0; i < Bloque.length; i++){
            returnS = returnS + "\n" + Bloque[i];
        }
        return returnS;
    }
    //-----------------------------------------

    //toString---------------------------------
    /** DELETE IF DON'T USE
    public String toString(CMdirectory directory_in, int setN){
        String returnS = "";
        //System.out.println("There're " + Bloque.length + "words/block");
        for (int i = 0; i < Bloque.length; i++){
            //System.out.println("In block, word: " + i);
            returnS = returnS + "\n" + " Block " + i+(4*setN) + " dataa: "+ Bloque[i];
        }
        return returnS;
    }
     **/
    //-----------------------------------------

    //getWord---------------------------------
    public Word getWord(int index){
        return Bloque[index];
    }
    //----------------------------------------

    //getNumberOfWordsPerBlock----------------
    public int getNumberOfWordsPerBlock(){
        return Bloque.length;
    }
    //----------------------------------------
}
