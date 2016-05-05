package fi.tamk.dreampult.Handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

import fi.tamk.dreampult.Dreampult;

/**
 * @author Kalle Heinonen & Tommi Hagelberg
 */
public class Localization {

    public I18NBundle myBundle;
    Locale locale;
    AssetHandler assets;
    Dreampult game;
    public String lang;

    /**
     * Initializes everything so that localization can be used properly
     *
     * @param assets AssetManager used by Dreampult
     * @param game used to gain access to Dreampult's AssetManager
     */
    public Localization(AssetHandler assets, Dreampult game){
        this.assets = assets;
        this.game = game;
        getPrefs();
        loadLang();
    }

    /**
     * Changes the language currently in use
     */
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

    /**
     * @return the locale currently in use
     */
    public Locale getLang(){
        return locale;
    }

    /**
     * Creates the locale based on the value stored in preferences
     */
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

    /**
     * Loads the I18NBundle from the AssetManager with the correct locale
     */
    public void loadLang(){
        assets.loadLocalization(locale);
        assets.manager.finishLoading();
        myBundle = assets.manager.get("MyBundle/", I18NBundle.class);
    }

    /**
     * @param lang currently used language is stored to the preferences
     */
    public void savePreferences(String lang) {
        Preferences prefs = Gdx.app.getPreferences("DreampultLanguage");
        prefs.putString("language", lang);
        prefs.flush();
    }
}
