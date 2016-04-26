package fi.tamk.dreampult.Handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

import fi.tamk.dreampult.Dreampult;

/**
 * Created by DV6-6B20 on 23.4.2016.
 */
public class Localization {

    public I18NBundle myBundle;
    Locale locale;
    AssetHandler assets;
    Dreampult game;
    public String lang;

    public Localization(AssetHandler assets, Dreampult game){
        this.assets = assets;
        this.game = game;
        getPrefs();
        loadLang();
    }

    public void changeLang(){
        lang = "en";
       if(locale.getLanguage().contains("fi")) {
            lang = "en";
        } else if(locale.getLanguage().contains("en")) {
            lang = "fin";
        }
        savePreferences(lang);
        getPrefs();
        loadLang();
    }

    public Locale getLang(){
        return locale;
    }

    private void getPrefs(){
        Preferences prefs = Gdx.app.getPreferences("DreampultLanguage");

        lang = prefs.getString("language");

        if(lang.equals("fin")) {
            locale = new Locale("fi", "FI");

        } else if(lang.equals("en")) {
            locale = new Locale("en", "EN");
        } else {
            locale = new Locale("en", "EN");
        }
    }

    public void loadLang(){
        assets.loadLocalization(locale);
        assets.manager.finishLoading();
        myBundle = assets.manager.get("MyBundle/", I18NBundle.class);
    }

    public void savePreferences(String lang) {
        Preferences prefs = Gdx.app.getPreferences("DreampultLanguage");
        prefs.putString("language", lang);
        prefs.flush();
    }
}
