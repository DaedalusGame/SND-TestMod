package yourmod.effect;

import com.tann.dice.gameplay.content.ent.Ent;
import com.tann.dice.gameplay.content.ent.Hero;
import com.tann.dice.gameplay.content.ent.type.HeroType;
import com.tann.dice.gameplay.content.ent.type.lib.HeroTypeLib;
import com.tann.dice.gameplay.context.DungeonContext;
import com.tann.dice.gameplay.fightLog.EntState;
import com.tann.dice.gameplay.fightLog.Snapshot;
import com.tann.dice.gameplay.phase.PhaseManager;
import com.tann.dice.gameplay.phase.levelEndPhase.rewardPhase.decisionPhase.reveal.RandomRevealPhase;
import com.tann.dice.gameplay.trigger.personal.Personal;
import com.tann.dice.statics.sound.Sounds;

public class PermanentPersonal extends Personal {
    public String str;

    public PermanentPersonal(String str) {
        this.str = str;
    }

    @Override
    public void endOfLevel(EntState entState, Snapshot snapshot) {
        Ent ent = entState.getEnt();
        if(ent instanceof Hero) {
            Hero hero = (Hero) ent;
            HeroType changeTo;
            try {
                changeTo = HeroTypeLib.byName("("+hero.getHeroType().getName()+").i."+str);
            } catch(Exception e) {
                return;
            }
            hero.levelUpTo(changeTo, null);
        }
    }
}
