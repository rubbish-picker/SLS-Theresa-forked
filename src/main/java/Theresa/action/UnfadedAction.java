package Theresa.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class UnfadedAction extends AbstractGameAction {
    public UnfadedAction(AbstractCard c) {
        this.c = c;
    }

    @Override
    public void update() {
        c.unfadeOut();
        this.isDone = true;
    }

    AbstractCard c;
}
