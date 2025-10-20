package Theresa.action;

import Theresa.card.attack.ThereItIs;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class ResetItAction extends AbstractGameAction {
    public ResetItAction(ThereItIs there) {
     this.t = there;
    }

    ThereItIs t;

    @Override
    public void update() {
        t.resetSelf();
        this.isDone = true;
    }
}
