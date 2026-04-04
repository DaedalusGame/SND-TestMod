package yourmod.effect.buffs;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.dice.gameplay.fightLog.EntState;
import com.tann.dice.gameplay.fightLog.Snapshot;
import com.tann.dice.gameplay.modifier.modBal.ModTierUtils;
import com.tann.dice.gameplay.save.settings.option.OptionLib;
import com.tann.dice.gameplay.trigger.Collision;
import com.tann.dice.gameplay.trigger.personal.startBuffed.StartBuffed;
import com.tann.dice.statics.Images;
import com.tann.dice.util.Colours;
import com.tann.dice.util.Pixl;
import com.tann.dice.util.Tann;
import yourmod.buffs.BuffUtil;

public class StartFueled extends StartBuffed {
    final int fuelAmount;

    public StartFueled(int fuelAmount) {
        this.fuelAmount = fuelAmount;
    }

    public String describeForSelfBuff() {
        return this.fuelAmount == 1 ? "Start [orange]fueled[cu]" : "Start [orange]fueled[cu] for " + this.fuelAmount;
    }

    public Actor makePanelActorI(boolean big) {
        Pixl p = new Pixl();
        p.image(Images.plusBig, Colours.grey).gap(1);
        Pixl pips = new Pixl();

        for(int i = 0; i < this.fuelAmount; ++i) {
            if (i % 5 == 0 && i != 0) {
                pips.row(1);
            }

            pips.image(Images.hp_square, Colours.orange).gap(1);
        }

        p.actor(pips.pix(8));

        return p.pix();
    }

    public String getImageName() {
        return "startFueled";
    }

    public void startOfCombat(Snapshot snapshot, EntState entState) {
        BuffUtil.addFuel(entState, fuelAmount);
    }

    public boolean isMultiplable() {
        return this.fuelAmount == 1;
    }

    public String hyphenTag() {
        return "" + this.fuelAmount;
    }
}
