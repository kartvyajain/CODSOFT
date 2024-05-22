import java.util.Scanner;

public class Task2 {
    int subjects;
    String grade;
    float avgper;
    float total;
    float marks[];
    Scanner sc = new Scanner(System.in);
    Task2(int s) {
        subjects = s;
        marks = new float[subjects];
       
        inputMarks();
        grade = gradeCalculation();
        total = sum();
        avgper = averagePercentage();
    }

    void inputMarks() {
      
        for (int i = 0; i < subjects; i++) {
            marks[i] = validInput();
        }
       
    }

    float sum() {
        float s = 0;
        for (int i = 0; i < subjects; i++) {
            s += marks[i];
        }
        return s;
    }

    float averagePercentage() {
        return (sum() / subjects);
    }

    String gradeCalculation() {
        float p = averagePercentage();
        if (p >= 90) {
            return "A";
        } else if (p >= 80) {
            return "B";
        } else if (p >= 70) {
            return "C";
        } else if (p >= 50) {
            return "D";
        } else {
            return "F";
        }
    }

    void display() {
        float roundedAvgPer = Math.round(avgper * 100.0f) / 100.0f;
        System.out.println("Total marks is: " + total);
        System.out.println("Average Percentage is: " + roundedAvgPer);
        System.out.println("Your grade is: " + grade);
    }

    float validInput() {
        float n = -1;
        do {
            try {
                System.out.println("Enter a marks between 0 to 100");
                n = sc.nextFloat();
            } catch (Exception e) {
                System.out.println("Enter a valid input");
                sc.next();
            }
        } while (!(n >= 0 && n <= 100));
        return n;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter total number of subjects");
        int sub = sc.nextInt();
        Task2 obj = new Task2(sub);
        
        System.out.println("Choose an option:");
        System.out.println("1. Display results");
        System.out.println("2. Exit");
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                obj.display();
                break;
            case 2:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice");
        }
        
        sc.close();
    }
}
