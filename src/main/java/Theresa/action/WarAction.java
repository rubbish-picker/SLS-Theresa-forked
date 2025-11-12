package Theresa.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class WarAction extends AbstractGameAction {
    public WarAction(boolean upgraded) {
        this.i = upgraded;
    }

    boolean i;

    @Override
    public void update() {
        if(i){
            addToTop(new ReducePowerAction(AbstractDungeon.player,AbstractDungeon.player,FrailPower.POWER_ID,2));
        }
        AbstractPower weak = AbstractDungeon.player.getPower(WeakPower.POWER_ID);
        if(weak!=null){
            int amount = weak.amount;
            addToTop(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new FrailPower(AbstractDungeon.player,amount,false),amount));
            addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, WeakPower.POWER_ID));
        }
        this.isDone = true;
    }
}
