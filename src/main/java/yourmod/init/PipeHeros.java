package yourmod.init;

import basemod.pipes.IInitializeHeroPipes;
import com.tann.dice.gameplay.content.ent.type.HeroType;
import com.tann.dice.gameplay.content.gen.pipe.entity.hero.PipeHero;
import yourmod.pipes.hero.PipeHeroMetaSide;

import java.util.List;

public class PipeHeros implements IInitializeHeroPipes {
    @Override
    public void initialize() {
        PipeHero.pipes.add(new PipeHeroMetaSide());
    }

    @Override
    public void modifyHiddenModifiers(List<HeroType> list) {

    }
}
