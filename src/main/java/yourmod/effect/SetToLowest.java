package yourmod.effect;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.tann.dice.gameplay.fightLog.EntSideState;
import com.tann.dice.gameplay.fightLog.EntState;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.AffectSides;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.EffectDraw;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.condition.AffectSideCondition;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.AffectSideEffect;
import com.tann.dice.util.Colours;
import com.tann.dice.util.TannFont;

import java.util.Iterator;
import java.util.List;

public class SetToLowest extends AffectSideEffect {
    public String getOverrideDescription(List<AffectSideCondition> conditions, List<AffectSideEffect> effects) {
        String sideDescription = "";
        if (conditions.isEmpty()) {
            sideDescription = "all sides";
        }

        AffectSideCondition asc;
        for(Iterator var4 = conditions.iterator(); var4.hasNext(); sideDescription = sideDescription + asc.describe()) {
            asc = (AffectSideCondition)var4.next();
        }

        return "Set the pips of " + sideDescription + " to the minimum of my other side's pips";
    }

    public String describe() {
        return null;
    }

    public EffectDraw getAddDraw(boolean hasSideImage, List<AffectSideCondition> conditions) {
        return new EffectDraw() {
            public void draw(Batch batch, int x, int y) {
                batch.setColor(Colours.light);
                TannFont.font.drawString(batch, "min", (float)(x + 8), (float)(y + 8), 1);
            }
        };
    }

    public void affect(EntSideState sideState, EntState owner, int index, AffectSides sourceTrigger, int sourceIndex) {
        int min = 999;

        for(int i = 0; i < 6; ++i) {
            EntSideState ess = new EntSideState(owner, owner.getEnt().getSides()[i], sourceIndex);
            int n = ess.getCalculatedEffect().getValue();
            if(n != -999) {
                min = Math.min(n, min);
            }
        }

        sideState.getCalculatedEffect().setValue(min);
    }
}
