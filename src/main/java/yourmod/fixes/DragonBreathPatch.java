package yourmod.fixes;

import basemod.pipes.APIUtilsPatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.bord.dice.modthedice.lib.*;
import com.bord.dice.modthedice.patcher.PatchingException;
import com.tann.dice.gameplay.content.ent.Ent;
import com.tann.dice.gameplay.content.gen.pipe.Pipe;
import com.tann.dice.gameplay.content.gen.pipe.entity.hero.PipeHero;
import com.tann.dice.gameplay.content.gen.pipe.entity.monster.PipeMonster;
import com.tann.dice.gameplay.content.gen.pipe.item.PipeItem;
import com.tann.dice.gameplay.content.gen.pipe.mod.PipeMod;
import com.tann.dice.gameplay.fightLog.EntState;
import com.tann.dice.screens.dungeon.DungeonScreen;
import com.tann.dice.screens.dungeon.panels.book.page.BookPage;
import com.tann.dice.screens.dungeon.panels.combatEffects.dragonBreath.DragonBreathController;
import com.tann.dice.screens.dungeon.panels.combatEffects.dragonBreath.DragonBreathParticle;
import com.tann.dice.util.Colours;
import com.tann.dice.util.Tann;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import javassist.expr.NewExpr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*@SpirePatch2(
        clz = DragonBreathController.class,
        method = "start"
)
public class DragonBreathPatch {
    public static void fix1(Ent source, float top, float bot, double[] topAngle, double[] botAngle, Vector2 panelPos) {
        if(source.isPlayer()) {
            panelPos.add(source.getEntPanel().getWidth(),0);
            topAngle[0] = Math.atan2(DungeonScreen.get().getWidth() - 50.0f - panelPos.x, top - panelPos.y);
            botAngle[0] = Math.atan2(DungeonScreen.get().getWidth() - 50.0f - panelPos.x, bot - panelPos.y);
        }
    }

    public static void fix2(Ent source, List<EntState> targets, Vector2 panelPos, float[] dist) {
        boolean hasHero = false;
        boolean hasEnemy = false;

        for (EntState entState: targets) {
            if(entState.isPlayer()) {
                hasHero = true;
            } else {
                hasEnemy = true;
            }
        }
        if(hasEnemy && hasHero) {
            if(Tann.chance(0.5f)){
                hasEnemy = false;
            } else {
                hasHero = false;
            }
        }

        if(!hasEnemy && !hasHero) {
            if(source.isPlayer()) {
                hasEnemy = true;
            } else {
                hasHero = true;
            }
        }

        if(hasEnemy) {
            dist[0] = panelPos.x - DungeonScreen.get().enemy.getX() + 16.800001F;
        } else if(hasHero) {
            dist[0] = panelPos.x - DungeonScreen.get().hero.getX() - 16.800001F;
        }
    }

    public static ExprEditor Instrument()
    {
        return new ExprEditor() {
            @Override
            public void edit(MethodCall m) throws CannotCompileException {
                if (m.getMethodName().equals("getX"))
                    m.replace("{" +
                            "$_ = $proceed($$);" +
                            "double[] ___topAngle = new double[] { topAngle };" +
                            "double[] ___botAngle = new double[] { botAngle };" +
                            "yourmod.fixes.DragonBreathPatch.fix1(this.source, top, bot, ___topAngle, ___botAngle, panelPos);" +
                            "topAngle = ___topAngle[0];" +
                            "botAngle = ___botAngle[0];" +
                            "}");

            }

            @Override
            public void edit(NewExpr e) throws CannotCompileException {
                if(e.getClassName().equals("com.tann.dice.screens.dungeon.panels.combatEffects.dragonBreath.DragonBreathParticle")){
                    e.replace("{" +
                            "$_ = $proceed($$);" +
                            "float[] ___dist = new float[] { dist };" +
                            "yourmod.fixes.DragonBreathPatch.fix2(this.source, targets, panelPos, ___dist);" +
                            "dist = ___dist[0];" +
                            "}");
                }
            }
        };
    }
}*/
