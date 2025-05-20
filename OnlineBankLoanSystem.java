import java.util.Scanner;

public class OnlineBankLoanSystem {
    static class LoanApplication {
        private String applicantName;
        private int applicantAge;
        private double loanAmount;
        private int loanTerm;  // in years
        private double interestRate;
        private String loanType;
        public LoanApplication(String applicantName, int applicantAge, double loanAmount, int loanTerm, String loanType) {
            this.applicantName = applicantName;
            this.applicantAge = applicantAge;
            this.loanAmount = loanAmount;
            this.loanTerm = loanTerm;
            this.loanType = loanType;
            switch (loanType.toLowerCase()) {
                case "home":
                    this.interestRate = 4.5;  
                    break;
                case "car":
                    this.interestRate = 6.0;  
                    break;
                case "personal":
                default:
                    this.interestRate = 7.5;  
                    break;
            }
        }
        public String getApplicantName() {
            return applicantName;
        }

        public int getApplicantAge() {
            return applicantAge;
        }

        public double getLoanAmount() {
            return loanAmount;
        }

        public int getLoanTerm() {
            return loanTerm;
        }

        public double getInterestRate() {
            return interestRate;
        }

        public String getLoanType() {
            return loanType;
        }
    }
    static class EligibilityCheck 
    {

        public static boolean checkEligibility(LoanApplication loanApp) {
            if (loanApp.getApplicantAge() < 21) {
                System.out.println("Applicant must be at least 21 years old.");
                return false;
            }
            if (loanApp.getLoanAmount() > 500000) {
                System.out.println("Loan amount exceeds the allowed limit.");
                return false;
            }
            if (loanApp.getLoanTerm() < 1 || loanApp.getLoanTerm() > 30) {
                System.out.println("Loan term must be between 1 to 30 years.");
                return false;
            }
            return true;
        }
    }
    static class RepaymentSchedule 
    {

        public static void generateRepaymentSchedule(LoanApplication loanApp, double prepaymentAmount) 
        {
            double principal = loanApp.getLoanAmount() - prepaymentAmount; 
            double annualRate = loanApp.getInterestRate();
            int loanTermInYears = loanApp.getLoanTerm();
            double monthlyRate = (annualRate / 12) / 100;
            int months = loanTermInYears * 12;
            double emi = (principal * monthlyRate * Math.pow(1 + monthlyRate, months)) / (Math.pow(1 + monthlyRate, months) - 1);
            System.out.println("Repayment Schedule for " + loanApp.getApplicantName());
            System.out.println("Loan Type: " + loanApp.getLoanType());
            System.out.println("Loan Amount: " + principal);
            System.out.println("Interest Rate: " + annualRate + "%");
            System.out.println("Loan Term: " + loanTermInYears + " years");
            System.out.println("Monthly EMI: " + String.format("%.2f", emi));
            System.out.println("\nLoan Repayment Schedule (Principal + Interest):");
            for (int i = 1; i <= months; i++) {
                double interestPayment = principal * monthlyRate;
                double principalPayment = emi - interestPayment;
                principal -= principalPayment;
                System.out.println("Month " + i + ": Principal Payment = " + String.format("%.2f", principalPayment) +
                        ", Interest Payment = " + String.format("%.2f", interestPayment) +
                        ", Remaining Loan = " + String.format("%.2f", principal));
            }
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to online Bank Loan System");
        System.out.print("Enter applicant name: ");
        String name = scanner.nextLine();
        System.out.print("Enter applicant age: ");
        int age = scanner.nextInt();
        System.out.print("Enter loan amount: ");
        double loanAmount = scanner.nextDouble();
        System.out.print("Enter loan term (in years): ");
        int loanTerm = scanner.nextInt();
        scanner.nextLine();  
        System.out.println("Select loan type: Personal, Home, Car");
        String loanType = scanner.nextLine().toLowerCase();
        LoanApplication loanApp = new LoanApplication(name, age, loanAmount, loanTerm, loanType);
        if (EligibilityCheck.checkEligibility(loanApp))
        {
            System.out.print("Enter prepayment amount (0 if none): ");
            double prepaymentAmount = scanner.nextDouble();
            RepaymentSchedule.generateRepaymentSchedule(loanApp, prepaymentAmount);
        } 
        else
        {
            System.out.println("Loan application failed due to eligibility criteria.");
        }
        scanner.close();
    }
}
