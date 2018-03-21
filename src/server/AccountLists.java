package server;

import java.io.*;
import java.util.HashMap;

public class AccountLists {
    private HashMap<String,String> accounts = new HashMap<>();
    private File file = new File("accounts.csv");

    // Populate the lists of accounts before we start the server, so we know what names are taken
    // and what password is the correct one
    public void loadAccounts(){
        if (file.length() > 0){
            try {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                   String[] details = line.split(",");
                   String name = details[0];
                   String password = details[1];
                   accounts.put(name,password);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Create an account, but only if it's not taken and doesn't contain fancy characters
    // (we should theoretically allow them in the passwords but just ignore the ',' delimiter
    public int createAccount(String name, String password){
        if (!isWord(name) || !isWord(password)){
            System.err.println("Must not have special characters in username/password!");
            return -1;
        }

        if (!accounts.containsKey(name)) {
            accounts.put(name,password);
        } else {
            System.err.println("Account already exists!");
            return -2;
        }

        try {
            FileWriter fw = new FileWriter(file,true);
            fw.append(name+","+password);
            fw.append("\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // If the account exists, AND the password is correct, return true;
    public boolean verifyAccount(String name, String password) {
        if (accounts.containsKey(name)){
            if (accounts.get(name).equals(password)){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // Returns true if the password is alphanumeric TODO: Make it numeric... it's just alpha
    private boolean isWord(String string) {
        return string.matches("[A-Za-z]+$");
    }
}
