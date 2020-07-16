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
public class UserAccount extends Account{

    private DataStorage data = new SQL_Database();
    private ArrayList<Attraction> attractionList
            = new ArrayList<Attraction>();
    private ArrayList<Attraction> youMayLikeAttractionList
            = new ArrayList<Attraction>();
    private ArrayList<Attraction> favDesAttractionList
            = new ArrayList<Attraction>();
    private ArrayList<String> attractionNames = new ArrayList<String>();
    private ArrayList<String> youMayLikeAttractionNames = new ArrayList<String>();
    private ArrayList<String> favDesAttractionNames = new ArrayList<String>();
    private Attraction attraction = new Attraction();
    private Review review = new Review();
    private ArrayList<Review> reviews
            = new ArrayList<Review>();

    public ArrayList<Attraction> getFavDesAttractionList() {
        return favDesAttractionList;
    }

    public void setFavDesAttractionList(ArrayList<Attraction> favDesAttractionList) {
        this.favDesAttractionList = favDesAttractionList;
    }

    public ArrayList<String> getFavDesAttractionNames() {
        return favDesAttractionNames;
    }

    public void setFavDesAttractionNames(ArrayList<String> favDesAttractionNames) {
        this.favDesAttractionNames = favDesAttractionNames;
    }
  
    public ArrayList<Attraction> getYouMayLikeAttractionList() {
        return youMayLikeAttractionList;
    }

    public void setYouMayLikeAttractionList(ArrayList<Attraction> youMayLikeAttractionList) {
        this.youMayLikeAttractionList = youMayLikeAttractionList;
    }

    public ArrayList<String> getYouMayLikeAttractionNames() {
        return youMayLikeAttractionNames;
    }

    public void setYouMayLikeAttractionNames(ArrayList<String> youMayLikeAttractionNames) {
        this.youMayLikeAttractionNames = youMayLikeAttractionNames;
    }

    public ArrayList<String> getAttractionNames() {
        return attractionNames;
    }

    public void setAttractionNames(ArrayList<String> attractionNames) {
        this.attractionNames = attractionNames;
    }
    
    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }
    
    public Attraction getAttraction() {
        return attraction;
    }

    public void setAttraction(Attraction attraction) {
        this.attraction = attraction;
    }
    
    public ArrayList<Attraction> getAttractionList() {
        return attractionList;
    }

    public void setAttractionList(ArrayList<Attraction> attractionList) {
        this.attractionList = attractionList;
    }

    public UserAccount(String u, String p, String n) {
        super(u, p, n);
    }
    
    public String createAttraction(){
        /*try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ("internalError");
        }*/
        
        DataStorage data = new SQL_Database();
        String str = data.createAttraction(attraction.getAttractionName(), attraction.getAttractionDesc(), 
                attraction.getState(), attraction.getCity(), attraction.getTag());
        
        return str;
    }
    
    public String SearchTagAttractionNames(){
            attractionList.clear();
            if(attractionNames.size() > 0) attractionNames.clear();
            attractionList = data.getApprovedAttractions(attraction.getTag(), 1);
            if(attractionList.size()==0) return null;
            for(int i=0; i<attractionList.size(); i++){
                attractionNames.add(attractionList.get(i).getAttractionName());
            }
            return "searchConfirmation";
    }
    
    public String SearchCityAttractionNames(){
            attractionList.clear();
            if(attractionNames.size() > 0) attractionNames.clear();
            attractionList = data.getApprovedAttractions(attraction.getCity(), 2);
            if(attractionList.size()==0) return null;
            for(int i=0; i<attractionList.size(); i++){
                attractionNames.add(attractionList.get(i).getAttractionName());
            }
            return "searchConfirmation";
    }
    
    public String FavDesAttractionNames(){
            favDesAttractionNames = data.getFavouriteDestinations();
            return "favoriteDestination";
    }
    
    public String SearchAttractionNameConfirm(String attractionName){
        attraction.setAttractionName(attractionName);
        return "displayAttraction";
    }
    
    public String SearchAttractionNameYouMayLikeConfirm(String attractionName){
        attraction.setAttractionName(attractionName);
        return "displayAttractionYouMayLike";
    }
    
    public String MyFavDestAttractionNameConfirm(String attractionName){
        attraction.setAttractionName(attractionName);
        return "favDestinationDetails";
    }
 
    public ArrayList<Attraction> GetAttractionDetails(){
        attractionList = data.getApprovedAttractionDetails(attraction.getAttractionName());
        if(attractionList.size()==0) return null;
        return attractionList;
    }
    
        public String SearchSaveFavorites(){
            String str = data.saveFavouriteDestination(attraction.getAttractionName());
            return str;
        }
        
        /*public ArrayList<Attraction> ViewFavouriteDestinations(){
            attractionList = data.getFavouriteDestinations();
            return attractionList;
        }*/    
        
        public String NavigateCreateReview(String attrname){
            review.setAttractionName(attrname);
            return "createReview";
        }
    
        public String CreateReview(float scores, String comments){
            data.createReview(attraction.getAttractionName(), scores, comments);
            return "displayAttraction";
    
        }
    
    public ArrayList<Review> DisplayReviews(){
        reviews = data.displayReviews(attraction.getAttractionName());
        return reviews;
    }
    
    public String YouMayLikeAttractionNames(){
            youMayLikeAttractionNames = data.getYouMayLike();
            /*if(attractionList.size()==0) return null;
            for(int i=0; i<attractionList.size(); i++){
                youMayLikeAttractionNames.add(attractionList.get(i).getAttractionName());
            }*/
            return "youMayLike";
    }
    
    public String YouMayLike(){
        if(attractionList.size() > 0)attractionList.clear();
        float first, second, third;
        attractionList = data.getApprovedAttractions(null, 0);
        /*first = second = third = Integer.MIN_VALUE;
        for(int i=0; i<attractionList.size(); i++)
        {
            if(attractionList.get(i).getScores() > first){
                third = second;
                second = first;
                first = attractionList.get(i).getScores();
                youMayLikeAttractionNames.add(attractionList.get(i).getAttractionName());
            }
            else if(attractionList.get(i).getScores() > second){
                third = second;
                second = attractionList.get(i).getScores();
                youMayLikeAttractionNames.add(attractionList.get(i).getAttractionName());
            }
            else if(attractionList.get(i).getScores() > third){
                third = attractionList.get(i).getScores();
                youMayLikeAttractionNames.add(attractionList.get(i).getAttractionName());
            }
        }*/  
        return "youMayLike";
    }
    
    
    
    public String signOut()
    {
        //FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login";
    }
}