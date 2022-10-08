public class CMdirectory {
    private CMdirectoryCell[] directory;

    //Constructor-----------------------------------
    public CMdirectory(){
        directory = new CMdirectoryCell[8];
        for (int i = 0; i < directory.length; i++){
            directory[i] = new CMdirectoryCell();
        }
        /** DELETE IF WORKS PROPERLY WITHOUT
        for (int i = 0; i < 8; i++){
            directory[i].setDirty(false);
            directory[i].setBusy(false);
            directory[i].setTag(-1);
        }
         **/
    }
    //----------------------------------------------

    //Getters---------------------------------------
    public CMdirectoryCell getCell(int i){
        //System.out.println("CMdirectory: Accessing cell " + i);
        return directory[i];
    }
    //----------------------------------------------

    //setEverythingTo0-------------------------------
    public void setEverythingTo0(){
        for (int i = 0; i < directory.length; i++){
            directory[i].setEverythingTo0();
        }
    }
    //------------------------------------------------

    //lookForTag-------------------------------------
    public int lookForTag(int Tag, int from, int to){
        //from: 0-7, to: 0-7
        int i = from;
        boolean notFound = true;
        while (notFound & i <= to){
            if (directory[i].compareTag(Tag)){
                notFound = false;
            }
            i++;
        }
        if (notFound){
            return -1;
        }
        else {
            return (i-1);
        }
    }
    //------------------------------------------------

    //toString---------------------------------------
    public String toString(){
        String returnS = "";
        for (int i = 0; i < directory.length; i++){
            returnS = returnS + directory[i] + "\n";
        }
        return returnS;
    }
    //-------------------------------------------------

}
