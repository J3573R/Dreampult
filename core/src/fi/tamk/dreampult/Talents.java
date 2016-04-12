package fi.tamk.dreampult;

/**
 * Created by Clown on 7.4.2016.
 */
public class Talents {

    private boolean growBouncy = false;
    private boolean growSlippery = false;

    private boolean boostLaunch = false;
    private boolean additionalLaunch = false;

    private boolean extraBounces = false;
    private boolean pyjamaGlide = false;

    public Talents() {
        //enableExtraBounces();
        //enablePyjamaGlide();
        //enableBoostLaunch();
        //enableAdditionalLaunch();
        //enableGrowBouncy();
        //enableGrowSlippery();
    }

    public boolean isGrowBouncy() {
        return growBouncy;
    }

    public void enableGrowBouncy() {
        this.growBouncy = true;
        this.growSlippery = false;
    }

    public boolean isGrowSlippery() {
        return growSlippery;
    }

    public void enableGrowSlippery() {
        this.growSlippery = true;
        this.growBouncy = false;
    }

    public boolean isBoostLaunch() {
        return boostLaunch;
    }

    public void enableBoostLaunch() {
        this.boostLaunch = true;
        this.additionalLaunch = false;
    }

    public boolean isAdditionalLaunch() {
        return additionalLaunch;
    }

    public void enableAdditionalLaunch() {
        this.additionalLaunch = true;
        this.boostLaunch = false;
    }

    public boolean isExtraBounces() {
        return extraBounces;
    }

    public void enableExtraBounces() {
        this.extraBounces = true;
        this.pyjamaGlide = false;
    }

    public boolean isPyjamaGlide() {
        return pyjamaGlide;
    }

    public void enablePyjamaGlide() {
        this.pyjamaGlide = true;
        this.extraBounces = false;
    }

    public void resetTalents() {
        this.growBouncy = false;
        this.growSlippery = false;

        this.boostLaunch = false;
        this.additionalLaunch = false;

        this.extraBounces = false;
        this.pyjamaGlide = false;
    }
}
