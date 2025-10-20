package Theresa.action;

import Theresa.patch.DustPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class MyCatAction extends AbstractGameAction {
    public MyCatAction(){
        this.startDuration = duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        if(startDuration==duration){
            ArrayList<AbstractCard> dusts = new ArrayList<>(DustPatch.dustManager.dustCards);
            for(AbstractCard card : dusts){
                if(AbstractDungeon.player.hand.size() >= 10)
                    break;
                DustPatch.dustManager.removeCard(card);
                CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                tmp.addToTop(card);
                tmp.moveToHand(card);
            }
        }
        this.tickDuration();
    }
}


