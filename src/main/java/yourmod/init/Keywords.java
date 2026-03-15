package yourmod.init;

import basemod.KeywordRegistry;
import basemod.LazyS;
import basemod.conditionalBonus.ConditionalBonusTypeRegistry;
import basemod.conditionalBonus.IConditionalBonusType;
import basemod.keywords.IOnAfterUseEffect;
import basemod.keywords.IOnUseEffect;
import basemod.keywords.LazyKeyword;
import com.tann.dice.gameplay.content.ent.Ent;
import com.tann.dice.gameplay.content.ent.Hero;
import com.tann.dice.gameplay.effect.Buff;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.effect.eff.EffBill;
import com.tann.dice.gameplay.effect.eff.VisualEffectType;
import com.tann.dice.gameplay.effect.eff.conditionalBonus.ConditionalBonus;
import com.tann.dice.gameplay.effect.eff.conditionalBonus.ConditionalBonusType;
import com.tann.dice.gameplay.effect.eff.conditionalBonus.conditionalRequirement.EnumConditionalRequirement;
import com.tann.dice.gameplay.effect.eff.conditionalBonus.conditionalRequirement.GSCConditionalRequirement;
import com.tann.dice.gameplay.effect.eff.keyword.KUtils;
import com.tann.dice.gameplay.effect.eff.keyword.Keyword;
import com.tann.dice.gameplay.effect.eff.keyword.KeywordAllowType;
import com.tann.dice.gameplay.effect.eff.keyword.KeywordCombineType;
import com.tann.dice.gameplay.effect.targetable.Targetable;
import com.tann.dice.gameplay.fightLog.EntSideState;
import com.tann.dice.gameplay.fightLog.EntState;
import com.tann.dice.gameplay.fightLog.Snapshot;
import com.tann.dice.gameplay.trigger.personal.Personal;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.named.Exerted;
import com.tann.dice.gameplay.trigger.personal.linked.stateCondition.GenericStateCondition;
import com.tann.dice.gameplay.trigger.personal.linked.stateCondition.StateConditionType;
import com.tann.dice.gameplay.trigger.personal.merge.PetrifySide;
import com.tann.dice.gameplay.trigger.personal.merge.Poison;
import com.tann.dice.gameplay.trigger.personal.merge.Regen;
import com.tann.dice.gameplay.trigger.personal.replaceSides.Decay;
import com.tann.dice.screens.shaderFx.DeathType;
import com.tann.dice.util.Colours;
import yourmod.TrueNameUtils;
import yourmod.buffs.Overburn;
import yourmod.buffs.Serenity;
import yourmod.conditionalBonus.*;
import yourmod.keywords.EntKeywordUsable;
import yourmod.keywords.KeywordColorTagSuffix;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Keywords implements basemod.keywords.IKeywordInitializer  {
    private static int getRegen(EntState state) {
        int total = 0;

        for (Personal buff : state.getActivePersonals()) {
            if(buff instanceof Regen) {
                total += ((Regen)buff).getRegen();
            }
        }

        return total;
    }

    private static int getPetrify(EntState state) {
        int total = 0;

        for (Personal buff : state.getActivePersonals()) {
            if(buff instanceof PetrifySide) {
                total += ((PetrifySide)buff).getPetrified().size();
            }
        }

        return total;
    }

    private static int countDeathSides(EntState state) {

        try {

            int total = 0;

            for(int i = 0; i < 6; ++i) {
                EntSideState ess = new EntSideState(state, state.getEnt().getSides()[i], -1);
                Eff eff = ess.getCalculatedEffect();
                if (eff.hasKeyword(Keyword.death)) {
                    total++;
                }
            }

            return total;
        } finally {
        }
    }

    private static void setShields(EntState ent, int val) {
        try {
            Method block = EntState.class.getDeclaredMethod("block", int.class);
            block.setAccessible(true);
            block.invoke(ent, val - ent.getShields());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize() {
        ConditionalBonusTypeRegistry.registerBonus("EmptyHP", new IConditionalBonusType() {
            @Override
            public int getBonus(Snapshot s, EntState sourceState, EntState targetState, int bonusAmount, int value, Eff eff) {
                return sourceState.getMissingHp();
            }
        });
        ConditionalBonusTypeRegistry.registerBonus("MaxHP", new IConditionalBonusType() {
            @Override
            public int getBonus(Snapshot s, EntState sourceState, EntState targetState, int bonusAmount, int value, Eff eff) {
                return sourceState.getMaxHp();
            }
        });
        ConditionalBonusTypeRegistry.registerBonus("Regen", new IConditionalBonusType() {
            @Override
            public int getBonus(Snapshot s, EntState sourceState, EntState targetState, int bonusAmount, int value, Eff eff) {

                return getRegen(sourceState);
            }
        });
        ConditionalBonusTypeRegistry.registerBonus("TotalRegen", new IConditionalBonusType() {
            @Override
            public int getBonus(Snapshot s, EntState sourceState, EntState targetState, int bonusAmount, int value, Eff eff) {
                int total = 0;

                for (EntState i : s.getStates((Boolean)null, false)) {
                    total += getRegen(i);
                }

                return total;
            }
        });
        ConditionalBonusTypeRegistry.registerBonus("Bones", new IConditionalBonusType() {
            @Override
            public int getBonus(Snapshot s, EntState sourceState, EntState targetState, int bonusAmount, int value, Eff eff) {
                int total = 0;

                for (EntState i : s.getStates((Boolean)null, false)) {
                    if(TrueNameUtils.getTrueName(i.getEnt()).toLowerCase().contains("bones")) {
                        total++;
                    }
                }

                return total;
            }
        });
        ConditionalBonusTypeRegistry.registerBonus("Revenants", new IConditionalBonusType() {
            @Override
            public int getBonus(Snapshot s, EntState sourceState, EntState targetState, int bonusAmount, int value, Eff eff) {
                int total = 0;

                for (EntState i : s.getStates((Boolean)null, false)) {
                    if (i.isPlayer() && ((Hero)i.getEnt()).isDiedLastRound()) {
                        total++;
                    }
                }

                return total;
            }
        });
        ConditionalBonusTypeRegistry.registerBonus("LimitMana", new IConditionalBonusType() {
            @Override
            public int getBonus(Snapshot s, EntState sourceState, EntState targetState, int bonusAmount, int value, Eff eff) {
                int total = ConditionalBonusType.CurrentMana.getBonus(s, sourceState, targetState,bonusAmount,value,eff);

                if(value > total) {
                    return total - value;
                }

                return 0;
            }
        });
        ConditionalBonusTypeRegistry.registerBonus("LimitHP", new IConditionalBonusType() {
            @Override
            public int getBonus(Snapshot s, EntState sourceState, EntState targetState, int bonusAmount, int value, Eff eff) {
                int total = ConditionalBonusType.CurrentHP.getBonus(s, sourceState, targetState,bonusAmount,value,eff);

                if(value > total) {
                    return total - value;
                }

                return 0;
            }
        });
        ConditionalBonusTypeRegistry.registerBonus("LimitMyTier", new IConditionalBonusType() {
            @Override
            public int getBonus(Snapshot s, EntState sourceState, EntState targetState, int bonusAmount, int value, Eff eff) {
                int total = ConditionalBonusType.MyTier.getBonus(s, sourceState, targetState,bonusAmount,value,eff);

                if(value > total) {
                    return total - value;
                }

                return 0;
            }
        });
        ConditionalBonusTypeRegistry.registerBonus("AllyShields", new IConditionalBonusType() {
            @Override
            public int getBonus(Snapshot s, EntState sourceState, EntState targetState, int bonusAmount, int value, Eff eff) {
                int total = 0;

                for (EntState ent : s.getStates(sourceState.isPlayer(), false)) {
                    total += ent.getShields();
                }
                return total;
            }
        });
        ConditionalBonusTypeRegistry.registerBonus("Petrify", new IConditionalBonusType() {
            @Override
            public int getBonus(Snapshot s, EntState sourceState, EntState targetState, int bonusAmount, int value, Eff eff) {

                return getPetrify(sourceState);
            }
        });


        //KeywordRegistry.registerBasic("antiGuilt", Colours.orange, "if this is not lethal, I die", null, KeywordAllowType.DEATHCHECK);
        //KeywordRegistry.registerBasic("good", Colours.light, "if this does not save a hero, I die", null, KeywordAllowType.DEATHCHECK);

        KeywordRegistry.registerSelf("selfPermaBoost", new LazyKeyword("permaBoost"));
        KeywordRegistry.registerSelf("selfVitality", new LazyKeyword("vitality"));
        KeywordRegistry.registerSelf("selfWither", new LazyKeyword("wither"));

        KeywordRegistry.registerBasic("hyperExert", Colours.purple, "replace all sides with blanks until the end of the "+ KUtils.describeN()+"th turn after this one", null, KeywordAllowType.NONBLANK);
        KeywordRegistry.onAfterUseEffects.register("hyperExert", new IOnAfterUseEffect() {
            @Override
            public void activate(int kVal, Eff src, List<Keyword> keywords, Snapshot snapshot, Ent source, int sideIndex) {
                EntState sourceState = snapshot.getState(source);
                Buff b = new Buff(kVal, new Exerted(sourceState.getEnt().getSize()));
                b.skipFirstTick();
                sourceState.addBuff(b);
            }
        });


        KeywordRegistry.registerBasic("hyperDecay", Colours.purple, "gets " + KUtils.describeN(false) + " " + KUtils.describeThisFight() + " after use", null, KeywordAllowType.PIPS_ONLY);

        KeywordRegistry.onAfterUseEffects.register("hyperDecay", new IOnAfterUseEffect() {
            @Override
            public void activate(int kVal, Eff src, List<Keyword> keywords, Snapshot snapshot, Ent source, int sideIndex) {
                EntState sourceState = snapshot.getState(source);
                sourceState.addBuff(new Buff(new Decay(sideIndex, -kVal)));
            }
        });

        KeywordRegistry.registerBasic("break", Colours.grey, "removes " + KUtils.describeN() + " shields", null, KeywordAllowType.UNKIND_TARG);
        KeywordRegistry.registerSelf("selfBreak", new LazyKeyword("break"));

        KeywordRegistry.onUseEffects.register("break", new IOnUseEffect() {
            @Override
            public void activate(EntState ent, int kVal, Eff eff, List<Keyword> keywords, Ent source, Targetable targetable) {
                setShields(ent, Math.max(0, ent.getShields() - kVal));
            }
        });

        KeywordRegistry.registerBasic("serenity", Colours.light, "+" + KUtils.describeN() + " to incoming healing", null, KeywordAllowType.KIND_TARG_PIPS);
        KeywordRegistry.registerSelf("selfSerenity", new LazyKeyword("serenity"));

        KeywordRegistry.onUseEffects.register("serenity", new IOnUseEffect() {
            @Override
            public void activate(EntState ent, int kVal, Eff eff, List<Keyword> keywords, Ent source, Targetable targetable) {
                ent.addBuff(new Buff(new Serenity(kVal)));
            }
        });

        KeywordRegistry.registerBasic("overburn", Colours.blue, "also inflicts " + KUtils.describeN() + " [blue]overburn[cu]", "When [blue]overburn[cu] is equal to or greater than my hp, me and all my allies take damage equal to my [blue]overburn[cu]", KeywordAllowType.UNKIND_TARG_PIPS);
        KeywordRegistry.registerSelf("selfOverburn", new LazyKeyword("overburn"));

        KeywordRegistry.onUseEffects.register("overburn", new IOnUseEffect() {
            @Override
            public void activate(EntState ent, int kVal, Eff eff, List<Keyword> keywords, Ent source, Targetable targetable) {
                ent.addBuff(new Buff(new Overburn(kVal)));

                int total = 0;
                for (Personal buff : ent.getActivePersonals()) {
                    if(buff instanceof Overburn) {
                        Overburn overburn = (Overburn) buff;
                        total += overburn.getValue();
                    }
                }

                if(total >= ent.getHp()) {
                    ent.addBuff(new Buff(new Overburn(-total)));

                    List<EntState> entList = ent.getSnapshot().getAliveEntStates(ent.isPlayer());
                    for (EntState other : entList) {
                        other.hit((new EffBill()).damage(total).visual(VisualEffectType.Flame).bEff(), (Ent)null);
                    }
                }
            }
        });

        KeywordColorTagSuffix slayer = new KeywordColorTagSuffix("slayer", Colours.light);
        KeywordColorTagSuffix bane = new KeywordColorTagSuffix("bane", Colours.grey);
        KeywordColorTagSuffix cost = new KeywordColorTagSuffix("cost", Colours.purple);
        KeywordColorTagSuffix limit = new KeywordColorTagSuffix("limit", Colours.grey);

        KeywordRegistry.registerBasic("boneslayer", Colours.light, "instantly kills bones", null, KeywordAllowType.UNKIND_TARG);
        KeywordRegistry.onUseEffects.register("boneslayer", new IOnUseEffect() {
            @Override
            public void activate(EntState ent, int kVal, Eff eff, List<Keyword> keywords, Ent source, Targetable targetable) {
                if(TrueNameUtils.getTrueName(ent.getEnt()).toLowerCase().contains("bones")) {
                    ent.kill(DeathType.Singularity);
                }
            }
        });
        KeywordRegistry.colorTagRegistry.register("boneslayer", slayer);

        KeywordRegistry.registerConditionalBonus("bonebane", Colours.light, "x2 if targetting a bones", "", new LazyS<>(() -> new ConditionalBonus(new BaneConditionalRequirement("bones", true))), null);
        KeywordRegistry.registerConditionalBonus("hydrabane", Colours.green, "x2 if targetting a hydra", "", new LazyS<>(() -> new ConditionalBonus(new BaneConditionalRequirement("hydra", true))), null);
        KeywordRegistry.registerConditionalBonus("dragonbane", Colours.red, "x2 if targetting a dragon", "", new LazyS<>(() -> new ConditionalBonus(new BaneConditionalRequirement("dragon", true))), null);

        KeywordRegistry.colorTagRegistry.register("bonebane", bane);
        KeywordRegistry.colorTagRegistry.register("hydrabane", bane);
        KeywordRegistry.colorTagRegistry.register("dragonbane", bane);

        KeywordRegistry.registerConditionalRequirement("revenant", Colours.yellow, "x2 if I died last fight", "", new LazyS<>(() -> new GSCConditionalRequirement(new GenericStateCondition(StateConditionType.DiedLasFight), true)));
        KeywordRegistry.registerConditionalBonus("bonemancy", Colours.light, KUtils.describePipBonus("bones on the field"), "",  new LazyS<>(() -> new ConditionalBonus(ConditionalBonusType.valueOf("Bones"))),null);
        KeywordRegistry.registerConditionalBonus("necromancy", Colours.purple, KUtils.describePipBonus("hero that died last fight"), "",  new LazyS<>(() -> new ConditionalBonus(ConditionalBonusType.valueOf("Revenants"))),null);

        KeywordRegistry.registerBasic("poisoncost", Colours.green, "costs " + KUtils.describeN() + " poison", null, KeywordAllowType.PIPS_ONLY);
        KeywordRegistry.registerBasic("hpcost", Colours.red, "costs " + KUtils.describeN() + " hp", null, KeywordAllowType.PIPS_ONLY);
        KeywordRegistry.registerBasic("shieldcost", Colours.grey, "costs " + KUtils.describeN() + " shields", null, KeywordAllowType.PIPS_ONLY);

        KeywordRegistry.colorTagRegistry.register("poisoncost", cost);
        KeywordRegistry.colorTagRegistry.register("hpcost", cost);
        KeywordRegistry.colorTagRegistry.register("shieldcost", cost);

        KeywordRegistry.onAfterUseEffects.register("poisoncost", new IOnAfterUseEffect() {
            @Override
            public void activate(int kVal, Eff src, List<Keyword> keywords, Snapshot snapshot, Ent source, int sideIndex) {
                EntState state = snapshot.getState(source);
                state.addBuff(new Buff(new Poison(-kVal)));
                try {
                    Field buffsField = EntState.class.getDeclaredField("buffs");
                    buffsField.setAccessible(true);
                    List<Buff> buffs = (List<Buff>)buffsField.get(state);
                    for(int i = buffs.size() - 1; i >= 0; --i) {
                        Buff b = (Buff)buffs.get(i);
                        if(b.personal instanceof Poison) {
                            Poison poison = (Poison) b.personal;

                            if(poison.getPoisonDamage() == 0) {
                                buffs.remove(i);
                            }
                        }
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        KeywordRegistry.keywordUsableRegistry.register("poisoncost", new EntKeywordUsable() {
            @Override
            public boolean isUsable(EntState state, Eff first) {
                return state.getBasePoisonPerTurn() >= KUtils.getValue(first);
            }

            @Override
            public String getInvalidTargetReason(EntState state, Eff first, boolean allowBadTargets) {
                return "Not enough poison";
            }
        });
        KeywordRegistry.onAfterUseEffects.register("hpcost", new IOnAfterUseEffect() {
            @Override
            public void activate(int kVal, Eff src, List<Keyword> keywords, Snapshot snapshot, Ent source, int sideIndex) {
                EntState state = snapshot.getState(source);
                state.takePain(kVal);
            }
        });
        KeywordRegistry.keywordUsableRegistry.register("hpcost", new EntKeywordUsable() {
            @Override
            public boolean isUsable(EntState state, Eff first) {
                return state.getHp() >= KUtils.getValue(first);
            }

            @Override
            public String getInvalidTargetReason(EntState state, Eff first, boolean allowBadTargets) {
                return "Not enough hp";
            }
        });
        KeywordRegistry.onAfterUseEffects.register("shieldcost", new IOnAfterUseEffect() {
            @Override
            public void activate(int kVal, Eff src, List<Keyword> keywords, Snapshot snapshot, Ent source, int sideIndex) {
                EntState state = snapshot.getState(source);
                setShields(state, Math.max(0, state.getShields() - kVal));
            }
        });
        KeywordRegistry.keywordUsableRegistry.register("shieldcost", new EntKeywordUsable() {
            @Override
            public boolean isUsable(EntState state, Eff first) {
                return state.getShields() >= KUtils.getValue(first);
            }

            @Override
            public String getInvalidTargetReason(EntState state, Eff first, boolean allowBadTargets) {
                return "Not enough shields";
            }
        });


        KeywordRegistry.registerEnumMeta("engue", new LazyKeyword("engage"), new LazyKeyword("plague"), KeywordCombineType.ConditionBonus);
        KeywordRegistry.registerEnumMeta("reborigil", new LazyKeyword("reborn"), new LazyKeyword("vigil"), KeywordCombineType.ConditionBonus);
        KeywordRegistry.registerEnumMeta("patiera", new LazyKeyword("patient"), new LazyKeyword("era"), KeywordCombineType.ConditionBonus);

        KeywordRegistry.registerConditionalBonus("corrosive", Colours.green, KUtils.describePipBonus("poison on target"), "", new LazyS<>(() -> new ConditionalBonus(new PostCalcConditionalRequirement(EnumConditionalRequirement.Always), ConditionalBonusType.TheirPoison, 1)),null);

        KeywordRegistry.registerMinus("minusCharged", new LazyKeyword("charged"));

        KeywordRegistry.registerConditionalBonus("short", Colours.orange,"x2 vs the bottommost target", "", new LazyS<>(() -> new ConditionalBonus(new BottomConditionalRequirement())),null);
        KeywordRegistry.registerConditionalBonus("toxic", Colours.green,"x2 vs poisoned target", "", new LazyS<>(() -> new ConditionalBonus(new PoisonConditionalRequirement(true))),null);
        KeywordRegistry.registerConditionalBonus("swapToxic", Colours.green,"x2 if I am poisoned", "", new LazyS<>(() -> new ConditionalBonus(new PoisonConditionalRequirement(false))),keyword -> {
            try {
                Field field = Keyword.class.getDeclaredField("metaKeyword");
                field.setAccessible(true);
                LazyKeyword toxic = new LazyKeyword("toxic");
                field.set(keyword, toxic.get());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        KeywordRegistry.registerConditionalBonus("hungry", Colours.purple, KUtils.describePipBonus("empty hp I have"), "",  new LazyS<>(() -> new ConditionalBonus(ConditionalBonusType.valueOf("EmptyHP"))),null);
        KeywordRegistry.registerMinus("minusHungry", new LazyKeyword("hungry"));
        KeywordRegistry.registerConditionalBonus("powerful", Colours.red, KUtils.describePipBonus("max hp I have"), "",  new LazyS<>(() -> new ConditionalBonus(ConditionalBonusType.valueOf("MaxHP"))),null);
        KeywordRegistry.registerConditionalBonus("soothing", Colours.red, KUtils.describePipBonus("regen on me"), "",  new LazyS<>(() -> new ConditionalBonus(ConditionalBonusType.valueOf("Regen"))),null);
        KeywordRegistry.registerConditionalBonus("halcyon", Colours.red, KUtils.describePipBonus("regen on all characters"), "",  new LazyS<>(() -> new ConditionalBonus(ConditionalBonusType.valueOf("TotalRegen"))),null);
        KeywordRegistry.registerConditionalBonus("rampart", Colours.grey, KUtils.describePipBonus("shield on all allies"), "",  new LazyS<>(() -> new ConditionalBonus(ConditionalBonusType.valueOf("AllyShields"))),null);
        KeywordRegistry.registerConditionalBonus("marble", Colours.light, KUtils.describePipBonus("petrified side"), "",  new LazyS<>(() -> new ConditionalBonus(ConditionalBonusType.valueOf("Petrify"))),null);
        KeywordRegistry.registerConditionalBonus("chargelimit", Colours.blue, "pips are limited to stored mana", "",  new LazyS<>(() -> new ConditionalBonus(ConditionalBonusType.valueOf("LimitMana"))),null);
        KeywordRegistry.registerConditionalBonus("fleshlimit", Colours.red, "pips are limited to my hp", "",  new LazyS<>(() -> new ConditionalBonus(ConditionalBonusType.valueOf("LimitHP"))),null);
        KeywordRegistry.registerConditionalBonus("skilllimit", Colours.light, "pips are limited to my level", "",  new LazyS<>(() -> new ConditionalBonus(ConditionalBonusType.valueOf("LimitMyTier"))),null);
        //KeywordRegistry.registerConditionalBonus("deathrite", Colours.light, KUtils.describePipBonus("death side on all characters"), "",  new LazyS<>(() -> new ConditionalBonus(ConditionalBonusType.valueOf("SidesDeath"))), null);
        KeywordRegistry.registerGroup("groupDeath", new LazyKeyword("death"), false);
        //KeywordRegistry.registerConditionalBonus("halve", Colours.blue, "halves", "",  new LazyS<>(() -> new ConditionalBonus(EnumConditionalRequirement.Always, ConditionalBonusType.Divide, 2)));

        KeywordRegistry.colorTagRegistry.register("chargelimit", limit);
        KeywordRegistry.colorTagRegistry.register("fleshlimit", limit);
        KeywordRegistry.colorTagRegistry.register("skilllimit", limit);


        KeywordRegistry.registerMinus("minusPowerful", new LazyKeyword("powerful"));

    }
}
