package yourmod.pipes;

import com.badlogic.gdx.graphics.Color;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.util.Colours;

public class PRNMetaSide extends PRNPart {
    public PRNMetaSide() {
    }

    public String regex() {
        return "([^\\d-]+(-\\d+){0,1})";
    }

    protected String describe() {
        String s2 = "-";
        return "[green]#[light]" + s2 + "[green]#[light]";
    }

    protected Color getColour() {
        return Colours.yellow;
    }
}
