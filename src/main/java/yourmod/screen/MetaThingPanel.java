package yourmod.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.effect.eff.keyword.Keyword;
import com.tann.dice.gameplay.phase.levelEndPhase.rewardPhase.decisionPhase.choice.choosable.Choosable;
import com.tann.dice.screens.dungeon.panels.entPanel.choosablePanel.ConcisePanel;
import com.tann.dice.util.Colours;
import com.tann.dice.util.Pixl;
import com.tann.dice.util.ui.TextWriter;
import yourmod.pipes.meta.MetaThing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MetaThingPanel extends ConcisePanel {
    public MetaThing getMetaThing() {
        return (MetaThing) choosable;
    }

    public MetaThingPanel(MetaThing metaThing, boolean big) {
        super(metaThing, big);
    }

    @Override
    protected boolean hasKeywords() {
        return false;
    }

    @Override
    protected ConcisePanel makeCopy(boolean b) {
        return new MetaThingPanel(getMetaThing(), b);
    }

    @Override
    protected String getFullDescription() {
        return choosable.describe();
    }

    @Override
    protected Eff getSingleEffOrNull(Keyword keyword) {
        return null;
    }

    @Override
    protected Color getBorderColour() {
        return Colours.grey;
    }

    @Override
    protected List<Keyword> getReferencedKeywords() {
        return Collections.emptyList();
    }

    @Override
    protected List<Actor> getMiddleActors(boolean b) {
        List<Actor> actors = getMetaThing().makeMiddleActors();

        return actors;
        //return Arrays.asList(new Pixl().text("Test test test test[n]Test test test").pix());
    }

    @Override
    protected String getTitle() {
        return TextWriter.rebracketTags(this.getMetaThing().getName());
    }

    @Override
    protected List<Actor> getExtraTopActors() {
        return Arrays.asList();
    }
}
