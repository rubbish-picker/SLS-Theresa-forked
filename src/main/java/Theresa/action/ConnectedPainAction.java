package Theresa.action;

import Theresa.helper.StringHelper;
import Theresa.patch.SilkPatch;
import Theresa.silk.AbstractSilk;
import Theresa.silk.MindSilk;
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
    ArrayList<AbstractCard> realTmp;

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
            realTmp = new ArrayList<>();
            for(int i =0; i<this.amount; i++){
                realTmp.add(tmp.get(i));
            }
            CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            tmpGroup.group = realTmp;
            AbstractDungeon.gridSelectScreen.open(tmpGroup,amount,true, StringHelper.OPERATION.TEXT[7] + amount + StringHelper.OPERATION.TEXT[8]);
        }
        else if(!selected){
            selected = true;
            for(AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards){
                realTmp.remove(c);
                AbstractSilk silk = new NormalSilk();
                SilkPatch.setSilk(c,silk);
                silk.applyPowers();
            }
            for(AbstractCard c : realTmp){
                AbstractSilk silk = new MindSilk();
                SilkPatch.setSilk(c,silk);
                silk.applyPowers();
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }

        this.tickDuration();
    }
}
