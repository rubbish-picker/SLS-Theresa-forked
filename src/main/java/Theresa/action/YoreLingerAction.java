package Theresa.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class YoreLingerAction extends AbstractGameAction {
    public YoreLingerAction(AbstractCard c,int amount){
        this.card = c;
        this.amount = amount;
    }

    @Override
    public void update() {
        if(card.baseDamage>=0){
            card.baseDamage += this.amount;
            card.damage = card.baseDamage;
        }
        if(card.baseBlock>=0){
            card.baseBlock += this.amount;
            card.block = card.baseBlock;
        }
        card.applyPowers();

        this.isDone = true;
    }

    AbstractCard card;
}
