public class MainMemoryCell {
    private int address;
    private int byteData;

    //Constructor--------------------------------
    public MainMemoryCell(int address_in, int byteData_in){
        address = address_in;
        byteData = byteData_in;
    }
    //-------------------------------------------

    //readCell-----------------------------------
        //returns the data if the address is the same, -1 if it's not
    public int readCell (int address_in){
        if (address_in == address){
            return byteData;  //Overflows can happen!
        }
        else {
            return -1;
        }
    }
    //-------------------------------------------

    //printCell----------------------------------
    public void printCell(){
        System.out.println("address: " + address + "; data: " + byteData);
    }
    //-------------------------------------------

    //setCell------------------------------------
    public void setCell(int value_in){
        byteData = value_in;
    }
    //-------------------------------------------
}
