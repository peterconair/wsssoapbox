/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.bean.backing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import org.wsssoapbox.bean.model.view.Theme;

/**
 *
 * @author Peter
 */

public class ThemeSwitcherBean {
        
    private Map<String, String> themes;
    
    private List<Theme> advancedThemes;
    
    private String theme;
    
    private GuestPreferences gp;

    public void setGp(GuestPreferences gp) {
        this.gp = gp;
    }
    
    public Map<String, String> getThemes() {
        return themes;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @PostConstruct
    public void init() {
        theme = gp.getTheme();
        
        advancedThemes = new ArrayList<Theme>();
        advancedThemes.add(new Theme("aristo", "aristo.png"));
        advancedThemes.add(new Theme("cupertino", "cupertino.png"));
        advancedThemes.add(new Theme("trontastic", "trontastic.png"));
        
        themes = new TreeMap<String, String>();
        themes.put("Aristo", "aristo");
        themes.put("Black-Tie", "black-tie");
    //    themes.put("Blitzer", "blitzer");
        themes.put("Bluesky", "bluesky");
        themes.put("Casablanca", "casablanca");
    //    themes.put("Cupertino", "cupertino");
        themes.put("Dark-Hive", "dark-hive");
    //    themes.put("Dot-Luv", "dot-luv");
        themes.put("Eggplant", "eggplant");
        themes.put("Excite-Bike", "excite-bike");
    //    themes.put("Flick", "flick");
    //    themes.put("Glass-X", "glass-x");
        themes.put("Home", "home");
        themes.put("Hot-Sneaks", "hot-sneaks");
        themes.put("Humanity", "humanity");
    //    themes.put("Le-Frog", "le-frog");
        themes.put("Midnight", "midnight");
        themes.put("Mint-Choc", "mint-choc");
   //     themes.put("Overcast", "overcast");
        themes.put("Pepper-Grinder", "pepper-grinder");
        themes.put("Redmond", "redmond");
        themes.put("Rocket", "rocket");
        themes.put("Sam", "sam");
  //      themes.put("Smoothness", "smoothness");
        themes.put("South-Street", "south-street");
        themes.put("Start", "start");
        themes.put("Sunny", "sunny");
        themes.put("Swanky-Purse", "swanky-purse");
        themes.put("Trontastic", "trontastic");
        themes.put("UI-Darkness", "ui-darkness");
        themes.put("UI-Lightness", "ui-lightness");
        themes.put("Vader", "vader");
    }
    
    public void saveTheme() {
        gp.setTheme(theme);
    }

    public List<Theme> getAdvancedThemes() {
        return advancedThemes;
    }
}
