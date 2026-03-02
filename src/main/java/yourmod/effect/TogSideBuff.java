package yourmod.effect;

import com.tann.dice.gameplay.content.item.Item;
import com.tann.dice.gameplay.effect.Buff;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.effect.eff.EffBill;
import com.tann.dice.gameplay.trigger.personal.Personal;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.AffectSides;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.condition.SpecificSidesType;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.weirdTextmod.BasicTextmodToggle;
import com.tann.dice.gameplay.trigger.personal.item.AsIfHasItem;
import yourmod.buffs.SpecificSideBuff;

import java.lang.reflect.Field;

public class TogSideBuff extends BasicTextmodToggle {
    public String describe() {
        return "turn buff side into specific side";
    }

    private AffectSides extractAffectSides(Personal personal) {
        if(personal instanceof  AffectSides) {
            return (AffectSides) personal;
        }
        else if(personal instanceof AsIfHasItem) {
            AsIfHasItem asIfHasItem = (AsIfHasItem) personal;
            try {
                Field field = AsIfHasItem.class.getDeclaredField("item");
                field.setAccessible(true);
                Item item = (Item) field.get(asIfHasItem);

                Personal singlePersonal = item.getSinglePersonalOrNull();
                return extractAffectSides(singlePersonal);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public Eff alterEff(Eff e) {
        Buff buff = e.getBuffAndCopy();
        AffectSides affectSides = extractAffectSides(buff.personal);

        if(affectSides != null) {
            return new EffBill(e).buff(new SpecificSideBuff(buff.turns, affectSides)).bEff();
        }

        return e;
    }
}