package Theresa.action;

import Theresa.patch.DustPatch;
import Theresa.patch.OtherEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
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
            ArrayList<AbstractCard> hands = new ArrayList<>(AbstractDungeon.player.hand.group);
            hands.removeIf(card -> card.hasTag(OtherEnum.Theresa_Darkness)||(card.type!= AbstractCard.CardType.ATTACK&&card.type!= AbstractCard.CardType.SKILL));
            hands.removeIf(card -> {
                for(CardQueueItem item : AbstractDungeon.actionManager.cardQueue){
                    if(item.card == card)
                        return true;
                }
                return false;
            });

            int exchangeAmount = Math.min(hands.size(), dusts.size());
            for(int i = 0; i < exchangeAmount; i++){
                AbstractCard dust = dusts.get(i);
                AbstractCard hand = hands.get(i);
                if(hand == AbstractDungeon.player.hoveredCard){
                    AbstractDungeon.player.releaseCard();
                }
                DustPatch.dustManager.removeCard(dust);
                AbstractDungeon.player.hand.removeCard(hand);
                DustPatch.dustManager.addCard(hand,i);
                AbstractDungeon.player.hand.group.add(i,dust);
            }

            AbstractDungeon.player.hand.refreshHandLayout();
        }
        this.tickDuration();
    }
}


