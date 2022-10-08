import java.util.InputMismatchException;

public class CMdata {
    private Set[] data;
    private CMdata onlyInstance = null;
    private int numberOfSets;

    //Constructor---------------------------
    public CMdata(int ss, int sb, int sw) throws InputMismatchException{
        /**
         * ss: Size Set
         * sb: Size Block
         * sw: Size word
         */
        try{
            data = new Set[8/ss];
            for (int i = 0; i < data.length; i++){
                data[i] = new Set(ss, sb, sw);
                //System.out.println("Set " + i + ", setPosFromDirectory = " + (i*ss) + " ; setPosToDirectory = " + (((i+1)* ss)-1));
                data[i].setPosFromDirectory(i*ss);
                data[i].setPosToDirectory(((i+1)* ss)-1);
            }
            numberOfSets = 8/ss;
        } catch (InputMismatchException e) {
            throw e;
        }
    }
    //-----------------------------------------

    //Intento de singleton(no funciona)-------
    public CMdata getCMdataInstance() throws Exception{
        if (onlyInstance == null){
            throw new Exception("CMdata instance hasn't been generated yet. Please call this method adding the three parameters: ss: size sets; sb: size blocks and sw: size words");
        }
        else {
            return onlyInstance;
        }
    }
    //------------------------------------------

    //Intento de singleton (no funciona)------------
    public CMdata getCMdataInstance(int ss, int sb, int sw){
        onlyInstance = new CMdata(ss, sb, sw); //Why am I not being asked to handle the exception??
        return onlyInstance;
    }
    //----------------------------------------------

    //Getters-----------------------------------
    public int getNumberOfSets() {
        return numberOfSets;
    }
    //-------------------------------------------

    //setEverythingTo0----------------------------
    public void setEverythingTo0(){
        for (int i = 0; i < data.length; i++){
            data[i].setEverythingTo0();
        }
    }
    //--------------------------------------------

    //toString------------------------------------
    public String toString(){
        String returnS = "";
        for (int i = 0; i < data.length; i++){
            returnS = returnS + "Set" + i + data[i];
        }
        return returnS;
    }
    //---------------------------------------------

    //toString------------------------------------
    public String toString(CMdirectory directory_in){
        System.out.println("Amount of sets: " + data.length);
        String returnS = "";
        for (int i = 0; i < data.length; i++){
            //System.out.println("Getting in set " + i);
            returnS = returnS + "\n-------------------------------------\nSet " + i + data[i].toString(directory_in, i);

        }
        return returnS;
    }
    //---------------------------------------------

    //getSet()-------------------------------------
    public Set getSet(int i){
        return data[i];
    }
    //---------------------------------------------

    //getLine--------------------------------------
    public Block getLine(int i){
        // pre: i < 8
        int j = 0;
        for (int setN = 0; setN < data.length; setN++){
            for (int blockN = 0; blockN < data[0].getBlocksPerSet(); blockN++){
                if (j == i){
                    return data[setN].getBlock(blockN);
                }
                j++;
            }
        }
        //Shouldn't happen
        return new Block(0, 0); //Should elevate an exception
    }
    //---------------------------------------------

}
