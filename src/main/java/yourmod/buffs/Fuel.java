package yourmod.buffs;

import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.trigger.global.linked.GlobalNumberLimit;
import com.tann.dice.gameplay.trigger.personal.Personal;
import com.tann.dice.gameplay.trigger.personal.merge.Merge;
import com.tann.dice.statics.sound.Sounds;
import com.tann.dice.util.Tann;

public class Fuel extends Merge {
    int value;
    String overrideName;

    public Fuel(int value) {
        this(value, (String)null);
    }

    public Fuel(int value, String overrideName) {
        this.value = value;
        this.overrideName = overrideName;
    }

    public int getValue() {
        return value;
    }

    public boolean showInEntPanelInternal() {
        return true;
    }

    public String getImageName() {
        return "fuel";
    }

    public String describeForGiveBuff(Eff source) {
        return this.value + " fuel";
    }

    public String describeForSelfBuff() {
        return "[notranslate][orange]Fuel[cu] " + value+ " ([orange]" + Tann.repeat("[hp-square][p]", this.value) + "[cu])";
    }

    public boolean canMergeInternal(Personal personal) {
        return this.overrideName == null && ((Fuel)personal).overrideName == null;
    }

    public void merge(Personal personal) {
        this.value += ((Fuel)personal).value;
        this.value = GlobalNumberLimit.box(this.value);
    }

    public String[] getSound() {
        return Sounds.fire;
    }

    public boolean showAsIncoming() {
        return false;
    }
}