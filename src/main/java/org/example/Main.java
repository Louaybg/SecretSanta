package org.example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.*;
public class Main {
    private static final Logger LOGGER =  LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, InterruptedException, IOException {
        Random rand = new Random();
        List <String> answeredList = new ArrayList<String>();
        Scanner friends = new Scanner(System.in);
        System.out.print("how many friends are going to participate :");
        String number = friends.nextLine();
        while (!checkUsingParseInt(number)){
            Scanner friends1 = new Scanner(System.in);
            System.out.print("Wrong number , how many friends are going to participate :");
            String number1 = friends1.nextLine();
            number = number1;
        }
        int n = Integer.parseInt(number);
        List<Participant> nameList=new ArrayList<Participant>();
        for(int i = 0; i < n; i++) {
            System.out.print("name of friend "+(i+1)+":");
            Scanner fri = new Scanner(System.in);
            String name = fri.nextLine();
            while (playerExist(nameList,name)){
                System.out.println("this name is already in use , give another name :");
                Scanner fri1 = new Scanner(System.in);
                String name1 = fri1.nextLine();
                name = name1;

            }
            answeredList.add(name);
            System.out.print("give me your password :");
            Scanner pass = new Scanner(System.in);
            String password1 = pass.nextLine();
            String Password = Hashing(password1);
            nameList.add(new Participant(name , Password));
            System.out.print("you are registered !");
            System.out.println();
            Thread.sleep(3000);
            clearScreen();
        }
        HashMap<Participant,Participant> map=new HashMap<>() ;
        int count=0;
        List<Participant> listToEmpty1= new ArrayList<>(nameList) ;
        while (count<nameList.size()) {
            Participant nametoMatch=nameList.get(count);
            listToEmpty1.remove(nametoMatch);
            nametoMatch.setGives(true);
            Participant pickedName=listToEmpty1.get(rand.nextInt(listToEmpty1.size())) ;
            map.put(nametoMatch,pickedName);
            listToEmpty1.remove(pickedName);
            pickedName.setGets(true);
            if (!nametoMatch.getGets()) {
                listToEmpty1.add(nametoMatch);
            }
            count++;
        }
        while (!answeredList.isEmpty()) {
            Set<Participant> listOfPlayer = map.keySet();
            Scanner gifter=new Scanner(System.in);
            System.out.print("Enter your name: ");
            String name=gifter.nextLine();
            if (map.containsKey(findPlayerRefrence(listOfPlayer,name))) {
                System.out.print("password:");
                Scanner Password = new Scanner(System.in);
                String PasswordTry = Password.nextLine();
                String pass = Hashing(PasswordTry);
                Participant p=findPlayerRefrence(listOfPlayer,name);
                assert p != null ;
                for (int j = 0; j < 3; j++) {
                    if (!passCheck(p, pass)){
                        System.out.print("wrong password , try again there are  "+(3-j)+"trials left");
                        Scanner Password1 = new Scanner(System.in);
                        String PasswordTry1 = Password1.nextLine();
                        String pass1 = Hashing(PasswordTry1);
                        pass = pass1 ;
                    }
                }
                if (passCheck(p, pass)) {
                    System.out.print("you will gift this person : ");
                    System.out.println(map.get(p).getName());
                    answeredList.remove(name);
                    Thread.sleep(3000);
                    clearScreen();
                }else{
                    System.out.println("wrong password , next player " );
                    Thread.sleep(3000);
                    clearScreen();
                }
            }else{
                System.out.println("name not found you are not registred ");
                Thread.sleep(3000);
                clearScreen();
            }
        }
    }
    public static Participant findPlayerRefrence(Set<Participant> participants , String name) {
        for (Participant p : participants) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }
    public static boolean playerExist(List<Participant> participants , String name) {
        for (Participant p : participants) {
            if (p.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    public static boolean passCheck (Participant p , String passTry) {
        return Objects.equals(p.getHPassword(), passTry);
    }
    public static String Hashing(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = new byte[1];
        KeySpec spec = new PBEKeySpec(password.toCharArray(),salt,10000,256);
        SecretKeyFactory f1 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash1 = f1.generateSecret(spec).getEncoded();
        Base64.Encoder enc1 = Base64.getEncoder();
        return enc1.encodeToString(hash1);
    }
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static boolean checkUsingParseInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
