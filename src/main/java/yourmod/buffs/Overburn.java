package yourmod.buffs;

import com.tann.dice.gameplay.content.ent.type.EntType;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.trigger.global.linked.GlobalNumberLimit;
import com.tann.dice.gameplay.trigger.personal.Personal;
import com.tann.dice.gameplay.trigger.personal.merge.Merge;
import com.tann.dice.statics.sound.Sounds;
import com.tann.dice.util.Tann;

public class Overburn extends Merge {
    int value;
    String overrideName;

    public Overburn(int value) {
        this(value, (String)null);
    }

    public Overburn(int value, String overrideName) {
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
        return "explode";
    }

    public String describeForGiveBuff(Eff source) {
        return this.value + " overburn";
    }

    public String describeForSelfBuff() {
        return this.value + " overburn";
    }

    public boolean canMergeInternal(Personal personal) {
        return this.overrideName == null && ((Overburn)personal).overrideName == null;
    }

    public void merge(Personal personal) {
        this.value += ((Overburn)personal).value;
        this.value = GlobalNumberLimit.box(this.value);
    }

    public String[] getSound() {
        return Sounds.fire;
    }

    public boolean showAsIncoming() {
        return false;
    }
}