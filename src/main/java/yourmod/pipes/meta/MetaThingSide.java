package yourmod.pipes.meta;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.dice.gameplay.content.ent.die.side.EnSiBi;
import com.tann.dice.gameplay.content.ent.die.side.EntSide;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.effect.eff.keyword.Keyword;
import com.tann.dice.util.Pixl;

import java.util.Collections;
import java.util.List;

public class MetaThingSide extends MetaThing {
    public EntSide entSide;
    public String id;

    public MetaThingSide(String name, String id, EntSide entSide) {
        super(name);
        this.id = id;
        this.entSide = entSide;
    }

    public String getID() {
        return id;
    }

    @Override
    public String describe() {
        Eff effForDesc = entSide.getBaseEffect().copy();
        if(effForDesc.getValue() != -999) effForDesc.setValue(69);

        String desc = effForDesc.describe().replace(" 69", "").replace("69 ", "");
        return desc;
    }

    @Override
    public List<Actor> makeMiddleActors() {
        return Collections.singletonList(new Pixl().text(id+": ").actor(this.entSide.withValue(-999).makeBasicSideActor(0, false, null)).pix());
    }
}
