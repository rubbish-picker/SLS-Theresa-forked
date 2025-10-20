package Theresa.action;

import Theresa.card.skill.PastCallFuture;
import Theresa.patch.SilkPatch;
import Theresa.power.buff.SilkPower;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PastCallFutureAction extends AbstractGameAction {
    public PastCallFutureAction(PastCallFuture ps){
        this.ps = ps;
    }

    @Override
    public void update() {
        boolean shouldExhaust = true;
        int draw = 0;
        for(AbstractCard c: AbstractDungeon.player.hand.group){
            if(SilkPatch.SilkCardField.silk.get(c) == null){
                shouldExhaust = false;
                draw++;
            }
        }
        addToTop(new DrawCardAction(draw));
        for(AbstractCard c: AbstractDungeon.player.hand.group){
            if(SilkPatch.SilkCardField.silk.get(c) == null){
                addToTop(new DiscardSpecificCardAction(c, AbstractDungeon.player.hand));
            }
        }
        if(shouldExhaust){
            for(AbstractGameAction action: AbstractDungeon.actionManager.actions){
                if(action instanceof UseCardAction){
                    AbstractCard c = ReflectionHacks.getPrivate(action, UseCardAction.class, "targetCard");
                    if(c == this.ps){
                        ((UseCardAction) action).exhaustCard = true;
                        action.actionType = ActionType.EXHAUST;
                        break;
                    }
                }
            }
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new SilkPower(AbstractDungeon.player,1),1));
        }
        this.isDone = true;
    }

    PastCallFuture ps;
}
