import java.util.List;
import java.util.Scanner;

public class MenuLauncher {

    public static void start(){
        System.out.println("***************************");
        System.out.println("File Analysis Application");
        System.out.println("***************************");
        System.out.print("Enter Absolute File Path: ");
        Scanner sc = new Scanner(System.in);
        String filename = sc.nextLine();
        int choice = 0;
        while (choice!=1 && choice!=2) {
            System.out.println();
            System.out.println("What do you want to do with this file?");
            System.out.println("1 - Find all the terms");
            System.out.println("2 - Find top N most frequent terms");
            System.out.print("Enter Choice: ");
            choice = sc.nextInt();
            if(choice!=1 && choice!=2)
                System.out.println("Invalid Choice");
        }


        if (choice==1){
            System.out.print("analysing....");
            Tokenizer tokenizer = new Tokenizer(filename);
            tokenizer.tokenize();
            Tokenizer.printToFile("output/terms.txt",tokenizer.getTokens());
            System.out.println("\nResult is in output/terms.txt");
        }
        if (choice==2){
            System.out.print("Enter value of N: ");
            int n = sc.nextInt();
            System.out.print("analysing....");
            Tokenizer tokenizer = new Tokenizer(filename);
            tokenizer.tokenize();
            List<String> terms = tokenizer.getTokens();
            FrequencyCounter.print_N_MostFrequentTermsToFile("output/top_"+n+"_terms.txt",terms,n);
            System.out.println("\nResult is in output/top_"+n+"_terms.txt");
        }
    }

    public static void main(String[] args) {
        start();
    }
}
