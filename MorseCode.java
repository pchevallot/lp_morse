package morse;

import java.util.Map;
import java.util.HashMap;
/**
 *
 * @author hasi_wk
 */
 
 // TROUVE SUR INTERNET !!!
public class MorseCode {
    private static final String placeHolder="?";
    private Map<String , String> txtmorse = new HashMap<String , String >();
    private Map<String , String> morsetxt = new HashMap<String , String >();
    public MorseCode(){
        //init maps
        txtmorse.put("A", ".-");    morsetxt.put(".-", "A");
        txtmorse.put("B", "-...");  morsetxt.put("-...", "B");
        txtmorse.put("C", "-.-.");  morsetxt.put("-.-.", "C");
        txtmorse.put("D", "-..");   morsetxt.put("-..", "D");
        txtmorse.put("E", ".");     morsetxt.put(".", "E");
        txtmorse.put("F", "..-.");  morsetxt.put("..-.", "F");
        txtmorse.put("G", "--.");   morsetxt.put("--.", "G");
        txtmorse.put("H", "....");  morsetxt.put("....", "H");
        txtmorse.put("I", "..");    morsetxt.put("..", "I");
        txtmorse.put("J", ".---");  morsetxt.put(".---", "J");
        txtmorse.put("K", "-.-");   morsetxt.put("-.-", "K");
        txtmorse.put("L", ".-..");  morsetxt.put(".-..", "L");
        txtmorse.put("M", "--");    morsetxt.put("--", "M");
        txtmorse.put("N", "-.");    morsetxt.put("-.", "N");
        txtmorse.put("O", "---");   morsetxt.put("---", "O");
        txtmorse.put("P", ".--.");  morsetxt.put(".--.", "P");
        txtmorse.put("Q", "--.-");  morsetxt.put("--.-", "Q");
        txtmorse.put("R", ".-.");   morsetxt.put(".-.", "R");
        txtmorse.put("S", "...");   morsetxt.put("...", "S");
        txtmorse.put("T", "-");     morsetxt.put("-", "T");
        txtmorse.put("U", "..-");   morsetxt.put("..-", "U");
        txtmorse.put("V", "...-");  morsetxt.put("...-", "V");
        txtmorse.put("W", ".--");   morsetxt.put(".--", "W");
        txtmorse.put("X", "-..-");  morsetxt.put("-..-", "X");
        txtmorse.put("Y", "-.--");  morsetxt.put("-.--", "Y");
        txtmorse.put("Z", "--..");  morsetxt.put("--..", "Z");
        txtmorse.put("1", ".----"); morsetxt.put(".----", "1");
        txtmorse.put("2", "..---"); morsetxt.put("..---", "2");
        txtmorse.put("3", "...--"); morsetxt.put("...--", "3");
        txtmorse.put("4", "....-"); morsetxt.put("....-", "4");
        txtmorse.put("5", "....."); morsetxt.put(".....", "5");
        txtmorse.put("6", "-...."); morsetxt.put("-....", "6");
        txtmorse.put("7", "--..."); morsetxt.put("--...", "7");
        txtmorse.put("8", "---.."); morsetxt.put("---..", "8");
        txtmorse.put("9", "----."); morsetxt.put("----.", "9");
        txtmorse.put("0", "-----"); morsetxt.put("-----", "0");
        txtmorse.put(".",".-.-.-"); morsetxt.put("-.-.-.",".");
        txtmorse.put(",", "--..--"); morsetxt.put("--..--",",");
        txtmorse.put("?", "..--.."); morsetxt.put("..--..", "?");
        txtmorse.put("'", ".----."); morsetxt.put(".----.","'");
        txtmorse.put("!", "-.-.--"); morsetxt.put("-.-.--","!");
    }
    /** ToMorse code method converts given string to morse
     * @param message
     * @return Morse code of the message
     */
    public String toMorse(String message){
        String morseCode="";
        String C ="";
        String code = "";
        /* Convert to uppercase*/
        message = message.toUpperCase();
        /* Iterate through every character and convert to morse with 1 space between
         * characters and 3 spaces between words, unknown characters will have a placeholder instead
         */
        for(int i=0 ; i<message.length();i++){
            C = message.charAt(i)+"";
            if(C.equals(" "))
                morseCode+= "   " ;
            else{
                code = txtmorse.get(C);
                if (code==null)
                    morseCode+=placeHolder+" ";
                else
                    morseCode+=code+" ";
            }
        }// end of for loop
        return morseCode;
    }
    /** Toplain method converts given morsecode into plain
     * @param morse code
     * @return plain text message
     */
    public String toPlain(String morse){
        String plain="";
        String C="";
        /* Split morse into words. there are three spaces between every word
         * and one space between two characters
         */
        String[] words = morse.split("    ");
        /* for every word , extract chracters and parse them
         *  word = morsecode version of the word
         *  char_ = morsecode of the character
         *  chars array contains the current word which is being converted
         */
        for(String word:words){
            String[] chars=word.split(" ");
            for(String char_:chars){
                C= morsetxt.get(char_);
                if(C==null) return "Invalid morse code found! process terminated. ["+char_+"]";
                plain+=C;
            }// end of the inner loop
            // end of a word
            plain+=" ";
        }// end of the outer loop
        return plain;
    }
}