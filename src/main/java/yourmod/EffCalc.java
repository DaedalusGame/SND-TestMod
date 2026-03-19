package yourmod;

public class EffCalc {
    public float base;
    public float perPip;

    public EffCalc(int base, int perPip) {
        this.base = base / 100f;
        this.perPip = perPip / 100f;
    }

    public float getValue(int pips) {
        if(pips == -999) {
            pips = 0;
        }
        return base + pips * perPip;
    }
}
