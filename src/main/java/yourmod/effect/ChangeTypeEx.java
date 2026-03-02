package yourmod.effect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tann.dice.gameplay.content.ent.die.side.EnSiBi;
import com.tann.dice.gameplay.content.ent.die.side.EntSide;
import com.tann.dice.gameplay.content.ent.die.side.HSL;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.effect.eff.EffType;
import com.tann.dice.gameplay.effect.eff.keyword.KUtils;
import com.tann.dice.gameplay.effect.eff.keyword.Keyword;
import com.tann.dice.gameplay.fightLog.EntSideState;
import com.tann.dice.gameplay.fightLog.EntState;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.AffectSides;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.EffectDraw;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.condition.AffectSideCondition;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.condition.TypeCondition;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.ChangeArt;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.ChangeType;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.ReplaceWith;
import com.tann.dice.util.Colours;

import java.util.Iterator;
import java.util.List;

public class ChangeTypeEx extends ChangeType {
    private ChangeArt changeArt;
    private int multiplier;

    public ChangeTypeEx(EnSiBi changeTo, String newSideDescription, int multiplier, EntSide artSource) {
        super(changeTo, newSideDescription, multiplier);
        changeArt = new ChangeArt(artSource);
        this.multiplier = multiplier;
    }

    @Override
    public EffectDraw getAddDraw(boolean hasSideImage, List<AffectSideCondition> conditions) {
        EntSide artSrc = ChangeTypeEx.this.changeArt.artSrc.withValue(-999);
        return new EffectDraw() {
            public void draw(Batch batch, int x, int y) {
                super.draw(batch, x, y);

                TextureRegion src = artSrc.getTexture2D();
                int offset = 0;
                int drawY = offset + y;
                //artSrc.draw(batch, ChangeArt.genericSource(), (float)x, (float)drawY, (Color)null, (TextureRegion)null);
                Color col = Colours.text;
                int bonus = ChangeTypeEx.this.multiplier == 1 ? -1 : ChangeTypeEx.this.multiplier;
                artSrc.drawWithMultiplier(batch, (float)x, (float)y, col, bonus);
            }
        };
    }

    @Override
    public void affect(EntSideState sideState, EntState owner, int index, AffectSides sourceTrigger, int sourceIndex) {
        Eff e = sideState.getCalculatedEffect();
        EffType originalType = e.getType();
        int newValue = e.hasValue() ? e.getValue() * this.multiplier : 0;
        EntSide newSide = this.changeTo.val(newValue);
        ReplaceWith.replaceSide(sideState, newSide);
        changeArt.affect(sideState, owner, index, sourceTrigger, sourceIndex);
        sideState.getCalculatedEffect().addKeywords(e.getKeywords());
        Iterator var11 = sourceTrigger.getConditions().iterator();

        while(true) {
            AffectSideCondition c;
            do {
                if (!var11.hasNext()) {
                    return;
                }

                c = (AffectSideCondition)var11.next();
            } while(!(c instanceof TypeCondition));

            TypeCondition tc = (TypeCondition)c;

            for (EffType et : tc.types) {

                for (Keyword k : KUtils.getKeywordsFor(et)) {
                    if (k != null && !tc.getAllIncludingEquivs().contains(originalType)) {
                        sideState.getCalculatedEffect().removeKeyword(k);
                    }
                }
            }
        }
    }
}
