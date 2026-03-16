package yourmod;

import basemod.BaseMod;
import basemod.LazyS;
import basemod.ledger.ILedgerPageType;
import basemod.ledger.LedgerPageTypeRegistry;
import basemod.sides.SpecificSidesTypeRegistry;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.bord.dice.modthedice.Loader;
import com.bord.dice.modthedice.lib.SpireInitializer;
import com.tann.dice.Main;
import com.tann.dice.gameplay.save.settings.Settings;
import com.tann.dice.screens.dungeon.panels.book.page.ledgerPage.LedgerPage;
import com.tann.dice.statics.Images;
import com.tann.dice.util.Colours;
import com.tann.dice.util.Pixl;
import com.tann.dice.util.TannLog;
import com.tann.dice.util.saves.Prefs;
import yourmod.init.Keywords;
import yourmod.init.PipeItems;
import yourmod.init.PipeMods;
import yourmod.screen.MetaPageType;
import yourmod.screen.MetaStorage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

        //LedgerPageTypeRegistry.registerLedgerPage("Meta", Colours.BLURPLE, new MetaPageType());

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
