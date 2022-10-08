import java.util.ArrayList;
import java.util.Scanner;

/**
 * ss = 1 -> Direct Mapping
 * ss = 2 or ss = 4 -> Set Associative
 * ss = 8 -> Fully Associative
 */

public class CacheMemory {
    private CMdata cmdata;
    private CMdirectory cmdirectory;
    private int sw; //Size Word: 4/8; 4 -> 2bits; 8 -> 3bits;
    private int sb;
    private int ss;
    private MainMemory MM;

    private int bitsForAddress;
    private int bitsForByte;
    private int bitsForWord;
    private int bitsForTag;
    private int bitsForSet;

    private int hits;
    private int misses;

    private int replacementPol; //FIFO = 0; LRU = 0;
    private int WriteStrategy;
    private int cmUpdates;
    private ArrayList<Integer> ControlArray;


    //Start()-----------------------------------------
    public void Start(int ss_in, int sb_in, int sw_in, int replacementPol_in, MainMemory MM_in, int WriteStrategy_in, int cmUpdates_in)
    {
        ss = ss_in;
        sb = sb_in;
        sw = sw_in;
        replacementPol = replacementPol_in;
        WriteStrategy = WriteStrategy_in;
        cmUpdates = cmUpdates_in;
        MM = MM_in;
        ControlArray = new ArrayList<Integer>();
        //Start cmdata:
        hits = 0;
        misses = 0;


        cmdata = new CMdata(ss, sb, sw);

        //Configure the way to interpretate addresses:
            //bits per address
        bitsForAddress = (int)((Math.log(MM.getNumberOfCellsInMM()))/ Math.log(2));
            //bits for byte
        bitsForByte = (int)(Math.log(sw) / Math.log(2));    //log_b(x) = ln(x) / ln(b)
            //bits for word
        bitsForWord = (int)(Math.log(sb/sw) / Math.log(2));
            //bits for tag
        if (ss == 8){   //Fully Associative
            bitsForTag = bitsForAddress - (bitsForByte + bitsForWord);
            bitsForSet = 0;
        }
        else if (ss == 1){   //Direct Mapped
            bitsForSet = 3; //8 blocks in CM, 1 in each Set -> 8 = 2³ -> 3 bits;
                            //Actually represents where in CM a certain block must be stored;
            bitsForTag = bitsForAddress - (bitsForByte + bitsForWord + bitsForSet);
            //tag = Block(MM)/num_lines_cache
            //line = Block(MM) mod num_lines_cache
        }
        else {  //Set Associative (2 or 4 Sets)
            bitsForSet = (int) (Math.log(ss) / Math.log(2));
            bitsForTag = bitsForAddress - (bitsForByte + bitsForWord + bitsForSet);
        }
        //System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
        System.out.println("|| The MM has " + MM.getNumberOfCellsInMM() + " cells, therefore addresses must have " + bitsForAddress + " bits                        ||");
        System.out.println("|| Of which, " + bitsForByte + " represent the byte, " + bitsForWord + " the word, " + bitsForSet + " the set and " + bitsForTag + " the tag                 ||");

        //Start CMdirectory:
        cmdirectory = new CMdirectory();

    }
    //--------------------------------------------------

    //setEverythingTo0----------------------------------
    public void setEverythingTo0(){
        cmdata.setEverythingTo0();
        cmdirectory.setEverythingTo0();
    }
    //--------------------------------------------------

    //interpretateAddress-------------------------------
    public void interpretateAddress(int address_in, int op_in) {
        int block = 0;  //Otherwise block might not have been initialized
        int word = 0;   //Otherwise word might not have been initialized
        int byteVar = 0;  //Otherwise byteVar might not have been initialized
        int ans = 0; //Otherwise ans might not have been initialized
        int tag = 0; //Otherwise tag might not have been initialized
        int set = 0; //Otherwise set might not have been initialized

        System.out.println("Interpretating address " + address_in);
        block = address_in / sb;
        //word = address_in/sw;    //add/bytesPerWord
        word = Math.floorMod(address_in, sb)/sw;
        byteVar = Math.floorMod(address_in, sw);
        switch (ss) {
            case 1:
                System.out.println("Correspondence type: Direct Mapping");
                tag = block / 8;
                set = Math.floorMod(block, 8);
                break;
            case 2:
                System.out.println("Correspondence type: Set Associative (4 sets)");
                tag = block / 4;
                set = Math.floorMod(block, 4);
                break;
            case 4:
                System.out.println("Correspondence type: Set Associative (2 sets)");
                tag = block / 2;
                set = Math.floorMod(block, 2);
                break;
            case 8:
                System.out.println("Correspondence type: Fully Associative");
                tag = block;
                set = 0; //Only one set
                break;
        }
        //After switch
        System.out.println("This address corresponds to the " + set + " set");
        System.out.println("Looking for tag " + tag + " from " + cmdata.getSet(set).getPosFromDirectory() + " to " + cmdata.getSet(set).getPosToDirectory());
        ans = cmdirectory.lookForTag(tag, cmdata.getSet(set).getPosFromDirectory(), cmdata.getSet(set).getPosToDirectory());

        if (ans == -1) {    //Miss
            System.out.println("Miss, getting block " + block + " from MM");
            misses++;
            ans = getBlockFromMM(block, tag, set, false, -1);  //ans = Where in CM the block has been placed
            cmdirectory.getCell(ans).setDirty(false);  //As it's a new block in CM it hasn't been modified
        } else {    //Hit
            System.out.println("Hit!");
            hits++;
            if (replacementPol == 1) {
                System.out.println("The replacement policy is LRU, therefore " + ans + " is now the last in ControlArray");
                for (int i = 0; i < ControlArray.size(); i++) {
                    if (ControlArray.get(i) == ans) {
                        int a = ControlArray.get(i);
                        ControlArray.remove(i);
                        ControlArray.add(a);
                        break;
                        }
                    }
                System.out.println(ControlArray);
            }
        }
        if (op_in == 0) {   //op = read
            System.out.println("The required data is in the " + ans + " line in CM");
            System.out.println("Set = " + ans / 8 / cmdata.getNumberOfSets() + ", line = " + Math.floorMod(ans, 8 / cmdata.getNumberOfSets()) + ", words = " + word + ", byte = " + byteVar);
            System.out.println("Value = " + cmdata.getSet(ans / 8 / cmdata.getNumberOfSets()).getBlock(Math.floorMod(ans, 8 / cmdata.getNumberOfSets())).getWord(word).getByte(byteVar));
        }
        else {  //op = write    //ans -> Where in CM the block is
            Scanner sc = new Scanner(System.in);
            System.out.println("Value to be written: ");
            int val = sc.nextInt();
            if (WriteStrategy == 0) {   //WriteStrategy = Write through
                //Write directly in MM
                MM.writeInMM(address_in, val);
                if (cmUpdates == 0) {    //CM DOES update
                    getBlockFromMM(block, tag, set, true, ans);    //Get from MM the modified block
                }
                else {  //CM doesn't update
                        //Delete block from CM
                    cmdata.getLine(ans).setEverythingTo0();
                    cmdirectory.getCell(ans).setEverythingTo0();
                }
            }
            else {  //WriteStrategy = Write Back
                //Modify the block in CM -> dirty = 1
                //System.out.println("word = " + word + ", sb = " + sb + ", sw = " + sw);
                cmdata.getLine(ans).getWord(Math.floorMod(word, sb/sw)).setByte(byteVar, val);   //Words/Line = sb/sw?
                cmdirectory.getCell(ans).setDirty(true);
            }
        }
    }
    //--------------------------------------------------

    //printCMdata---------------------------------------
    public void printCMdata(){
        System.out.println(cmdata);
    }
    //--------------------------------------------------

    //printCMdirectory----------------------------------
    public void printCMdirectory(){
        System.out.println(cmdirectory);
    }
    //--------------------------------------------------

    //printCM-------------------------------------------
    public void printCM(){
        System.out.println(cmdata.toString(cmdirectory));
    }
    //-------------------------------------------------

    //GetBlockFromMM()---------------------------------
    public int getBlockFromMM(int block, int tag, int set, boolean hit, int modCM_in){
        //calculate first address:
        int add = block * sb;
        int modCM;
        //Where should the block be placed? //Empty Space/ FIFO (Fist In First Out)/ LRU (Least Recently Used)
        if (hit){
            modCM = modCM_in;
        }
        else {
            modCM = cmdata.getSet(set).setHasAvailableSpace(cmdirectory);
            System.out.println("Is there available space in CM in set " + set + "? = " + modCM);
        }

        if (modCM == -1){ //There's no available space in the set, block must be replaced
            switch (ss){
                case 8: //Fully Associative Mapping
                    modCM = ControlArray.remove(0);
                    break;
                case 4: //Set Associative Mapping
                case 2:
                    int i = 0;
                    while (ControlArray.get(i) < set*ss & i < ControlArray.size()){
                        i++;
                    }
                    modCM = ControlArray.remove(i);
                    break;
                case 1: //Direct Mapping
                    modCM = set;
            }
            //Difference between FIFO and LRU -> how ControlArray is managed
        }
        System.out.println("Block in CM that has to be modified: " + modCM);
        if (WriteStrategy == 1 & cmdirectory.getCell(modCM).isDirty()){ //WriteStrategy = Write Back and Is dirty -> transfer block to MM
            System.out.println("Block " + modCM + " is Dirty, therefore, transferring to MM before replacing");
            transferFromCMtoMM(modCM, set);
        }
        //Tranfer Block
        TransferBlockFromMM(add, modCM, set);

        //Change the tag
        System.out.println("Changing tag, modCM = " + modCM + "; tag = " + tag);
        cmdirectory.getCell(modCM).setTag(tag);
        cmdirectory.getCell(modCM).setBusy(true);
        System.out.println("Busy = " + cmdirectory.getCell(modCM).isBusy());

        //Print ControlArray
        System.out.println("ControlArray = " + ControlArray);
        return modCM;
    }
    //------------------------------------------------

    //TransferBlockFromMM-----------------------------
    private void TransferBlockFromMM(int add, int modCM, int set) {
        for (int i = 0; i < sb/sw; i++){
            for (int e = 0; e < sw; e++){
                cmdata.getSet(set).getBlock(Math.floorMod(modCM, ss)).getWord(i).setByte(e, MM.readFromAddress(add));
                add++;
            }
        }
        ControlArray.add(modCM);
    }
    //-------------------------------------------------

    //writeInByteCM------------------------------------
    public void writeInByteCM(int ans_in, int word_in, int byte_in){
        Scanner sc = new Scanner(System.in);
        System.out.println("block = " + ans_in + "; word = " + word_in + "; byte = " + byte_in);
        System.out.println("Value to be written: ");
        int val = sc.nextInt();
        cmdata.getLine(ans_in).getWord(word_in).setByte(byte_in, val);
        cmdirectory.getCell(ans_in).setDirty(true);
        System.out.println("Value has been written and dirty = true");
    }
    //-------------------------------------------------

    //transferFromCMtoMM-------------------------------
    public void transferFromCMtoMM(int modCM_in, int set){
        System.out.println();
        //Where to transfer the data:
        int start = 0; //Otherwise might not have been initialised
        switch (ss) {
            case 8: //Fully Associative; tag = Block(MM)
                start = cmdirectory.getCell(modCM_in).getTag();
                break;
            case 4:
            case 2: //Set Associative; tag = Block(MM)/num_sets_cache -> Block(MM) = tag * num_sets_cache
                start = cmdirectory.getCell(modCM_in).getTag() * cmdata.getNumberOfSets();
                break;
            case 1: //Direct mapping; tag = Block(MM)/num_lines_cache -> Block(MM) = tag * num_lines_cache
                start = cmdirectory.getCell(modCM_in).getTag() * 8;
                break;
        }
        start = start * sb; //BlocK(MM) address = Block(MM) * sb;
        System.out.println("Block corresponds to " + start + " address");
        System.out.println("Transferring block...");
        //int start = address in MM of the first byte of the block
        for (int i = start; i < start + sb; i++){
            MM.writeInMM(i, cmdata.getLine(modCM_in).getWord(i/ cmdata.getLine(modCM_in).getNumberOfWordsPerBlock()).getByte(Math.floorMod(i, sw)));
        }
    }
    //-------------------------------------------------

    //seeHnMrates--------------------------------------
    public void seeHnMrates(){
        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
        System.out.println("|| Hits = " + hits + "                                                                            ||");
        System.out.println("|| Misses = " + misses + "                                                                          ||");
        System.out.println("|| Total access time = " + (hits + misses) + "                                                               ||");
        if (hits+misses != 0) {
            System.out.println("|| Hit Rate = " + (float)hits / (float)(hits + misses));
        }
        else {
            System.out.println("|| Hit Rate = 100                                                                      ||");
        }
        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");






    }
}


