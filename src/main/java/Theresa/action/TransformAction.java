package Theresa.action;

import Theresa.power.buff.HatePower;
import Theresa.power.buff.HopePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TransformAction extends AbstractGameAction {
    public TransformAction(AbstractCreature target){
        this.target = target;
    }

    @Override
    public void update() {
        AbstractPower hope = this.target.getPower(HopePower.POWER_ID);
        AbstractPower hate = this.target.getPower(HatePower.POWER_ID);
        int hopeAmount = 0;
        int hateAmount = 0;
        if (hope != null) {
            hopeAmount = hope.amount;
        }
        if (hate != null) {
            hateAmount = hate.amount;
        }
        if (hate != null && hopeAmount > hateAmount) {
            addToTop(new ApplyPowerAction(this.target, this.target, new HopePower(this.target, 1), 1));
            if (hate.amount <= 1)
                addToTop(new RemoveSpecificPowerAction(this.target, this.target, HatePower.POWER_ID));
            else
                addToTop(new ReducePowerAction(this.target, this.target, HatePower.POWER_ID, 1));
        }
        if (hope != null && hateAmount > hopeAmount) {
            addToTop(new ApplyPowerAction(this.target, this.target, new HatePower(this.target, 1), 1));
            if (hope.amount <= 1)
                addToTop(new RemoveSpecificPowerAction(this.target, this.target, HopePower.POWER_ID));
            else
                addToTop(new ReducePowerAction(this.target, this.target, HopePower.POWER_ID, 1));
        }

        this.isDone = true;
    }
}
