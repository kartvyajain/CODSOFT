import java.util.Scanner;

public class task1 {
    Scanner sc = new Scanner(System.in);
    int guess, t;

    task1() {
        guess = 4;
        t = 0;
    }

    void guessGame(int ra) {
        int ug = validInput();
        if (ug == ra) {
            System.out.println("You guessed right at " + (t + 1) + " try");
        } else {
            t++;
            if (guess - t + 1 <= 0) {
                System.out.println("You have no more tries left. The correct number was " + ra);
                return;
            }
            if (ug > ra) {
                System.out.println("You guessed wrong.\nGuess lower. You have " + (guess - t + 1) + " tries left");
            } else {
                System.out.println("You guessed wrong.\nGuess bigger. You have " + (guess - t + 1) + " tries left");
            }

            guessGame(ra);
        }
    }

    int validInput() {
        int n = -1;
        do {
            try {
                System.out.println("Enter a number between 1 to 100");
                n = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Enter a valid input");
                sc.next();
            }
        } while (!(n >= 1 && n <= 100));
        return n;
    }

    public static void main(String[] args) {
        task1 obj = new task1();
        int randomNumber = (int) (Math.random() * 100) + 1;
        obj.guessGame(randomNumber);
    }
}
