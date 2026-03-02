package yourmod.effect;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.effect.eff.keyword.Keyword;
import com.tann.dice.gameplay.effect.targetable.ability.spell.SpellUtils;
import com.tann.dice.gameplay.trigger.Collision;
import com.tann.dice.gameplay.trigger.global.Global;
import com.tann.dice.statics.Images;
import com.tann.dice.util.Colours;
import com.tann.dice.util.ImageActor;
import com.tann.dice.util.Pixl;
import com.tann.dice.util.Tann;
import com.tann.dice.util.ui.TextWriter;

import java.util.Collections;
import java.util.List;

public class GlobalSpellKeywordEx extends Global {
    final String spellName;
    final Keyword keyword;

    public GlobalSpellKeywordEx(Keyword addKeyword) {
        this((String)null, addKeyword);
    }

    public GlobalSpellKeywordEx(String spellName, Keyword addKeyword) {
        if (!SpellUtils.allowAddingKeyword(addKeyword)) {
            throw new RuntimeException("Adding " + addKeyword + " to spells");
        } else {
            this.spellName = spellName;
            this.keyword = addKeyword;
        }
    }

    public String describeForSelfBuff() {
        String result = "Add " + this.keyword.getColourTaggedString();
        result = result + " to [blue]";
        return this.spellName == null ? result + "all spells" : result + this.spellName;
    }

    public Actor makePanelActorI(boolean big) {
        Actor ia = new ImageActor(Images.tr_manaSpellBottom, Colours.blue);
        TextWriter tw = new TextWriter("[grey]+" + this.keyword.getColourTaggedString());
        if (tw.getWidth() > ia.getWidth()) {
            ia = new ImageActor(Images.tr_manaSpellBottomWide, Colours.blue);
        }

        Group g = Tann.makeGroup(ia);
        g.addActor(tw);
        tw.setPosition((float)((int)(g.getWidth() / 2.0F - tw.getWidth() / 2.0F)), (float)((int)(8.0F - tw.getHeight() / 2.0F)));
        if (this.spellName != null) {
            g = (new Pixl()).text("[blue]" + this.spellName).row(2).actor(g).pix();
        }

        return g;
    }

    public void affectSpell(String title, Eff result) {
        if (this.spellName == null || title.equalsIgnoreCase(this.spellName)) {
            result.addKeyword(this.keyword);
        }
    }

    public List<Keyword> getReferencedKeywords() {
        return Collections.singletonList(this.keyword);
    }

    public long getCollisionBits(Boolean player) {
        return Collision.SPELL;
    }
}
