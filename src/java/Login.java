/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author divya
 */
@Named(value = "login")
@SessionScoped
public class Login implements Serializable {

    private String id;
    private String password;
    private Account acct;
    private String oldPsw;
    private String newPsw1;
    private String newPsw2;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account getAcct() {
        return acct;
    }

    public void setAcct(Account acct) {
        this.acct = acct;
    }

    public String getOldPsw() {
        return oldPsw;
    }

    public void setOldPsw(String oldPsw) {
        this.oldPsw = oldPsw;
    }

    public String getNewPsw1() {
        return newPsw1;
    }

    public void setNewPsw1(String newPsw1) {
        this.newPsw1 = newPsw1;
    }

    public String getNewPsw2() {
        return newPsw2;
    }

    public void setNewPsw2(String newPsw2) {
        this.newPsw2 = newPsw2;
    }
    
    

    public String login()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");       
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ("internalError");
        }
        
        DataStorage data = new SQL_Database();
        ArrayList<String> arr = new ArrayList<String>();
        arr = data.login(id, password);
        
            if(arr.get(0).equals("admin"))
            {
                acct = new AdminAccount(id, password, arr.get(1));
                return "welcomeAdmin";
            }
            else if(!arr.get(0).equals("admin") && (!arr.get(0).equals("The password is not correct!")) && (!arr.get(0).equals("The login failed!"))){
                acct = new UserAccount(id, password, arr.get(1));
                return "welcomeUser";
            }
            else{
                return "loginNotOk";
            }    
    }
    
    public String ResetPassword(){
        boolean newPswOK = false; 
        boolean matchOldPsw = false;
        DataStorage data = new SQL_Database();
        
        if(newPsw1.equals(newPsw2))
        {
            newPswOK = true;
        }
        
        if(oldPsw.equals(password))
        {
            matchOldPsw = true;
        }
        
        if(!newPswOK)
        {
            return ("confirmationResetWrong.xhtml");
        }
        else if(!matchOldPsw)
        {   
            return ("confirmationResetWrong.xhtml");   
        }
        else
        {
            return data.updatePassword(id, newPsw1);
        }
    }
    public String signOut()
    {
        //FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login";
    }
}
