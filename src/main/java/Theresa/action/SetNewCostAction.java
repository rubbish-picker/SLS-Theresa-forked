package Theresa.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class SetNewCostAction extends AbstractGameAction {
    public SetNewCostAction(AbstractCard target, int costDiff) {
        this.c = target;
        this.amount = costDiff;
    }

    @Override
    public void update() {
        if(c!=null && c.cost>=0){
            c.updateCost(amount);
            c.flash();
        }

        this.isDone = true;
    }

    AbstractCard c;
}
