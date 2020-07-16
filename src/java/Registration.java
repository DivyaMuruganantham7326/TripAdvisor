/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author divya
 */
@Named(value = "registration")
@RequestScoped
public class Registration {

    private String name;
    private String userid;
    private String password;
    private String tag1;
    private String tag2;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTag1() {
        return tag1;
    }

    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }

    public String getTag2() {
        return tag2;
    }

    public void setTag2(String tag2) {
        this.tag2 = tag2;
    }
    
    public String register()
    {
        String output = null;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");       
        }
        catch (Exception e)
        {
            return ("Internal Error! Please try again later.");
        }
        
        DataStorage data = new SQL_Database();
        
        if(!checkString(userid))
            output = "Account creation failed. Check if you User ID contains atleast one letter and one digit";
        else if(tag1.equals(" ")||(tag2.equals(" ")))
            output = "Account creation failed. Select proper tag1 and tag2";
        else if(userid.equals(password))
            output = "Account creation failed. Password cannot be same as userid";
        else
            output = data.createUserAccount(name, userid, password, tag1, tag2);
       
        return output;
    }
    
    private static boolean checkString(String str) {
        char ch;
        boolean characterFlag = false;
        boolean numberFlag = false;
        for(int i=0;i < str.length();i++) {
            ch = str.charAt(i);
            if( Character.isDigit(ch)) {
                numberFlag = true;
            }
            else if (Character.isLetter(ch)) {
                characterFlag = true;
            } 
            if(numberFlag && characterFlag)
                return true;
        }
        return false;
    }
}
