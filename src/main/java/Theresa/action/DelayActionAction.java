package Theresa.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class DelayActionAction extends AbstractGameAction {
    public DelayActionAction(AbstractGameAction action) {
        this.action = action;
    }

    @Override
    public void update() {
        addToBot(action);
        this.isDone = true;
    }

    AbstractGameAction action;
}
