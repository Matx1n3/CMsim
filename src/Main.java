import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        boolean exit = false;

        while (!exit) {

            System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
            System.out.println("||--------------------------------CACHE MEMORY SIMULATOR-------------------------------||");
            System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
            System.out.println("|| Enter the size of the words(bytes/word; 4/8):                                       ||");
            Scanner sc = new Scanner(System.in);
            int sw = sc.nextInt();
            System.out.println("|| Enter the size of the blocks(32/64):                                                ||");
            int sb = sc.nextInt();
            System.out.println("|| Enter the size of the sets (1, 2, 4 or 8):                                          ||");
            int ss = sc.nextInt();
            int replacementPol;
            if (ss == 1) {
                replacementPol = 0;
            } else {
                System.out.println("|| Enter the replacement policy(FIFO(0)/LRU(1)):                                       ||");
                replacementPol = sc.nextInt();
            }
            System.out.println("|| Enter the write strategy (Write-through = 0; Write-back = 1):                       ||");
            int writeStrategy = sc.nextInt();
            int cmUpdates;
            if (writeStrategy == 0) {
                System.out.println("|| Cache updates (no = 0; 1 = yes):                                                ||");
                cmUpdates = sc.nextInt();
            } else {
                cmUpdates = 0;
            }
            System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
            System.out.println("|| Initializing MM...                                                                  ||");
            MainMemory MM = new MainMemory(sb, sw);
            System.out.println("|| Done                                                                                ||");
            System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
            System.out.println("|| Initializing CM...                                                                  ||");
            CacheMemory CM = new CacheMemory();
            CM.Start(ss, sb, sw, replacementPol, MM, writeStrategy, cmUpdates);
            System.out.println("|| Done                                                                                ||");

            boolean restart = false;

            while (!restart & !exit) {

                System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
                System.out.println("||--------------------------------CACHE MEMORY SIMULATOR-------------------------------||");
                System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
                System.out.println("|| 1.- Perform operation                                                               ||");
                System.out.println("|| 2.- Print CM                                                                        ||");
                System.out.println("|| 3.- Print MM                                                                        ||");
                System.out.println("|| 4.- See Hit and Miss rates                                                          ||");
                System.out.println("|| 5.- Restart                                                                         ||");
                System.out.println("|| 6.- Exit                                                                            ||");
                System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
                System.out.println("||                                 CHOOSE AN OPTION:                                   ||");
                int option = sc.nextInt();
                System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
                switch (option) {
                    case 1:
                        performOp(sc, CM);
                        break;
                    case 2:
                        CM.printCM();
                        break;
                    case 3:
                        MM.printMM();
                        break;
                    case 4:
                        CM.seeHnMrates();
                        break;
                    case 5:
                        restart = true;
                        break;
                    case 6:
                        exit = true;
                        break;
                }
            }
        }
        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||");
        System.out.println("|| Exit...                                           ||");
        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||");
    }

    private static void performOp(Scanner sc, CacheMemory CM){
        System.out.println("Enter an address: ");
        int address = sc.nextInt();
        System.out.println("Operation to be executed (read = 0, write = 1): ");
        int op = sc.nextInt();
        CM.interpretateAddress(address, op);
    }
}