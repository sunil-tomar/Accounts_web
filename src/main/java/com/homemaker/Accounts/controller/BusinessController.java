package com.homemaker.Accounts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/business")
public class BusinessController {
    
 @GetMapping("/bu-list")
 public List<String> getBusinessList() {
        List<String> businessList = new ArrayList<>();
        businessList.add("Hospital");
        businessList.add("chemist");
        businessList.add("medical");
        businessList.add("OPD");
        return businessList;
    }

    @GetMapping("/fetch-bu-list-by-name")
    public List<String>getBuListByName(@RequestParam("buName") String buName){
     List<String> buListData= getbusnissListData(); //DATA buList
     List<String> filterdList = getFilteredBuListbyName(buListData, buName); //Matching List.
        return filterdList;
    }
     List<String> getFilteredBuListbyName(List<String> buList, String buName){
     List<String> filteredBuList=new ArrayList<>(); //empty list.

     //filtering logic.
     for(int i=0; i< buList.size(); i++){
        if (buName.equals(buList.get(i))){ //checking match name.
            filteredBuList.add(buList.get(i)) ; //adding only matching name.
        }
     }
     return filteredBuList;// returning only matching list.
     }


    public List<String> getbusnissListData(){
        List<String> buList= new ArrayList<>();
        buList.add("hospitalsTyp");
        buList.add("CityHospitals");
        buList.add("CentralHospitals");
     return buList;
    }

}

