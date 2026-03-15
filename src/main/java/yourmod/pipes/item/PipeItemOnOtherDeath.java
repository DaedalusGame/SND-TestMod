package yourmod.pipes.item;

import com.tann.dice.gameplay.content.ent.type.HeroType;
import com.tann.dice.gameplay.content.ent.type.lib.HeroTypeLib;
import com.tann.dice.gameplay.content.ent.type.lib.HeroTypeUtils;
import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNPref;
import com.tann.dice.gameplay.content.item.ItBill;
import com.tann.dice.gameplay.content.item.Item;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.effect.eff.EffBill;
import com.tann.dice.gameplay.trigger.personal.death.OnDeathEffect;
import com.tann.dice.gameplay.trigger.personal.death.OtherDeathEffect;
import com.tann.dice.gameplay.trigger.personal.onHit.OnHitFromPipe;

public class PipeItemOnOtherDeath extends PipeRegexNamed<Item> {
    public static final PRNPart PREF = new PRNPref("otherdeathdata");

    public PipeItemOnOtherDeath() {
        super(new PRNPart[]{PREF, HERO});
    }

    protected Item internalMake(String[] groups) {
        String heroName = groups[0];
        if (heroName == null) {
            return null;
        } else {
            HeroType ht = HeroTypeLib.byName(heroName);
            return ht.isMissingno() ? null : this.makeInternal(ht);
        }
    }

    private Item makeInternal(HeroType ht) {
        Boolean friendly = null;
        switch (ht.hp % 3) {
            case (0): friendly = null; break;
            case (1): friendly = true; break;
            case (2): friendly = false; break;
        }
        Eff e = OnHitFromPipe.getLeftmostBlankDerived(ht);
        OtherDeathEffect startOfCombat = new OtherDeathEffect(ht.getName(true, false),friendly, new EffBill(e));
        return new ItBill(PREF + ht.getName()).prs(startOfCombat).bItem();
    }

    public Item example() {
        return this.makeInternal(HeroTypeUtils.random());
    }

    public boolean isComplexAPI() {
        return true;
    }
}