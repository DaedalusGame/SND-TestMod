package yourmod.screen;

import basemod.ledger.ILedgerPageType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.tann.dice.Main;
import com.tann.dice.gameplay.content.ent.type.EntType;
import com.tann.dice.gameplay.content.gen.pipe.Pipe;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.effect.eff.keyword.KUtils;
import com.tann.dice.gameplay.effect.eff.keyword.Keyword;
import com.tann.dice.gameplay.fightLog.EntState;
import com.tann.dice.gameplay.mode.creative.pastey.PasteMode;
import com.tann.dice.gameplay.phase.levelEndPhase.rewardPhase.decisionPhase.choice.choosable.Choosable;
import com.tann.dice.gameplay.progress.chievo.unlock.Unlockable;
import com.tann.dice.gameplay.save.settings.option.OptionLib;
import com.tann.dice.screens.dungeon.panels.Explanel.EntPanelInventory;
import com.tann.dice.screens.dungeon.panels.book.page.ledgerPage.LedgerPage;
import com.tann.dice.screens.dungeon.panels.book.page.ledgerPage.LedgerUtils;
import com.tann.dice.statics.Images;
import com.tann.dice.statics.sound.Sounds;
import com.tann.dice.util.*;
import com.tann.dice.util.listener.TannListener;
import com.tann.dice.util.tp.TP;
import com.tann.dice.util.tp.TTP;
import com.tann.dice.util.ui.ClipboardUtils;
import com.tann.dice.util.ui.ScrollPane;
import com.tann.dice.util.ui.choice.ChoiceDialog;
import com.tann.dice.util.ui.resolver.MetaResolver;
import com.tann.dice.util.ui.resolver.Resolver;
import com.tann.dice.util.ui.standardButton.StandardButton;
import yourmod.Mod;

import java.util.ArrayList;
import java.util.List;

public class MetaPageType implements ILedgerPageType {
    @Override
    public String getName(LedgerPage.LedgerPageType type) {
        return type.name();
    }

    private static void attemptToAddPin(Object o, List<String> pinnedStrings, int contentWidth, LedgerPage ledgerPage) {
        if (o instanceof EntType) {
            addPin(((EntType)o).getName(), pinnedStrings);
        } else if (o instanceof Unlockable && o instanceof Choosable) {
            Choosable c = (Choosable)o;
            addPin(c.getName(), pinnedStrings);
        } else {
            if (!(o instanceof Keyword)) {
                return;
            }

            addPin(((Keyword)o).getName(), pinnedStrings);
        }

        Sounds.playSound(Sounds.pipSmall);
        ledgerPage.showThing(getGroup(contentWidth, ledgerPage, pinnedStrings));
    }

    private static void clearPins(List<String> pinnedStrings) {
        pinnedStrings.clear();
    }

    private static boolean addPin(String name, List<String> pinnedStrings) {
        pinnedStrings.remove(name);
        pinnedStrings.add(0, name);
        return true;
    }

    @Override
    public Actor getActor(int contentWidth, LedgerPage ledgerPage) {
        return getGroup(contentWidth, ledgerPage, null);
    }

    private static Group getGroup(int contentWidth, LedgerPage ledgerPage, List<String> strings) {
        List<String> pinnedStrings;
        if(strings == null) {
            pinnedStrings = Mod.metaStorage.getPins();
        } else {
            pinnedStrings = strings;
            Mod.metaStorage.setPins(pinnedStrings);
        }

        Pixl p = new Pixl(3);
        List<Actor> buttons = new ArrayList<Actor>();

        MetaResolver resolver = new MetaResolver() {
            @Override
            public void resolve(Object o) {
                attemptToAddPin(o, pinnedStrings, contentWidth, ledgerPage);
            }
        };

        StandardButton name = new StandardButton("[grey]name");
        name.setRunnable(new Runnable() {
            @Override
            public void run() {
                Sounds.playSound(Sounds.pipSmall);
                resolver.activate();
            }
        });
        StandardButton paste = new StandardButton("[purple]paste");
        paste.setRunnable(new Runnable() {
            public void run() {
                Pipe.setupChecks();
                this.doInput();
                Pipe.disableChecks();
            }

            private void doInput() {
                String contents = ClipboardUtils.pasteSafer();
                if (contents == null) {
                    Sounds.playSound(Sounds.pipSmall);
                    Main.getCurrentScreen().showDialog("invalid clipboard text", Colours.red);
                } else if (!contents.startsWith("=")) {
                    String genericError = PasteMode.getPasteErrorGeneric(contents);
                    if (genericError != null) {
                        Main.getCurrentScreen().showDialog(genericError, Colours.red);
                    } else {
                        boolean success = resolver.debugResolve(contents);
                        if (!success) {
                            Sounds.playSound(Sounds.pipSmall);
                            Main.getCurrentScreen().showDialog("failed to find", Colours.red);
                        }

                    }
                } else {
                    contents = contents.substring(1);
                    String[] modNames = contents.split(",");
                    int amt = pinnedStrings.size();
                    for (String modName : modNames) {
                        resolver.debugResolve(modName);
                    }

                    int added = pinnedStrings.size() - amt;
                    Main.getCurrentScreen().showDialog("added " + added, Colours.blue);
                }
            }
        });
        StandardButton clear = new StandardButton("[red]clear");
        clear.setRunnable(new Runnable() {
            @Override
            public void run() {
                ChoiceDialog cd = new ChoiceDialog("[purple]Clear " + pinnedStrings.size() + " pins?", ChoiceDialog.ChoiceNames.PurpleYes, new Runnable() {
                    public void run() {
                        clearPins(pinnedStrings);
                        ledgerPage.showThing(getGroup(contentWidth, ledgerPage, pinnedStrings));
                        Sounds.playSound(Sounds.magic);
                        Main.getCurrentScreen().popSingleMedium();
                    }
                }, new Runnable() {
                    public void run() {
                        Sounds.playSound(Sounds.pop);
                        Main.getCurrentScreen().popSingleMedium();
                    }
                });
                Main.getCurrentScreen().push(cd, 0.8F);
                Tann.center(cd);
                Sounds.playSound(Sounds.pipSmall);
            }
        });

        buttons.add(name);
        buttons.add(paste);
        buttons.add(clear);

        p.listActor((int)(contentWidth * 0.9F), buttons);
        Pixl extraP = new Pixl(2);

        final List<TP<String, Actor>> pinnedElements = new ArrayList<>();

        MetaResolver mr = new MetaResolver() {
            public void resolve(Object o) {
                if (o instanceof EntType) {
                    EntType et = (EntType) o;
                    pinnedElements.add(new TP(et.getName(), (new EntPanelInventory(et.makeEnt())).withoutDice().getFullActor()));
                } else if (o instanceof Unlockable && o instanceof Choosable) {
                    Unlockable u = (Unlockable)o;
                    Choosable c = (Choosable)o;
                    Actor tst = u.makeUnlockActor(true);
                    boolean hasScroll = false;
                    if (tst instanceof Group) {
                        Group gtst = (Group)tst;
                        hasScroll = TannStageUtils.hasActor(gtst, ScrollPane.class);
                    }

                    float wThresh = 0.6F;
                    float hThresh = 0.4F;
                    boolean tooBig = tst.getWidth() > (float)Main.width * wThresh || tst.getHeight() > (float)Main.height * hThresh;
                    if (tooBig || hasScroll) {
                        tst = u.makeUnlockActor(false);
                    }

                    pinnedElements.add(new TP(c.getName(), tst));
                } else if (o instanceof Keyword) {
                    Keyword k = (Keyword)o;
                    pinnedElements.add(new TP(k.getName(), KUtils.makeActor(k, null)));
                }

            }
        };

        List<String> failed = new ArrayList();
        for (String pinnedString : pinnedStrings) {
            Pipe.setupChecks();
            boolean success = mr.debugResolve(pinnedString);
            Pipe.disableChecks();
            if (!success) {
                failed.add(pinnedString);
            }
        }
        pinnedStrings.removeAll(failed);

        for (TP<String, Actor> pinnedElement : pinnedElements) {
            Pixl pp = new Pixl();
            Actor closeButt = new ImageActor(Images.tut_close);
            closeButt.addListener(new TannListener() {
                public boolean action(int button, int pointer, float x, float y) {
                    Sounds.playSound(Sounds.pipSmall);
                    pinnedStrings.remove(pinnedElement.a);
                    ledgerPage.showThing(getGroup(contentWidth, ledgerPage, pinnedStrings));
                    return true;
                }
            });
            pp.actor(closeButt);
            Actor a = pp.row(-1).actor(pinnedElement.b).pix();
            extraP.actor(a, contentWidth * 0.85F);
        }

        p.row(3).actor(extraP.pix(2));
        p.row(3).actor(OptionLib.SHOW_COPY.makeFullDescribedUnlockActor());

        return p.pix();
    }
}
