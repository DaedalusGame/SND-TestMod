package yourmod;

import basemod.BaseMod;
import basemod.LazyS;
import basemod.sides.SpecificSidesTypeRegistry;
import com.badlogic.gdx.math.Vector2;
import com.bord.dice.modthedice.lib.SpireInitializer;
import com.tann.dice.statics.Images;
import yourmod.init.Keywords;
import yourmod.init.PipeItems;
import yourmod.init.PipeMods;

@SpireInitializer
public class Mod {

    public static void initialize() {
        BaseMod.register(new Keywords());
        BaseMod.register(new PipeItems());
        BaseMod.register(new PipeMods());

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
                new LazyS<>(() -> Images.replaceAll),
                new Vector2[]{
                        new Vector2(0.0F, 15.0F),
                        new Vector2(15.0F, 15.0F),
                        new Vector2(30.0F, 15.0F),
                },
                "the three left sides",
                "left3");
        SpecificSidesTypeRegistry.registerSpecificSidesType(
                "Left4",
                new int[]{2, 4, 0, 1},
                new LazyS<>(() -> Images.replaceAll),
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
                new LazyS<>(() -> Images.replaceAll),
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
                new LazyS<>(() -> Images.replaceAll),
                new Vector2[]{
                        new Vector2(15.0F, 15.0F),
                        new Vector2(15.0F, 0.0F),
                },
                "the two bottom sides",
                "bot2");
        SpecificSidesTypeRegistry.registerSpecificSidesType(
                "Top2",
                new int[]{4, 0},
                new LazyS<>(() -> Images.replaceAll),
                new Vector2[]{
                        new Vector2(15.0F, 15.0F),
                        new Vector2(15.0F, 30.0F),
                },
                "the two top sides",
                "top2");

        SpecificSidesTypeRegistry.registerSpecificSidesType(
                "Ring",
                new int[]{2, 0, 1, 3},
                new LazyS<>(() -> Images.replaceAll),
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
