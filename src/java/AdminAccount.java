/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import javax.faces.context.FacesContext;

/**
 *
 * @author divya
 */
public class AdminAccount extends Account{

    private DataStorage data = new SQL_Database();
    private ArrayList<Attraction> attr
            = new ArrayList<Attraction>();
    
    public AdminAccount(String u, String p, String n) {
        super(u, p, n);
    }

    public ArrayList<Attraction> getAttr() {
        return attr;
    }

    public void setAttr(ArrayList<Attraction> attr) {
        this.attr = attr;
    }
 
    public ArrayList<Attraction> displayPendingAttraction(){
        attr = data.getPendingRequests();
        return attr;
    }
    
    public String updateAttraction(){
        for(int i=0; i<attr.size();i++){
            if(attr.get(i).getStatus().equals("Approve")) attr.get(i).setStatus("Approved");
            else if(attr.get(i).getStatus().equals("Reject")) attr.get(i).setStatus("Rejected");
            else attr.get(i).setStatus("Pending");
            data.updateAttraction(attr.get(i).getAttractionName(), attr.get(i).getStatus());
        }
        return "adminRequestConfirmation";
    }
    
    public String signOut()
    {
        //FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login";
    }
}
