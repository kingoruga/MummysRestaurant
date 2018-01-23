/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;

/**
 *
 * @author syntel
 */
public class AreaModel {
    Connector conn;
    public boolean addArea(int zip){
        conn = new Connector();
        return conn.addZipToServiceArea(zip);
    }
    
    public boolean removeArea(int zip){
        conn = new Connector();
        return conn.removeZipFromServiceArea(zip);
    }
    
    public List<String> getAreas(){
        conn = new Connector();
        return conn.getAreas();
    }
    
    public List<String> getFoodInAreas(int zip){
        conn = new Connector();
        return conn.getFoodItemsInArea(zip);
    }
    public boolean addPackagetoArea(int zip, String packageNo){
        conn = new Connector();
        return conn.addPackagetoArea(zip,packageNo);
    }
    public boolean removePackageFromArea(int zip, String packageNo){
        conn = new Connector();
        return conn.removePackageFromArea(zip,packageNo);
    }
}
