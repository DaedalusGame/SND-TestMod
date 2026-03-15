package yourmod;

import com.tann.dice.gameplay.content.ent.Ent;
import com.tann.dice.gameplay.content.ent.Hero;
import com.tann.dice.gameplay.content.ent.type.EntType;
import com.tann.dice.gameplay.effect.Trait;
import com.tann.dice.gameplay.fightLog.EntState;
import yourmod.effect.ITrueName;

public class TrueNameUtils {
    public static String getTrueName(EntType type) {
        for (Trait trait : type.traits) {
            if(trait.personal instanceof ITrueName) {
                return ((ITrueName) trait.personal).getTrueName();
            }
        }

        return type.getName(false);
    }

    public static String getTrueName(Ent ent) {
        return getTrueName(ent.entType);
    }
}
