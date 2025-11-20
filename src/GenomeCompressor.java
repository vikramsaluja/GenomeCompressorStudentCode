/******************************************************************************
 *  Compilation:  javac GenomeCompressor.java
 *  Execution:    java GenomeCompressor - < input.txt   (compress)
 *  Execution:    java GenomeCompressor + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   genomeTest.txt
 *                virus.txt
 *
 *  Compress or expand a genomic sequence using a 2-bit code.
 ******************************************************************************/

/**
 *  The {@code GenomeCompressor} class provides static methods for compressing
 *  and expanding a genomic sequence using a 2-bit code.
 *
 *  @author Robert Sedgewick
 *  @authorin Wayne
 *  @author Zach Blick
 */
public class GenomeCompressor {

    /**
     * Reads a sequence of 8-bit extended ASCII characters over the alphabet
     * { A, C, T, G } from standard input; compresses and writes the results to standard output.
     */
    public static void compress() {
        // Read in the 8 bit sequence
        String sequence = BinaryStdIn.readString();

        // Create Header for edge cases
        int header = sequence.length();

        BinaryStdOut.write(header);

        for(int i = 0; i < header; i++){
            // Current letter
            char c = sequence.charAt(i);
            // Call helper function to get the compressed key
            int key = compressHelper(c);
            BinaryStdOut.write(key, 2);
        }
        BinaryStdOut.close();
    }

    /**
     * Reads a binary sequence from standard input; expands and writes the results to standard output.
     */
    public static void expand() {
        int header = BinaryStdIn.readInt();
        // Iterate through the whole sequence
        for(int i = 0; i < header; i++){
            int key = BinaryStdIn.readInt(2);
            // Call helper function and get the letter
            char c = expandHelper(key);
            // Print out the letter
            BinaryStdOut.write(c);
        }

        BinaryStdOut.close();
    }

    // Helper function for compress
    public static int compressHelper(char c){
        // Depending on what the current character is, return a key associated with that letter
        if(c == 'A'){
            return 0b00;
        }
        else if(c == 'C'){
            return 0b01;
        }
        else if(c == 'G'){
            return 0b10;
        }
        else{
            return 0b11;
        }
    }

    // Helper function to expand the sequence
    public static char expandHelper(int key){
        // Return the letter that corresponds with each key
        if(key == 0b00){
            return 'A';
        }
        else if(key == 0b01){
            return 'C';
        }
        else if(key == 0b10){
            return 'G';
        }
        else {
            return 'T';
        }
    }


    /**
     * Main, when invoked at the command line, calls {@code compress()} if the command-line
     * argument is "-" an {@code expand()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}