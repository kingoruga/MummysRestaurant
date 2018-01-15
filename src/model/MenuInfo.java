/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.MenuController;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author syntel
 */
public class MenuInfo {
    Connector menuInfo;
    List<MenuController> meals = new ArrayList<>();
    
     public List getMenuOptions(){
       menuInfo = new Connector();
        return menuInfo.showMenuQuery();
    }
}
