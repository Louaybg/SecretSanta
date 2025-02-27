package org.example;

public class Participant {
    private String name ;
    private String Hpassword ;
    private boolean gives = false ;
    private boolean gets = false ;

    public  String getName(){
        return name ;
    }
    public String getHPassword(){
        return Hpassword ;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.Hpassword = password;
    }

    public Participant(String name , String password) {
        this.name = name;
        this.Hpassword = password;
    }
    public boolean getGives() {
        return gives;
    }
    public void setGives(boolean gives) {
        this.gives = gives;
    }
    public boolean getGets() {
        return gets;
    }
    public void setGets(boolean gets) {
        this.gets = gets;
    }
    @Override
    public String toString() {
        return "name {"+name+"}"+ " Hpassword=" + Hpassword  ;
    }
}
