package yourmod.pipes;

import com.badlogic.gdx.graphics.Color;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.util.Colours;

public class PRNTog extends PRNPart {
    public PRNTog() {
    }

    public String regex() {
        return "(tog.+)";
    }

    protected String describe() {
        return "tog*";
    }

    protected Color getColour() {
        return Colours.grey;
    }
}

