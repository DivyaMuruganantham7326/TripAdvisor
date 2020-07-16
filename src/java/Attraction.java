
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author divya
 */
public class Attraction {
    private String attractionName;
    private String attractionDesc;
    private String state;
    private String city;
    private String tag;
    private float scores;
    private String status;

    public Attraction(){
        
    }
    
    public Attraction(String attrName, String attrDesc, String st, String ct, String t, Float s){
        attractionName = attrName;
        attractionDesc = attrDesc;
        state = st;
        city = ct;
        tag = t;
        scores = (float)(Math.round(s * 10)/10.0);
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public float getScores() {
        return scores;
    }

    public void setScores(float scores) {
        this.scores = scores;
    }
    
    public String getAttractionName() {
        return attractionName;
    }

    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }

    public String getAttractionDesc() {
        return attractionDesc;
    }

    public void setAttractionDesc(String attractionDesc) {
        this.attractionDesc = attractionDesc;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
