package yourmod.buffs;

import com.tann.dice.gameplay.content.ent.type.EntType;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.effect.eff.EffType;
import com.tann.dice.gameplay.trigger.global.linked.GlobalNumberLimit;
import com.tann.dice.gameplay.trigger.personal.Personal;
import com.tann.dice.gameplay.trigger.personal.merge.Merge;
import com.tann.dice.statics.sound.Sounds;
import com.tann.dice.util.Tann;

public class Serenity extends Merge {
    int value;
    String overrideName;

    public Serenity(int value) {
        this(value, (String)null);
    }

    public Serenity(int value, String overrideName) {
        this.value = value;
        this.overrideName = overrideName;
    }

    public boolean showInEntPanelInternal() {
        return true;
    }

    public String getImageName() {
        return "bonusHealing";
    }

    public String describeForGiveBuff(Eff source) {
        return this.value + " serenity";
    }

    public String describeForSelfBuff() {
        return Tann.delta(this.value) + " to incoming healing";
    }

    public boolean canMergeInternal(Personal personal) {
        return this.overrideName == null && ((Serenity)personal).overrideName == null;
    }

    public void merge(Personal personal) {
        this.value += ((Serenity)personal).value;
        this.value = GlobalNumberLimit.box(this.value);
    }

    public String[] getSound() {
        return Sounds.regen;
    }

    public int affectHealing(int heal) {
        if (heal <= 0) {
            return heal;
        } else {
            return Math.max(0, heal + this.value);
        }
    }

    public float affectStrengthCalc(float total, float avgRawValue, EntType type) {
        return total;
    }

    public float affectTotalHpCalc(float hp, EntType entType) {
        return hp + (float)(this.value * 3);
    }

    public boolean showAsIncoming() {
        return false;
    }
}
