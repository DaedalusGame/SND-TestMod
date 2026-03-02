package yourmod.keywords;

import basemod.keywords.IKeywordUsable;
import com.tann.dice.gameplay.content.ent.Ent;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.effect.eff.keyword.KUtils;
import com.tann.dice.gameplay.effect.targetable.Targetable;
import com.tann.dice.gameplay.fightLog.EntState;
import com.tann.dice.gameplay.fightLog.FightLog;
import com.tann.dice.gameplay.fightLog.Snapshot;
import com.tann.dice.screens.dungeon.TargetingManager;

public abstract class EntKeywordUsable implements IKeywordUsable {
    public boolean isUsable(EntState state, Eff first, boolean onlyRecommended, boolean cantrip) {
        return isUsable(state, first);
    }

    public abstract boolean isUsable(EntState state, Eff first);

    public abstract String getInvalidTargetReason(EntState state, Eff first, boolean allowBadTargets);

    @Override
    public boolean isUsable(TargetingManager manager, Eff first, Snapshot snapshot, Targetable t, boolean onlyRecommended, boolean cantrip) {
        Ent ent = t.getSource();
        if(ent == null) return false;
        EntState state = ent.getState(FightLog.Temporality.Present);
        if(state == null) return false;

        return isUsable(state, first, onlyRecommended, cantrip);
    }

    @Override
    public String getInvalidTargetReason(TargetingManager manager, Eff first, Snapshot snapshot, Ent target, Targetable targetable, boolean allowBadTargets) {
        Ent ent = targetable.getSource();
        if(ent == null) return null;
        EntState state = ent.getState(FightLog.Temporality.Present);
        if(state == null) return null;

        if(!isUsable(state, first)) {
            return getInvalidTargetReason(state, first, allowBadTargets);
        }

        return null;
    }
}
