package Theresa.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class IncreaseMagicNumberAction extends AbstractGameAction {
    public IncreaseMagicNumberAction(AbstractCard c,int amount) {
        this.amount = amount;
        this.c = c;
    }

    AbstractCard c;

    @Override
    public void update() {
        c.baseMagicNumber+=amount;
        c.magicNumber=c.baseMagicNumber;
        c.isMagicNumberModified = false;
        isDone = true;
    }
}
