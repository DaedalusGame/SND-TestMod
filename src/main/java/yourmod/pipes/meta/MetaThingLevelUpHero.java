package yourmod.pipes.meta;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.dice.gameplay.content.ent.type.HeroCol;
import com.tann.dice.gameplay.trigger.personal.equipRestrict.EquipRestrictCol;
import com.tann.dice.util.Pixl;

import java.util.Collections;
import java.util.List;

public class MetaThingLevelUpHero extends MetaThing {
    HeroCol col;

    public MetaThingLevelUpHero(String name, HeroCol col) {
        super(name);
        this.col = col;
    }

    public HeroCol getHeroColor() {
        return col;
    }

    @Override
    public List<Actor> makeMiddleActors() {
        return Collections.singletonList(new Pixl().text("[light]Level up[cu] ").actor(EquipRestrictCol.restrActor(col)).pix());
    }

    @Override
    public String describe() {
        return ""+col.colourTaggedName(false)+" heroes can generate heroes";
    }
}
