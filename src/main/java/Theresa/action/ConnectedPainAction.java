package Theresa.action;

import Theresa.helper.StringHelper;
import Theresa.patch.SilkPatch;
import Theresa.silk.AbstractSilk;
import Theresa.silk.NormalSilk;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Collections;

public class ConnectedPainAction extends AbstractGameAction {
    public ConnectedPainAction(int amount){
        this.amount = amount;
        this.duration = startDuration = Settings.ACTION_DUR_FAST;
        actionType = ActionType.CARD_MANIPULATION;
    }

    boolean selected = false;

    @Override
    public void update() {
        if(startDuration == duration){
            ArrayList<AbstractCard> tmp = new ArrayList<>();
            for(AbstractCard c : AbstractDungeon.player.drawPile.group){
                if(SilkPatch.SilkCardField.silk.get(c) == null){
                    tmp.add(c);
                }
            }
            if(this.amount>tmp.size()){
                this.amount = tmp.size();
            }
            if(this.amount==0){
                this.isDone = true;
                return;
            }
            Collections.shuffle(tmp,AbstractDungeon.cardRandomRng.random);
            ArrayList<AbstractCard> realTmp = new ArrayList<>();
            for(int i =0; i<this.amount; i++){
                realTmp.add(tmp.get(i));
            }
            CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            tmpGroup.group = realTmp;
            AbstractDungeon.gridSelectScreen.open(tmpGroup,amount,true, StringHelper.OPERATION.TEXT[7] + amount + StringHelper.OPERATION.TEXT[8]);
        }
        else if(!selected){
            selected = true;
            int amount = 0;
            for(AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards){
                AbstractSilk silk = new NormalSilk();
                SilkPatch.setSilk(c,silk);
                silk.applyPowers();
                amount++;
            }
            addToTop(new LostHPAction(AbstractDungeon.player,AbstractDungeon.player,amount));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }

        this.tickDuration();
    }
}
