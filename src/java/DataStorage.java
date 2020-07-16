/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 *
 * @author divya
 */
public interface DataStorage {
    String createUserAccount(String name, String userid, String psw, String tag1, String tag2);
    ArrayList<String> login(String userid, String psw);
    String createAttraction(String attractionName, String attractiondesc, String state, String city, String tag);
    ArrayList<Attraction> getApprovedAttractions(String userInput, int options);
    ArrayList<Attraction> getApprovedAttractionDetails(String attractionName);
    ArrayList<Attraction> getPendingRequests();
    void updateAttraction(String attrName, String status);
    void createReview(String attrname, float scores, String comments);
    String saveFavouriteDestination(String attrname);
    ArrayList<String> getFavouriteDestinations();
    ArrayList<String> getYouMayLike();
    ArrayList<Review> displayReviews(String attrname);
    String updatePassword(String id, String newPsw1);
}
