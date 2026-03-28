package yourmod;

import basemod.BaseMod;
import basemod.LazyS;
import basemod.ability.AbilityRegistry;
import basemod.ability.ITacticCostType;
import basemod.ledger.LedgerPageTypeRegistry;
import basemod.sides.SpecificSidesTypeRegistry;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.bord.dice.modthedice.lib.SpireInitializer;
import com.tann.dice.Main;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.effect.eff.EffType;
import com.tann.dice.gameplay.effect.eff.keyword.Keyword;
import com.tann.dice.gameplay.effect.targetable.ability.tactic.TacticCostType;
import com.tann.dice.statics.Images;
import com.tann.dice.util.Colours;
import com.tann.dice.util.TannLog;
import com.tann.dice.util.saves.Prefs;
import yourmod.init.*;
import yourmod.screen.MetaPageType;
import yourmod.screen.MetaStorage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

@SpireInitializer
public class Mod {
    public static String MODID = "testmod";

    public static MetaStorage metaStorage;

    public static TextureRegion loadTex(String path) {
        try {
            Method loadMethod = Images.class.getDeclaredMethod("load", String.class);
            loadMethod.setAccessible(true);
            return (TextureRegion)loadMethod.invoke(null, path);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void resetSettings() {
        metaStorage = new MetaStorage();
        metaStorage.reset();
    }

    public static void loadMeta() {
        String settingsJson = Prefs.getString(MODID+":meta", (String)null);
        TannLog.log("load4");
        if (settingsJson == null) {
            TannLog.log("load4.1");
            resetSettings();
            TannLog.log("load4.2");
        } else {
            try {
                TannLog.log("load4.3");
                metaStorage = Main.getJson().fromJson(MetaStorage.class, settingsJson);
            } catch (Exception var3) {
                var3.printStackTrace();
                TannLog.error("Failed to load settings: " + var3.getMessage());
                resetSettings();
            }
        }
    }

    public static void initialize() {
        BaseMod.register(new Keywords());
        BaseMod.register(new PipeItems());
        BaseMod.register(new PipeMods());
        BaseMod.register(new PipeMonsters());
        BaseMod.register(new PipeHeroes());

        EffTypes.init();

        AbilityRegistry.textModFuncs.add((Function<Eff, TacticCostType>) eff -> {
            if(eff.getType() == EffType.Kill) {
                return TacticCostType.valueOf("kill");
            }
            if(eff.getType() == EffType.Summon) {
                return TacticCostType.valueOf("summon");
            }
            if(eff.getType() == EffType.Buff) {
                return TacticCostType.valueOf("buff");
            }
            if(eff.hasKeyword(Keyword.charged)) {
                return TacticCostType.valueOf("charged");
            }
            if(eff.hasKeyword(Keyword.nothing)) {
                return TacticCostType.valueOf("nothing");
            }
            if(eff.getValue() == 999) {
                return TacticCostType.valueOf("wildSide");
            }
            return null;
        });
        AbilityRegistry.registerTacticCostType("charged", "charged", true, new ITacticCostType() {
            @Override
            public String desc(TacticCostType instance) {
                return "[blue]charged[cu] pip";
            }

            @Override
            public boolean isValid(TacticCostType instance, Eff checkEff) {
                return checkEff.hasKeyword(Keyword.charged);
            }
        });
        AbilityRegistry.registerTacticCostType("nothing", "nothing", false, new ITacticCostType() {
            @Override
            public String desc(TacticCostType instance) {
                return "[grey]nothing[cu] side";
            }

            @Override
            public boolean isValid(TacticCostType instance, Eff checkEff) {
                return checkEff.hasKeyword(Keyword.nothing);
            }
        });
        AbilityRegistry.registerTacticCostType("wildSide", "wildside", false, new ITacticCostType() {
            @Override
            public String desc(TacticCostType instance) {
                return "any side";
            }

            @Override
            public boolean isValid(TacticCostType instance, Eff checkEff) {
                return true;
            }
        });
        AbilityRegistry.registerTacticCostType("kill", "kill", false, new ITacticCostType() {
            @Override
            public String desc(TacticCostType instance) {
                return "kill side";
            }

            @Override
            public boolean isValid(TacticCostType instance, Eff checkEff) {
                return checkEff.getType() == EffType.Kill;
            }
        });
        AbilityRegistry.registerTacticCostType("summon", "summon", true, new ITacticCostType() {
            @Override
            public String desc(TacticCostType instance) {
                return "summon pip";
            }

            @Override
            public boolean isValid(TacticCostType instance, Eff checkEff) {
                return checkEff.getType() == EffType.Summon;
            }
        });
        AbilityRegistry.registerTacticCostType("buff", "buff", false, new ITacticCostType() {
            @Override
            public String desc(TacticCostType instance) {
                return "buff side";
            }

            @Override
            public boolean isValid(TacticCostType instance, Eff checkEff) {
                return checkEff.getType() == EffType.Buff;
            }
        });

        LedgerPageTypeRegistry.registerLedgerPage("Meta", Colours.BLURPLE, new MetaPageType());

        /*SpecificSidesTypeRegistry.registerSpecificSidesType(
                "Left3",
                new int[]{2, 4, 0, 1, 3, 5},
                new LazyS<>(() -> Images.replaceAll),
                new Vector2[]{
                        new Vector2(0.0F, 15.0F),
                        new Vector2(15.0F, 15.0F),
                        new Vector2(15.0F, 30.0F),
                        new Vector2(15.0F, 0.0F),
                        new Vector2(30.0F, 15.0F),
                        new Vector2(45.0F, 15.0F)
                },
                "all sides",
                "all");*/

        SpecificSidesTypeRegistry.registerSpecificSidesType(
                "Left3",
                new int[]{2, 4, 3},
                new LazyS<>(() -> loadTex("trigger/equip-stuff/itemDiagram/left3")),
                new Vector2[]{
                        new Vector2(0.0F, 5.0F),
                        new Vector2(15.0F, 5.0F),
                        new Vector2(30.0F, 5.0F),
                },
                "the three left sides",
                "left3");
        SpecificSidesTypeRegistry.registerSpecificSidesType(
                "Left4",
                new int[]{2, 4, 0, 1},
                new LazyS<>(() -> loadTex("trigger/equip-stuff/itemDiagram/left4")),
                new Vector2[]{
                        new Vector2(0.0F, 15.0F),
                        new Vector2(15.0F, 15.0F),
                        new Vector2(15.0F, 30.0F),
                        new Vector2(15.0F, 0.0F)
                },
                "the four left sides",
                "left4");
        SpecificSidesTypeRegistry.registerSpecificSidesType(
                "Left5",
                new int[]{2, 4, 0, 1, 3},
                new LazyS<>(() -> loadTex("trigger/equip-stuff/itemDiagram/left5")),
                new Vector2[]{
                        new Vector2(0.0F, 15.0F),
                        new Vector2(15.0F, 15.0F),
                        new Vector2(15.0F, 30.0F),
                        new Vector2(15.0F, 0.0F),
                        new Vector2(30.0F, 15.0F)
                },
                "the five left sides",
                "left5");

        SpecificSidesTypeRegistry.registerSpecificSidesType(
                "Bottom2",
                new int[]{4, 1},
                new LazyS<>(() -> loadTex("trigger/equip-stuff/itemDiagram/bot2")),
                new Vector2[]{
                        new Vector2(5.0F, 15.0F),
                        new Vector2(5.0F, 0.0F),
                },
                "the two bottom sides",
                "bot2");
        SpecificSidesTypeRegistry.registerSpecificSidesType(
                "Top2",
                new int[]{4, 0},
                new LazyS<>(() -> loadTex("trigger/equip-stuff/itemDiagram/top2")),
                new Vector2[]{
                        new Vector2(5.0F, 5.0F),
                        new Vector2(5.0F, 20.0F),
                },
                "the two top sides",
                "top2");

        SpecificSidesTypeRegistry.registerSpecificSidesType(
                "Ring",
                new int[]{2, 0, 1, 3},
                new LazyS<>(() -> loadTex("trigger/equip-stuff/itemDiagram/left5")),
                new Vector2[]{
                        new Vector2(0.0F, 15.0F),
                        new Vector2(15.0F, 30.0F),
                        new Vector2(15.0F, 0.0F),
                        new Vector2(30.0F, 15.0F)
                },
                "the ring around the middle side",
                "ring");
    }
}
