package yourmod.pipes.hero;

import basemod.pipes.IHasGroupCount;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.dice.Main;
import com.tann.dice.gameplay.content.ent.EntSize;
import com.tann.dice.gameplay.content.ent.die.side.EntSide;
import com.tann.dice.gameplay.content.ent.die.side.EntSidesLib;
import com.tann.dice.gameplay.content.ent.die.side.blob.ESB;
import com.tann.dice.gameplay.content.ent.type.EntType;
import com.tann.dice.gameplay.content.ent.type.HeroType;
import com.tann.dice.gameplay.content.ent.type.bill.HTBill;
import com.tann.dice.gameplay.content.ent.type.lib.HeroTypeLib;
import com.tann.dice.gameplay.content.ent.type.lib.HeroTypeUtils;
import com.tann.dice.gameplay.content.gen.pipe.entity.hero.side.PipeHeroSides;
import com.tann.dice.gameplay.content.gen.pipe.item.facade.FacadeUtils;
import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNSideMulti;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNMid;
import com.tann.dice.gameplay.effect.eff.keyword.Keyword;
import com.tann.dice.util.Colours;
import com.tann.dice.util.Pair;
import com.tann.dice.util.Pixl;
import com.tann.dice.util.Tann;
import com.tann.dice.util.listener.TannListener;
import com.tann.dice.util.ui.standardButton.StandardButton;
import yourmod.pipes.PRNMetaSideMulti;
import yourmod.pipes.meta.MetaThingLib;
import yourmod.pipes.meta.MetaThingSide;

import java.util.ArrayList;
import java.util.List;

import static yourmod.pipes.hero.PipeHeroMetaSideMini.getSide;

public class PipeHeroMetaSide  extends PipeRegexNamed<HeroType> implements IHasGroupCount {
    static PRNPart MID = new PRNMid("metasd");

    public PipeHeroMetaSide() {
        super(HERO, MID, new PRNMetaSideMulti());
    }

    @Override
    public int getGroupCount() {
        return 2;
    }

    public HeroType example() {
        List<String> parts = new ArrayList<String>();
        int toAdd = 6;

        for(int i = 0; i < toAdd; ++i) {
            parts.add(rBit());
        }

        String data = Tann.join(":", parts);
        return this.make(HeroTypeUtils.random(), data.split(":"), data);
    }

    public static String rBit() {
        return Tann.randomInt(20) + "-" + (Tann.randomInt(7) + 1);
    }

    public boolean isComplexAPI() {
        return true;
    }

    protected HeroType internalMake(String[] groups) {
        HeroType ht = HeroTypeLib.byName(groups[0]);
        String[] data = groups[1].split(":");
        return this.make(ht, data, groups[1]);
    }

    private HeroType make(HeroType ht, String[] data, String unparsed) {
        if (ht.isMissingno()) {
            return null;
        } else {
            List<EntSide> replacedSides = new ArrayList<>();

            for (String datum : data) {
                String[] parts = datum.split("-");

                int val = 0;
                if (parts.length == 2) {
                    if (!Tann.isInt(parts[1])) {
                        return null;
                    }

                    val = Integer.parseInt(parts[1]);
                }

                EntSide es = getSide(parts[0]).withValue(val);

                if (es == null) {
                    return null;
                }

                replacedSides.add(es);
            }

            EntSide[] cpy = new EntSide[6];
            EntType.realToNice(cpy);

            for(int i = 0; i < 6; ++i) {
                if (i < replacedSides.size()) {
                    cpy[i] = replacedSides.get(i);
                } else {
                    cpy[i] = ESB.blank;
                }
            }

            EntType.niceToReal(cpy);
            return HeroTypeUtils.copy(ht).name(ht.getName() + MID + unparsed).sidesRaw(cpy).bEntType();
        }
    }

    public static Actor makeSidesWithLabels() {
        Pixl p = (new Pixl(2, 2)).border(Colours.grey);




        List<Pair<String,EntSide>> objs = new ArrayList<>();

        for (Object o : MetaThingLib.getPinThings()) {
            if (!(o instanceof MetaThingSide)) {
                continue;
            }
            MetaThingSide metaThingSide = (MetaThingSide) o;
            objs.add(new Pair<>(metaThingSide.id,metaThingSide.entSide));
        }

        for (Pair<String,EntSide> pair : objs) {
            final EntSide rl = FacadeUtils.maybeWithValue(pair.b, 1);
            Actor a = rl.makeBasicSideActor(0, false, (Keyword) null);
            a.addListener(new TannListener() {
                public boolean action(int button, int pointer, float x, float y) {
                    String n = "statue.metasd." + pair.a + "-" + Tann.randomInt(12);
                    HeroType ht = HeroTypeLib.byName(n);
                    Main.getCurrentScreen().pushAndCenter(FacadeUtils.example(pair.a, ht, rl.getTexture(), "" + pair.a), 0.5F);
                    return true;
                }
            });
            p.actor(a, (float) Main.width * 0.5F);
        }

        return p.pix();
    }

    public Actor getExtraActor() {
        return (new StandardButton("ids")).makeTiny().setRunnable(new Runnable() {
            public void run() {
                Main.getCurrentScreen().pushAndCenter(Tann.makeScrollpaneIfNecessary(PipeHeroMetaSide.makeSidesWithLabels()), 0.5F);
            }
        });
    }
}
