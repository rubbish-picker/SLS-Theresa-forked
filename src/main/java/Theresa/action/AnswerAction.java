package Theresa.action;

import Theresa.patch.DustPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;

public class AnswerAction extends AbstractGameAction {
    public AnswerAction(AbstractCreature target) {
        this.target = target;
        this.startDuration = duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.DISCARD;
    }

    @Override
    public void update() {
        if(startDuration==duration){
            ArrayList<AbstractCard> dusts = new ArrayList<>(DustPatch.dustManager.dustCards);
            ArrayList<AbstractCard> attacks = new ArrayList<>();
            for(AbstractCard card : dusts){
                if(card.type == AbstractCard.CardType.ATTACK){
                    DustPatch.dustManager.removeCard(card);
                    CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                    tmp.addToTop(card);
                    tmp.moveToDiscardPile(card);
                    attacks.add(card);
                }
            }
            for(AbstractCard card : attacks){
                AbstractCard copy = card.makeSameInstanceOf();
                addToBot(new PlayCardAction(copy,target,true));
            }

        }
        this.tickDuration();
    }
}


