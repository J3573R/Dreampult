package fi.tamk.dreampult.Handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

import fi.tamk.dreampult.Dreampult;

/**
 * Created by DV6-6B20 on 23.4.2016.
 */
public class LocalizationHandler {

    public I18NBundle myBundle;
    Locale gameLocale;
    Dreampult dreampult;

    public LocalizationHandler(Dreampult pult, Locale startLocale) {
        dreampult = pult;
        gameLocale = startLocale;

        setLocale(gameLocale);
    }

    public void setLocale(Locale locale) {
        myBundle = I18NBundle.createBundle(Gdx.files.internal("myBundle"), locale);
    }

    public I18NBundle getBundle() {
        return myBundle;
    }
}
