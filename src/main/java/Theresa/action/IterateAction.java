package Theresa.action;

import Theresa.patch.DustPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;

public class IterateAction extends AbstractGameAction {
    public IterateAction(int extraDraw){
        this.amount = extraDraw;
        this.startDuration = duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.DISCARD;
    }

    @Override
    public void update() {
        if(startDuration==duration){
            ArrayList<AbstractCard> dusts = new ArrayList<>(DustPatch.dustManager.dustCards);
            for(AbstractCard card : dusts){
                DustPatch.dustManager.removeCard(card);
                CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                tmp.addToTop(card);
                tmp.moveToDiscardPile(card);
                this.amount++;
            }
            if(amount>0)
                addToTop(new DrawCardAction(amount));
        }
        this.tickDuration();
    }
}
