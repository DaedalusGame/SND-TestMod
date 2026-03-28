package yourmod.buffs;

import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.fightLog.EntState;
import com.tann.dice.gameplay.trigger.global.linked.GlobalNumberLimit;
import com.tann.dice.gameplay.trigger.personal.Personal;
import com.tann.dice.gameplay.trigger.personal.ShieldsRemaining;
import com.tann.dice.statics.sound.Sounds;
import com.tann.dice.util.Tann;

public class Doom extends Personal {
    Eff eff;

    public Doom(Eff eff) {
        this.eff = eff;
    }

    public Eff getEffect() {
        return eff;
    }

    @Override
    public Personal transformForBuff() {
        return new Doom(eff);
    }

    @Override
    public void endOfTurn(EntState entState) {
        if(this.buff != null && this.buff.turns == 1){
            ShieldsRemaining.useEffMaybeUntargeted(entState, eff);
        }
    }

    public boolean showInEntPanelInternal() {
        return true;
    }

    public String getImageName() {
        return "doom";
    }

    public String describeForGiveBuff(Eff source) {
        return "[grey]doom[cu] ("+eff.describe()+")";
    }

    public String describeForSelfBuff() {
        return "[notranslate][grey]Doom[cu] ("+eff.describe()+")";
    }

    public String[] getSound() {
        return Sounds.deathWeird;
    }

    public boolean showAsIncoming() {
        return false;
    }
}
