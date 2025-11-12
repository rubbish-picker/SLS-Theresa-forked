package Theresa.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EternalAction extends AbstractGameAction {
    public EternalAction(AbstractCard c) {
        this.c = c;
    }

    @Override
    public void update() {
        AbstractCard cardToRemove = null;
        for(AbstractCard tmp : AbstractDungeon.player.masterDeck.group) {
            if(tmp.cardID == c.cardID && tmp.uuid == c.uuid) {
                cardToRemove = tmp;
                break;
            }
        }
        if(cardToRemove != null) {
            AbstractDungeon.player.masterDeck.removeCard(cardToRemove);
        }

        this.isDone = true;
    }

    AbstractCard c;
}
