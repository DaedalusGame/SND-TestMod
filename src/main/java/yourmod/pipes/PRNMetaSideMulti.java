package yourmod.pipes;

import com.badlogic.gdx.graphics.Color;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.util.Colours;

public class PRNMetaSideMulti extends PRNPart {
    public PRNMetaSideMulti() {
    }

    public String regex() {
        return "((?:[^\\d-]+(?:-\\d+){0,1}\\:)*[^\\d-]+(?:-\\d+){0,1})";
    }

    protected String describe() {
        String s1 = ":";
        String s2 = "-";
        return "[green]#[light]" + s2 + "[green]#[light]" + s1 + "[grey]...";
    }

    protected Color getColour() {
        return Colours.yellow;
    }
}
