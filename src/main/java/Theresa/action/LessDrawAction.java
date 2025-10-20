package Theresa.action;

import Theresa.patch.DustPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;

public class LessDrawAction extends AbstractGameAction {
    public LessDrawAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        if(!DustPatch.dustManager.isFull())
            addToTop(new DrawCardAction(this.amount));
        this.isDone = true;
    }
}
