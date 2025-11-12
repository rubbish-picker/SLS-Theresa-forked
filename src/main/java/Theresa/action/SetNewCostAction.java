package Theresa.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class SetNewCostAction extends AbstractGameAction {
    public SetNewCostAction(AbstractCard target, int costDiff) {
        this.c = target;
        this.amount = costDiff;
    }

    public SetNewCostAction forTurn(){
        this.isForTurn = true;
        return this;
    }

    boolean isForTurn = false;

    @Override
    public void update() {
        if(c!=null && c.cost>=0){
            if(!isForTurn)
                c.updateCost(amount);
            else {
                int tmp = c.costForTurn+amount;
                if(tmp<0)
                    tmp = 0;
                c.setCostForTurn(tmp);
            }
            c.flash();
        }

        this.isDone = true;
    }

    AbstractCard c;
}
