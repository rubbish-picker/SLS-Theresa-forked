package Theresa.action;

import Theresa.patch.DustPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class InterAction extends AbstractGameAction {
    public InterAction(AbstractCard c) {
        this.c = c;
        startDuration = duration = Settings.ACTION_DUR_XFAST;
    }

    AbstractCard c;

    @Override
    public void update() {
        if(duration == startDuration) {
            AbstractPlayer p = AbstractDungeon.player;
            if(p.hand.contains(c) && !DustPatch.dustManager.dustCards.isEmpty()){
                AbstractCard dustToExchange = null;
                for(AbstractCard tC : DustPatch.dustManager.dustCards){
                    if(dustToExchange==null || dustToExchange.costForTurn>tC.costForTurn){
                        dustToExchange = tC;
                    }
                }
                if(dustToExchange!=null && dustToExchange.costForTurn<c.costForTurn){
                    //交换成功
                    int index = p.hand.group.indexOf(c);
                    int index2 = DustPatch.dustManager.dustCards.indexOf(dustToExchange);
                    if(c.cost>0){
                        c.updateCost(-1);
                    }
                    p.hand.removeCard(c);
                    DustPatch.dustManager.removeCard(dustToExchange);
                    p.hand.group.add(index, dustToExchange);
                    DustPatch.dustManager.addCard(c,index2);
                    p.hand.refreshHandLayout();
                    c.flash();
                    dustToExchange.flash();
                }
            }
        }
        this.tickDuration();
    }
}
