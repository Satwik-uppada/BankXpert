package Java_App;

import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


abstract class Account {
    private String accountNumber;
    private String password;
    private double balance;
    private ArrayList<String> accountTransactions;

    public Account(String accountNumber, String password) {
        this.accountNumber = accountNumber;
        this.password = password;
        this.balance = 0.0;
        this.accountTransactions = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount,String depositDateTime,String customer_folder) {
        balance += amount;
        String transactionDetails = "Deposit: " + amount + " at " + depositDateTime;
        addTransaction(transactionDetails);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(customer_folder + getAccountNumber() + ".txt", true));
            writer.write(transactionDetails);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing transaction to file: " + e.getMessage());
        }

    }

    public abstract void withdraw(double amount,String formattedDateTime,String customer_folder);

    public void checkBalance() {
        System.out.println("Current Balance: " + balance);
    }

    public void printAccountTransactions() {
        System.out.println("Account Transactions:");
        for (String transaction : accountTransactions) {
            System.out.println(transaction);
        }
    }

    protected void addTransaction(String transaction) {
        accountTransactions.add(transaction);
    }
}

class CheckingAccount extends Account {

    public CheckingAccount(String accountNumber, String password) {
        super(accountNumber, password);
    }

    @Override
    public void withdraw(double amount,String withdrawDateTime,String customer_folder) {
         if(getBalance() <= 5000){
             System.out.println();
             System.out.println("*** This is Current account ***");
             System.out.println("You can't withdraw initial deposit of 5000 through this application please consult the nearest bank if you want that money");
        }

        else if(amount == getBalance()){
            System.out.println("Think twice before payment, You are withdrawing all your money");
            System.out.println("Please conform the processing ...");
            System.out.println("1.yes");
            System.out.println("2.no");
            Scanner choice =new Scanner(System.in);
            int opinion = choice.nextInt();
            switch(opinion){
                case 1:
                    setBalance(getBalance() - amount);
                    String transactionDetails = "Withdraw: -" + amount + " at " + withdrawDateTime;
                    addTransaction(transactionDetails);
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(customer_folder + getAccountNumber() + ".txt", true));
                        writer.write(transactionDetails);
                        writer.newLine();
                        writer.close();
                    } catch (IOException e) {
                        System.out.println("Error while withdrawing: " + e.getMessage());
                    }
                    System.out.println("Withdrawn is completed... Spend wisely! ");
                    break;
                case 2:
                    System.out.println("Your withdraw process is stopped");
                    System.out.println("Thanks for maintaining sufficient balance");
                    break;
                default:
                    System.out.println("Please choose above options");
                    System.out.println("Try again .....");
                    System.out.println("You are redirecting to menu ....");
            }

        }
        else if (amount < getBalance()) {
            setBalance(getBalance() - amount);
            String transactionDetails = "Withdraw: -" + amount + " at " + withdrawDateTime;
            addTransaction(transactionDetails);
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(customer_folder + getAccountNumber() + ".txt", true));
                writer.write(transactionDetails);
                writer.newLine();
                writer.close();
            } catch (IOException e) {
                System.out.println("Error while withdrawing: " + e.getMessage());
            }

        }
        else {
            System.out.println("Insufficient balance. This process cannot be completed.");
        }
    }

    public void transfer(double amount, Account recipientAccount,String formattedDateTime,String customer_folder) {
        if (amount < getBalance()) {
            setBalance(getBalance() - amount);
            recipientAccount.setBalance(recipientAccount.getBalance() + amount);
            addTransaction("Transfer: -" + amount + " to " + recipientAccount.getAccountNumber()+" at "+ formattedDateTime);
            System.out.println("Transfer completed successfully. ");

        }
        else if(amount == getBalance()){
            System.out.println("Think twice before payment, You are transferring all your money");
            System.out.println("Please conform the processing ...");
            System.out.println("1.yes");
            System.out.println("2.no");
            Scanner choice =new Scanner(System.in);
            int opinion = choice.nextInt();
            switch(opinion){
                case 1:
                    setBalance(getBalance() - amount);
                    recipientAccount.setBalance(recipientAccount.getBalance() + amount);
                    String transactionDetails_sender = "Transfer: -" + amount + " to " + recipientAccount.getAccountNumber();
                    addTransaction(transactionDetails_sender);
                    String transactionDetails_receiver = "Credited " +amount +" from " + getAccountNumber();
                    try {
                        BufferedWriter sender = new BufferedWriter(new FileWriter(customer_folder + getAccountNumber() + ".txt", true));
                        sender.write(transactionDetails_sender);
                        sender.newLine();
                        sender.close();

                        BufferedWriter receiver = new BufferedWriter(new FileWriter(customer_folder + recipientAccount.getAccountNumber() + ".txt", true));
                        receiver.write(transactionDetails_receiver);
                        receiver.newLine();
                        receiver.close();

                    } catch (IOException e) {
                        System.out.println("Error writing transaction to file: " + e.getMessage());
                    }
                    System.out.println("Transfer completed successfully. ");
                    break;
                case 2:
                    System.out.println("Your payment process is stopped");
                    System.out.println("Thanks for maintaining sufficient balance");
                    break;
                default:
                    System.out.println("Please choose above options");
                    System.out.println("Try again .....");
                    System.out.println("You are redirecting to menu ....");
            }

        }
        else {
            System.out.println("Insufficient balance. This process cannot be completed.");
        }
    }
}

class SavingAccount extends Account {

    public SavingAccount(String accountNumber, String password) {
        super(accountNumber, password);
    }

    @Override
    public void withdraw(double amount,String withdrawDateTime,String customer_folder ) {
        if(getBalance() <= 2000){
            System.out.println("You are withdrawing all your amount including initial deposit ");
            System.out.println("Please conform the processing ...");
            System.out.println("1.yes");
            System.out.println("2.no");
            Scanner choice =new Scanner(System.in);
            int opinion = choice.nextInt();
            switch(opinion){
                case 1:
                    setBalance(getBalance() - amount);
                    String transactionDetails = "Withdraw: -" + amount + " at " + withdrawDateTime;
                    addTransaction(transactionDetails);
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(customer_folder + getAccountNumber() + ".txt", true));
                        writer.write(transactionDetails);
                        writer.newLine();
                        writer.close();
                    } catch (IOException e) {
                        System.out.println("Error while withdrawing: " + e.getMessage());
                    }
                    System.out.println("Withdrawn is completed... Spend wisely! ");
                    break;
                case 2:
                    System.out.println("Your withdraw process is stopped");
                    System.out.println("Thanks for maintaining sufficient balance");
                    break;
                default:
                    System.out.println("Please choose above options");
                    System.out.println("Try again .....");
                    System.out.println("You are redirecting to menu ....");
            }
        }

        else if(amount == getBalance()){
            System.out.println("Think twice before payment, You are withdrawing all your money");
            System.out.println("Please conform the processing ...");
            System.out.println("1.yes");
            System.out.println("2.no");
            Scanner choice =new Scanner(System.in);
            int opinion = choice.nextInt();
            switch(opinion){
                case 1:
                    setBalance(getBalance() - amount);
                    String transactionDetails = "Withdraw: -" + amount + " at " + withdrawDateTime;
                    addTransaction(transactionDetails);
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(customer_folder + getAccountNumber() + ".txt", true));
                        writer.write(transactionDetails);
                        writer.newLine();
                        writer.close();
                    } catch (IOException e) {
                        System.out.println("Error writing transaction to file: " + e.getMessage());
                    }
                    System.out.println("Withdrawn is completed... Spend wisely! ");
                    break;
                case 2:
                    System.out.println("Your payment process is stopped");
                    System.out.println("Thanks for maintaining sufficient balance");
                    break;
                default:
                    System.out.println("Please choose above options");
                    System.out.println("Try again .....");
                    System.out.println("You are redirecting to menu ....");
            }

        }
        else if (amount < getBalance()) {
            setBalance(getBalance() - amount);
            String transactionDetails = "Withdraw: -" + amount + " at " + withdrawDateTime;
            addTransaction(transactionDetails);
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(customer_folder + getAccountNumber() + ".txt", true));
                writer.write(transactionDetails);
                writer.newLine();
                writer.close();
            } catch (IOException e) {
                System.out.println("Error writing transaction to file: " + e.getMessage());
            }

        }
        else {
            System.out.println("Insufficient balance. This process cannot be completed.");
        }
    }
    public void transfer(double amount, Account recipientAccount,String formattedDateTime,String customer_folder) {
         if(getBalance() <= 2000){
            System.out.println("You are transferring all your amount including initial deposit ");
            System.out.println("Please conform the processing ...");
            System.out.println("1.yes");
            System.out.println("2.no");
            Scanner choice =new Scanner(System.in);
            int opinion = choice.nextInt();
            switch(opinion){
                case 1:
                    setBalance(getBalance() - amount);
                    String transactionDetails_sender = "Transfer: -" + amount + " to " + recipientAccount.getAccountNumber();
                    addTransaction(transactionDetails_sender);
                    String transactionDetails_receiver = "Credited " +amount +" from " + getAccountNumber();

                    try {
                        BufferedWriter sender = new BufferedWriter(new FileWriter(customer_folder + getAccountNumber() + ".txt", true));
                        sender.write(transactionDetails_sender);
                        sender.newLine();
                        sender.close();

                        BufferedWriter receiver = new BufferedWriter(new FileWriter(customer_folder + recipientAccount.getAccountNumber() + ".txt", true));
                        receiver.write(transactionDetails_receiver);
                        receiver.newLine();
                        receiver.close();

                    } catch (IOException e) {
                        System.out.println("Error writing transaction to file: " + e.getMessage());
                    }
                    System.out.println("Transfer completed successfully. ");
                    break;
                case 2:
                    System.out.println("Your payment process is stopped");
                    System.out.println("Thanks for maintaining sufficient balance");
                    break;
                default:
                    System.out.println("Please choose above options");
                    System.out.println("Try again .....");
                    System.out.println("You are redirecting to menu ....");
            }
        }
         else if(amount == getBalance()){
             System.out.println("Think twice before payment, You are transferring all your money");
             System.out.println("Please conform the processing ...");
             System.out.println("1.yes");
             System.out.println("2.no");
             Scanner choice =new Scanner(System.in);
             int opinion = choice.nextInt();
             switch(opinion){
                 case 1:
                     setBalance(getBalance() - amount);
                     String transactionDetails_sender = "Transfer: -" + amount + " to " + recipientAccount.getAccountNumber();
                     addTransaction(transactionDetails_sender);
                     String transactionDetails_receiver = "Credited " +amount +" from " + getAccountNumber();

                     try {
                         BufferedWriter sender = new BufferedWriter(new FileWriter(customer_folder + getAccountNumber() + ".txt", true));
                         sender.write(transactionDetails_sender);
                         sender.newLine();
                         sender.close();

                         BufferedWriter receiver = new BufferedWriter(new FileWriter(customer_folder + recipientAccount.getAccountNumber() + ".txt", true));
                         receiver.write(transactionDetails_receiver);
                         receiver.newLine();
                         receiver.close();

                     } catch (IOException e) {
                         System.out.println("Error writing transaction to file: " + e.getMessage());
                     }
                     System.out.println("Transfer completed successfully. ");
                     break;
                 case 2:
                     System.out.println("Your payment process is stopped");
                     System.out.println("Thanks for maintaining sufficient balance");
                     break;
                 default:
                     System.out.println("Please choose above options");
                     System.out.println("Try again .....");
                     System.out.println("You are redirecting to menu ....");
             }

         }
        else if (amount < getBalance()) {
            setBalance(getBalance() - amount);
            String transactionDetails_sender = "Transfer: -" + amount + " to " + recipientAccount.getAccountNumber() +" at " + formattedDateTime;
            addTransaction(transactionDetails_sender);
            double recipientBalance = recipientAccount.getBalance();
            recipientAccount.setBalance(recipientBalance + amount);
            String transactionDetails_receiver = "Credited " +amount +" from " + getAccountNumber() +" at " +formattedDateTime;
            recipientAccount.addTransaction(transactionDetails_receiver);
            try {
                BufferedWriter sender = new BufferedWriter(new FileWriter(customer_folder + getAccountNumber() + ".txt", true));
                sender.write(transactionDetails_sender);
                sender.newLine();
                sender.close();

                BufferedWriter receiver = new BufferedWriter(new FileWriter(customer_folder + recipientAccount.getAccountNumber() + ".txt", true));
                receiver.write(transactionDetails_receiver);
                receiver.newLine();
                receiver.close();

            } catch (IOException e) {
                System.out.println("Error writing transaction to file: " + e.getMessage());
            }
            System.out.println("Transfer completed successfully. ");
        }


        else {
            System.out.println("Insufficient balance. This process cannot be completed.");
        }
    }
}

class Customer {
    private String name;
    private int age;
    private String city;
    private String dob;
    private String mobile_no;
    private String id_no;
    private Account account;

    public Customer(String name, int age, String city, String dob, String mobile_no, String id_no) {
        this.name = name;
        this.age = age;
        this.city = city;
        this.id_no = id_no;
        this.mobile_no = mobile_no;
        this.dob = dob;
        this.account = null;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }


    public String getDob() {
        return dob;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public String getId_no() {
        return id_no;
    }

    public int getAge() {
        return age;
    }


}
public class BankXpert {
        private static ArrayList<Customer> customers;
        private static ArrayList<Account> accounts;
        private static final String customer_folder = "C:\\Users\\uppada satwik\\IdeaProjects\\Bricks Delivery App\\src\\Java_App\\customer files\\";

        public static void main(String[] args) {
            customers = new ArrayList<>();
            accounts = new ArrayList<>();

            displayHomeScreen();
        }
        public static void displayHomeScreen(){
            System.out.println();
            System.out.println("*** WELCOME TO BANKXPERT ****");
            System.out.println("Please signup to access all the features ");
            System.out.println("1 - SignUp");
            System.out.println("2 - Exit");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            if (choice == 1){
                register(customer_folder);
            }
            else if (choice == 2){
                System.out.println("Thanks for using BankXpert... Have a nice day!");
                System.exit(0);
            }
            else{
                System.out.println("Invalid choice.");
                displayHomeScreen();
            }


        }
        public static void displayLoginScreen() {
            System.out.println();
            System.out.println("***** Welcome to BankXpert *****");
            System.out.println("1 - SignIn");
            System.out.println("2 - Signup for new account");
            System.out.println("3 - Exit");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    login(customer_folder);
                    break;
                case 2:
                    register(customer_folder);
                    break;

                case 3:
                    System.out.println("Thanks for using BankXpert!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
                    displayLoginScreen();
            }
        }

        public static void login(String customer_folder) {
            System.out.println();
            System.out.println("Enter account number:");
            Scanner scanner = new Scanner(System.in);
            String accountNumber = scanner.nextLine();

            System.out.println("Enter password:");
            String password = scanner.nextLine();


            Customer customer = findCustomer(accountNumber);
            if (customer != null && customer.getAccount() != null && customer.getAccount().getAccountNumber().equals(accountNumber)
                    && customer.getAccount().getPassword().equals(password)) {
                displayOptions(customer, customer_folder);
            } else {
                System.out.println("Invalid account number or password.");
                displayLoginScreen();
            }
        }


        public static void register(String customer_folder) {
            System.out.println();
            System.out.println("Enter name:");
            Scanner scanner = new Scanner(System.in);
            String name = scanner.nextLine();

            System.out.println("Enter age:");
            int age = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter city:");
            String city = scanner.nextLine();

            System.out.println("Enter Date of birth(dd-mm-yyyy):");
            String dob = scanner.nextLine();

            System.out.println("Enter Mobile.No:");
            String mobile_no = scanner.nextLine();

            System.out.println("Enter Identification.No(PAN/Driving Licence/AAdhar.No):");
            String id_no = scanner.nextLine();

            Customer customer = new Customer(name, age, city, dob, mobile_no, id_no);
            customers.add(customer);

            System.out.println("Choose account type:");
            System.out.println("1 - Current Account");
            System.out.println("2 - Saving Account");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createCheckingAccount(customer, customer_folder);
                    break;
                case 2:
                    createSavingAccount(customer, customer_folder);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("Account created successfully.");
            System.out.println("+-------------------------------+");
            System.out.println("+ Account Number: " + customer.getAccount().getAccountNumber()+"  +");
            System.out.println("+ Account Password: " + customer.getAccount().getPassword()+"    +");
            System.out.println("+-------------------------------+");
            System.out.println("Note: - Please don't forgot this password(non recoverable)");
            System.out.println();
            System.out.println("+---------------- Account Details ----------------+");
            System.out.println("Name: " + customer.getName());
            System.out.println("Account Number: " + customer.getAccount().getAccountNumber());
            if (choice == 1) {
                System.out.println("Account Type: Current Account");
            } else if (choice == 2) {
                System.out.println("Account Type: Savings Account");
            } else {
                System.out.println("Please choose correct option");
            }

            System.out.println("Age: " + customer.getAge());
            System.out.println("Date of Birth: " + customer.getDob());
            System.out.println("Mobile Number: " + customer.getMobile_no());
            System.out.println("Identification Number: " + customer.getId_no());


            displayLoginScreen();
        }

        public static void createCheckingAccount(Customer customer, String customer_folder) {
            String accountNumber = generateAccountNumber();
            String password = generatePassword();
            String name = customer.getName();
            int age = customer.getAge();
            String dob = customer.getDob();
            String mobile_no = customer.getMobile_no();
            String id_no = customer.getId_no();
            String account_type = "Checking account";
            Account account = new CheckingAccount(accountNumber, password);
            customer.setAccount(account);
            accounts.add(account);
            createCustomerFile(name, age, dob, mobile_no, id_no, account_type, accountNumber, customer_folder);
        }

        public static void createSavingAccount(Customer customer, String customer_files) {
            String accountNumber = generateAccountNumber();
            String password = generatePassword();
            String name = customer.getName();
            int age = customer.getAge();
            String dob = customer.getDob();
            String mobile_no = customer.getMobile_no();
            String id_no = customer.getId_no();
            String account_type = "Savings account";
            Account account = new SavingAccount(accountNumber, password);
            customer.setAccount(account);
            accounts.add(account);
            createCustomerFile(name, age, dob, mobile_no, id_no, account_type, accountNumber, customer_files);
        }

        public static void createCustomerFile(String name, int age, String dob, String mobile_no, String account_type, String id_no, String accountNumber, String customer_folder) {
            try {
                File file = new File(customer_folder + accountNumber + ".txt");
                if (file.createNewFile()) {
                    System.out.println("Customer file created successfully.");
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    writer.write("Name: " + name);
                    writer.newLine();
                    writer.write("Account Number: " + accountNumber);
                    writer.newLine();
                    writer.write("Account Type: " + account_type);
                    writer.newLine();
                    writer.write("Age: " + age);
                    writer.newLine();
                    writer.write("Date of Birth: " + dob);
                    writer.newLine();
                    writer.write("Mobile Number: " + mobile_no);
                    writer.newLine();
                    writer.write("Identification Number: " + id_no);
                    writer.newLine();
                    writer.write("*----------------------------------- Transaction History -----------------------------------*");
                    writer.newLine();
                    writer.close();
                } else {
                    System.out.println("Customer file already exists.");
                }
            } catch (IOException e) {
                System.out.println("Error creating customer file: " + e.getMessage());
            }
        }

        public static String generateAccountNumber() {
            StringBuilder accountNumber = new StringBuilder();
            Random random = new Random();
            for (int i = 0; i < 12; i++) {
                int digit = random.nextInt(9); // Generate a single digit (0-9)
                accountNumber.append(digit);
            }
            return accountNumber.toString();
        }

        public static String generatePassword() {
            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@&#$!l";
            Random random = new Random();
            StringBuilder password = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                password.append(characters.charAt(random.nextInt(characters.length())));
            }
            return password.toString();
        }

        public static Customer findCustomer(String accountNumber) {
            for (Customer customer : customers) {
                if (customer.getAccount() != null && customer.getAccount().getAccountNumber().equals(accountNumber)) {
                    return customer;
                }
            }
            return null;
        }

        public static void displayOptions(Customer customer, String customer_folder) {
            System.out.println();
            System.out.println("Your Current Balance is: "+customer.getAccount().getBalance());
            System.out.println("1 - Deposit");
            System.out.println("2 - Withdraw");
            System.out.println("3 - Transfer");
            System.out.println("4 - Check Balance");
            System.out.println("5 - Print Account Transactions");
            System.out.println("6 - Exit");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    performDeposit(customer, customer_folder);
                    break;
                case 2:
                    performWithdraw(customer, customer_folder);
                    break;
                case 3:
                    performTransfer(customer);
                    break;
                case 4:
                    customer.getAccount().checkBalance();
                    break;
                case 5:
                    customer.getAccount().printAccountTransactions();
                    break;
                case 6:
                    System.out.println("Logged out successfully.");
                    displayLoginScreen();
                default:
                    System.out.println("Invalid choice.");
                    displayLoginScreen();
            }

            if (choice != 6) {
                displayOptions(customer, customer_folder);
            }
        }

        public static void performDeposit(Customer customer, String customer_folder) {
            System.out.println("Enter the amount to deposit:");
            Scanner scanner = new Scanner(System.in);
            double amount = scanner.nextDouble();

            // Get the current date and time
            LocalDateTime depositDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDateTime = depositDateTime.format(formatter);

            // Perform the deposit and record the transaction
            customer.getAccount().deposit(amount, formattedDateTime, customer_folder);
            System.out.println("Deposit completed successfully.");
        }

        public static void performWithdraw(Customer customer, String customer_folder) {

            LocalDateTime depositDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDateTime = depositDateTime.format(formatter);

            if(customer.getAccount().getBalance() == 0){
                System.out.println("Your current balance is '0.0 Rupees'. you can't withdraw amount");
            }
            else if(customer.getAccount().getBalance() <= 2000){
                System.out.println("");
            }
            else{
                System.out.println("Enter the amount to withdraw:");
                Scanner scanner = new Scanner(System.in);
                double amount = scanner.nextDouble();
                customer.getAccount().withdraw(amount, formattedDateTime, customer_folder);

            }
        }

        public static void performTransfer(Customer customer) {

            LocalDateTime depositDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDateTime = depositDateTime.format(formatter);
            if (customer.getAccount().getBalance()==0){
                System.out.println("Your current balance is '0.0 Rupees'");
                System.out.println("we can't proceed for transferring!");
            }
            else {
                if (customer.getAccount() instanceof CheckingAccount) {
                    if(customer.getAccount().getBalance() <= 5000){
                        System.out.println("you have only initial deposit amount in your account");
                        System.out.println("Sorry,We can't proceed transferring ...");
                    }
                    else {
                        System.out.println("Enter the amount to transfer:");
                        Scanner scanner = new Scanner(System.in);
                        double amount = scanner.nextDouble();
                        scanner.nextLine();

                        System.out.println("Enter the recipient's account number:");
                        String recipientAccountNumber = scanner.nextLine();

                        Account recipientAccount = findAccount(recipientAccountNumber);
                        if (recipientAccount != null) {
                            CheckingAccount checkingAccount = (CheckingAccount) customer.getAccount();
                            if (amount <= checkingAccount.getBalance()) {
                                checkingAccount.transfer(amount, recipientAccount, formattedDateTime, customer_folder);
                                System.out.println("Transfer completed successfully.");
                                displayOptions(customer, customer_folder);
                            } else {
                                System.out.println("Insufficient balance. This process cannot be completed.");
                                displayOptions(customer, customer_folder);
                            }
                        } else {
                            System.out.println("Recipient's account not found.");
                            displayOptions(customer, customer_folder);
                        }
                    }
                }
                else {
                        System.out.println("Enter the amount to transfer:");
                        Scanner scanner = new Scanner(System.in);
                        double amount = scanner.nextDouble();
                        scanner.nextLine();

                        System.out.println("Enter the recipient's account number:");
                        String recipientAccountNumber = scanner.nextLine();

                        Account recipientAccount = findAccount(recipientAccountNumber);
                        if (recipientAccount != null) {
                            SavingAccount savingAccount = (SavingAccount) customer.getAccount();
                            if (amount <= savingAccount.getBalance()) {
                                savingAccount.transfer(amount, recipientAccount, formattedDateTime, customer_folder);
                                displayOptions(customer, customer_folder);
                            } else {
                                System.out.println("Insufficient balance. This process cannot be completed.");
                                displayOptions(customer, customer_folder);
                            }
                        } else {
                            System.out.println("Recipient's account not found.");
                            displayOptions(customer, customer_folder);
                        }
                        displayOptions(customer, customer_folder);

                }
            }
        }

        public static Account findAccount(String accountNumber) {
            for (Account account : accounts) {
                if (account.getAccountNumber().equals(accountNumber)) {
                    return account;
                }
            }
            return null;
        }
    }
