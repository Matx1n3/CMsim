import java.util.InputMismatchException;

public class Set {
    private Block[] set;
    private int posFromDirectory; //Which CMdirectoryCells correspond to a particular set
    private int posToDirectory;
    private int ss;

    //Constructor----------------------
    public Set(int ss, int sb, int sw) throws InputMismatchException {
        /**
         * ss: size sets
         * sb: size blocks
         * sw: size words
         */
        this.ss = ss;
        try {
            if (ss == 1 || ss == 2 || ss == 4 || ss == 8) {
                set = new Block[ss];
                for (int i = 0; i < set.length; i++) {
                    set[i] = new Block(sb, sw);
                }
            }
            else {
                throw new InputMismatchException("ss: 1, 2, 4 or 8");
            }
        }
        catch (InputMismatchException e){
            throw e;
        }
    }
    //----------------------------------

    //Getters----------------------------
    public int getPosFromDirectory(){
        return posFromDirectory;
    }

    public int getPosToDirectory(){
        return posToDirectory;
    }

    //------------------------------------

    //Setters-----------------------------
    public void setPosFromDirectory(int posFromDirectory) {
        this.posFromDirectory = posFromDirectory;
    }

    public void setPosToDirectory(int posToDirectory) {
        this.posToDirectory = posToDirectory;
    }
    //------------------------------------

    //setEverythingTo0------------------
    public void setEverythingTo0(){
        for (int i = 0; i < set.length; i++){
            set[i].setEverythingTo0();
        }
    }
    //----------------------------------

    //toString---------------------------
    public String toString(){
        String returnS = "";
        for (int i = 0; i < set.length; i++){
            returnS = returnS + set[i] + "\n----------------------------------------------------------------\n";
        }
        return returnS;
    }
    //-------------------------------------

    //toString---------------------------
    public String toString(CMdirectory directory_in, int setN_in){
        String returnS = "";
        for (int i = 0; i < set.length; i++){
            //System.out.println("Set_in = " + setN_in + "; Accessing block: " + i);
            //returnS = returnS + set[i].toString(directory_in, setN_in) + "\n----------------------------------------------------------------\n";
            returnS = returnS + "\n" + " Block " + (i+(set.length*setN_in)) + " " + directory_in.getCell(ss*setN_in+i).toString() + "; data: "+ set[i];
        }
        return returnS;
    }
    //-------------------------------------

    //setHasAvailableSpace-------------------
    public int setHasAvailableSpace(CMdirectory cMdirectory){
        System.out.println("Looking for available space in CM; From: " + posFromDirectory + " to " + posToDirectory);
        for (int i = posFromDirectory; i <= posToDirectory; i++){
            //System.out.println("i = " + i + " cellIsBusy = " + cMdirectory.getCell(i).isBusy());
            if (!cMdirectory.getCell(i).isBusy()){
                return i;
            }
        }
        return -1;
    }
    //--------------------------------------

    //getBlock------------------------------
    public Block getBlock(int index){
        return set[index];
    }
    //--------------------------------------

    //getWordsPerSet------------------------
    public int getBlocksPerSet(){
        return set.length;
    }
    //--------------------------------------


}
