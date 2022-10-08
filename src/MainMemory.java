public class MainMemory {
    private MainMemoryCell[] MM;
    private int numberOfCellsInMM;

    //Constructor------------------------------------------
    public MainMemory(int sb, int sw) {
        numberOfCellsInMM = 8 * sw * sb;
        MM = new MainMemoryCell[8 * sw * sb];
        for (int i = 0; i < MM.length; i++) {
            MM[i] = new MainMemoryCell(i, 6);
        }
    }
    //-----------------------------------------------------

    //printMM----------------------------------------------
    public void printMM(int sw) {
        String word;
        for (int i = 0; i < MM.length; i = i + sw) {
            word = "";
            for (int j = 0; j < sw; j++) {
                word = word + "|| @" + (i+j) + " -> " + MM[i].readCell(i) + " || ";
            }
            System.out.println(word);
        }
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
