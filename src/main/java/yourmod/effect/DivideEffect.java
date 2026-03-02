package yourmod.effect;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.tann.dice.gameplay.content.ent.EntSize;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.fightLog.EntSideState;
import com.tann.dice.gameplay.fightLog.EntState;
import com.tann.dice.gameplay.trigger.Collision;
import com.tann.dice.gameplay.trigger.global.linked.GlobalNumberLimit;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.AffectSides;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.EffectDraw;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.condition.AffectSideCondition;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.AffectSideEffect;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.MultiplyEffect;
import com.tann.dice.statics.Images;
import com.tann.dice.util.Colours;
import com.tann.dice.util.TannFont;
import com.tann.dice.util.lang.Words;

import java.util.List;

public class DivideEffect extends AffectSideEffect {
    final int upper;
    final int lower;

    public DivideEffect(int upper, int lower) {
        this.upper = upper;
        this.lower = lower;
    }

    public String getToFrom() {
        return "of";
    }

    public String describe() {
        return upper + "/" + lower + " the pips";
    }

    public void affect(EntSideState sideState, EntState owner, int index, AffectSides sourceTrigger, int sourceIndex) {
        Eff e = sideState.getCalculatedEffect();
        if (e.hasValue()) {
            int newValue = e.getValue() * this.upper / this.lower;
            e.setValue(newValue);
        }

    }

    public boolean needsGraphic() {
        return true;
    }

    public EffectDraw getAddDraw(final boolean hasSideImage, List<AffectSideCondition> conditions) {
        return new EffectDraw() {
            public void draw(Batch batch, int x, int y, int index) {
                int size = EntSize.reg.getPixels();
                batch.setColor(Colours.orange);
               /*if (hasSideImage) {
                    batch.setColor(Colours.orange);

                    for(int i = 0; i < DivideEffect.this.multiple; ++i) {
                        batch.draw(Images.multiplier, (float)(x + 16 - 5), (float)(y + 2 + i * 4));
                    }
                } else {*/
                    TannFont.font.drawString(batch, DivideEffect.this.upper+ "/" + DivideEffect.this.lower, (float)((int)((float)x + (float)size / 2.0F)), (float)((int)((float)y + (float)size / 2.0F)), 1);
                //}

            }
        };
    }

    public AffectSideEffect genMult(int mult) {
        return new DivideEffect(GlobalNumberLimit.box(this.upper * mult), this.lower);
    }

    public boolean isMultiplable() {
        return true;
    }

    public long getCollisionBits(Boolean player) {
        return Collision.LARGE_VALUES;
    }
}