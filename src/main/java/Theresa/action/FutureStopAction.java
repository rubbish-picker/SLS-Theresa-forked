package Theresa.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class FutureStopAction extends AbstractGameAction {
    public FutureStopAction(AbstractCard card) {
        this.c = card;
        this.actionType = ActionType.WAIT;
        startDuration = duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if(duration == startDuration) {
            AbstractPlayer p = AbstractDungeon.player;
            p.hand.refreshHandLayout();
            ArrayList<AbstractCard> cards = new ArrayList<>(p.hand.group);
            int index = 0;
            while(p.hand.size()<10 && index<cards.size()-1) {
                AbstractCard theHand = cards.get(index);
                AbstractCard tmp = c.makeStatEquivalentCopy();
                tmp.current_x = theHand.target_x;
                tmp.current_y = theHand.target_y + 400F * Settings.scale;

                int actualIndex = p.hand.group.indexOf(theHand);
                p.hand.group.add(actualIndex+1,tmp);
                CardCrawlGame.sound.play("CARD_OBTAIN");
                index++;
                p.hand.refreshHandLayout();
            }

//            cards.clear();
//            cards.addAll(p.drawPile.group);
//            index = 0;
//            while(index<cards.size()-1) {
//                AbstractCard theDraw = cards.get(index);
//                AbstractCard tmp = c.makeStatEquivalentCopy();
//                int actualIndex = p.drawPile.group.indexOf(theDraw);
//                p.drawPile.group.add(actualIndex+1,tmp);
//                index++;
//            }
        }
        this.tickDuration();
    }

    AbstractCard c;
}
