public class MainMemory {
    private MainMemoryCell[] MM;
    private int numberOfCellsInMM;

    //Constructor------------------------------------------
    public MainMemory(int sb, int sw){
        numberOfCellsInMM = 8 * sw * sb;
        MM = new MainMemoryCell[8*sw*sb];
        for (int i = 0; i < MM.length; i++ ){
            MM[i] = new MainMemoryCell(i, 6);
        }
    }
    //-----------------------------------------------------

    //printMM----------------------------------------------
    public void printMM(){
        for (int i = 0; i < MM.length; i++){
            MM[i].printCell();
        }
        //System.out.println("Done printing MM");
        //System.out.println("Done printing MM");
    }

    //readFromAddress--------------------------------------
    public int readFromAddress(int address){
        if (address <= numberOfCellsInMM){
            return MM[address].readCell(address);
        }
        else {
            System.out.println("\n¡That address doesn't exist in MM!¡Accessing to address mod n of addresses!");
            return readFromAddress(address%numberOfCellsInMM);
        }
    }
    //-----------------------------------------------------

    //getNumberOfCells-------------------------------------
    public int getNumberOfCellsInMM(){
        return numberOfCellsInMM;
    }
    //-----------------------------------------------------

    //writeInMM--------------------------------------------
    public void writeInMM(int address_in, int value_in){
        MM[address_in].setCell(value_in);
    }
    //-----------------------------------------------------
}
