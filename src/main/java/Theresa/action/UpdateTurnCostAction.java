package Theresa.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

public class UpdateTurnCostAction extends AbstractGameAction {
    public UpdateTurnCostAction(AbstractCard c, CardGroup group, int amount) {
        this.c = c;
        this.group = group;
        this.amount = amount;
    }

    @Override
    public void update() {
        if(group.contains(c)){
            c.setCostForTurn(c.costForTurn+amount);
        }

        this.isDone = true;
    }

    AbstractCard c;
    CardGroup group;
}
