import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

//This is Part-A of the Task
public class Tokenizer {
    private String filename;
    private String data;
    private List<String> tokens;

    public Tokenizer(String filename) {
        this.filename = filename;
        data = "";
        initData();
        tokens = null;
    }

    public List<String> getTokens() {
        return tokens;
    }

    private void initData() {
        String line = null;
        try {
            File file = new File(filename);    //creates a new file instance
            FileReader fr = new FileReader(file);   //reads the file
            BufferedReader br = new BufferedReader(fr);  //creates a buffering character input stream

            while ((line = br.readLine()) != null) {
                data += (line.trim());
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void tokenize() {
        tokenizeData();
        toLowerCase();
        removeStopwords();
        removeNumbersAndCharacters();
        porterStemTokens();
//        for (int i = 0; i < tokens.size(); i++) {
//            System.out.println(tokens.get(i));
//        }
    }

    public void toLowerCase(){
        for (int i = 0; i < tokens.size(); i++) {
            //if word is not abbreviation convert to lowercase
            if(!isAbbreviation(tokens.get(i)))
                tokens.set(i,tokens.get(i).toLowerCase(Locale.ROOT));
        }
    }
    //Part-1(i)
    public void tokenizeData() {
        handleAbbreviations();

        //All other punctuation should be considered word separators: "200,000" should
        //become ["200", "000"]
        String[] stTokens = this.data.split("[\\p{Punct}\\s]+");
        this.tokens = new ArrayList<>(Arrays.asList(stTokens));
    }

    //It Finds all Abbreaviations like A.B.C and replace them with ABC
    public void handleAbbreviations() {
        StringTokenizer tokenizer = new StringTokenizer(data, " ");
        data = "";
        String tokenText;
        while (tokenizer.hasMoreTokens()) {
            tokenText = tokenizer.nextToken();
            if (tokenText != null) {
                if (Pattern.compile("\\b([a-zA-Z]\\.){2,}+").matcher(tokenText).find())
                    tokenText = tokenText.replace(".", "");
                //System.out.println(tokenText);
                data+=(tokenText + " ");
            }
        }
    }
    public boolean hasNumberOrCharachter(String str){
        String s = str.toLowerCase(Locale.ROOT);
        if(str.length()==1)
            return true;
        for(int i =0;i<str.length();i++){
            if (s.charAt(i)<97 || s.charAt(i)>122)
                return true;
        }
        return false;
    }
    public void removeNumbersAndCharacters(){
        for(int i =0;i<this.tokens.size();i++){
            if(hasNumberOrCharachter(tokens.get(i)))
                tokens.remove(i);
        }
    }

    //Part 1(ii)
    public void removeStopwords() {
        List<String> stopwords = new ArrayList<>();

        //Initialize Stopwords List
        String line = null;
        try {
            File file = new File("stopwords.txt");    //creates a new file instance
            FileReader fr = new FileReader(file);   //reads the file
            BufferedReader br = new BufferedReader(fr);  //creates a buffering character input stream
            while ((line = br.readLine()) != null) {
                stopwords.add(line
                                .trim()
                                .toLowerCase());
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        //Loop through tokens and remove if any stopword is found
        for (int i = 0; i < tokens.size(); i++) {
            if (stopwords.contains(tokens.get(i))) {
                tokens.remove(i);
                i--;
            }
        }
        //loop through tokens and remove all stopwords

    }

    private boolean isAbbreviation(String str){

        if(str.length()<2)
            return false;
        //convert String to char array
        char[] charArray = str.toCharArray();

        for(int i=0; i < charArray.length; i++){

            //if any character is not in upper case, return false
            if( !Character.isUpperCase( charArray[i] ))
                return false;
        }

        return true;
    }

    public void porterStemTokens() {
        PorterStemmer stemmer = new PorterStemmer();
        for (int i = 0; i < tokens.size(); i++) {
           tokens.set(i,stemmer.stemWord(tokens.get(i)));
        }
    }

    public static void printToFile(String filename,List<String> tokens){
        try {
            PrintWriter writer = new PrintWriter(new File(filename));
            for (int i = 0; i < tokens.size(); i++) {
                writer.println(tokens.get(i));
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Tokenizer tokenizer = new Tokenizer("mobydick.txt");
        tokenizer.tokenize();
        printToFile("tokenized.txt",tokenizer.getTokens());
    }
}
