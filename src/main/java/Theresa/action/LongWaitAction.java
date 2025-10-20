package Theresa.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class LongWaitAction extends AbstractGameAction {
    public LongWaitAction(float duration) {
        this.duration = duration;
        actionType = ActionType.WAIT;
    }

    @Override
    public void update() {
        this.tickDuration();
    }
}
