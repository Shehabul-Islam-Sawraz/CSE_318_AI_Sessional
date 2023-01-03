import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input_data/input.txt"));
        for(int i=0; i<3;i++){
            System.out.println(scanner.nextLine());
        }
        for(int i=0; i<10;i++){
            String[] numbers = scanner.nextLine().split(", ");
            for(int j=0;j<10;j++){
                System.out.print(numbers[j]+" ");
            }
            System.out.println("\n");
        }
    }
}